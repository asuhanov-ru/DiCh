package org.jhipster.dich.service;

import java.util.List;
import javax.persistence.criteria.JoinType;
import org.jhipster.dich.domain.*; // for static metamodels
import org.jhipster.dich.domain.PageWord;
import org.jhipster.dich.repository.PageWordRepository;
import org.jhipster.dich.service.criteria.PageWordCriteria;
import org.jhipster.dich.service.dto.PageWordDTO;
import org.jhipster.dich.service.mapper.PageWordMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link PageWord} entities in the database.
 * The main input is a {@link PageWordCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PageWordDTO} or a {@link Page} of {@link PageWordDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PageWordQueryService extends QueryService<PageWord> {

    private final Logger log = LoggerFactory.getLogger(PageWordQueryService.class);

    private final PageWordRepository pageWordRepository;

    private final PageWordMapper pageWordMapper;

    public PageWordQueryService(PageWordRepository pageWordRepository, PageWordMapper pageWordMapper) {
        this.pageWordRepository = pageWordRepository;
        this.pageWordMapper = pageWordMapper;
    }

    /**
     * Return a {@link List} of {@link PageWordDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PageWordDTO> findByCriteria(PageWordCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PageWord> specification = createSpecification(criteria);
        return pageWordMapper.toDto(pageWordRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PageWordDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PageWordDTO> findByCriteria(PageWordCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PageWord> specification = createSpecification(criteria);
        return pageWordRepository.findAll(specification, page).map(pageWordMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PageWordCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PageWord> specification = createSpecification(criteria);
        return pageWordRepository.count(specification);
    }

    /**
     * Function to convert {@link PageWordCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PageWord> createSpecification(PageWordCriteria criteria) {
        Specification<PageWord> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PageWord_.id));
            }
            if (criteria.gets_word() != null) {
                specification = specification.and(buildStringSpecification(criteria.gets_word(), PageWord_.s_word));
            }
            if (criteria.getn_top() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getn_top(), PageWord_.n_top));
            }
            if (criteria.getn_left() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getn_left(), PageWord_.n_left));
            }
            if (criteria.getn_heigth() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getn_heigth(), PageWord_.n_heigth));
            }
            if (criteria.getn_width() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getn_width(), PageWord_.n_width));
            }
            if (criteria.getn_idx() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getn_idx(), PageWord_.n_idx));
            }
            if (criteria.getPageImageId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPageImageId(),
                            root -> root.join(PageWord_.pageImage, JoinType.LEFT).get(PageImage_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
