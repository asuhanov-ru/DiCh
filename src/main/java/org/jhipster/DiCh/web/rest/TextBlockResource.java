package org.jhipster.dich.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jhipster.dich.repository.TextBlockRepository;
import org.jhipster.dich.service.TextBlockQueryService;
import org.jhipster.dich.service.TextBlockService;
import org.jhipster.dich.service.criteria.TextBlockCriteria;
import org.jhipster.dich.service.dto.TextBlockDTO;
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
 * REST controller for managing {@link org.jhipster.dich.domain.TextBlock}.
 */
@RestController
@RequestMapping("/api")
public class TextBlockResource {

    private final Logger log = LoggerFactory.getLogger(TextBlockResource.class);

    private static final String ENTITY_NAME = "textBlock";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TextBlockService textBlockService;

    private final TextBlockRepository textBlockRepository;

    private final TextBlockQueryService textBlockQueryService;

    public TextBlockResource(
        TextBlockService textBlockService,
        TextBlockRepository textBlockRepository,
        TextBlockQueryService textBlockQueryService
    ) {
        this.textBlockService = textBlockService;
        this.textBlockRepository = textBlockRepository;
        this.textBlockQueryService = textBlockQueryService;
    }

    /**
     * {@code POST  /text-blocks} : Create a new textBlock.
     *
     * @param textBlockDTO the textBlockDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new textBlockDTO, or with status {@code 400 (Bad Request)} if the textBlock has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/text-blocks")
    public ResponseEntity<TextBlockDTO> createTextBlock(@RequestBody TextBlockDTO textBlockDTO) throws URISyntaxException {
        log.debug("REST request to save TextBlock : {}", textBlockDTO);
        if (textBlockDTO.getId() != null) {
            throw new BadRequestAlertException("A new textBlock cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TextBlockDTO result = textBlockService.save(textBlockDTO);
        return ResponseEntity
            .created(new URI("/api/text-blocks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /text-blocks/:id} : Updates an existing textBlock.
     *
     * @param id the id of the textBlockDTO to save.
     * @param textBlockDTO the textBlockDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated textBlockDTO,
     * or with status {@code 400 (Bad Request)} if the textBlockDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the textBlockDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/text-blocks/{id}")
    public ResponseEntity<TextBlockDTO> updateTextBlock(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TextBlockDTO textBlockDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TextBlock : {}, {}", id, textBlockDTO);
        if (textBlockDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, textBlockDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!textBlockRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TextBlockDTO result = textBlockService.update(textBlockDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, textBlockDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /text-blocks/:id} : Partial updates given fields of an existing textBlock, field will ignore if it is null
     *
     * @param id the id of the textBlockDTO to save.
     * @param textBlockDTO the textBlockDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated textBlockDTO,
     * or with status {@code 400 (Bad Request)} if the textBlockDTO is not valid,
     * or with status {@code 404 (Not Found)} if the textBlockDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the textBlockDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/text-blocks/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TextBlockDTO> partialUpdateTextBlock(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TextBlockDTO textBlockDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TextBlock partially : {}, {}", id, textBlockDTO);
        if (textBlockDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, textBlockDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!textBlockRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TextBlockDTO> result = textBlockService.partialUpdate(textBlockDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, textBlockDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /text-blocks} : get all the textBlocks.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of textBlocks in body.
     */
    @GetMapping("/text-blocks")
    public ResponseEntity<List<TextBlockDTO>> getAllTextBlocks(
        TextBlockCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get TextBlocks by criteria: {}", criteria);
        Page<TextBlockDTO> page = textBlockQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /text-blocks/count} : count all the textBlocks.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/text-blocks/count")
    public ResponseEntity<Long> countTextBlocks(TextBlockCriteria criteria) {
        log.debug("REST request to count TextBlocks by criteria: {}", criteria);
        return ResponseEntity.ok().body(textBlockQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /text-blocks/:id} : get the "id" textBlock.
     *
     * @param id the id of the textBlockDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the textBlockDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/text-blocks/{id}")
    public ResponseEntity<TextBlockDTO> getTextBlock(@PathVariable Long id) {
        log.debug("REST request to get TextBlock : {}", id);
        Optional<TextBlockDTO> textBlockDTO = textBlockService.findOne(id);
        return ResponseUtil.wrapOrNotFound(textBlockDTO);
    }

    /**
     * {@code DELETE  /text-blocks/:id} : delete the "id" textBlock.
     *
     * @param id the id of the textBlockDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/text-blocks/{id}")
    public ResponseEntity<Void> deleteTextBlock(@PathVariable Long id) {
        log.debug("REST request to delete TextBlock : {}", id);
        textBlockService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
