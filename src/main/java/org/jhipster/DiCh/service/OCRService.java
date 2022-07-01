package org.jhipster.dich.service;

import static net.sourceforge.lept4j.Leptonica1.pixRead;

import java.io.File;
import net.sourceforge.lept4j.Pix;
import net.sourceforge.tess4j.ITessAPI;
import net.sourceforge.tess4j.TessAPI;
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
        Pix pixs = pixRead(file_name);
        TessAPI api = TessAPI.INSTANCE;
        ITessAPI.TessBaseAPI handle = api.TessBaseAPICreate();
        api.TessBaseAPIInit2(handle, tessdata, "lat", 1);
        api.TessBaseAPISetImage2(handle, pixs);
        //log.debug("Try to OCR file: {} result: {}", file_name, result);
        if (handle != null) api.TessBaseAPIDelete(handle);
    }
}
