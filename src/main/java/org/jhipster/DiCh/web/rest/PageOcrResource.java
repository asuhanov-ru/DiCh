package org.jhipster.dich.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jhipster.dich.domain.PageImage;
import org.jhipster.dich.domain.PageWord;
import org.jhipster.dich.repository.PageImageRepository;
import org.jhipster.dich.repository.PageTextRepository;
import org.jhipster.dich.repository.PageWordRepository;
import org.jhipster.dich.service.*;
import org.jhipster.dich.service.criteria.PageImageCriteria;
import org.jhipster.dich.service.criteria.PageLayoutCriteria;
import org.jhipster.dich.service.criteria.PageTextCriteria;
import org.jhipster.dich.service.criteria.PageWordCriteria;
import org.jhipster.dich.service.dto.PageLayoutDTO;
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
    private final PageLayoutQueryService pageLayoutQueryService;

    public PageOcrResource(PageWordQueryService pageWordQueryService, PageLayoutQueryService pageLayoutQueryService) {
        this.pageWordQueryService = pageWordQueryService;
        this.pageLayoutQueryService = pageLayoutQueryService;
    }

    /**
     * {@code GET  /v2/page-ocr/} : get page ocr.
     *
     * @param criteria the criteria of the pageWord to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the PageWord, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/v2/page-ocr")
    public ResponseEntity<List<PageWordDTO>> getPageText(PageWordCriteria criteria) {
        Optional<List<PageWordDTO>> pageWords = Optional.ofNullable(pageWordQueryService.findByCriteria(criteria));

        return ResponseUtil.wrapOrNotFound(pageWords);
    }

    @GetMapping("/v2/page-layout")
    public ResponseEntity<List<PageLayoutDTO>> getPageLayout(PageLayoutCriteria criteria) {
        Optional<List<PageLayoutDTO>> pageLayout = Optional.ofNullable(pageLayoutQueryService.findByCriteria(criteria));

        return ResponseUtil.wrapOrNotFound(pageLayout);
    }
}
