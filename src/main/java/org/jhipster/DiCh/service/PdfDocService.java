package org.jhipster.dich.service;

import com.itextpdf.commons.utils.MessageFormatUtil;
import com.itextpdf.kernel.exceptions.KernelExceptionMessageConstant;
import com.itextpdf.kernel.exceptions.PdfException;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.kernel.pdf.PdfObject;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.navigation.PdfDestination;
import com.itextpdf.kernel.pdf.xobject.PdfImageXObject;
import java.util.*;
import org.jhipster.dich.domain.Media;
import org.jhipster.dich.repository.MediaRepository;
import org.jhipster.dich.service.dto.PageImageTransferDto;
import org.jhipster.dich.service.dto.PdfOutlineTreeNodeDto;
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

    private final MediaRepository mediaRepository;

    public PdfDocService(MediaRepository mediaRepository) {
        this.mediaRepository = mediaRepository;
    }

    private void addOutlineToPage(PdfOutlineTreeNodeDto outline, PdfDictionary item, PdfDocument doc) {
        outline.setPageNumber(-1);
        PdfObject outlineDestination = item.get(PdfName.Dest);
        if (outlineDestination != null) {
            PdfDestination destination = PdfDestination.makeDestination(outlineDestination);
            PdfDictionary pageObj = (PdfDictionary) destination.getDestinationPage(doc.getCatalog().getNameTree(PdfName.Dests));

            if (pageObj != null) outline.setPageNumber(doc.getPageNumber(pageObj));
        }
    }

    public void ProcessPdf(String file_name) throws Exception {
        PdfDocument srcDoc = new PdfDocument(new PdfReader(SRC + file_name));
        PdfPage page = srcDoc.getFirstPage();
        PdfResources res = page.getResources();
        String pageLabels[] = srcDoc.getPageLabels();
        Integer n = srcDoc.getNumberOfPages();
        srcDoc.close();
    }

    public PdfOutlineTreeNodeDto getOutline(Long mediaId) throws Exception {
        Optional<Media> media = mediaRepository.findOneWithEagerRelationships(mediaId);
        String fileName = media.map(el -> el.getFileName()).orElse("");
        PdfDocument srcDoc = new PdfDocument(new PdfReader(SRC + fileName));

        PdfDictionary outlineRoot = srcDoc.getCatalog().getPdfObject().getAsDictionary(PdfName.Outlines);
        PdfDictionary current = outlineRoot.getAsDictionary(PdfName.First);

        PdfOutlineTreeNodeDto outlines = new PdfOutlineTreeNodeDto(null, outlineRoot.getIndirectReference().getObjNumber(), "ROOT");
        PdfOutlineTreeNodeDto parentOutline = outlines;

        Map<PdfOutlineTreeNodeDto, PdfDictionary> nextUnprocessedChildForParentMap = new HashMap<>();
        Set<PdfDictionary> alreadyVisitedOutlinesSet = new HashSet<>();

        while (current != null) {
            PdfDictionary parent = current.getAsDictionary(PdfName.Parent);

            if (null == parent) {
                throw new PdfException(
                    MessageFormatUtil.format(
                        KernelExceptionMessageConstant.CORRUPTED_OUTLINE_NO_PARENT_ENTRY,
                        current.getIndirectReference()
                    )
                );
            }

            PdfString title = current.getAsString(PdfName.Title);
            if (null == title) {
                throw new PdfException(
                    MessageFormatUtil.format(
                        KernelExceptionMessageConstant.CORRUPTED_OUTLINE_NO_TITLE_ENTRY,
                        current.getIndirectReference()
                    )
                );
            }

            PdfOutlineTreeNodeDto currentOutline = new PdfOutlineTreeNodeDto(
                parentOutline,
                current.getIndirectReference().getObjNumber(),
                title.toUnicodeString()
            );

            alreadyVisitedOutlinesSet.add(current);
            addOutlineToPage(currentOutline, current, srcDoc);
            if (parentOutline.getChildren() == null) parentOutline.setChildren(new ArrayList<PdfOutlineTreeNodeDto>());
            parentOutline.getChildren().add(currentOutline);

            PdfDictionary first = current.getAsDictionary(PdfName.First);
            PdfDictionary next = current.getAsDictionary(PdfName.Next);

            if (first != null) {
                if (alreadyVisitedOutlinesSet.contains(first)) {
                    throw new PdfException(
                        MessageFormatUtil.format(KernelExceptionMessageConstant.CORRUPTED_OUTLINE_DICTIONARY_HAS_INFINITE_LOOP, first)
                    );
                }
                // Down in hierarchy; when returning up, process `next`.
                nextUnprocessedChildForParentMap.put(parentOutline, next);
                parentOutline = currentOutline;
                current = first;
            } else if (next != null) {
                if (alreadyVisitedOutlinesSet.contains(next)) {
                    throw new PdfException(
                        MessageFormatUtil.format(KernelExceptionMessageConstant.CORRUPTED_OUTLINE_DICTIONARY_HAS_INFINITE_LOOP, next)
                    );
                }
                // Next sibling in hierarchy
                current = next;
            } else {
                // Up in hierarchy using 'nextUnprocessedChildForParentMap'.
                current = null;
                while (current == null && parentOutline != null) {
                    parentOutline = parentOutline.getParent();
                    if (parentOutline != null) {
                        current = nextUnprocessedChildForParentMap.get(parentOutline);
                    }
                }
            }
        }

        srcDoc.close();
        return (outlines);
    }

    public int getPageCount(String file_name) throws Exception {
        log.debug("Try to get total pages number of PDF file: {} at location: {}", file_name, SRC);
        PdfDocument srcDoc = new PdfDocument(new PdfReader(SRC + file_name));
        int retValue = srcDoc.getNumberOfPages();
        srcDoc.close();
        return retValue;
    }

    public Optional<PageImageTransferDto> getPageImage(String fileName, int pageNumber) throws Exception {
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
                }
            });

        dto.setPageNumber(pageNumber);

        return Optional.of(dto);
    }
}
