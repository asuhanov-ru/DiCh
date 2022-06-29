package org.jhipster.dich.service.impl;

import java.util.Optional;
import org.jhipster.dich.domain.PageText;
import org.jhipster.dich.repository.PageTextRepository;
import org.jhipster.dich.service.PageTextService;
import org.jhipster.dich.service.dto.PageTextDTO;
import org.jhipster.dich.service.mapper.PageTextMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PageText}.
 */
@Service
@Transactional
public class PageTextServiceImpl implements PageTextService {

    private final Logger log = LoggerFactory.getLogger(PageTextServiceImpl.class);

    private final PageTextRepository pageTextRepository;

    private final PageTextMapper pageTextMapper;

    public PageTextServiceImpl(PageTextRepository pageTextRepository, PageTextMapper pageTextMapper) {
        this.pageTextRepository = pageTextRepository;
        this.pageTextMapper = pageTextMapper;
    }

    @Override
    public PageTextDTO save(PageTextDTO pageTextDTO) {
        log.debug("Request to save PageText : {}", pageTextDTO);
        PageText pageText = pageTextMapper.toEntity(pageTextDTO);
        pageText = pageTextRepository.save(pageText);
        return pageTextMapper.toDto(pageText);
    }

    @Override
    public PageTextDTO update(PageTextDTO pageTextDTO) {
        log.debug("Request to save PageText : {}", pageTextDTO);
        PageText pageText = pageTextMapper.toEntity(pageTextDTO);
        pageText = pageTextRepository.save(pageText);
        return pageTextMapper.toDto(pageText);
    }

    @Override
    public Optional<PageTextDTO> partialUpdate(PageTextDTO pageTextDTO) {
        log.debug("Request to partially update PageText : {}", pageTextDTO);

        return pageTextRepository
            .findById(pageTextDTO.getId())
            .map(existingPageText -> {
                pageTextMapper.partialUpdate(existingPageText, pageTextDTO);

                return existingPageText;
            })
            .map(pageTextRepository::save)
            .map(pageTextMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PageTextDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PageTexts");
        return pageTextRepository.findAll(pageable).map(pageTextMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PageTextDTO> findOne(Long id) {
        log.debug("Request to get PageText : {}", id);
        return pageTextRepository.findById(id).map(pageTextMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PageText : {}", id);
        pageTextRepository.deleteById(id);
    }
}
