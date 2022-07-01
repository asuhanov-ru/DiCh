package org.jhipster.dich.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jhipster.dich.repository.PageWordRepository;
import org.jhipster.dich.service.PageWordQueryService;
import org.jhipster.dich.service.PageWordService;
import org.jhipster.dich.service.criteria.PageWordCriteria;
import org.jhipster.dich.service.dto.PageWordDTO;
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
 * REST controller for managing {@link org.jhipster.dich.domain.PageWord}.
 */
@RestController
@RequestMapping("/api")
public class PageWordResource {

    private final Logger log = LoggerFactory.getLogger(PageWordResource.class);

    private static final String ENTITY_NAME = "pageWord";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PageWordService pageWordService;

    private final PageWordRepository pageWordRepository;

    private final PageWordQueryService pageWordQueryService;

    public PageWordResource(
        PageWordService pageWordService,
        PageWordRepository pageWordRepository,
        PageWordQueryService pageWordQueryService
    ) {
        this.pageWordService = pageWordService;
        this.pageWordRepository = pageWordRepository;
        this.pageWordQueryService = pageWordQueryService;
    }

    /**
     * {@code POST  /page-words} : Create a new pageWord.
     *
     * @param pageWordDTO the pageWordDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pageWordDTO, or with status {@code 400 (Bad Request)} if the pageWord has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/page-words")
    public ResponseEntity<PageWordDTO> createPageWord(@RequestBody PageWordDTO pageWordDTO) throws URISyntaxException {
        log.debug("REST request to save PageWord : {}", pageWordDTO);
        if (pageWordDTO.getId() != null) {
            throw new BadRequestAlertException("A new pageWord cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PageWordDTO result = pageWordService.save(pageWordDTO);
        return ResponseEntity
            .created(new URI("/api/page-words/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /page-words/:id} : Updates an existing pageWord.
     *
     * @param id the id of the pageWordDTO to save.
     * @param pageWordDTO the pageWordDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pageWordDTO,
     * or with status {@code 400 (Bad Request)} if the pageWordDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pageWordDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/page-words/{id}")
    public ResponseEntity<PageWordDTO> updatePageWord(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PageWordDTO pageWordDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PageWord : {}, {}", id, pageWordDTO);
        if (pageWordDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pageWordDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pageWordRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PageWordDTO result = pageWordService.update(pageWordDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pageWordDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /page-words/:id} : Partial updates given fields of an existing pageWord, field will ignore if it is null
     *
     * @param id the id of the pageWordDTO to save.
     * @param pageWordDTO the pageWordDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pageWordDTO,
     * or with status {@code 400 (Bad Request)} if the pageWordDTO is not valid,
     * or with status {@code 404 (Not Found)} if the pageWordDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the pageWordDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/page-words/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PageWordDTO> partialUpdatePageWord(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PageWordDTO pageWordDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PageWord partially : {}, {}", id, pageWordDTO);
        if (pageWordDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pageWordDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pageWordRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PageWordDTO> result = pageWordService.partialUpdate(pageWordDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pageWordDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /page-words} : get all the pageWords.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pageWords in body.
     */
    @GetMapping("/page-words")
    public ResponseEntity<List<PageWordDTO>> getAllPageWords(
        PageWordCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get PageWords by criteria: {}", criteria);
        Page<PageWordDTO> page = pageWordQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /page-words/count} : count all the pageWords.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/page-words/count")
    public ResponseEntity<Long> countPageWords(PageWordCriteria criteria) {
        log.debug("REST request to count PageWords by criteria: {}", criteria);
        return ResponseEntity.ok().body(pageWordQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /page-words/:id} : get the "id" pageWord.
     *
     * @param id the id of the pageWordDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pageWordDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/page-words/{id}")
    public ResponseEntity<PageWordDTO> getPageWord(@PathVariable Long id) {
        log.debug("REST request to get PageWord : {}", id);
        Optional<PageWordDTO> pageWordDTO = pageWordService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pageWordDTO);
    }

    /**
     * {@code DELETE  /page-words/:id} : delete the "id" pageWord.
     *
     * @param id the id of the pageWordDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/page-words/{id}")
    public ResponseEntity<Void> deletePageWord(@PathVariable Long id) {
        log.debug("REST request to delete PageWord : {}", id);
        pageWordService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
