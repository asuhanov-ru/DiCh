package org.jhipster.dich.service;

import java.util.List;
import javax.persistence.criteria.JoinType;
import org.jhipster.dich.domain.*; // for static metamodels
import org.jhipster.dich.domain.PageLayout;
import org.jhipster.dich.repository.PageLayoutRepository;
import org.jhipster.dich.service.criteria.PageLayoutCriteria;
import org.jhipster.dich.service.dto.PageLayoutDTO;
import org.jhipster.dich.service.mapper.PageLayoutMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link PageLayout} entities in the database.
 * The main input is a {@link PageLayoutCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PageLayoutDTO} or a {@link Page} of {@link PageLayoutDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PageLayoutQueryService extends QueryService<PageLayout> {

    private final Logger log = LoggerFactory.getLogger(PageLayoutQueryService.class);

    private final PageLayoutRepository pageLayoutRepository;

    private final PageLayoutMapper pageLayoutMapper;

    public PageLayoutQueryService(PageLayoutRepository pageLayoutRepository, PageLayoutMapper pageLayoutMapper) {
        this.pageLayoutRepository = pageLayoutRepository;
        this.pageLayoutMapper = pageLayoutMapper;
    }

    /**
     * Return a {@link List} of {@link PageLayoutDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PageLayoutDTO> findByCriteria(PageLayoutCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PageLayout> specification = createSpecification(criteria);
        return pageLayoutMapper.toDto(pageLayoutRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PageLayoutDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PageLayoutDTO> findByCriteria(PageLayoutCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PageLayout> specification = createSpecification(criteria);
        return pageLayoutRepository.findAll(specification, page).map(pageLayoutMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PageLayoutCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PageLayout> specification = createSpecification(criteria);
        return pageLayoutRepository.count(specification);
    }

    /**
     * Function to convert {@link PageLayoutCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PageLayout> createSpecification(PageLayoutCriteria criteria) {
        Specification<PageLayout> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PageLayout_.id));
            }
            if (criteria.getMediaId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMediaId(), PageLayout_.mediaId));
            }
            if (criteria.getPageNumber() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPageNumber(), PageLayout_.pageNumber));
            }
        }
        return specification;
    }
}
