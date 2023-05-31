package org.jhipster.dich.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jhipster.dich.repository.InlineStyleRangesRepository;
import org.jhipster.dich.service.InlineStyleRangesQueryService;
import org.jhipster.dich.service.InlineStyleRangesService;
import org.jhipster.dich.service.criteria.InlineStyleRangesCriteria;
import org.jhipster.dich.service.dto.InlineStyleRangesDTO;
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
 * REST controller for managing {@link org.jhipster.dich.domain.InlineStyleRanges}.
 */
@RestController
@RequestMapping("/api")
public class InlineStyleRangesResource {

    private final Logger log = LoggerFactory.getLogger(InlineStyleRangesResource.class);

    private static final String ENTITY_NAME = "inlineStyleRanges";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InlineStyleRangesService inlineStyleRangesService;

    private final InlineStyleRangesRepository inlineStyleRangesRepository;

    private final InlineStyleRangesQueryService inlineStyleRangesQueryService;

    public InlineStyleRangesResource(
        InlineStyleRangesService inlineStyleRangesService,
        InlineStyleRangesRepository inlineStyleRangesRepository,
        InlineStyleRangesQueryService inlineStyleRangesQueryService
    ) {
        this.inlineStyleRangesService = inlineStyleRangesService;
        this.inlineStyleRangesRepository = inlineStyleRangesRepository;
        this.inlineStyleRangesQueryService = inlineStyleRangesQueryService;
    }

    /**
     * {@code POST  /inline-style-ranges} : Create a new inlineStyleRanges.
     *
     * @param inlineStyleRangesDTO the inlineStyleRangesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new inlineStyleRangesDTO, or with status {@code 400 (Bad Request)} if the inlineStyleRanges has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/inline-style-ranges")
    public ResponseEntity<InlineStyleRangesDTO> createInlineStyleRanges(@RequestBody InlineStyleRangesDTO inlineStyleRangesDTO)
        throws URISyntaxException {
        log.debug("REST request to save InlineStyleRanges : {}", inlineStyleRangesDTO);
        if (inlineStyleRangesDTO.getId() != null) {
            throw new BadRequestAlertException("A new inlineStyleRanges cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InlineStyleRangesDTO result = inlineStyleRangesService.save(inlineStyleRangesDTO);
        return ResponseEntity
            .created(new URI("/api/inline-style-ranges/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /inline-style-ranges/:id} : Updates an existing inlineStyleRanges.
     *
     * @param id the id of the inlineStyleRangesDTO to save.
     * @param inlineStyleRangesDTO the inlineStyleRangesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inlineStyleRangesDTO,
     * or with status {@code 400 (Bad Request)} if the inlineStyleRangesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the inlineStyleRangesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/inline-style-ranges/{id}")
    public ResponseEntity<InlineStyleRangesDTO> updateInlineStyleRanges(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody InlineStyleRangesDTO inlineStyleRangesDTO
    ) throws URISyntaxException {
        log.debug("REST request to update InlineStyleRanges : {}, {}", id, inlineStyleRangesDTO);
        if (inlineStyleRangesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, inlineStyleRangesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!inlineStyleRangesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        InlineStyleRangesDTO result = inlineStyleRangesService.update(inlineStyleRangesDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, inlineStyleRangesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /inline-style-ranges/:id} : Partial updates given fields of an existing inlineStyleRanges, field will ignore if it is null
     *
     * @param id the id of the inlineStyleRangesDTO to save.
     * @param inlineStyleRangesDTO the inlineStyleRangesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inlineStyleRangesDTO,
     * or with status {@code 400 (Bad Request)} if the inlineStyleRangesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the inlineStyleRangesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the inlineStyleRangesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/inline-style-ranges/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<InlineStyleRangesDTO> partialUpdateInlineStyleRanges(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody InlineStyleRangesDTO inlineStyleRangesDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update InlineStyleRanges partially : {}, {}", id, inlineStyleRangesDTO);
        if (inlineStyleRangesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, inlineStyleRangesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!inlineStyleRangesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<InlineStyleRangesDTO> result = inlineStyleRangesService.partialUpdate(inlineStyleRangesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, inlineStyleRangesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /inline-style-ranges} : get all the inlineStyleRanges.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of inlineStyleRanges in body.
     */
    @GetMapping("/inline-style-ranges")
    public ResponseEntity<List<InlineStyleRangesDTO>> getAllInlineStyleRanges(
        InlineStyleRangesCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get InlineStyleRanges by criteria: {}", criteria);
        Page<InlineStyleRangesDTO> page = inlineStyleRangesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /inline-style-ranges/count} : count all the inlineStyleRanges.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/inline-style-ranges/count")
    public ResponseEntity<Long> countInlineStyleRanges(InlineStyleRangesCriteria criteria) {
        log.debug("REST request to count InlineStyleRanges by criteria: {}", criteria);
        return ResponseEntity.ok().body(inlineStyleRangesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /inline-style-ranges/:id} : get the "id" inlineStyleRanges.
     *
     * @param id the id of the inlineStyleRangesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the inlineStyleRangesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/inline-style-ranges/{id}")
    public ResponseEntity<InlineStyleRangesDTO> getInlineStyleRanges(@PathVariable Long id) {
        log.debug("REST request to get InlineStyleRanges : {}", id);
        Optional<InlineStyleRangesDTO> inlineStyleRangesDTO = inlineStyleRangesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(inlineStyleRangesDTO);
    }

    /**
     * {@code DELETE  /inline-style-ranges/:id} : delete the "id" inlineStyleRanges.
     *
     * @param id the id of the inlineStyleRangesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/inline-style-ranges/{id}")
    public ResponseEntity<Void> deleteInlineStyleRanges(@PathVariable Long id) {
        log.debug("REST request to delete InlineStyleRanges : {}", id);
        inlineStyleRangesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
