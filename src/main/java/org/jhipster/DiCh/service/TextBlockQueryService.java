package org.jhipster.dich.service;

import java.util.List;
import javax.persistence.criteria.JoinType;
import org.jhipster.dich.domain.*; // for static metamodels
import org.jhipster.dich.domain.TextBlock;
import org.jhipster.dich.repository.TextBlockRepository;
import org.jhipster.dich.service.criteria.TextBlockCriteria;
import org.jhipster.dich.service.dto.TextBlockDTO;
import org.jhipster.dich.service.mapper.TextBlockMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link TextBlock} entities in the database.
 * The main input is a {@link TextBlockCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TextBlockDTO} or a {@link Page} of {@link TextBlockDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TextBlockQueryService extends QueryService<TextBlock> {

    private final Logger log = LoggerFactory.getLogger(TextBlockQueryService.class);

    private final TextBlockRepository textBlockRepository;

    private final TextBlockMapper textBlockMapper;

    public TextBlockQueryService(TextBlockRepository textBlockRepository, TextBlockMapper textBlockMapper) {
        this.textBlockRepository = textBlockRepository;
        this.textBlockMapper = textBlockMapper;
    }

    /**
     * Return a {@link List} of {@link TextBlockDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TextBlockDTO> findByCriteria(TextBlockCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TextBlock> specification = createSpecification(criteria);
        return textBlockMapper.toDto(textBlockRepository.findAll(specification, Sort.by(Sort.Direction.ASC, "blockIndex")));
    }

    /**
     * Return a {@link Page} of {@link TextBlockDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TextBlockDTO> findByCriteria(TextBlockCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TextBlock> specification = createSpecification(criteria);
        return textBlockRepository.findAll(specification, page).map(textBlockMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TextBlockCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TextBlock> specification = createSpecification(criteria);
        return textBlockRepository.count(specification);
    }

    /**
     * Function to convert {@link TextBlockCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TextBlock> createSpecification(TextBlockCriteria criteria) {
        Specification<TextBlock> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TextBlock_.id));
            }
            if (criteria.getMediaId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMediaId(), TextBlock_.mediaId));
            }
            if (criteria.getPageNumber() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPageNumber(), TextBlock_.pageNumber));
            }
            if (criteria.getBlockIndex() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBlockIndex(), TextBlock_.blockIndex));
            }
            if (criteria.getBlockUUID() != null) {
                specification = specification.and(buildSpecification(criteria.getBlockUUID(), TextBlock_.blockUUID));
            }
        }
        return specification;
    }
}
