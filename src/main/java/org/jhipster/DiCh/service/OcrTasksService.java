package org.jhipster.dich.service;

import java.util.Optional;
import org.jhipster.dich.domain.OcrTasks;
import org.jhipster.dich.repository.OcrTasksRepository;
import org.jhipster.dich.service.dto.OcrTasksDTO;
import org.jhipster.dich.service.mapper.OcrTasksMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link OcrTasks}.
 */
@Service
@Transactional
public class OcrTasksService {

    private final Logger log = LoggerFactory.getLogger(OcrTasksService.class);

    private final OcrTasksRepository ocrTasksRepository;

    private final OcrTasksMapper ocrTasksMapper;

    public OcrTasksService(OcrTasksRepository ocrTasksRepository, OcrTasksMapper ocrTasksMapper) {
        this.ocrTasksRepository = ocrTasksRepository;
        this.ocrTasksMapper = ocrTasksMapper;
    }

    /**
     * Save a ocrTasks.
     *
     * @param ocrTasksDTO the entity to save.
     * @return the persisted entity.
     */
    public OcrTasksDTO save(OcrTasksDTO ocrTasksDTO) {
        log.debug("Request to save OcrTasks : {}", ocrTasksDTO);
        OcrTasks ocrTasks = ocrTasksMapper.toEntity(ocrTasksDTO);
        ocrTasks = ocrTasksRepository.save(ocrTasks);
        return ocrTasksMapper.toDto(ocrTasks);
    }

    /**
     * Update a ocrTasks.
     *
     * @param ocrTasksDTO the entity to save.
     * @return the persisted entity.
     */
    public OcrTasksDTO update(OcrTasksDTO ocrTasksDTO) {
        log.debug("Request to save OcrTasks : {}", ocrTasksDTO);
        OcrTasks ocrTasks = ocrTasksMapper.toEntity(ocrTasksDTO);
        ocrTasks = ocrTasksRepository.save(ocrTasks);
        return ocrTasksMapper.toDto(ocrTasks);
    }

    /**
     * Partially update a ocrTasks.
     *
     * @param ocrTasksDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<OcrTasksDTO> partialUpdate(OcrTasksDTO ocrTasksDTO) {
        log.debug("Request to partially update OcrTasks : {}", ocrTasksDTO);

        return ocrTasksRepository
            .findById(ocrTasksDTO.getId())
            .map(existingOcrTasks -> {
                ocrTasksMapper.partialUpdate(existingOcrTasks, ocrTasksDTO);

                return existingOcrTasks;
            })
            .map(ocrTasksRepository::save)
            .map(ocrTasksMapper::toDto);
    }

    /**
     * Get all the ocrTasks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<OcrTasksDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OcrTasks");
        return ocrTasksRepository.findAll(pageable).map(ocrTasksMapper::toDto);
    }

    /**
     * Get one ocrTasks by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OcrTasksDTO> findOne(Long id) {
        log.debug("Request to get OcrTasks : {}", id);
        return ocrTasksRepository.findById(id).map(ocrTasksMapper::toDto);
    }

    /**
     * Delete the ocrTasks by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete OcrTasks : {}", id);
        ocrTasksRepository.deleteById(id);
    }
}
