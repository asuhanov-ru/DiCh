package org.jhipster.dich.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link org.jhipster.dich.domain.PageText} entity. This class is used
 * in {@link org.jhipster.dich.web.rest.PageTextResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /page-texts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class PageTextCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter page_id;

    private LongFilter pageImageId;

    private Boolean distinct;

    public PageTextCriteria() {}

    public PageTextCriteria(PageTextCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.page_id = other.page_id == null ? null : other.page_id.copy();
        this.pageImageId = other.pageImageId == null ? null : other.pageImageId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public PageTextCriteria copy() {
        return new PageTextCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getPage_id() {
        return page_id;
    }

    public StringFilter page_id() {
        if (page_id == null) {
            page_id = new StringFilter();
        }
        return page_id;
    }

    public void setPage_id(StringFilter page_id) {
        this.page_id = page_id;
    }

    public LongFilter getPageImageId() {
        return pageImageId;
    }

    public LongFilter pageImageId() {
        if (pageImageId == null) {
            pageImageId = new LongFilter();
        }
        return pageImageId;
    }

    public void setPageImageId(LongFilter pageImageId) {
        this.pageImageId = pageImageId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PageTextCriteria that = (PageTextCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(page_id, that.page_id) &&
            Objects.equals(pageImageId, that.pageImageId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, page_id, pageImageId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PageTextCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (page_id != null ? "page_id=" + page_id + ", " : "") +
            (pageImageId != null ? "pageImageId=" + pageImageId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
