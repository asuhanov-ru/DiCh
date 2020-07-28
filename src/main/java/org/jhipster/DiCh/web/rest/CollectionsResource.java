package org.jhipster.dich.web.rest;

import org.jhipster.dich.domain.Collections;
import org.jhipster.dich.repository.CollectionsRepository;
import org.jhipster.dich.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

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
        return ResponseEntity.created(new URI("/api/collections/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /collections} : Updates an existing collections.
     *
     * @param collections the collections to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated collections,
     * or with status {@code 400 (Bad Request)} if the collections is not valid,
     * or with status {@code 500 (Internal Server Error)} if the collections couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/collections")
    public ResponseEntity<Collections> updateCollections(@Valid @RequestBody Collections collections) throws URISyntaxException {
        log.debug("REST request to update Collections : {}", collections);
        if (collections.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Collections result = collectionsRepository.save(collections);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, collections.getId().toString()))
            .body(result);
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
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
