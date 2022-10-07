package org.jhipster.dich.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jhipster.dich.domain.PageImage;
import org.jhipster.dich.domain.PageImage_;
import org.jhipster.dich.domain.PageWord;
import org.jhipster.dich.repository.PageImageRepository;
import org.jhipster.dich.repository.PageTextRepository;
import org.jhipster.dich.repository.PageWordRepository;
import org.jhipster.dich.service.OCRService;
import org.jhipster.dich.service.PageTextQueryService;
import org.jhipster.dich.service.PageTextService;
import org.jhipster.dich.service.PageWordQueryService;
import org.jhipster.dich.service.criteria.PageImageCriteria;
import org.jhipster.dich.service.criteria.PageTextCriteria;
import org.jhipster.dich.service.criteria.PageWordCriteria;
import org.jhipster.dich.service.dto.PageTextDTO;
import org.jhipster.dich.service.dto.PageWordDTO;
import org.jhipster.dich.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.jhipster.dich.domain.PageText}.
 */
@RestController
@RequestMapping("/api")
public class PageOcrResource {

    private final Logger log = LoggerFactory.getLogger(PageOcrResource.class);

    private final PageWordQueryService pageWordQueryService;

    public PageOcrResource(PageWordQueryService pageWordQueryService) {
        this.pageWordQueryService = pageWordQueryService;
    }

    /**
     * {@code GET  /v2/page-ocr/} : get page ocr.
     *
     * @param criteria the criteria of the pageWord to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the PageWord, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/v2/page-ocr")
    public ResponseEntity<List<PageWordDTO>> getPageText(PageWordCriteria criteria) {
        log.debug("REST request to get ocr by criteria : {}", criteria);

        Optional<List<PageWordDTO>> pageWords = Optional.ofNullable(pageWordQueryService.findByCriteria(criteria));

        return ResponseUtil.wrapOrNotFound(pageWords);
    }
}
