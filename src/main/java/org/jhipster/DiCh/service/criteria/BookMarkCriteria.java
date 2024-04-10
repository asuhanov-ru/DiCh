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
import tech.jhipster.service.filter.UUIDFilter;

/**
 * Criteria class for the {@link org.jhipster.dich.domain.BookMark} entity. This class is used
 * in {@link org.jhipster.dich.web.rest.BookMarkResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /book-marks?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class BookMarkCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private UUIDFilter bookMarkUUID;

    private LongFilter mediaId;

    private IntegerFilter pageNumber;

    private UUIDFilter textBlockUUID;

    private IntegerFilter anchor;

    private StringFilter label;

    private Boolean distinct;

    public BookMarkCriteria() {}

    public BookMarkCriteria(BookMarkCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.bookMarkUUID = other.bookMarkUUID == null ? null : other.bookMarkUUID.copy();
        this.mediaId = other.mediaId == null ? null : other.mediaId.copy();
        this.pageNumber = other.pageNumber == null ? null : other.pageNumber.copy();
        this.textBlockUUID = other.textBlockUUID == null ? null : other.textBlockUUID.copy();
        this.anchor = other.anchor == null ? null : other.anchor.copy();
        this.label = other.label == null ? null : other.label.copy();
        this.distinct = other.distinct;
    }

    @Override
    public BookMarkCriteria copy() {
        return new BookMarkCriteria(this);
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

    public UUIDFilter getBookMarkUUID() {
        return bookMarkUUID;
    }

    public UUIDFilter bookMarkUUID() {
        if (bookMarkUUID == null) {
            bookMarkUUID = new UUIDFilter();
        }
        return bookMarkUUID;
    }

    public void setBookMarkUUID(UUIDFilter bookMarkUUID) {
        this.bookMarkUUID = bookMarkUUID;
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

    public UUIDFilter getTextBlockUUID() {
        return textBlockUUID;
    }

    public UUIDFilter textBlockUUID() {
        if (textBlockUUID == null) {
            textBlockUUID = new UUIDFilter();
        }
        return textBlockUUID;
    }

    public void setTextBlockUUID(UUIDFilter textBlockUUID) {
        this.textBlockUUID = textBlockUUID;
    }

    public IntegerFilter getAnchor() {
        return anchor;
    }

    public IntegerFilter anchor() {
        if (anchor == null) {
            anchor = new IntegerFilter();
        }
        return anchor;
    }

    public void setAnchor(IntegerFilter anchor) {
        this.anchor = anchor;
    }

    public StringFilter getLabel() {
        return label;
    }

    public StringFilter label() {
        if (label == null) {
            label = new StringFilter();
        }
        return label;
    }

    public void setLabel(StringFilter label) {
        this.label = label;
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
        final BookMarkCriteria that = (BookMarkCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(bookMarkUUID, that.bookMarkUUID) &&
            Objects.equals(mediaId, that.mediaId) &&
            Objects.equals(pageNumber, that.pageNumber) &&
            Objects.equals(textBlockUUID, that.textBlockUUID) &&
            Objects.equals(anchor, that.anchor) &&
            Objects.equals(label, that.label) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bookMarkUUID, mediaId, pageNumber, textBlockUUID, anchor, label, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BookMarkCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (bookMarkUUID != null ? "bookMarkUUID=" + bookMarkUUID + ", " : "") +
            (mediaId != null ? "mediaId=" + mediaId + ", " : "") +
            (pageNumber != null ? "pageNumber=" + pageNumber + ", " : "") +
            (textBlockUUID != null ? "textBlockUUID=" + textBlockUUID + ", " : "") +
            (anchor != null ? "anchor=" + anchor + ", " : "") +
            (label != null ? "label=" + label + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
