package org.jhipster.dich.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.jhipster.dich.domain.Collections;
import org.jhipster.dich.repository.CollectionsRepository;
import org.jhipster.dich.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.jhipster.dich.domain.Collections}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CollectionsResource {

    private final Logger log = LoggerFactory.getLogger(CollectionsResource.class);

    private static final String ENTITY_NAME = "collections";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CollectionsRepository collectionsRepository;

    public CollectionsResource(CollectionsRepository collectionsRepository) {
        this.collectionsRepository = collectionsRepository;
    }

    /**
     * {@code POST  /collections} : Create a new collections.
     *
     * @param collections the collections to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new collections, or with status {@code 400 (Bad Request)} if the collections has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/collections")
    public ResponseEntity<Collections> createCollections(@Valid @RequestBody Collections collections) throws URISyntaxException {
        log.debug("REST request to save Collections : {}", collections);
        if (collections.getId() != null) {
            throw new BadRequestAlertException("A new collections cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Collections result = collectionsRepository.save(collections);
        return ResponseEntity
            .created(new URI("/api/collections/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /collections/:id} : Updates an existing collections.
     *
     * @param id the id of the collections to save.
     * @param collections the collections to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated collections,
     * or with status {@code 400 (Bad Request)} if the collections is not valid,
     * or with status {@code 500 (Internal Server Error)} if the collections couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/collections/{id}")
    public ResponseEntity<Collections> updateCollections(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Collections collections
    ) throws URISyntaxException {
        log.debug("REST request to update Collections : {}, {}", id, collections);
        if (collections.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, collections.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!collectionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Collections result = collectionsRepository.save(collections);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, collections.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /collections/:id} : Partial updates given fields of an existing collections, field will ignore if it is null
     *
     * @param id the id of the collections to save.
     * @param collections the collections to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated collections,
     * or with status {@code 400 (Bad Request)} if the collections is not valid,
     * or with status {@code 404 (Not Found)} if the collections is not found,
     * or with status {@code 500 (Internal Server Error)} if the collections couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/collections/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Collections> partialUpdateCollections(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Collections collections
    ) throws URISyntaxException {
        log.debug("REST request to partial update Collections partially : {}, {}", id, collections);
        if (collections.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, collections.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!collectionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Collections> result = collectionsRepository
            .findById(collections.getId())
            .map(existingCollections -> {
                if (collections.getName() != null) {
                    existingCollections.setName(collections.getName());
                }
                if (collections.getDescription() != null) {
                    existingCollections.setDescription(collections.getDescription());
                }

                return existingCollections;
            })
            .map(collectionsRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, collections.getId().toString())
        );
    }

    /**
     * {@code GET  /collections} : get all the collections.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of collections in body.
     */
    @GetMapping("/collections")
    public List<Collections> getAllCollections() {
        log.debug("REST request to get all Collections");
        return collectionsRepository.findAll();
    }

    /**
     * {@code GET  /collections/:id} : get the "id" collections.
     *
     * @param id the id of the collections to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the collections, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/collections/{id}")
    public ResponseEntity<Collections> getCollections(@PathVariable Long id) {
        log.debug("REST request to get Collections : {}", id);
        Optional<Collections> collections = collectionsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(collections);
    }

    /**
     * {@code DELETE  /collections/:id} : delete the "id" collections.
     *
     * @param id the id of the collections to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/collections/{id}")
    public ResponseEntity<Void> deleteCollections(@PathVariable Long id) {
        log.debug("REST request to delete Collections : {}", id);
        collectionsRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
