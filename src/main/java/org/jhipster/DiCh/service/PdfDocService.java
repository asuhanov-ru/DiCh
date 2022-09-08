package org.jhipster.dich.service;

import com.itextpdf.kernel.pdf.*;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.xobject.PdfImageXObject;
import com.itextpdf.kernel.xmp.impl.Base64;
import java.io.File;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.apache.commons.io.FileUtils;
import org.jhipster.dich.service.dto.PageImageTransferDto;
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

    public Optional<PageImageTransferDto> getPageImage(String fileName, int pageNumber) throws Exception {
        log.debug("Try to get image of page {} of PDF file: {} at location: {}", pageNumber, fileName, SRC);

        PageImageTransferDto dto = new PageImageTransferDto();
        PdfDocument srcDoc = new PdfDocument(new PdfReader(SRC + fileName));
        PdfPage page = srcDoc.getPage(pageNumber);
        PdfResources resources = page.getResources();
        PdfDictionary dictionary = resources.getResource(PdfName.XObject);
        Set<PdfName> names = dictionary.keySet();
        Set<Map.Entry<PdfName, PdfObject>> namedObjects = dictionary.entrySet();
        namedObjects
            .iterator()
            .forEachRemaining(Entry -> {
                PdfObject obj = Entry.getValue();

                if (obj instanceof PdfStream) {
                    PdfStream stream = (PdfStream) obj;
                    PdfImageXObject pimg = new PdfImageXObject(stream);
                    dto.setImage(pimg.getImageBytes());

                    log.debug("Found PdfStream ");
                }
            });

        dto.setPageNumber(pageNumber);

        return Optional.of(dto);
    }
}
