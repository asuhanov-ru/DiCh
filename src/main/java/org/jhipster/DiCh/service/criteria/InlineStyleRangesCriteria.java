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
 * Criteria class for the {@link org.jhipster.dich.domain.InlineStyleRanges} entity. This class is used
 * in {@link org.jhipster.dich.web.rest.InlineStyleRangesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /inline-style-ranges?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class InlineStyleRangesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter startPos;

    private IntegerFilter stopPos;

    private LongFilter textBlockId;

    private Boolean distinct;

    public InlineStyleRangesCriteria() {}

    public InlineStyleRangesCriteria(InlineStyleRangesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.startPos = other.startPos == null ? null : other.startPos.copy();
        this.stopPos = other.stopPos == null ? null : other.stopPos.copy();
        this.textBlockId = other.textBlockId == null ? null : other.textBlockId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public InlineStyleRangesCriteria copy() {
        return new InlineStyleRangesCriteria(this);
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

    public IntegerFilter getStartPos() {
        return startPos;
    }

    public IntegerFilter startPos() {
        if (startPos == null) {
            startPos = new IntegerFilter();
        }
        return startPos;
    }

    public void setStartPos(IntegerFilter startPos) {
        this.startPos = startPos;
    }

    public IntegerFilter getStopPos() {
        return stopPos;
    }

    public IntegerFilter stopPos() {
        if (stopPos == null) {
            stopPos = new IntegerFilter();
        }
        return stopPos;
    }

    public void setStopPos(IntegerFilter stopPos) {
        this.stopPos = stopPos;
    }

    public LongFilter getTextBlockId() {
        return textBlockId;
    }

    public LongFilter textBlockId() {
        if (textBlockId == null) {
            textBlockId = new LongFilter();
        }
        return textBlockId;
    }

    public void setTextBlockId(LongFilter textBlockId) {
        this.textBlockId = textBlockId;
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
        final InlineStyleRangesCriteria that = (InlineStyleRangesCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(startPos, that.startPos) &&
            Objects.equals(stopPos, that.stopPos) &&
            Objects.equals(textBlockId, that.textBlockId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startPos, stopPos, textBlockId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InlineStyleRangesCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (startPos != null ? "startPos=" + startPos + ", " : "") +
            (stopPos != null ? "stopPos=" + stopPos + ", " : "") +
            (textBlockId != null ? "textBlockId=" + textBlockId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
