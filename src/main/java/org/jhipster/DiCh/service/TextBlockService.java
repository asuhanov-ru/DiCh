package org.jhipster.dich.service;

import java.util.Optional;
import org.jhipster.dich.domain.TextBlock;
import org.jhipster.dich.repository.TextBlockRepository;
import org.jhipster.dich.service.dto.TextBlockDTO;
import org.jhipster.dich.service.mapper.TextBlockMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TextBlock}.
 */
@Service
@Transactional
public class TextBlockService {

    private final Logger log = LoggerFactory.getLogger(TextBlockService.class);

    private final TextBlockRepository textBlockRepository;

    private final TextBlockMapper textBlockMapper;

    public TextBlockService(TextBlockRepository textBlockRepository, TextBlockMapper textBlockMapper) {
        this.textBlockRepository = textBlockRepository;
        this.textBlockMapper = textBlockMapper;
    }

    /**
     * Save a textBlock.
     *
     * @param textBlockDTO the entity to save.
     * @return the persisted entity.
     */
    public TextBlockDTO save(TextBlockDTO textBlockDTO) {
        log.debug("Request to save TextBlock : {}", textBlockDTO);
        TextBlock textBlock = textBlockMapper.toEntity(textBlockDTO);
        textBlock = textBlockRepository.save(textBlock);
        return textBlockMapper.toDto(textBlock);
    }

    /**
     * Update a textBlock.
     *
     * @param textBlockDTO the entity to save.
     * @return the persisted entity.
     */
    public TextBlockDTO update(TextBlockDTO textBlockDTO) {
        log.debug("Request to save TextBlock : {}", textBlockDTO);
        TextBlock textBlock = textBlockMapper.toEntity(textBlockDTO);
        textBlock = textBlockRepository.save(textBlock);
        return textBlockMapper.toDto(textBlock);
    }

    /**
     * Partially update a textBlock.
     *
     * @param textBlockDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TextBlockDTO> partialUpdate(TextBlockDTO textBlockDTO) {
        log.debug("Request to partially update TextBlock : {}", textBlockDTO);

        return textBlockRepository
            .findById(textBlockDTO.getId())
            .map(existingTextBlock -> {
                textBlockMapper.partialUpdate(existingTextBlock, textBlockDTO);

                return existingTextBlock;
            })
            .map(textBlockRepository::save)
            .map(textBlockMapper::toDto);
    }

    /**
     * Get all the textBlocks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TextBlockDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TextBlocks");
        return textBlockRepository.findAll(pageable).map(textBlockMapper::toDto);
    }

    /**
     * Get all the textBlocks with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<TextBlockDTO> findAllWithEagerRelationships(Pageable pageable) {
        return textBlockRepository.findAllWithEagerRelationships(pageable).map(textBlockMapper::toDto);
    }

    /**
     * Get one textBlock by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TextBlockDTO> findOne(Long id) {
        log.debug("Request to get TextBlock : {}", id);
        return textBlockRepository.findOneWithEagerRelationships(id).map(textBlockMapper::toDto);
    }

    /**
     * Delete the textBlock by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TextBlock : {}", id);
        textBlockRepository.deleteById(id);
    }
}
