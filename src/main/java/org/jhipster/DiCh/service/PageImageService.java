package org.jhipster.dich.service;

import java.util.Optional;
import org.jhipster.dich.domain.PageImage;
import org.jhipster.dich.repository.PageImageRepository;
import org.jhipster.dich.service.dto.PageImageDTO;
import org.jhipster.dich.service.mapper.PageImageMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PageImage}.
 */
@Service
@Transactional
public class PageImageService {

    private final Logger log = LoggerFactory.getLogger(PageImageService.class);

    private final PageImageRepository pageImageRepository;

    private final PageImageMapper pageImageMapper;

    public PageImageService(PageImageRepository pageImageRepository, PageImageMapper pageImageMapper) {
        this.pageImageRepository = pageImageRepository;
        this.pageImageMapper = pageImageMapper;
    }

    /**
     * Save a pageImage.
     *
     * @param pageImageDTO the entity to save.
     * @return the persisted entity.
     */
    public PageImageDTO save(PageImageDTO pageImageDTO) {
        log.debug("Request to save PageImage : {}", pageImageDTO);
        PageImage pageImage = pageImageMapper.toEntity(pageImageDTO);
        pageImage = pageImageRepository.save(pageImage);
        return pageImageMapper.toDto(pageImage);
    }

    /**
     * Update a pageImage.
     *
     * @param pageImageDTO the entity to save.
     * @return the persisted entity.
     */
    public PageImageDTO update(PageImageDTO pageImageDTO) {
        log.debug("Request to save PageImage : {}", pageImageDTO);
        PageImage pageImage = pageImageMapper.toEntity(pageImageDTO);
        pageImage = pageImageRepository.save(pageImage);
        return pageImageMapper.toDto(pageImage);
    }

    /**
     * Partially update a pageImage.
     *
     * @param pageImageDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PageImageDTO> partialUpdate(PageImageDTO pageImageDTO) {
        log.debug("Request to partially update PageImage : {}", pageImageDTO);

        return pageImageRepository
            .findById(pageImageDTO.getId())
            .map(existingPageImage -> {
                pageImageMapper.partialUpdate(existingPageImage, pageImageDTO);

                return existingPageImage;
            })
            .map(pageImageRepository::save)
            .map(pageImageMapper::toDto);
    }

    /**
     * Get all the pageImages.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PageImageDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PageImages");
        return pageImageRepository.findAll(pageable).map(pageImageMapper::toDto);
    }

    /**
     * Get one pageImage by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PageImageDTO> findOne(Long id) {
        log.debug("Request to get PageImage : {}", id);
        return pageImageRepository.findById(id).map(pageImageMapper::toDto);
    }

    /**
     * Delete the pageImage by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PageImage : {}", id);
        pageImageRepository.deleteById(id);
    }
}
