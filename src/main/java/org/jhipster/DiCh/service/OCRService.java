package org.jhipster.dich.service;

import static net.sourceforge.lept4j.Leptonica1.pixRead;

import java.io.File;
import java.util.List;
import java.util.Optional;
import net.sourceforge.lept4j.Pix;
import net.sourceforge.tess4j.ITessAPI;
import net.sourceforge.tess4j.TessAPI;
import net.sourceforge.tess4j.TesseractException;
import org.jhipster.dich.domain.PageImage;
import org.jhipster.dich.repository.PageImageRepository;
import org.jhipster.dich.service.criteria.OcrTasksCriteria;
import org.jhipster.dich.service.dto.OcrTasksDTO;
import org.jhipster.dich.service.dto.PageImageDTO;
import org.jhipster.dich.service.mapper.PageImageMapper;
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

    public OCRService(PageImageRepository pageImageRepository, OcrTasksQueryService ocrTasksQueryService) {
        this.pageImageRepository = pageImageRepository;
        this.ocrTasksQueryService = ocrTasksQueryService;
    }

    @Value("${collections.location}")
    private String SRC;

    @Value("${tessdata.location}")
    private String tessdata;

    public void doOCR() throws TesseractException {
        String file_name = SRC + "29.jpg";
        File image = new File(file_name);
        Pix pixs = pixRead(file_name);
        TessAPI api = TessAPI.INSTANCE;
        ITessAPI.TessBaseAPI handle = api.TessBaseAPICreate();
        api.TessBaseAPIInit2(handle, tessdata, "lat", 1);
        api.TessBaseAPISetImage2(handle, pixs);
        //log.debug("Try to OCR file: {} result: {}", file_name, result);
        if (handle != null) api.TessBaseAPIDelete(handle);
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

        log.debug("Find tasks {}", ocrTasks);
    }
}
