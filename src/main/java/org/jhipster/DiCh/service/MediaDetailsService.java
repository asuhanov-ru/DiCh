package org.jhipster.dich.service;

import java.util.Optional;
import org.jhipster.dich.repository.MediaRepository;
import org.jhipster.dich.service.dto.MediaDetailsDto;
import org.jhipster.dich.service.mapper.MediaDetailsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MediaDetailsService {

    private final Logger log = LoggerFactory.getLogger(MediaDetailsService.class);

    private final MediaRepository mediaRepository;

    private final MediaDetailsMapper mediaDetailsMapper;

    private final PdfDocService pdfDocService;

    public MediaDetailsService(MediaRepository mediaRepository, PdfDocService pdfDocService, MediaDetailsMapper mediaDetailsMapper) {
        this.mediaRepository = mediaRepository;
        this.pdfDocService = pdfDocService;
        this.mediaDetailsMapper = mediaDetailsMapper;
    }

    @Transactional(readOnly = true)
    public Optional<MediaDetailsDto> findOne(Long id) {
        log.debug("Request to get Media details : {}", id);
        Optional<MediaDetailsDto> mediaDetailsDto = mediaRepository.findOneWithEagerRelationships(id).map(mediaDetailsMapper::toDto);
        if (mediaDetailsDto.isPresent()) {
            String fileName = mediaDetailsDto.map(dto -> dto.getFileName()).orElse("");
            int pageCount = 0;

            try {
                pageCount = pdfDocService.getPageCount(fileName);
            } catch (Exception e) {
                log.debug("Error {}", e);
            }
            int finalPageCount = pageCount;
            mediaDetailsDto.ifPresent(dto -> dto.setLastPageNumber(finalPageCount));
        }
        return mediaDetailsDto;
    }
}
