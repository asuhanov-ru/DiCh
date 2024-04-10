package org.jhipster.dich.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.jhipster.dich.domain.Media;
import org.jhipster.dich.repository.MediaRepository;
import org.jhipster.dich.service.MediaDetailsService;
import org.jhipster.dich.service.PdfDocService;
import org.jhipster.dich.service.dto.MediaDetailsDto;
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
 * REST controller for managing {@link org.jhipster.dich.domain.Media}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class MediaResourceV2 {

    private final Logger log = LoggerFactory.getLogger(MediaResourceV2.class);

    private static final String ENTITY_NAME = "media";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MediaRepository mediaRepository;

    private final MediaDetailsService mediaDetailsService;

    private final PdfDocService pdfDocService;

    public MediaResourceV2(MediaRepository mediaRepository, MediaDetailsService mediaDetailsService, PdfDocService pdfDocService) {
        this.mediaRepository = mediaRepository;
        this.mediaDetailsService = mediaDetailsService;
        this.pdfDocService = pdfDocService;
    }

    /**
     * {@code POST  /v2/media} : Create a new media.
     *
     * @param media the media to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new media, or with status {@code 400 (Bad Request)} if the media has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/v2/media")
    public ResponseEntity<Media> createMedia(@Valid @RequestBody Media media) throws URISyntaxException {
        log.debug("REST request to save Media : {}", media);
        if (media.getId() != null) {
            throw new BadRequestAlertException("A new media cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Media result = mediaRepository.save(media);
        return ResponseEntity
            .created(new URI("/api/v2/media/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /v2/media/:id} : Updates an existing media.
     *
     * @param id the id of the media to save.
     * @param media the media to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated media,
     * or with status {@code 400 (Bad Request)} if the media is not valid,
     * or with status {@code 500 (Internal Server Error)} if the media couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/v2/media/{id}")
    public ResponseEntity<Media> updateMedia(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Media media)
        throws URISyntaxException {
        log.debug("REST request to update Media : {}, {}", id, media);
        if (media.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, media.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mediaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Media result = mediaRepository.save(media);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, media.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /v2/media/:id} : Partial updates given fields of an existing media, field will ignore if it is null
     *
     * @param id the id of the media to save.
     * @param media the media to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated media,
     * or with status {@code 400 (Bad Request)} if the media is not valid,
     * or with status {@code 404 (Not Found)} if the media is not found,
     * or with status {@code 500 (Internal Server Error)} if the media couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/v2/media/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Media> partialUpdateMedia(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Media media
    ) throws URISyntaxException {
        log.debug("REST request to partial update Media partially : {}, {}", id, media);
        if (media.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, media.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mediaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Media> result = mediaRepository
            .findById(media.getId())
            .map(existingMedia -> {
                if (media.getFileName() != null) {
                    existingMedia.setFileName(media.getFileName());
                }
                if (media.getFileType() != null) {
                    existingMedia.setFileType(media.getFileType());
                }
                if (media.getFileDesc() != null) {
                    existingMedia.setFileDesc(media.getFileDesc());
                }

                return existingMedia;
            })
            .map(mediaRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, media.getId().toString())
        );
    }

    /**
     * {@code GET  /v2/media} : get all the media.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of media in body.
     */
    @GetMapping("/v2/media")
    public List<Media> getAllMedia(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Media");
        return mediaRepository.findAllWithEagerRelationships();
    }

    /**
     * {@code GET  /v2/media/:id} : get the "id" media.
     *
     * @param id the id of the media to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the media, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/v2/media/{id}")
    public ResponseEntity<MediaDetailsDto> getMedia(@PathVariable Long id) {
        log.debug("REST request to get Media : {}", id);
        Optional<MediaDetailsDto> mediaDetails = mediaDetailsService.findOne(id);

        mediaDetails.map(media -> {
            try {
                media.setOutlines(pdfDocService.getOutline(media.getId()));
            } catch (Exception e) {
                log.debug("Error {}", e);
            }
            return 0;
        });

        return ResponseUtil.wrapOrNotFound(mediaDetails);
    }

    /**
     * {@code DELETE  /v2/media/:id} : delete the "id" media.
     *
     * @param id the id of the media to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/v2/media/{id}")
    public ResponseEntity<Void> deleteMedia(@PathVariable Long id) {
        log.debug("REST request to delete Media : {}", id);
        mediaRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
