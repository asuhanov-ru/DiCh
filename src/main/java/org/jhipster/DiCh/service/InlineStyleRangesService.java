package org.jhipster.dich.service;

import java.util.Optional;
import org.jhipster.dich.domain.InlineStyleRanges;
import org.jhipster.dich.repository.InlineStyleRangesRepository;
import org.jhipster.dich.service.dto.InlineStyleRangesDTO;
import org.jhipster.dich.service.mapper.InlineStyleRangesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link InlineStyleRanges}.
 */
@Service
@Transactional
public class InlineStyleRangesService {

    private final Logger log = LoggerFactory.getLogger(InlineStyleRangesService.class);

    private final InlineStyleRangesRepository inlineStyleRangesRepository;

    private final InlineStyleRangesMapper inlineStyleRangesMapper;

    public InlineStyleRangesService(
        InlineStyleRangesRepository inlineStyleRangesRepository,
        InlineStyleRangesMapper inlineStyleRangesMapper
    ) {
        this.inlineStyleRangesRepository = inlineStyleRangesRepository;
        this.inlineStyleRangesMapper = inlineStyleRangesMapper;
    }

    /**
     * Save a inlineStyleRanges.
     *
     * @param inlineStyleRangesDTO the entity to save.
     * @return the persisted entity.
     */
    public InlineStyleRangesDTO save(InlineStyleRangesDTO inlineStyleRangesDTO) {
        log.debug("Request to save InlineStyleRanges : {}", inlineStyleRangesDTO);
        InlineStyleRanges inlineStyleRanges = inlineStyleRangesMapper.toEntity(inlineStyleRangesDTO);
        inlineStyleRanges = inlineStyleRangesRepository.save(inlineStyleRanges);
        return inlineStyleRangesMapper.toDto(inlineStyleRanges);
    }

    /**
     * Update a inlineStyleRanges.
     *
     * @param inlineStyleRangesDTO the entity to save.
     * @return the persisted entity.
     */
    public InlineStyleRangesDTO update(InlineStyleRangesDTO inlineStyleRangesDTO) {
        log.debug("Request to save InlineStyleRanges : {}", inlineStyleRangesDTO);
        InlineStyleRanges inlineStyleRanges = inlineStyleRangesMapper.toEntity(inlineStyleRangesDTO);
        inlineStyleRanges = inlineStyleRangesRepository.save(inlineStyleRanges);
        return inlineStyleRangesMapper.toDto(inlineStyleRanges);
    }

    /**
     * Partially update a inlineStyleRanges.
     *
     * @param inlineStyleRangesDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<InlineStyleRangesDTO> partialUpdate(InlineStyleRangesDTO inlineStyleRangesDTO) {
        log.debug("Request to partially update InlineStyleRanges : {}", inlineStyleRangesDTO);

        return inlineStyleRangesRepository
            .findById(inlineStyleRangesDTO.getId())
            .map(existingInlineStyleRanges -> {
                inlineStyleRangesMapper.partialUpdate(existingInlineStyleRanges, inlineStyleRangesDTO);

                return existingInlineStyleRanges;
            })
            .map(inlineStyleRangesRepository::save)
            .map(inlineStyleRangesMapper::toDto);
    }

    /**
     * Get all the inlineStyleRanges.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<InlineStyleRangesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all InlineStyleRanges");
        return inlineStyleRangesRepository.findAll(pageable).map(inlineStyleRangesMapper::toDto);
    }

    /**
     * Get one inlineStyleRanges by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<InlineStyleRangesDTO> findOne(Long id) {
        log.debug("Request to get InlineStyleRanges : {}", id);
        return inlineStyleRangesRepository.findById(id).map(inlineStyleRangesMapper::toDto);
    }

    /**
     * Delete the inlineStyleRanges by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete InlineStyleRanges : {}", id);
        inlineStyleRangesRepository.deleteById(id);
    }
}
