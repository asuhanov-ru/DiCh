package org.jhipster.dich.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.jhipster.dich.domain.Collections;
import org.jhipster.dich.repository.CollectionsRepository;
import org.jhipster.dich.service.dto.CollectionsDTO;
import org.jhipster.dich.service.mapper.CollectionsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Collections}.
 */
@Service
@Transactional
public class CollectionsService {

    private final Logger log = LoggerFactory.getLogger(CollectionsService.class);

    private final CollectionsRepository collectionsRepository;

    private final CollectionsMapper collectionsMapper;

    public CollectionsService(CollectionsRepository collectionsRepository, CollectionsMapper collectionsMapper) {
        this.collectionsRepository = collectionsRepository;
        this.collectionsMapper = collectionsMapper;
    }

    /**
     * Save a collections.
     *
     * @param collectionsDTO the entity to save.
     * @return the persisted entity.
     */
    public CollectionsDTO save(CollectionsDTO collectionsDTO) {
        log.debug("Request to save Collections : {}", collectionsDTO);
        Collections collections = collectionsMapper.toEntity(collectionsDTO);
        collections = collectionsRepository.save(collections);
        return collectionsMapper.toDto(collections);
    }

    /**
     * Update a collections.
     *
     * @param collectionsDTO the entity to save.
     * @return the persisted entity.
     */
    public CollectionsDTO update(CollectionsDTO collectionsDTO) {
        log.debug("Request to save Collections : {}", collectionsDTO);
        Collections collections = collectionsMapper.toEntity(collectionsDTO);
        collections = collectionsRepository.save(collections);
        return collectionsMapper.toDto(collections);
    }

    /**
     * Partially update a collections.
     *
     * @param collectionsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CollectionsDTO> partialUpdate(CollectionsDTO collectionsDTO) {
        log.debug("Request to partially update Collections : {}", collectionsDTO);

        return collectionsRepository
            .findById(collectionsDTO.getId())
            .map(existingCollections -> {
                collectionsMapper.partialUpdate(existingCollections, collectionsDTO);

                return existingCollections;
            })
            .map(collectionsRepository::save)
            .map(collectionsMapper::toDto);
    }

    /**
     * Get all the collections.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CollectionsDTO> findAll() {
        log.debug("Request to get all Collections");
        return collectionsRepository.findAll().stream().map(collectionsMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one collections by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CollectionsDTO> findOne(Long id) {
        log.debug("Request to get Collections : {}", id);
        return collectionsRepository.findById(id).map(collectionsMapper::toDto);
    }

    /**
     * Delete the collections by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Collections : {}", id);
        collectionsRepository.deleteById(id);
    }
}
