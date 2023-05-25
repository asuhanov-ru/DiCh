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
 * Criteria class for the {@link org.jhipster.dich.domain.Book} entity. This class is used
 * in {@link org.jhipster.dich.web.rest.BookResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /books?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class BookCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private IntegerFilter mediaStartPage;

    private IntegerFilter mediaEndPage;

    private LongFilter authorId;

    private LongFilter mediaId;

    private Boolean distinct;

    public BookCriteria() {}

    public BookCriteria(BookCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.mediaStartPage = other.mediaStartPage == null ? null : other.mediaStartPage.copy();
        this.mediaEndPage = other.mediaEndPage == null ? null : other.mediaEndPage.copy();
        this.authorId = other.authorId == null ? null : other.authorId.copy();
        this.mediaId = other.mediaId == null ? null : other.mediaId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public BookCriteria copy() {
        return new BookCriteria(this);
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

    public StringFilter getName() {
        return name;
    }

    public StringFilter name() {
        if (name == null) {
            name = new StringFilter();
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public IntegerFilter getMediaStartPage() {
        return mediaStartPage;
    }

    public IntegerFilter mediaStartPage() {
        if (mediaStartPage == null) {
            mediaStartPage = new IntegerFilter();
        }
        return mediaStartPage;
    }

    public void setMediaStartPage(IntegerFilter mediaStartPage) {
        this.mediaStartPage = mediaStartPage;
    }

    public IntegerFilter getMediaEndPage() {
        return mediaEndPage;
    }

    public IntegerFilter mediaEndPage() {
        if (mediaEndPage == null) {
            mediaEndPage = new IntegerFilter();
        }
        return mediaEndPage;
    }

    public void setMediaEndPage(IntegerFilter mediaEndPage) {
        this.mediaEndPage = mediaEndPage;
    }

    public LongFilter getAuthorId() {
        return authorId;
    }

    public LongFilter authorId() {
        if (authorId == null) {
            authorId = new LongFilter();
        }
        return authorId;
    }

    public void setAuthorId(LongFilter authorId) {
        this.authorId = authorId;
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
        final BookCriteria that = (BookCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(mediaStartPage, that.mediaStartPage) &&
            Objects.equals(mediaEndPage, that.mediaEndPage) &&
            Objects.equals(authorId, that.authorId) &&
            Objects.equals(mediaId, that.mediaId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, mediaStartPage, mediaEndPage, authorId, mediaId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BookCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (mediaStartPage != null ? "mediaStartPage=" + mediaStartPage + ", " : "") +
            (mediaEndPage != null ? "mediaEndPage=" + mediaEndPage + ", " : "") +
            (authorId != null ? "authorId=" + authorId + ", " : "") +
            (mediaId != null ? "mediaId=" + mediaId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
