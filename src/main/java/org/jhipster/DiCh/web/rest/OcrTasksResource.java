package org.jhipster.dich.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jhipster.dich.repository.OcrTasksRepository;
import org.jhipster.dich.service.OcrTasksQueryService;
import org.jhipster.dich.service.OcrTasksService;
import org.jhipster.dich.service.criteria.OcrTasksCriteria;
import org.jhipster.dich.service.dto.OcrTasksDTO;
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
 * REST controller for managing {@link org.jhipster.dich.domain.OcrTasks}.
 */
@RestController
@RequestMapping("/api")
public class OcrTasksResource {

    private final Logger log = LoggerFactory.getLogger(OcrTasksResource.class);

    private static final String ENTITY_NAME = "ocrTasks";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OcrTasksService ocrTasksService;

    private final OcrTasksRepository ocrTasksRepository;

    private final OcrTasksQueryService ocrTasksQueryService;

    public OcrTasksResource(
        OcrTasksService ocrTasksService,
        OcrTasksRepository ocrTasksRepository,
        OcrTasksQueryService ocrTasksQueryService
    ) {
        this.ocrTasksService = ocrTasksService;
        this.ocrTasksRepository = ocrTasksRepository;
        this.ocrTasksQueryService = ocrTasksQueryService;
    }

    /**
     * {@code POST  /ocr-tasks} : Create a new ocrTasks.
     *
     * @param ocrTasksDTO the ocrTasksDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ocrTasksDTO, or with status {@code 400 (Bad Request)} if the ocrTasks has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ocr-tasks")
    public ResponseEntity<OcrTasksDTO> createOcrTasks(@RequestBody OcrTasksDTO ocrTasksDTO) throws URISyntaxException {
        log.debug("REST request to save OcrTasks : {}", ocrTasksDTO);
        if (ocrTasksDTO.getId() != null) {
            throw new BadRequestAlertException("A new ocrTasks cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OcrTasksDTO result = ocrTasksService.save(ocrTasksDTO);
        return ResponseEntity
            .created(new URI("/api/ocr-tasks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ocr-tasks/:id} : Updates an existing ocrTasks.
     *
     * @param id the id of the ocrTasksDTO to save.
     * @param ocrTasksDTO the ocrTasksDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ocrTasksDTO,
     * or with status {@code 400 (Bad Request)} if the ocrTasksDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ocrTasksDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ocr-tasks/{id}")
    public ResponseEntity<OcrTasksDTO> updateOcrTasks(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OcrTasksDTO ocrTasksDTO
    ) throws URISyntaxException {
        log.debug("REST request to update OcrTasks : {}, {}", id, ocrTasksDTO);
        if (ocrTasksDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ocrTasksDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ocrTasksRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OcrTasksDTO result = ocrTasksService.update(ocrTasksDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ocrTasksDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ocr-tasks/:id} : Partial updates given fields of an existing ocrTasks, field will ignore if it is null
     *
     * @param id the id of the ocrTasksDTO to save.
     * @param ocrTasksDTO the ocrTasksDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ocrTasksDTO,
     * or with status {@code 400 (Bad Request)} if the ocrTasksDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ocrTasksDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ocrTasksDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ocr-tasks/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OcrTasksDTO> partialUpdateOcrTasks(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OcrTasksDTO ocrTasksDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update OcrTasks partially : {}, {}", id, ocrTasksDTO);
        if (ocrTasksDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ocrTasksDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ocrTasksRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OcrTasksDTO> result = ocrTasksService.partialUpdate(ocrTasksDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ocrTasksDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ocr-tasks} : get all the ocrTasks.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ocrTasks in body.
     */
    @GetMapping("/ocr-tasks")
    public ResponseEntity<List<OcrTasksDTO>> getAllOcrTasks(
        OcrTasksCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get OcrTasks by criteria: {}", criteria);
        Page<OcrTasksDTO> page = ocrTasksQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ocr-tasks/count} : count all the ocrTasks.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ocr-tasks/count")
    public ResponseEntity<Long> countOcrTasks(OcrTasksCriteria criteria) {
        log.debug("REST request to count OcrTasks by criteria: {}", criteria);
        return ResponseEntity.ok().body(ocrTasksQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ocr-tasks/:id} : get the "id" ocrTasks.
     *
     * @param id the id of the ocrTasksDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ocrTasksDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ocr-tasks/{id}")
    public ResponseEntity<OcrTasksDTO> getOcrTasks(@PathVariable Long id) {
        log.debug("REST request to get OcrTasks : {}", id);
        Optional<OcrTasksDTO> ocrTasksDTO = ocrTasksService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ocrTasksDTO);
    }

    /**
     * {@code DELETE  /ocr-tasks/:id} : delete the "id" ocrTasks.
     *
     * @param id the id of the ocrTasksDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ocr-tasks/{id}")
    public ResponseEntity<Void> deleteOcrTasks(@PathVariable Long id) {
        log.debug("REST request to delete OcrTasks : {}", id);
        ocrTasksService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
