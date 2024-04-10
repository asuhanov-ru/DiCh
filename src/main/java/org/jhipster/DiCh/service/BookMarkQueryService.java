package org.jhipster.dich.service;

import java.util.List;
import javax.persistence.criteria.JoinType;
import org.jhipster.dich.domain.*; // for static metamodels
import org.jhipster.dich.domain.BookMark;
import org.jhipster.dich.repository.BookMarkRepository;
import org.jhipster.dich.service.criteria.BookMarkCriteria;
import org.jhipster.dich.service.dto.BookMarkDTO;
import org.jhipster.dich.service.mapper.BookMarkMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link BookMark} entities in the database.
 * The main input is a {@link BookMarkCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BookMarkDTO} or a {@link Page} of {@link BookMarkDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BookMarkQueryService extends QueryService<BookMark> {

    private final Logger log = LoggerFactory.getLogger(BookMarkQueryService.class);

    private final BookMarkRepository bookMarkRepository;

    private final BookMarkMapper bookMarkMapper;

    public BookMarkQueryService(BookMarkRepository bookMarkRepository, BookMarkMapper bookMarkMapper) {
        this.bookMarkRepository = bookMarkRepository;
        this.bookMarkMapper = bookMarkMapper;
    }

    /**
     * Return a {@link List} of {@link BookMarkDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BookMarkDTO> findByCriteria(BookMarkCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<BookMark> specification = createSpecification(criteria);
        return bookMarkMapper.toDto(bookMarkRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link BookMarkDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BookMarkDTO> findByCriteria(BookMarkCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<BookMark> specification = createSpecification(criteria);
        return bookMarkRepository.findAll(specification, page).map(bookMarkMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BookMarkCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<BookMark> specification = createSpecification(criteria);
        return bookMarkRepository.count(specification);
    }

    /**
     * Function to convert {@link BookMarkCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<BookMark> createSpecification(BookMarkCriteria criteria) {
        Specification<BookMark> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), BookMark_.id));
            }
            if (criteria.getBookMarkUUID() != null) {
                specification = specification.and(buildSpecification(criteria.getBookMarkUUID(), BookMark_.bookMarkUUID));
            }
            if (criteria.getMediaId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMediaId(), BookMark_.mediaId));
            }
            if (criteria.getPageNumber() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPageNumber(), BookMark_.pageNumber));
            }
            if (criteria.getTextBlockUUID() != null) {
                specification = specification.and(buildSpecification(criteria.getTextBlockUUID(), BookMark_.textBlockUUID));
            }
            if (criteria.getAnchor() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAnchor(), BookMark_.anchor));
            }
            if (criteria.getLabel() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLabel(), BookMark_.label));
            }
        }
        return specification;
    }
}
