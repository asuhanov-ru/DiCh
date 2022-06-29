package org.jhipster.dich.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jhipster.dich.repository.PageTextRepository;
import org.jhipster.dich.service.PageTextQueryService;
import org.jhipster.dich.service.PageTextService;
import org.jhipster.dich.service.criteria.PageTextCriteria;
import org.jhipster.dich.service.dto.PageTextDTO;
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
 * REST controller for managing {@link org.jhipster.dich.domain.PageText}.
 */
@RestController
@RequestMapping("/api")
public class PageTextResource {

    private final Logger log = LoggerFactory.getLogger(PageTextResource.class);

    private static final String ENTITY_NAME = "pageText";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PageTextService pageTextService;

    private final PageTextRepository pageTextRepository;

    private final PageTextQueryService pageTextQueryService;

    public PageTextResource(
        PageTextService pageTextService,
        PageTextRepository pageTextRepository,
        PageTextQueryService pageTextQueryService
    ) {
        this.pageTextService = pageTextService;
        this.pageTextRepository = pageTextRepository;
        this.pageTextQueryService = pageTextQueryService;
    }

    /**
     * {@code POST  /page-texts} : Create a new pageText.
     *
     * @param pageTextDTO the pageTextDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pageTextDTO, or with status {@code 400 (Bad Request)} if the pageText has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/page-texts")
    public ResponseEntity<PageTextDTO> createPageText(@RequestBody PageTextDTO pageTextDTO) throws URISyntaxException {
        log.debug("REST request to save PageText : {}", pageTextDTO);
        if (pageTextDTO.getId() != null) {
            throw new BadRequestAlertException("A new pageText cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PageTextDTO result = pageTextService.save(pageTextDTO);
        return ResponseEntity
            .created(new URI("/api/page-texts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /page-texts/:id} : Updates an existing pageText.
     *
     * @param id the id of the pageTextDTO to save.
     * @param pageTextDTO the pageTextDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pageTextDTO,
     * or with status {@code 400 (Bad Request)} if the pageTextDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pageTextDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/page-texts/{id}")
    public ResponseEntity<PageTextDTO> updatePageText(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PageTextDTO pageTextDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PageText : {}, {}", id, pageTextDTO);
        if (pageTextDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pageTextDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pageTextRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PageTextDTO result = pageTextService.update(pageTextDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pageTextDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /page-texts/:id} : Partial updates given fields of an existing pageText, field will ignore if it is null
     *
     * @param id the id of the pageTextDTO to save.
     * @param pageTextDTO the pageTextDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pageTextDTO,
     * or with status {@code 400 (Bad Request)} if the pageTextDTO is not valid,
     * or with status {@code 404 (Not Found)} if the pageTextDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the pageTextDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/page-texts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PageTextDTO> partialUpdatePageText(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PageTextDTO pageTextDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PageText partially : {}, {}", id, pageTextDTO);
        if (pageTextDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pageTextDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pageTextRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PageTextDTO> result = pageTextService.partialUpdate(pageTextDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pageTextDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /page-texts} : get all the pageTexts.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pageTexts in body.
     */
    @GetMapping("/page-texts")
    public ResponseEntity<List<PageTextDTO>> getAllPageTexts(
        PageTextCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get PageTexts by criteria: {}", criteria);
        Page<PageTextDTO> page = pageTextQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /page-texts/count} : count all the pageTexts.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/page-texts/count")
    public ResponseEntity<Long> countPageTexts(PageTextCriteria criteria) {
        log.debug("REST request to count PageTexts by criteria: {}", criteria);
        return ResponseEntity.ok().body(pageTextQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /page-texts/:id} : get the "id" pageText.
     *
     * @param id the id of the pageTextDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pageTextDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/page-texts/{id}")
    public ResponseEntity<PageTextDTO> getPageText(@PathVariable Long id) {
        log.debug("REST request to get PageText : {}", id);
        Optional<PageTextDTO> pageTextDTO = pageTextService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pageTextDTO);
    }

    /**
     * {@code DELETE  /page-texts/:id} : delete the "id" pageText.
     *
     * @param id the id of the pageTextDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/page-texts/{id}")
    public ResponseEntity<Void> deletePageText(@PathVariable Long id) {
        log.debug("REST request to delete PageText : {}", id);
        pageTextService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
