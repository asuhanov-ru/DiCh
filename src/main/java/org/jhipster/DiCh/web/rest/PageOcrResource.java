package org.jhipster.dich.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jhipster.dich.repository.PageTextRepository;
import org.jhipster.dich.service.PageTextQueryService;
import org.jhipster.dich.service.PageTextService;
import org.jhipster.dich.service.criteria.PageTextCriteria;
import org.jhipster.dich.service.dto.PageTextDTO;
import org.jhipster.dich.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    private final PageTextService pageTextService;

    public PageOcrResource(PageTextService pageTextService) {
        this.pageTextService = pageTextService;
    }

    /**
     * {@code GET  /page-texts/:id} : get the "id" pageText.
     *
     * @param id the id of the pageTextDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pageTextDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/page-ocr/{id}")
    public ResponseEntity<PageTextDTO> getPageText(@PathVariable Long id) {
        log.debug("REST request to get PageText : {}", id);
        Optional<PageTextDTO> pageTextDTO = pageTextService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pageTextDTO);
    }
}
