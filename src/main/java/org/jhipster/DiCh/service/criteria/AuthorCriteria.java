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
 * Criteria class for the {@link org.jhipster.dich.domain.Author} entity. This class is used
 * in {@link org.jhipster.dich.web.rest.AuthorResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /authors?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class AuthorCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter callsign;

    private LongFilter bookId;

    private Boolean distinct;

    public AuthorCriteria() {}

    public AuthorCriteria(AuthorCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.callsign = other.callsign == null ? null : other.callsign.copy();
        this.bookId = other.bookId == null ? null : other.bookId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public AuthorCriteria copy() {
        return new AuthorCriteria(this);
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

    public StringFilter getCallsign() {
        return callsign;
    }

    public StringFilter callsign() {
        if (callsign == null) {
            callsign = new StringFilter();
        }
        return callsign;
    }

    public void setCallsign(StringFilter callsign) {
        this.callsign = callsign;
    }

    public LongFilter getBookId() {
        return bookId;
    }

    public LongFilter bookId() {
        if (bookId == null) {
            bookId = new LongFilter();
        }
        return bookId;
    }

    public void setBookId(LongFilter bookId) {
        this.bookId = bookId;
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
        final AuthorCriteria that = (AuthorCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(callsign, that.callsign) &&
            Objects.equals(bookId, that.bookId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, callsign, bookId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AuthorCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (callsign != null ? "callsign=" + callsign + ", " : "") +
            (bookId != null ? "bookId=" + bookId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
