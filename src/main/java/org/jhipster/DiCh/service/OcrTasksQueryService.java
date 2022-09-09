package org.jhipster.dich.service;

import java.util.List;
import javax.persistence.criteria.JoinType;
import org.jhipster.dich.domain.*; // for static metamodels
import org.jhipster.dich.domain.OcrTasks;
import org.jhipster.dich.repository.OcrTasksRepository;
import org.jhipster.dich.service.criteria.OcrTasksCriteria;
import org.jhipster.dich.service.dto.OcrTasksDTO;
import org.jhipster.dich.service.mapper.OcrTasksMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link OcrTasks} entities in the database.
 * The main input is a {@link OcrTasksCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OcrTasksDTO} or a {@link Page} of {@link OcrTasksDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OcrTasksQueryService extends QueryService<OcrTasks> {

    private final Logger log = LoggerFactory.getLogger(OcrTasksQueryService.class);

    private final OcrTasksRepository ocrTasksRepository;

    private final OcrTasksMapper ocrTasksMapper;

    public OcrTasksQueryService(OcrTasksRepository ocrTasksRepository, OcrTasksMapper ocrTasksMapper) {
        this.ocrTasksRepository = ocrTasksRepository;
        this.ocrTasksMapper = ocrTasksMapper;
    }

    /**
     * Return a {@link List} of {@link OcrTasksDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OcrTasksDTO> findByCriteria(OcrTasksCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<OcrTasks> specification = createSpecification(criteria);
        return ocrTasksMapper.toDto(ocrTasksRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link OcrTasksDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OcrTasksDTO> findByCriteria(OcrTasksCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<OcrTasks> specification = createSpecification(criteria);
        return ocrTasksRepository.findAll(specification, page).map(ocrTasksMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OcrTasksCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<OcrTasks> specification = createSpecification(criteria);
        return ocrTasksRepository.count(specification);
    }

    /**
     * Function to convert {@link OcrTasksCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<OcrTasks> createSpecification(OcrTasksCriteria criteria) {
        Specification<OcrTasks> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), OcrTasks_.id));
            }
            if (criteria.getMediaId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMediaId(), OcrTasks_.mediaId));
            }
            if (criteria.getPageNumber() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPageNumber(), OcrTasks_.pageNumber));
            }
            if (criteria.getJobStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getJobStatus(), OcrTasks_.jobStatus));
            }
            if (criteria.getCreateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreateTime(), OcrTasks_.createTime));
            }
            if (criteria.getStartTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartTime(), OcrTasks_.startTime));
            }
            if (criteria.getStopTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStopTime(), OcrTasks_.stopTime));
            }
        }
        return specification;
    }
}
