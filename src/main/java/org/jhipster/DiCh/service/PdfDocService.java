package org.jhipster.dich.service;

import com.itextpdf.kernel.pdf.*;
import com.itextpdf.kernel.pdf.PdfPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Service for processing PDFs.
 * <p>
 *
 */
@Service
public class PdfDocService {

    private final Logger log = LoggerFactory.getLogger(PdfDocService.class);

    @Value("${collections.location}")
    private String SRC;

    public void ProcessPdf(String file_name) throws Exception {
        log.debug("Try to process PDF file: {} at location: {}", file_name, SRC);
        PdfDocument srcDoc = new PdfDocument(new PdfReader(SRC + file_name));
        PdfPage page = srcDoc.getFirstPage();
        PdfResources res = page.getResources();
        String pageLabels[] = srcDoc.getPageLabels();
        Integer n = srcDoc.getNumberOfPages();
        srcDoc.close();
    }

    public int getPageCount(String file_name) throws Exception {
        log.debug("Try to get total pages number of PDF file: {} at location: {}", file_name, SRC);
        PdfDocument srcDoc = new PdfDocument(new PdfReader(SRC + file_name));
        int retValue = srcDoc.getNumberOfPages();
        srcDoc.close();
        return retValue;
    }
}
