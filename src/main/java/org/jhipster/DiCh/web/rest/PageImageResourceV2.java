package org.jhipster.dich.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jhipster.dich.domain.Media;
import org.jhipster.dich.repository.MediaRepository;
import org.jhipster.dich.repository.PageImageRepository;
import org.jhipster.dich.service.PageImageQueryService;
import org.jhipster.dich.service.PageImageService;
import org.jhipster.dich.service.PdfDocService;
import org.jhipster.dich.service.criteria.PageImageCriteria;
import org.jhipster.dich.service.dto.MediaDetailsDto;
import org.jhipster.dich.service.dto.PageImageDTO;
import org.jhipster.dich.service.dto.PageImageTransferDto;
import org.jhipster.dich.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.jhipster.dich.domain.PageImage}.
 */
@RestController
@RequestMapping("/api")
public class PageImageResourceV2 {

    private final Logger log = LoggerFactory.getLogger(PageImageResourceV2.class);

    private static final String ENTITY_NAME = "pageImage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PageImageService pageImageService;

    private final PageImageRepository pageImageRepository;

    private final PageImageQueryService pageImageQueryService;

    private final PdfDocService pdfDocService;

    private final MediaRepository mediaRepository;

    public PageImageResourceV2(
        PageImageService pageImageService,
        PageImageRepository pageImageRepository,
        PageImageQueryService pageImageQueryService,
        PdfDocService pdfDocService,
        MediaRepository mediaRepository
    ) {
        this.pageImageService = pageImageService;
        this.pageImageRepository = pageImageRepository;
        this.pageImageQueryService = pageImageQueryService;
        this.pdfDocService = pdfDocService;
        this.mediaRepository = mediaRepository;
    }

    /**
     * {@code GET  /page-images} : get all the pageImages.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pageImages in body.
     */
    @GetMapping("/v2/page-images")
    public ResponseEntity<List<PageImageDTO>> getAllPageImages(
        PageImageCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get PageImages by criteria: {}", criteria);
        Page<PageImageDTO> page = pageImageQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /V2/page-images/count} : count all the pageImages.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/v2/page-images/count")
    public ResponseEntity<Long> countPageImages(PageImageCriteria criteria) {
        log.debug("REST request to count PageImages by criteria: {}", criteria);

        return ResponseEntity.ok().body(pageImageQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /V2/page-images/:id} : get the "id" pageImage.
     *
     * @param id the id of the pageImageDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pageImageDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/v2/page-images/{id}")
    public ResponseEntity<PageImageTransferDto> getPageImage(@PathVariable Long id, @RequestParam int pageNumber) throws Exception {
        log.debug("REST request to get Image of page {} of media {}", pageNumber, id);
        Optional<Media> media = mediaRepository.findOneWithEagerRelationships(id);

        Optional<PageImageTransferDto> dto = pdfDocService.getPageImage(media.map(el -> el.getFileName()).orElse(""), pageNumber);

        return ResponseUtil.wrapOrNotFound(dto);
    }
}
