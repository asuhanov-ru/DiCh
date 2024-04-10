package org.jhipster.dich.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BigDecimalFilter;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;
import tech.jhipster.service.filter.UUIDFilter;

/**
 * Criteria class for the {@link org.jhipster.dich.domain.PageLayout} entity. This class is used
 * in {@link org.jhipster.dich.web.rest.PageLayoutResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /page-layouts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class PageLayoutCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter mediaId;

    private IntegerFilter pageNumber;

    private StringFilter iterator_level;

    private BigDecimalFilter rect_top;

    private BigDecimalFilter rect_left;

    private BigDecimalFilter rect_right;

    private BigDecimalFilter rect_bottom;

    private IntegerFilter parent_id;

    private UUIDFilter itemGUID;

    private UUIDFilter parentGUID;

    private Boolean distinct;

    public PageLayoutCriteria() {}

    public PageLayoutCriteria(PageLayoutCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.mediaId = other.mediaId == null ? null : other.mediaId.copy();
        this.pageNumber = other.pageNumber == null ? null : other.pageNumber.copy();
        this.iterator_level = other.iterator_level == null ? null : other.iterator_level.copy();
        this.rect_top = other.rect_top == null ? null : other.rect_top.copy();
        this.rect_left = other.rect_left == null ? null : other.rect_left.copy();
        this.rect_right = other.rect_right == null ? null : other.rect_right.copy();
        this.rect_bottom = other.rect_bottom == null ? null : other.rect_bottom.copy();
        this.parent_id = other.parent_id == null ? null : other.parent_id.copy();
        this.itemGUID = other.itemGUID == null ? null : other.itemGUID.copy();
        this.parentGUID = other.parentGUID == null ? null : other.parentGUID.copy();
        this.distinct = other.distinct;
    }

    @Override
    public PageLayoutCriteria copy() {
        return new PageLayoutCriteria(this);
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

    public LongFilter getMediaId() {
        return mediaId;
    }

    public LongFilter mediaId() {
        if (mediaId == null) {
            mediaId = new LongFilter();
        }
        return mediaId;
    }

    public void setMediaId(LongFilter mediaId) {
        this.mediaId = mediaId;
    }

    public IntegerFilter getPageNumber() {
        return pageNumber;
    }

    public IntegerFilter pageNumber() {
        if (pageNumber == null) {
            pageNumber = new IntegerFilter();
        }
        return pageNumber;
    }

    public void setPageNumber(IntegerFilter pageNumber) {
        this.pageNumber = pageNumber;
    }

    public StringFilter getIterator_level() {
        return iterator_level;
    }

    public StringFilter iterator_level() {
        if (iterator_level == null) {
            iterator_level = new StringFilter();
        }
        return iterator_level;
    }

    public void setIterator_level(StringFilter iterator_level) {
        this.iterator_level = iterator_level;
    }

    public BigDecimalFilter getRect_top() {
        return rect_top;
    }

    public BigDecimalFilter rect_top() {
        if (rect_top == null) {
            rect_top = new BigDecimalFilter();
        }
        return rect_top;
    }

    public void setRect_top(BigDecimalFilter rect_top) {
        this.rect_top = rect_top;
    }

    public BigDecimalFilter getRect_left() {
        return rect_left;
    }

    public BigDecimalFilter rect_left() {
        if (rect_left == null) {
            rect_left = new BigDecimalFilter();
        }
        return rect_left;
    }

    public void setRect_left(BigDecimalFilter rect_left) {
        this.rect_left = rect_left;
    }

    public BigDecimalFilter getRect_right() {
        return rect_right;
    }

    public BigDecimalFilter rect_right() {
        if (rect_right == null) {
            rect_right = new BigDecimalFilter();
        }
        return rect_right;
    }

    public void setRect_right(BigDecimalFilter rect_right) {
        this.rect_right = rect_right;
    }

    public BigDecimalFilter getRect_bottom() {
        return rect_bottom;
    }

    public BigDecimalFilter rect_bottom() {
        if (rect_bottom == null) {
            rect_bottom = new BigDecimalFilter();
        }
        return rect_bottom;
    }

    public void setRect_bottom(BigDecimalFilter rect_bottom) {
        this.rect_bottom = rect_bottom;
    }

    public IntegerFilter getParent_id() {
        return parent_id;
    }

    public IntegerFilter parent_id() {
        if (parent_id == null) {
            parent_id = new IntegerFilter();
        }
        return parent_id;
    }

    public void setParent_id(IntegerFilter parent_id) {
        this.parent_id = parent_id;
    }

    public UUIDFilter getItemGUID() {
        return itemGUID;
    }

    public UUIDFilter itemGUID() {
        if (itemGUID == null) {
            itemGUID = new UUIDFilter();
        }
        return itemGUID;
    }

    public void setItemGUID(UUIDFilter itemGUID) {
        this.itemGUID = itemGUID;
    }

    public UUIDFilter getParentGUID() {
        return parentGUID;
    }

    public UUIDFilter parentGUID() {
        if (parentGUID == null) {
            parentGUID = new UUIDFilter();
        }
        return parentGUID;
    }

    public void setParentGUID(UUIDFilter parentGUID) {
        this.parentGUID = parentGUID;
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
        final PageLayoutCriteria that = (PageLayoutCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(mediaId, that.mediaId) &&
            Objects.equals(pageNumber, that.pageNumber) &&
            Objects.equals(iterator_level, that.iterator_level) &&
            Objects.equals(rect_top, that.rect_top) &&
            Objects.equals(rect_left, that.rect_left) &&
            Objects.equals(rect_right, that.rect_right) &&
            Objects.equals(rect_bottom, that.rect_bottom) &&
            Objects.equals(parent_id, that.parent_id) &&
            Objects.equals(itemGUID, that.itemGUID) &&
            Objects.equals(parentGUID, that.parentGUID) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            mediaId,
            pageNumber,
            iterator_level,
            rect_top,
            rect_left,
            rect_right,
            rect_bottom,
            parent_id,
            itemGUID,
            parentGUID,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PageLayoutCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (mediaId != null ? "mediaId=" + mediaId + ", " : "") +
            (pageNumber != null ? "pageNumber=" + pageNumber + ", " : "") +
            (iterator_level != null ? "iterator_level=" + iterator_level + ", " : "") +
            (rect_top != null ? "rect_top=" + rect_top + ", " : "") +
            (rect_left != null ? "rect_left=" + rect_left + ", " : "") +
            (rect_right != null ? "rect_right=" + rect_right + ", " : "") +
            (rect_bottom != null ? "rect_bottom=" + rect_bottom + ", " : "") +
            (parent_id != null ? "parent_id=" + parent_id + ", " : "") +
            (itemGUID != null ? "itemGUID=" + itemGUID + ", " : "") +
            (parentGUID != null ? "parentGUID=" + parentGUID + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
