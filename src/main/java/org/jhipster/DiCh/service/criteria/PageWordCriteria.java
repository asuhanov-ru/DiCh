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
 * Criteria class for the {@link org.jhipster.dich.domain.PageWord} entity. This class is used
 * in {@link org.jhipster.dich.web.rest.PageWordResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /page-words?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class PageWordCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter word;

    private LongFilter left;

    private LongFilter top;

    private LongFilter right;

    private LongFilter bottom;

    private LongFilter pageImageId;

    private Boolean distinct;

    public PageWordCriteria() {}

    public PageWordCriteria(PageWordCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.word = other.word == null ? null : other.word.copy();
        this.left = other.left == null ? null : other.left.copy();
        this.top = other.top == null ? null : other.top.copy();
        this.right = other.right == null ? null : other.right.copy();
        this.bottom = other.bottom == null ? null : other.bottom.copy();
        this.pageImageId = other.pageImageId == null ? null : other.pageImageId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public PageWordCriteria copy() {
        return new PageWordCriteria(this);
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

    public StringFilter getWord() {
        return word;
    }

    public StringFilter word() {
        if (word == null) {
            word = new StringFilter();
        }
        return word;
    }

    public void setWord(StringFilter word) {
        this.word = word;
    }

    public LongFilter getLeft() {
        return left;
    }

    public LongFilter left() {
        if (left == null) {
            left = new LongFilter();
        }
        return left;
    }

    public void setLeft(LongFilter left) {
        this.left = left;
    }

    public LongFilter getTop() {
        return top;
    }

    public LongFilter top() {
        if (top == null) {
            top = new LongFilter();
        }
        return top;
    }

    public void setTop(LongFilter top) {
        this.top = top;
    }

    public LongFilter getRight() {
        return right;
    }

    public LongFilter right() {
        if (right == null) {
            right = new LongFilter();
        }
        return right;
    }

    public void setRight(LongFilter right) {
        this.right = right;
    }

    public LongFilter getBottom() {
        return bottom;
    }

    public LongFilter bottom() {
        if (bottom == null) {
            bottom = new LongFilter();
        }
        return bottom;
    }

    public void setBottom(LongFilter bottom) {
        this.bottom = bottom;
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
        final PageWordCriteria that = (PageWordCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(word, that.word) &&
            Objects.equals(left, that.left) &&
            Objects.equals(top, that.top) &&
            Objects.equals(right, that.right) &&
            Objects.equals(bottom, that.bottom) &&
            Objects.equals(pageImageId, that.pageImageId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, word, left, top, right, bottom, pageImageId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PageWordCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (word != null ? "word=" + word + ", " : "") +
            (left != null ? "left=" + left + ", " : "") +
            (top != null ? "top=" + top + ", " : "") +
            (right != null ? "right=" + right + ", " : "") +
            (bottom != null ? "bottom=" + bottom + ", " : "") +
            (pageImageId != null ? "pageImageId=" + pageImageId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
