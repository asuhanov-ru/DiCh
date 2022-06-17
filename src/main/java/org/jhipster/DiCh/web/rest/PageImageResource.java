package org.jhipster.dich.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jhipster.dich.repository.PageImageRepository;
import org.jhipster.dich.service.PageImageQueryService;
import org.jhipster.dich.service.PageImageService;
import org.jhipster.dich.service.criteria.PageImageCriteria;
import org.jhipster.dich.service.dto.PageImageDTO;
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
public class PageImageResource {

    private final Logger log = LoggerFactory.getLogger(PageImageResource.class);

    private static final String ENTITY_NAME = "pageImage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PageImageService pageImageService;

    private final PageImageRepository pageImageRepository;

    private final PageImageQueryService pageImageQueryService;

    public PageImageResource(
        PageImageService pageImageService,
        PageImageRepository pageImageRepository,
        PageImageQueryService pageImageQueryService
    ) {
        this.pageImageService = pageImageService;
        this.pageImageRepository = pageImageRepository;
        this.pageImageQueryService = pageImageQueryService;
    }

    /**
     * {@code POST  /page-images} : Create a new pageImage.
     *
     * @param pageImageDTO the pageImageDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pageImageDTO, or with status {@code 400 (Bad Request)} if the pageImage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/page-images")
    public ResponseEntity<PageImageDTO> createPageImage(@RequestBody PageImageDTO pageImageDTO) throws URISyntaxException {
        log.debug("REST request to save PageImage : {}", pageImageDTO);
        if (pageImageDTO.getId() != null) {
            throw new BadRequestAlertException("A new pageImage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PageImageDTO result = pageImageService.save(pageImageDTO);
        return ResponseEntity
            .created(new URI("/api/page-images/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /page-images/:id} : Updates an existing pageImage.
     *
     * @param id the id of the pageImageDTO to save.
     * @param pageImageDTO the pageImageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pageImageDTO,
     * or with status {@code 400 (Bad Request)} if the pageImageDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pageImageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/page-images/{id}")
    public ResponseEntity<PageImageDTO> updatePageImage(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PageImageDTO pageImageDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PageImage : {}, {}", id, pageImageDTO);
        if (pageImageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pageImageDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pageImageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PageImageDTO result = pageImageService.update(pageImageDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pageImageDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /page-images/:id} : Partial updates given fields of an existing pageImage, field will ignore if it is null
     *
     * @param id the id of the pageImageDTO to save.
     * @param pageImageDTO the pageImageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pageImageDTO,
     * or with status {@code 400 (Bad Request)} if the pageImageDTO is not valid,
     * or with status {@code 404 (Not Found)} if the pageImageDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the pageImageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/page-images/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PageImageDTO> partialUpdatePageImage(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PageImageDTO pageImageDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PageImage partially : {}, {}", id, pageImageDTO);
        if (pageImageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pageImageDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pageImageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PageImageDTO> result = pageImageService.partialUpdate(pageImageDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pageImageDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /page-images} : get all the pageImages.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pageImages in body.
     */
    @GetMapping("/page-images")
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
     * {@code GET  /page-images/count} : count all the pageImages.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/page-images/count")
    public ResponseEntity<Long> countPageImages(PageImageCriteria criteria) {
        log.debug("REST request to count PageImages by criteria: {}", criteria);
        return ResponseEntity.ok().body(pageImageQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /page-images/:id} : get the "id" pageImage.
     *
     * @param id the id of the pageImageDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pageImageDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/page-images/{id}")
    public ResponseEntity<PageImageDTO> getPageImage(@PathVariable Long id) {
        log.debug("REST request to get PageImage : {}", id);
        Optional<PageImageDTO> pageImageDTO = pageImageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pageImageDTO);
    }

    /**
     * {@code DELETE  /page-images/:id} : delete the "id" pageImage.
     *
     * @param id the id of the pageImageDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/page-images/{id}")
    public ResponseEntity<Void> deletePageImage(@PathVariable Long id) {
        log.debug("REST request to delete PageImage : {}", id);
        pageImageService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
