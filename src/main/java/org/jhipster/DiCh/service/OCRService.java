package org.jhipster.dich.service;

import com.ochafik.lang.jnaerator.runtime.NativeSize;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.sourceforge.lept4j.Leptonica;
import net.sourceforge.lept4j.Pix;
import net.sourceforge.tess4j.ITessAPI;
import net.sourceforge.tess4j.ITessAPI.*;
import net.sourceforge.tess4j.TessAPI;
import org.jetbrains.annotations.NotNull;
import org.jhipster.dich.domain.Media;
import org.jhipster.dich.domain.PageImage;
import org.jhipster.dich.domain.PageWord;
import org.jhipster.dich.repository.MediaRepository;
import org.jhipster.dich.repository.PageImageRepository;
import org.jhipster.dich.repository.PageWordRepository;
import org.jhipster.dich.service.criteria.OcrTasksCriteria;
import org.jhipster.dich.service.dto.OcrTasksDTO;
import org.jhipster.dich.service.dto.PageImageTransferDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.filter.StringFilter;

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

    public OCRService(
        PageImageRepository pageImageRepository,
        OcrTasksQueryService ocrTasksQueryService,
        OcrTasksService ocrTasksService,
        PdfDocService pdfDocService,
        MediaRepository mediaRepository,
        PageWordRepository pageWordRepository
    ) {
        this.pageImageRepository = pageImageRepository;
        this.ocrTasksQueryService = ocrTasksQueryService;
        this.ocrTasksService = ocrTasksService;
        this.pdfDocService = pdfDocService;
        this.mediaRepository = mediaRepository;
        this.pageWordRepository = pageWordRepository;
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
        //ProgressMonitor pmo = new ProgressMonitor(monitor);
        //pmo.start();
        api.TessBaseAPIRecognize(handle, monitor);
        //logger.info("Message: " + pmo.getMessage());

        TessResultIterator ri = api.TessBaseAPIGetIterator(handle);
        TessPageIterator pi = api.TessResultIteratorGetPageIterator(ri);
        api.TessPageIteratorBegin(pi);
        log.debug("Bounding boxes:\nchar(s) left top right bottom confidence font-attributes");
        int level = TessPageIteratorLevel.RIL_WORD;
        List<PageWord> pageWords = new ArrayList<>();
        long idx = 0;

        // int height = image.getHeight();
        do {
            idx++;
            Pointer ptr = api.TessResultIteratorGetUTF8Text(ri, level);
            String word = ptr.getString(0);
            api.TessDeleteText(ptr);
            float confidence = api.TessResultIteratorConfidence(ri, level);
            IntBuffer leftB = IntBuffer.allocate(1);
            IntBuffer topB = IntBuffer.allocate(1);
            IntBuffer rightB = IntBuffer.allocate(1);
            IntBuffer bottomB = IntBuffer.allocate(1);
            api.TessPageIteratorBoundingBox(pi, level, leftB, topB, rightB, bottomB);
            int left = leftB.get();
            int top = topB.get();
            int right = rightB.get();
            int bottom = bottomB.get();
            //System.out.print(String.format("%s %d %d %d %d %f", word, left, top, right, bottom, confidence));
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
            pageWords.add(pageWord);
        } while (api.TessPageIteratorNext(pi, level) == 1);
        pageWordRepository.saveAll(pageWords);
        //        api.TessPageIteratorDelete(pi);
        api.TessResultIteratorDelete(ri);

        //Pointer utf8Text = api.TessBaseAPIGetUTF8Text(handle);
        //String result = utf8Text.getString(0);

        //log.debug("OCR page: {} from media: {} {}", ocrTask.getPageNumber(), ocrTask.getMediaId(), pageWords);
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
            Optional<OcrTasksDTO> result = ocrTasksService.partialUpdate(ocrTask);
            log.debug("Start task {}", result);
            try {
                doOCR(ocrTask, ZonedDateTime.now());
                ocrTask.setJobStatus("PROCESSED");
                ocrTask.setStopTime(ZonedDateTime.now());
                result = ocrTasksService.partialUpdate(ocrTask);
                log.debug("Task {} done", result);
            } catch (Throwable e) {
                log.debug("OCR filed with message {}", e.getMessage());
            }
        }
    }
}
