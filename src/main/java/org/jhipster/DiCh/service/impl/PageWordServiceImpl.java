package org.jhipster.dich.service.impl;

import java.util.Optional;
import org.jhipster.dich.domain.PageWord;
import org.jhipster.dich.repository.PageWordRepository;
import org.jhipster.dich.service.PageWordService;
import org.jhipster.dich.service.dto.PageWordDTO;
import org.jhipster.dich.service.mapper.PageWordMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PageWord}.
 */
@Service
@Transactional
public class PageWordServiceImpl implements PageWordService {

    private final Logger log = LoggerFactory.getLogger(PageWordServiceImpl.class);

    private final PageWordRepository pageWordRepository;

    private final PageWordMapper pageWordMapper;

    public PageWordServiceImpl(PageWordRepository pageWordRepository, PageWordMapper pageWordMapper) {
        this.pageWordRepository = pageWordRepository;
        this.pageWordMapper = pageWordMapper;
    }

    @Override
    public PageWordDTO save(PageWordDTO pageWordDTO) {
        log.debug("Request to save PageWord : {}", pageWordDTO);
        PageWord pageWord = pageWordMapper.toEntity(pageWordDTO);
        pageWord = pageWordRepository.save(pageWord);
        return pageWordMapper.toDto(pageWord);
    }

    @Override
    public PageWordDTO update(PageWordDTO pageWordDTO) {
        log.debug("Request to save PageWord : {}", pageWordDTO);
        PageWord pageWord = pageWordMapper.toEntity(pageWordDTO);
        pageWord = pageWordRepository.save(pageWord);
        return pageWordMapper.toDto(pageWord);
    }

    @Override
    public Optional<PageWordDTO> partialUpdate(PageWordDTO pageWordDTO) {
        log.debug("Request to partially update PageWord : {}", pageWordDTO);

        return pageWordRepository
            .findById(pageWordDTO.getId())
            .map(existingPageWord -> {
                pageWordMapper.partialUpdate(existingPageWord, pageWordDTO);

                return existingPageWord;
            })
            .map(pageWordRepository::save)
            .map(pageWordMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PageWordDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PageWords");
        return pageWordRepository.findAll(pageable).map(pageWordMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PageWordDTO> findOne(Long id) {
        log.debug("Request to get PageWord : {}", id);
        return pageWordRepository.findById(id).map(pageWordMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PageWord : {}", id);
        pageWordRepository.deleteById(id);
    }
}
