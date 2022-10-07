package org.jhipster.dich.service;

import java.util.Optional;
import org.jhipster.dich.domain.PageLayout;
import org.jhipster.dich.repository.PageLayoutRepository;
import org.jhipster.dich.service.dto.PageLayoutDTO;
import org.jhipster.dich.service.mapper.PageLayoutMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PageLayout}.
 */
@Service
@Transactional
public class PageLayoutService {

    private final Logger log = LoggerFactory.getLogger(PageLayoutService.class);

    private final PageLayoutRepository pageLayoutRepository;

    private final PageLayoutMapper pageLayoutMapper;

    public PageLayoutService(PageLayoutRepository pageLayoutRepository, PageLayoutMapper pageLayoutMapper) {
        this.pageLayoutRepository = pageLayoutRepository;
        this.pageLayoutMapper = pageLayoutMapper;
    }

    /**
     * Save a pageLayout.
     *
     * @param pageLayoutDTO the entity to save.
     * @return the persisted entity.
     */
    public PageLayoutDTO save(PageLayoutDTO pageLayoutDTO) {
        log.debug("Request to save PageLayout : {}", pageLayoutDTO);
        PageLayout pageLayout = pageLayoutMapper.toEntity(pageLayoutDTO);
        pageLayout = pageLayoutRepository.save(pageLayout);
        return pageLayoutMapper.toDto(pageLayout);
    }

    /**
     * Update a pageLayout.
     *
     * @param pageLayoutDTO the entity to save.
     * @return the persisted entity.
     */
    public PageLayoutDTO update(PageLayoutDTO pageLayoutDTO) {
        log.debug("Request to save PageLayout : {}", pageLayoutDTO);
        PageLayout pageLayout = pageLayoutMapper.toEntity(pageLayoutDTO);
        pageLayout = pageLayoutRepository.save(pageLayout);
        return pageLayoutMapper.toDto(pageLayout);
    }

    /**
     * Partially update a pageLayout.
     *
     * @param pageLayoutDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PageLayoutDTO> partialUpdate(PageLayoutDTO pageLayoutDTO) {
        log.debug("Request to partially update PageLayout : {}", pageLayoutDTO);

        return pageLayoutRepository
            .findById(pageLayoutDTO.getId())
            .map(existingPageLayout -> {
                pageLayoutMapper.partialUpdate(existingPageLayout, pageLayoutDTO);

                return existingPageLayout;
            })
            .map(pageLayoutRepository::save)
            .map(pageLayoutMapper::toDto);
    }

    /**
     * Get all the pageLayouts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PageLayoutDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PageLayouts");
        return pageLayoutRepository.findAll(pageable).map(pageLayoutMapper::toDto);
    }

    /**
     * Get one pageLayout by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PageLayoutDTO> findOne(Long id) {
        log.debug("Request to get PageLayout : {}", id);
        return pageLayoutRepository.findById(id).map(pageLayoutMapper::toDto);
    }

    /**
     * Delete the pageLayout by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PageLayout : {}", id);
        pageLayoutRepository.deleteById(id);
    }
}
