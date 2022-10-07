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
import tech.jhipster.service.filter.ZonedDateTimeFilter;

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

    private StringFilter s_word;

    private LongFilter n_top;

    private LongFilter n_left;

    private LongFilter n_heigth;

    private LongFilter n_width;

    private LongFilter n_idx;

    private LongFilter mediaId;

    private IntegerFilter pageNumber;

    private ZonedDateTimeFilter version;

    private Boolean distinct;

    public PageWordCriteria() {}

    public PageWordCriteria(PageWordCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.s_word = other.s_word == null ? null : other.s_word.copy();
        this.n_top = other.n_top == null ? null : other.n_top.copy();
        this.n_left = other.n_left == null ? null : other.n_left.copy();
        this.n_heigth = other.n_heigth == null ? null : other.n_heigth.copy();
        this.n_width = other.n_width == null ? null : other.n_width.copy();
        this.n_idx = other.n_idx == null ? null : other.n_idx.copy();
        this.mediaId = other.mediaId == null ? null : other.mediaId.copy();
        this.pageNumber = other.pageNumber == null ? null : other.pageNumber.copy();
        this.version = other.version == null ? null : other.version.copy();
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

    public StringFilter gets_word() {
        return s_word;
    }

    public StringFilter s_word() {
        if (s_word == null) {
            s_word = new StringFilter();
        }
        return s_word;
    }

    public void sets_word(StringFilter s_word) {
        this.s_word = s_word;
    }

    public LongFilter getn_top() {
        return n_top;
    }

    public LongFilter n_top() {
        if (n_top == null) {
            n_top = new LongFilter();
        }
        return n_top;
    }

    public void setn_top(LongFilter n_top) {
        this.n_top = n_top;
    }

    public LongFilter getn_left() {
        return n_left;
    }

    public LongFilter n_left() {
        if (n_left == null) {
            n_left = new LongFilter();
        }
        return n_left;
    }

    public void setn_left(LongFilter n_left) {
        this.n_left = n_left;
    }

    public LongFilter getn_heigth() {
        return n_heigth;
    }

    public LongFilter n_heigth() {
        if (n_heigth == null) {
            n_heigth = new LongFilter();
        }
        return n_heigth;
    }

    public void setn_heigth(LongFilter n_heigth) {
        this.n_heigth = n_heigth;
    }

    public LongFilter getn_width() {
        return n_width;
    }

    public LongFilter n_width() {
        if (n_width == null) {
            n_width = new LongFilter();
        }
        return n_width;
    }

    public void setn_width(LongFilter n_width) {
        this.n_width = n_width;
    }

    public LongFilter getn_idx() {
        return n_idx;
    }

    public LongFilter n_idx() {
        if (n_idx == null) {
            n_idx = new LongFilter();
        }
        return n_idx;
    }

    public void setn_idx(LongFilter n_idx) {
        this.n_idx = n_idx;
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

    public ZonedDateTimeFilter getVersion() {
        return version;
    }

    public ZonedDateTimeFilter version() {
        if (version == null) {
            version = new ZonedDateTimeFilter();
        }
        return version;
    }

    public void setVersion(ZonedDateTimeFilter version) {
        this.version = version;
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
            Objects.equals(s_word, that.s_word) &&
            Objects.equals(n_top, that.n_top) &&
            Objects.equals(n_left, that.n_left) &&
            Objects.equals(n_heigth, that.n_heigth) &&
            Objects.equals(n_width, that.n_width) &&
            Objects.equals(n_idx, that.n_idx) &&
            Objects.equals(mediaId, that.mediaId) &&
            Objects.equals(pageNumber, that.pageNumber) &&
            Objects.equals(version, that.version) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, s_word, n_top, n_left, n_heigth, n_width, n_idx, mediaId, pageNumber, version, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PageWordCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (s_word != null ? "s_word=" + s_word + ", " : "") +
            (n_top != null ? "n_top=" + n_top + ", " : "") +
            (n_left != null ? "n_left=" + n_left + ", " : "") +
            (n_heigth != null ? "n_heigth=" + n_heigth + ", " : "") +
            (n_width != null ? "n_width=" + n_width + ", " : "") +
            (n_idx != null ? "n_idx=" + n_idx + ", " : "") +
            (mediaId != null ? "mediaId=" + mediaId + ", " : "") +
            (pageNumber != null ? "pageNumber=" + pageNumber + ", " : "") +
            (version != null ? "version=" + version + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
