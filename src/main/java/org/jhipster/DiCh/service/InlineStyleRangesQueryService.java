package org.jhipster.dich.service;

import java.util.List;
import javax.persistence.criteria.JoinType;
import org.jhipster.dich.domain.*; // for static metamodels
import org.jhipster.dich.domain.InlineStyleRanges;
import org.jhipster.dich.repository.InlineStyleRangesRepository;
import org.jhipster.dich.service.criteria.InlineStyleRangesCriteria;
import org.jhipster.dich.service.dto.InlineStyleRangesDTO;
import org.jhipster.dich.service.mapper.InlineStyleRangesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link InlineStyleRanges} entities in the database.
 * The main input is a {@link InlineStyleRangesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link InlineStyleRangesDTO} or a {@link Page} of {@link InlineStyleRangesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class InlineStyleRangesQueryService extends QueryService<InlineStyleRanges> {

    private final Logger log = LoggerFactory.getLogger(InlineStyleRangesQueryService.class);

    private final InlineStyleRangesRepository inlineStyleRangesRepository;

    private final InlineStyleRangesMapper inlineStyleRangesMapper;

    public InlineStyleRangesQueryService(
        InlineStyleRangesRepository inlineStyleRangesRepository,
        InlineStyleRangesMapper inlineStyleRangesMapper
    ) {
        this.inlineStyleRangesRepository = inlineStyleRangesRepository;
        this.inlineStyleRangesMapper = inlineStyleRangesMapper;
    }

    /**
     * Return a {@link List} of {@link InlineStyleRangesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<InlineStyleRangesDTO> findByCriteria(InlineStyleRangesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<InlineStyleRanges> specification = createSpecification(criteria);
        return inlineStyleRangesMapper.toDto(inlineStyleRangesRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link InlineStyleRangesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<InlineStyleRangesDTO> findByCriteria(InlineStyleRangesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<InlineStyleRanges> specification = createSpecification(criteria);
        return inlineStyleRangesRepository.findAll(specification, page).map(inlineStyleRangesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(InlineStyleRangesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<InlineStyleRanges> specification = createSpecification(criteria);
        return inlineStyleRangesRepository.count(specification);
    }

    /**
     * Function to convert {@link InlineStyleRangesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<InlineStyleRanges> createSpecification(InlineStyleRangesCriteria criteria) {
        Specification<InlineStyleRanges> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), InlineStyleRanges_.id));
            }
            if (criteria.getStartPos() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartPos(), InlineStyleRanges_.startPos));
            }
            if (criteria.getStopPos() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStopPos(), InlineStyleRanges_.stopPos));
            }
            if (criteria.getTextBlockId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTextBlockId(),
                            root -> root.join(InlineStyleRanges_.textBlock, JoinType.LEFT).get(TextBlock_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
