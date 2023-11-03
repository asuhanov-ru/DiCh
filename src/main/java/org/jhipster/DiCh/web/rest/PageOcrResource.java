package org.jhipster.dich.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.jhipster.dich.domain.PageImage;
import org.jhipster.dich.domain.PageWord;
import org.jhipster.dich.repository.PageImageRepository;
import org.jhipster.dich.repository.PageTextRepository;
import org.jhipster.dich.repository.PageWordRepository;
import org.jhipster.dich.service.*;
import org.jhipster.dich.service.criteria.*;
import org.jhipster.dich.service.dto.*;
import org.jhipster.dich.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.jhipster.dich.domain.PageText}.
 */
@RestController
@RequestMapping("/api")
public class PageOcrResource {

    private static final String ENTITY_NAME = "page";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final Logger log = LoggerFactory.getLogger(PageOcrResource.class);

    private final PageWordQueryService pageWordQueryService;
    private final PageLayoutQueryService pageLayoutQueryService;

    private final TextBlockQueryService textBlockQueryService;

    private final PdfDocService pdfDocService;

    private final OCRService ocrService;

    public PageOcrResource(
        PageWordQueryService pageWordQueryService,
        PageLayoutQueryService pageLayoutQueryService,
        TextBlockQueryService textBlockQueryService,
        PdfDocService pdfDocService,
        OCRService ocrService
    ) {
        this.pageWordQueryService = pageWordQueryService;
        this.pageLayoutQueryService = pageLayoutQueryService;
        this.textBlockQueryService = textBlockQueryService;
        this.pdfDocService = pdfDocService;
        this.ocrService = ocrService;
    }

    /**
     * {@code GET  /v2/page-ocr/} : get page ocr.
     *
     * @param criteria the criteria of the pageWord to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the PageWord, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/v2/page-ocr")
    public ResponseEntity<List<PageWordDTO>> getPageWordsList(PageWordCriteria criteria) {
        Optional<List<PageWordDTO>> pageWords = Optional.ofNullable(pageWordQueryService.findByCriteria(criteria));

        return ResponseUtil.wrapOrNotFound(pageWords);
    }

    @PostMapping("/v2/page-ocr")
    public ResponseEntity<Void> doOcr(PageWordCriteria criteria) {
        OcrTasksDTO dto = new OcrTasksDTO();

        LongFilter mediaIdFilter = criteria.getMediaId();
        IntegerFilter pageNumberFilter = criteria.getPageNumber();

        long mediaId = mediaIdFilter.getEquals();
        int pageNumber = pageNumberFilter.getEquals();

        dto.setMediaId(mediaId);
        dto.setPageNumber(pageNumber);

        try {
            ocrService.doOCR(dto, ZonedDateTime.now());
        } catch (Throwable e) {
            log.debug("OCR filed with message {}", e.getMessage());
        }

        return ResponseEntity
            .noContent()
            .headers(
                HeaderUtil.createAlert(
                    applicationName,
                    "A new OCR was created for mediaId " + String.valueOf(mediaId) + " page " + String.valueOf(pageNumber),
                    ""
                )
            )
            .build();
    }

    @GetMapping("/v2/page-layout")
    public ResponseEntity<List<PageLayoutDTO>> getPageLayoutList(PageLayoutCriteria criteria) {
        Optional<List<PageLayoutDTO>> pageLayout = Optional.ofNullable(pageLayoutQueryService.findByCriteria(criteria));

        return ResponseUtil.wrapOrNotFound(pageLayout);
    }

    @GetMapping("/v2/page-layout-map")
    public ResponseEntity<Map<UUID, PageLayoutDTO>> getPageLayoutMap(PageLayoutCriteria criteria) {
        Optional<Map<UUID, PageLayoutDTO>> pageLayout = Optional.ofNullable(
            pageLayoutQueryService
                .findByCriteria(criteria)
                .stream()
                .collect(Collectors.toMap(PageLayoutDTO::getItemGUID, Function.identity()))
        );

        return ResponseUtil.wrapOrNotFound(pageLayout);
    }

    @GetMapping("/v2/page-text-block")
    public ResponseEntity<List<TextBlockDTO>> getPageTextBlocksList(TextBlockCriteria criteria) {
        // TO-DO replace it with ordered query
        Optional<List<TextBlockDTO>> textBlocks = Optional.ofNullable(
            textBlockQueryService
                .findByCriteria(criteria)
                .stream()
                .sorted(Comparator.comparing(TextBlockDTO::getBlockIndex))
                .collect(Collectors.toList())
        );
        return ResponseUtil.wrapOrNotFound(textBlocks);
    }

    @GetMapping("/v2/pdf-outline/{mediaId}")
    public ResponseEntity<PdfOutlineTreeNodeDto> getPdfOutline(@PathVariable Long mediaId) {
        Optional<PdfOutlineTreeNodeDto> outlines = Optional.empty();

        try {
            outlines = Optional.of(pdfDocService.getOutline(mediaId));
        } catch (Exception e) {
            log.debug("Error {}", e);
        }
        return ResponseUtil.wrapOrNotFound(outlines);
    }
}
