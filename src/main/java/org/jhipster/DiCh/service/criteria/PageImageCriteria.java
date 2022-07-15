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
 * Criteria class for the {@link org.jhipster.dich.domain.PageImage} entity. This class is used
 * in {@link org.jhipster.dich.web.rest.PageImageResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /page-images?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class PageImageCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter image_file_name;

    private LongFilter pageWordId;

    private Boolean distinct;

    public PageImageCriteria() {}

    public PageImageCriteria(PageImageCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.image_file_name = other.image_file_name == null ? null : other.image_file_name.copy();
        this.pageWordId = other.pageWordId == null ? null : other.pageWordId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public PageImageCriteria copy() {
        return new PageImageCriteria(this);
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

    public StringFilter getImage_file_name() {
        return image_file_name;
    }

    public StringFilter image_file_name() {
        if (image_file_name == null) {
            image_file_name = new StringFilter();
        }
        return image_file_name;
    }

    public void setImage_file_name(StringFilter image_file_name) {
        this.image_file_name = image_file_name;
    }

    public LongFilter getPageWordId() {
        return pageWordId;
    }

    public LongFilter pageWordId() {
        if (pageWordId == null) {
            pageWordId = new LongFilter();
        }
        return pageWordId;
    }

    public void setPageWordId(LongFilter pageWordId) {
        this.pageWordId = pageWordId;
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
        final PageImageCriteria that = (PageImageCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(image_file_name, that.image_file_name) &&
            Objects.equals(pageWordId, that.pageWordId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, image_file_name, pageWordId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PageImageCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (image_file_name != null ? "image_file_name=" + image_file_name + ", " : "") +
            (pageWordId != null ? "pageWordId=" + pageWordId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
