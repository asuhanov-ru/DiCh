package org.jhipster.dich.service;

import static net.sourceforge.lept4j.Leptonica1.pixRead;

import com.ochafik.lang.jnaerator.runtime.NativeSize;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;
import java.io.File;
import java.nio.ByteBuffer;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import net.sourceforge.lept4j.Leptonica;
import net.sourceforge.lept4j.Pix;
import net.sourceforge.tess4j.ITessAPI;
import net.sourceforge.tess4j.TessAPI;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.jetbrains.annotations.NotNull;
import org.jhipster.dich.domain.Media;
import org.jhipster.dich.domain.PageImage;
import org.jhipster.dich.repository.MediaRepository;
import org.jhipster.dich.repository.PageImageRepository;
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

    private final String testResourcesDataPath = "src/test/resources/test-data";

    private final PdfDocService pdfDocService;

    private final MediaRepository mediaRepository;

    public OCRService(
        PageImageRepository pageImageRepository,
        OcrTasksQueryService ocrTasksQueryService,
        OcrTasksService ocrTasksService,
        PdfDocService pdfDocService,
        MediaRepository mediaRepository
    ) {
        this.pageImageRepository = pageImageRepository;
        this.ocrTasksQueryService = ocrTasksQueryService;
        this.ocrTasksService = ocrTasksService;
        this.pdfDocService = pdfDocService;
        this.mediaRepository = mediaRepository;
    }

    @Value("${collections.location}")
    private String SRC;

    @Value("${tessdata.location}")
    private String tessdata;

    public void doOCR(@NotNull OcrTasksDTO ocrTask) throws Exception {
        Optional<Media> media = mediaRepository.findOneWithEagerRelationships(ocrTask.getMediaId());
        Optional<PageImageTransferDto> dto = pdfDocService.getPageImage(
            media.map(el -> el.getFileName()).orElse(""),
            ocrTask.getPageNumber()
        );
        byte[] imageData = dto.map(el -> el.getImage()).orElse(null);
        ByteBuffer buf = ByteBuffer.wrap(imageData);

        Leptonica leptInstance = Leptonica.INSTANCE;
        Pix pixs = leptInstance.pixReadMem(buf, new NativeSize(imageData.length));
        //Tesseract instance = new Tesseract();
        //instance.setDatapath(tessdata);
        //instance.setLanguage("lat");
        //String result = instance.doOCR(image);
        TessAPI api = TessAPI.INSTANCE;
        TessAPI.TessBaseAPI handle = api.TessBaseAPICreate();
        api.TessBaseAPIInit3(handle, tessdata, "lat");
        api.TessBaseAPISetImage2(handle, pixs);
        api.TessBaseAPISetPageSegMode(handle, ITessAPI.TessPageSegMode.PSM_AUTO_OSD);
        Pointer utf8Text = api.TessBaseAPIGetUTF8Text(handle);
        String result = utf8Text.getString(0);
        log.debug("OCR page: {} from media: {} {}", ocrTask.getPageNumber(), ocrTask.getMediaId(), result);
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
                doOCR(ocrTask);
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
