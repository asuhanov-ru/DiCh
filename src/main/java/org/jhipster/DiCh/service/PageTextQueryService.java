package org.jhipster.dich.service;

import java.util.List;
import javax.persistence.criteria.JoinType;
import org.jhipster.dich.domain.*; // for static metamodels
import org.jhipster.dich.domain.PageText;
import org.jhipster.dich.repository.PageTextRepository;
import org.jhipster.dich.service.criteria.PageTextCriteria;
import org.jhipster.dich.service.dto.PageTextDTO;
import org.jhipster.dich.service.mapper.PageTextMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link PageText} entities in the database.
 * The main input is a {@link PageTextCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PageTextDTO} or a {@link Page} of {@link PageTextDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PageTextQueryService extends QueryService<PageText> {

    private final Logger log = LoggerFactory.getLogger(PageTextQueryService.class);

    private final PageTextRepository pageTextRepository;

    private final PageTextMapper pageTextMapper;

    public PageTextQueryService(PageTextRepository pageTextRepository, PageTextMapper pageTextMapper) {
        this.pageTextRepository = pageTextRepository;
        this.pageTextMapper = pageTextMapper;
    }

    /**
     * Return a {@link List} of {@link PageTextDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PageTextDTO> findByCriteria(PageTextCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PageText> specification = createSpecification(criteria);
        return pageTextMapper.toDto(pageTextRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PageTextDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PageTextDTO> findByCriteria(PageTextCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PageText> specification = createSpecification(criteria);
        return pageTextRepository.findAll(specification, page).map(pageTextMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PageTextCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PageText> specification = createSpecification(criteria);
        return pageTextRepository.count(specification);
    }

    /**
     * Function to convert {@link PageTextCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PageText> createSpecification(PageTextCriteria criteria) {
        Specification<PageText> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PageText_.id));
            }
            if (criteria.getPage_id() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPage_id(), PageText_.page_id));
            }
            if (criteria.getPageImageId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPageImageId(),
                            root -> root.join(PageText_.pageImage, JoinType.LEFT).get(PageImage_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
