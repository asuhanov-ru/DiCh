package org.jhipster.dich.service;

import com.ochafik.lang.jnaerator.runtime.NativeSize;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import net.sourceforge.lept4j.Leptonica;
import net.sourceforge.lept4j.Pix;
import net.sourceforge.tess4j.ITessAPI;
import net.sourceforge.tess4j.ITessAPI.*;
import net.sourceforge.tess4j.TessAPI;
import org.jetbrains.annotations.NotNull;
import org.jhipster.dich.domain.*;
import org.jhipster.dich.repository.*;
import org.jhipster.dich.service.criteria.OcrTasksCriteria;
import org.jhipster.dich.service.dto.OcrTasksDTO;
import org.jhipster.dich.service.dto.PageImageTransferDto;
import org.jhipster.dich.service.dto.PageLayoutDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.filter.StringFilter;

/*

    If Tesseract API instance fail to initialize see
    AppData\Local\Temp\lept4j\win32-x86-64>
    AppData\Local\Temp\tess4j\win32-x86-64>
    both must contain same couple of dlls

 */
@Service
@Transactional
public class OCRService {

    private final Logger log = LoggerFactory.getLogger(OCRService.class);

    private final PageImageRepository pageImageRepository;

    private final OcrTasksQueryService ocrTasksQueryService;

    private final OcrTasksService ocrTasksService;

    private final PdfDocService pdfDocService;

    private final MediaRepository mediaRepository;

    private final PageWordRepository pageWordRepository;

    private final PageLayoutRepository pageLayoutRepository;

    private final TextBlockRepository textBlockRepository;

    public OCRService(
        PageImageRepository pageImageRepository,
        OcrTasksQueryService ocrTasksQueryService,
        OcrTasksService ocrTasksService,
        PdfDocService pdfDocService,
        MediaRepository mediaRepository,
        PageWordRepository pageWordRepository,
        PageLayoutRepository pageLayoutRepository,
        TextBlockRepository textBlockRepository
    ) {
        this.pageImageRepository = pageImageRepository;
        this.ocrTasksQueryService = ocrTasksQueryService;
        this.ocrTasksService = ocrTasksService;
        this.pdfDocService = pdfDocService;
        this.mediaRepository = mediaRepository;
        this.pageWordRepository = pageWordRepository;
        this.pageLayoutRepository = pageLayoutRepository;
        this.textBlockRepository = textBlockRepository;
    }

    @Value("${collections.location}")
    private String SRC;

    @Value("${tessdata.location}")
    private String tessdata;

    public void doOCR(@NotNull OcrTasksDTO ocrTask, ZonedDateTime version) throws Exception {
        Optional<Media> media = mediaRepository.findOneWithEagerRelationships(ocrTask.getMediaId());
        Optional<PageImageTransferDto> dto = pdfDocService.getPageImage(
            media.map(el -> el.getFileName()).orElse(""),
            ocrTask.getPageNumber()
        );
        byte[] imageData = dto.map(el -> el.getImage()).orElse(null);
        ByteBuffer buf = ByteBuffer.wrap(imageData);

        Leptonica leptInstance = Leptonica.INSTANCE;
        Pix pixs = leptInstance.pixReadMem(buf, new NativeSize(imageData.length));
        TessAPI api = TessAPI.INSTANCE;
        TessAPI.TessBaseAPI handle = api.TessBaseAPICreate();
        api.TessBaseAPIInit3(handle, tessdata, "lat");
        api.TessBaseAPISetImage2(handle, pixs);
        api.TessBaseAPISetPageSegMode(handle, ITessAPI.TessPageSegMode.PSM_AUTO_OSD);

        ITessAPI.ETEXT_DESC monitor = new ETEXT_DESC();
        TimeVal timeout = new TimeVal();
        timeout.tv_sec = new NativeLong(0L); // time > 0 causes blank output
        monitor.end_time = timeout;

        api.TessBaseAPIRecognize(handle, monitor);

        TessResultIterator ri = api.TessBaseAPIGetIterator(handle);
        TessPageIterator pi = api.TessResultIteratorGetPageIterator(ri);
        api.TessPageIteratorBegin(pi);

        int level = TessPageIteratorLevel.RIL_WORD;
        List<PageWord> pageWords = new ArrayList<>();
        List<PageLayout> pageLayouts = new ArrayList<>();
        List<TextBlock> textBlocks = new ArrayList<>();
        long idx = 0;

        IntBuffer leftB = IntBuffer.allocate(1);
        IntBuffer topB = IntBuffer.allocate(1);
        IntBuffer rightB = IntBuffer.allocate(1);
        IntBuffer bottomB = IntBuffer.allocate(1);

        UUID currentTextLineUUID = null;
        UUID currentBlockUUID = null;
        UUID currentParaUUID = null;
        UUID currentTextBlockUUID = null;

        // int height = image.getHeight();
        do {
            idx++;
            Pointer ptr = api.TessResultIteratorGetUTF8Text(ri, level);
            String word = ptr.getString(0);
            api.TessDeleteText(ptr);

            if (api.TessPageIteratorIsAtBeginningOf(pi, TessPageIteratorLevel.RIL_BLOCK) == 1) {
                PageLayout pageLayoutDTO = new PageLayout();

                currentBlockUUID = UUID.randomUUID();

                pageLayoutDTO.setMediaId(ocrTask.getMediaId());
                pageLayoutDTO.setPageNumber(ocrTask.getPageNumber());
                pageLayoutDTO.setIterator_level("RIL_BLOCK");
                pageLayoutDTO.setItemGUID(currentBlockUUID);

                api.TessPageIteratorBoundingBox(pi, TessPageIteratorLevel.RIL_BLOCK, leftB, topB, rightB, bottomB);
                int left = leftB.get(0);
                int top = topB.get(0);
                int right = rightB.get(0);
                int bottom = bottomB.get(0);

                pageLayoutDTO.setRect_top(BigDecimal.valueOf(top));
                pageLayoutDTO.setRect_bottom(BigDecimal.valueOf(bottom));
                pageLayoutDTO.setRect_left(BigDecimal.valueOf(left));
                pageLayoutDTO.setRect_right(BigDecimal.valueOf(right));
                pageLayouts.add(pageLayoutDTO);
            }

            if (api.TessPageIteratorIsAtBeginningOf(pi, TessPageIteratorLevel.RIL_PARA) == 1) {
                PageLayout pageLayoutDTO = new PageLayout();
                currentParaUUID = UUID.randomUUID();

                pageLayoutDTO.setMediaId(ocrTask.getMediaId());
                pageLayoutDTO.setPageNumber(ocrTask.getPageNumber());
                pageLayoutDTO.setIterator_level("RIL_PARA");
                pageLayoutDTO.setItemGUID(currentParaUUID);
                pageLayoutDTO.setParentGUID(currentBlockUUID);

                api.TessPageIteratorBoundingBox(pi, TessPageIteratorLevel.RIL_PARA, leftB, topB, rightB, bottomB);
                int left = leftB.get(0);
                int top = topB.get(0);
                int right = rightB.get(0);
                int bottom = bottomB.get(0);

                pageLayoutDTO.setRect_top(BigDecimal.valueOf(top));
                pageLayoutDTO.setRect_bottom(BigDecimal.valueOf(bottom));
                pageLayoutDTO.setRect_left(BigDecimal.valueOf(left));
                pageLayoutDTO.setRect_right(BigDecimal.valueOf(right));

                pageLayouts.add(pageLayoutDTO);
            }

            if (api.TessPageIteratorIsAtBeginningOf(pi, TessPageIteratorLevel.RIL_TEXTLINE) == 1) {
                PageLayout pageLayoutDTO = new PageLayout();
                TextBlock textBlock = new TextBlock();

                currentTextLineUUID = UUID.randomUUID();
                currentTextBlockUUID = UUID.randomUUID();

                pageLayoutDTO.setMediaId(ocrTask.getMediaId());
                pageLayoutDTO.setPageNumber(ocrTask.getPageNumber());
                pageLayoutDTO.setIterator_level("RIL_TEXTLINE");
                pageLayoutDTO.setItemGUID(currentTextLineUUID);
                pageLayoutDTO.setParentGUID(currentParaUUID);

                api.TessPageIteratorBoundingBox(pi, TessPageIteratorLevel.RIL_TEXTLINE, leftB, topB, rightB, bottomB);

                int left = leftB.get(0);
                int top = topB.get(0);
                int right = rightB.get(0);
                int bottom = bottomB.get(0);

                pageLayoutDTO.setRect_top(BigDecimal.valueOf(top));
                pageLayoutDTO.setRect_bottom(BigDecimal.valueOf(bottom));
                pageLayoutDTO.setRect_left(BigDecimal.valueOf(left));
                pageLayoutDTO.setRect_right(BigDecimal.valueOf(right));
                pageLayouts.add(pageLayoutDTO);

                textBlock.setMediaId(ocrTask.getMediaId());
                textBlock.setPageNumber(ocrTask.getPageNumber());
                textBlock.setBlockUUID(currentTextBlockUUID);
                textBlock.setBlockIndex(textBlocks.size());
                textBlocks.add(textBlock);
            }

            api.TessPageIteratorBoundingBox(pi, level, leftB, topB, rightB, bottomB);
            int left = leftB.get(0);
            int top = topB.get(0);
            int right = rightB.get(0);
            int bottom = bottomB.get(0);

            PageWord pageWord = new PageWord();
            pageWord.setMediaId(ocrTask.getMediaId());
            pageWord.setPageNumber(ocrTask.getPageNumber());
            pageWord.sets_word(word);
            pageWord.setn_idx(idx);
            pageWord.setn_left(Long.valueOf(left));
            pageWord.setn_top(Long.valueOf(top));
            pageWord.setn_heigth(Long.valueOf(bottom - top));
            pageWord.setn_width((Long.valueOf(right - left)));
            pageWord.setVersion(version);
            pageWord.setTextLineUUID(currentTextLineUUID);
            pageWord.setTextBlockUUID(currentTextBlockUUID);
            pageWords.add(pageWord);
        } while (api.TessPageIteratorNext(pi, level) == 1);

        // Delete existing OCR
        pageWordRepository.deleteByMediaIdAndPageNumber(ocrTask.getMediaId(), ocrTask.getPageNumber());
        // Insert new OCR
        pageWordRepository.saveAll(pageWords);
        pageLayoutRepository.saveAll(pageLayouts);
        textBlockRepository.saveAll(textBlocks);

        api.TessResultIteratorDelete(ri);

        //release API
        if (handle != null) api.TessBaseAPIDelete(handle);
        //release Pix resource
        PointerByReference pRef = new PointerByReference();
        pRef.setValue(pixs.getPointer());
        leptInstance.pixDestroy(pRef);
    }

    @Transactional(readOnly = true)
    public Optional<PageImage> findOne(Long id) {
        log.debug("Request to get PageImage : {}", id);
        Optional<PageImage> pageImage = pageImageRepository.findById(id);
        return pageImage;
    }

    public void processOCRTasks() {
        OcrTasksCriteria criteria = new OcrTasksCriteria();
        StringFilter jobStatusFilter = new StringFilter();
        jobStatusFilter.setEquals("NEW");
        criteria.setJobStatus(jobStatusFilter);

        List<OcrTasksDTO> ocrTasks = ocrTasksQueryService.findByCriteria(criteria);
        if (ocrTasks.stream().count() > 0) {
            OcrTasksDTO ocrTask = ocrTasks.get(0);
            ocrTask.setJobStatus("PROCESSING");
            ocrTask.setStartTime(ZonedDateTime.now());
            ocrTasksService.partialUpdate(ocrTask);
            try {
                doOCR(ocrTask, ZonedDateTime.now());
                ocrTask.setJobStatus("PROCESSED");
                ocrTask.setStopTime(ZonedDateTime.now());
                ocrTasksService.partialUpdate(ocrTask);
            } catch (Throwable e) {
                log.debug("OCR filed with message {}", e.getMessage());

                ocrTask.setJobStatus("FAILED");
                ocrTask.setStopTime(ZonedDateTime.now());
                ocrTasksService.partialUpdate(ocrTask);
            }
        }
    }
}
