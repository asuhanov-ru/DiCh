package org.jhipster.dich.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.jhipster.dich.domain.Media;
import org.jhipster.dich.repository.MediaRepository;
import org.jhipster.dich.service.dto.MediaDTO;
import org.jhipster.dich.service.mapper.MediaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Media}.
 */
@Service
@Transactional
public class MediaService {

    private final Logger log = LoggerFactory.getLogger(MediaService.class);

    private final MediaRepository mediaRepository;

    private final MediaMapper mediaMapper;

    public MediaService(MediaRepository mediaRepository, MediaMapper mediaMapper) {
        this.mediaRepository = mediaRepository;
        this.mediaMapper = mediaMapper;
    }

    /**
     * Save a media.
     *
     * @param mediaDTO the entity to save.
     * @return the persisted entity.
     */
    public MediaDTO save(MediaDTO mediaDTO) {
        log.debug("Request to save Media : {}", mediaDTO);
        Media media = mediaMapper.toEntity(mediaDTO);
        media = mediaRepository.save(media);
        return mediaMapper.toDto(media);
    }

    /**
     * Update a media.
     *
     * @param mediaDTO the entity to save.
     * @return the persisted entity.
     */
    public MediaDTO update(MediaDTO mediaDTO) {
        log.debug("Request to save Media : {}", mediaDTO);
        Media media = mediaMapper.toEntity(mediaDTO);
        media = mediaRepository.save(media);
        return mediaMapper.toDto(media);
    }

    /**
     * Partially update a media.
     *
     * @param mediaDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<MediaDTO> partialUpdate(MediaDTO mediaDTO) {
        log.debug("Request to partially update Media : {}", mediaDTO);

        return mediaRepository
            .findById(mediaDTO.getId())
            .map(existingMedia -> {
                mediaMapper.partialUpdate(existingMedia, mediaDTO);

                return existingMedia;
            })
            .map(mediaRepository::save)
            .map(mediaMapper::toDto);
    }

    /**
     * Get all the media.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<MediaDTO> findAll() {
        log.debug("Request to get all Media");
        return mediaRepository
            .findAllWithEagerRelationships()
            .stream()
            .map(mediaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the media with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<MediaDTO> findAllWithEagerRelationships(Pageable pageable) {
        return mediaRepository.findAllWithEagerRelationships(pageable).map(mediaMapper::toDto);
    }

    /**
     * Get one media by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MediaDTO> findOne(Long id) {
        log.debug("Request to get Media : {}", id);
        return mediaRepository.findOneWithEagerRelationships(id).map(mediaMapper::toDto);
    }

    /**
     * Delete the media by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Media : {}", id);
        mediaRepository.deleteById(id);
    }
}
