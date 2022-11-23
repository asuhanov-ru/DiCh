package org.jhipster.dich.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jhipster.dich.repository.PageLayoutRepository;
import org.jhipster.dich.service.PageLayoutQueryService;
import org.jhipster.dich.service.PageLayoutService;
import org.jhipster.dich.service.criteria.PageLayoutCriteria;
import org.jhipster.dich.service.dto.PageLayoutDTO;
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
 * REST controller for managing {@link org.jhipster.dich.domain.PageLayout}.
 */
@RestController
@RequestMapping("/api")
public class PageLayoutResource {

    private final Logger log = LoggerFactory.getLogger(PageLayoutResource.class);

    private static final String ENTITY_NAME = "pageLayout";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PageLayoutService pageLayoutService;

    private final PageLayoutRepository pageLayoutRepository;

    private final PageLayoutQueryService pageLayoutQueryService;

    public PageLayoutResource(
        PageLayoutService pageLayoutService,
        PageLayoutRepository pageLayoutRepository,
        PageLayoutQueryService pageLayoutQueryService
    ) {
        this.pageLayoutService = pageLayoutService;
        this.pageLayoutRepository = pageLayoutRepository;
        this.pageLayoutQueryService = pageLayoutQueryService;
    }

    /**
     * {@code POST  /page-layouts} : Create a new pageLayout.
     *
     * @param pageLayoutDTO the pageLayoutDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pageLayoutDTO, or with status {@code 400 (Bad Request)} if the pageLayout has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/page-layouts")
    public ResponseEntity<PageLayoutDTO> createPageLayout(@RequestBody PageLayoutDTO pageLayoutDTO) throws URISyntaxException {
        log.debug("REST request to save PageLayout : {}", pageLayoutDTO);
        if (pageLayoutDTO.getId() != null) {
            throw new BadRequestAlertException("A new pageLayout cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PageLayoutDTO result = pageLayoutService.save(pageLayoutDTO);
        return ResponseEntity
            .created(new URI("/api/page-layouts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /page-layouts/:id} : Updates an existing pageLayout.
     *
     * @param id the id of the pageLayoutDTO to save.
     * @param pageLayoutDTO the pageLayoutDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pageLayoutDTO,
     * or with status {@code 400 (Bad Request)} if the pageLayoutDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pageLayoutDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/page-layouts/{id}")
    public ResponseEntity<PageLayoutDTO> updatePageLayout(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PageLayoutDTO pageLayoutDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PageLayout : {}, {}", id, pageLayoutDTO);
        if (pageLayoutDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pageLayoutDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pageLayoutRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PageLayoutDTO result = pageLayoutService.update(pageLayoutDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pageLayoutDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /page-layouts/:id} : Partial updates given fields of an existing pageLayout, field will ignore if it is null
     *
     * @param id the id of the pageLayoutDTO to save.
     * @param pageLayoutDTO the pageLayoutDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pageLayoutDTO,
     * or with status {@code 400 (Bad Request)} if the pageLayoutDTO is not valid,
     * or with status {@code 404 (Not Found)} if the pageLayoutDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the pageLayoutDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/page-layouts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PageLayoutDTO> partialUpdatePageLayout(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PageLayoutDTO pageLayoutDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PageLayout partially : {}, {}", id, pageLayoutDTO);
        if (pageLayoutDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pageLayoutDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pageLayoutRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PageLayoutDTO> result = pageLayoutService.partialUpdate(pageLayoutDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pageLayoutDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /page-layouts} : get all the pageLayouts.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pageLayouts in body.
     */
    @GetMapping("/page-layouts")
    public ResponseEntity<List<PageLayoutDTO>> getAllPageLayouts(
        PageLayoutCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get PageLayouts by criteria: {}", criteria);
        Page<PageLayoutDTO> page = pageLayoutQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /page-layouts/count} : count all the pageLayouts.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/page-layouts/count")
    public ResponseEntity<Long> countPageLayouts(PageLayoutCriteria criteria) {
        log.debug("REST request to count PageLayouts by criteria: {}", criteria);
        return ResponseEntity.ok().body(pageLayoutQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /page-layouts/:id} : get the "id" pageLayout.
     *
     * @param id the id of the pageLayoutDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pageLayoutDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/page-layouts/{id}")
    public ResponseEntity<PageLayoutDTO> getPageLayout(@PathVariable Long id) {
        log.debug("REST request to get PageLayout : {}", id);
        Optional<PageLayoutDTO> pageLayoutDTO = pageLayoutService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pageLayoutDTO);
    }

    /**
     * {@code DELETE  /page-layouts/:id} : delete the "id" pageLayout.
     *
     * @param id the id of the pageLayoutDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/page-layouts/{id}")
    public ResponseEntity<Void> deletePageLayout(@PathVariable Long id) {
        log.debug("REST request to delete PageLayout : {}", id);
        pageLayoutService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
