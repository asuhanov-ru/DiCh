package org.jhipster.dich.web.rest;

import org.jhipster.dich.domain.MediaStructure;
import org.jhipster.dich.repository.MediaStructureRepository;
import org.jhipster.dich.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link org.jhipster.dich.domain.MediaStructure}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class MediaStructureResource {

    private final Logger log = LoggerFactory.getLogger(MediaStructureResource.class);

    private static final String ENTITY_NAME = "mediaStructure";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MediaStructureRepository mediaStructureRepository;

    public MediaStructureResource(MediaStructureRepository mediaStructureRepository) {
        this.mediaStructureRepository = mediaStructureRepository;
    }

    /**
     * {@code POST  /media-structures} : Create a new mediaStructure.
     *
     * @param mediaStructure the mediaStructure to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mediaStructure, or with status {@code 400 (Bad Request)} if the mediaStructure has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/media-structures")
    public ResponseEntity<MediaStructure> createMediaStructure(@RequestBody MediaStructure mediaStructure) throws URISyntaxException {
        log.debug("REST request to save MediaStructure : {}", mediaStructure);
        if (mediaStructure.getId() != null) {
            throw new BadRequestAlertException("A new mediaStructure cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MediaStructure result = mediaStructureRepository.save(mediaStructure);
        return ResponseEntity.created(new URI("/api/media-structures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /media-structures} : Updates an existing mediaStructure.
     *
     * @param mediaStructure the mediaStructure to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mediaStructure,
     * or with status {@code 400 (Bad Request)} if the mediaStructure is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mediaStructure couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/media-structures")
    public ResponseEntity<MediaStructure> updateMediaStructure(@RequestBody MediaStructure mediaStructure) throws URISyntaxException {
        log.debug("REST request to update MediaStructure : {}", mediaStructure);
        if (mediaStructure.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MediaStructure result = mediaStructureRepository.save(mediaStructure);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mediaStructure.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /media-structures} : get all the mediaStructures.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mediaStructures in body.
     */
    @GetMapping("/media-structures")
    public List<MediaStructure> getAllMediaStructures() {
        log.debug("REST request to get all MediaStructures");
        return mediaStructureRepository.findAll();
    }

    /**
     * {@code GET  /media-structures/:id} : get the "id" mediaStructure.
     *
     * @param id the id of the mediaStructure to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mediaStructure, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/media-structures/{id}")
    public ResponseEntity<MediaStructure> getMediaStructure(@PathVariable Long id) {
        log.debug("REST request to get MediaStructure : {}", id);
        Optional<MediaStructure> mediaStructure = mediaStructureRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(mediaStructure);
    }

    /**
     * {@code DELETE  /media-structures/:id} : delete the "id" mediaStructure.
     *
     * @param id the id of the mediaStructure to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/media-structures/{id}")
    public ResponseEntity<Void> deleteMediaStructure(@PathVariable Long id) {
        log.debug("REST request to delete MediaStructure : {}", id);
        mediaStructureRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
