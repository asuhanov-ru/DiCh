package org.jhipster.dich.service;

import java.util.List;
import javax.persistence.criteria.JoinType;
import org.jhipster.dich.domain.*; // for static metamodels
import org.jhipster.dich.domain.PageImage;
import org.jhipster.dich.repository.PageImageRepository;
import org.jhipster.dich.service.criteria.PageImageCriteria;
import org.jhipster.dich.service.dto.PageImageDTO;
import org.jhipster.dich.service.mapper.PageImageMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link PageImage} entities in the database.
 * The main input is a {@link PageImageCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PageImageDTO} or a {@link Page} of {@link PageImageDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PageImageQueryService extends QueryService<PageImage> {

    private final Logger log = LoggerFactory.getLogger(PageImageQueryService.class);

    private final PageImageRepository pageImageRepository;

    private final PageImageMapper pageImageMapper;

    public PageImageQueryService(PageImageRepository pageImageRepository, PageImageMapper pageImageMapper) {
        this.pageImageRepository = pageImageRepository;
        this.pageImageMapper = pageImageMapper;
    }

    /**
     * Return a {@link List} of {@link PageImageDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PageImageDTO> findByCriteria(PageImageCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PageImage> specification = createSpecification(criteria);
        return pageImageMapper.toDto(pageImageRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PageImageDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PageImageDTO> findByCriteria(PageImageCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PageImage> specification = createSpecification(criteria);
        return pageImageRepository.findAll(specification, page).map(pageImageMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PageImageCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PageImage> specification = createSpecification(criteria);
        return pageImageRepository.count(specification);
    }

    /**
     * Function to convert {@link PageImageCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PageImage> createSpecification(PageImageCriteria criteria) {
        Specification<PageImage> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PageImage_.id));
            }
            if (criteria.getImage_file_name() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImage_file_name(), PageImage_.image_file_name));
            }
            if (criteria.getPageWordId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPageWordId(),
                            root -> root.join(PageImage_.pageWords, JoinType.LEFT).get(PageWord_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
