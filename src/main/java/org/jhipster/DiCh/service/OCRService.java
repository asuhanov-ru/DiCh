package org.jhipster.dich.service;

import java.io.File;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OCRService {

    private final Logger log = LoggerFactory.getLogger(PdfDocService.class);

    @Value("${collections.location}")
    private String SRC;

    @Value("${tessdata.location}")
    private String tessdata;

    public void doOCR() throws TesseractException {
        String file_name = SRC + "29.jpg";
        File image = new File(file_name);
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath(tessdata);
        tesseract.setLanguage("lat");
        tesseract.setPageSegMode(1);
        tesseract.setOcrEngineMode(1);
        String result = tesseract.doOCR(image);
        log.debug("Try to OCR file: {} result: {}", file_name, result);
    }
}
