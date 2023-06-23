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
 * Criteria class for the {@link org.jhipster.dich.domain.TextBlock} entity. This class is used
 * in {@link org.jhipster.dich.web.rest.TextBlockResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /text-blocks?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class TextBlockCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter pageNumber;

    private IntegerFilter blockIndex;

    private UUIDFilter blockUUID;

    private LongFilter mediaId;

    private Boolean distinct;

    public TextBlockCriteria() {}

    public TextBlockCriteria(TextBlockCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.pageNumber = other.pageNumber == null ? null : other.pageNumber.copy();
        this.blockIndex = other.blockIndex == null ? null : other.blockIndex.copy();
        this.blockUUID = other.blockUUID == null ? null : other.blockUUID.copy();
        this.mediaId = other.mediaId == null ? null : other.mediaId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public TextBlockCriteria copy() {
        return new TextBlockCriteria(this);
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

    public IntegerFilter getBlockIndex() {
        return blockIndex;
    }

    public IntegerFilter blockIndex() {
        if (blockIndex == null) {
            blockIndex = new IntegerFilter();
        }
        return blockIndex;
    }

    public void setBlockIndex(IntegerFilter blockIndex) {
        this.blockIndex = blockIndex;
    }

    public UUIDFilter getBlockUUID() {
        return blockUUID;
    }

    public UUIDFilter blockUUID() {
        if (blockUUID == null) {
            blockUUID = new UUIDFilter();
        }
        return blockUUID;
    }

    public void setBlockUUID(UUIDFilter blockUUID) {
        this.blockUUID = blockUUID;
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
        final TextBlockCriteria that = (TextBlockCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(pageNumber, that.pageNumber) &&
            Objects.equals(blockIndex, that.blockIndex) &&
            Objects.equals(blockUUID, that.blockUUID) &&
            Objects.equals(mediaId, that.mediaId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pageNumber, blockIndex, blockUUID, mediaId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TextBlockCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (pageNumber != null ? "pageNumber=" + pageNumber + ", " : "") +
            (blockIndex != null ? "blockIndex=" + blockIndex + ", " : "") +
            (blockUUID != null ? "blockUUID=" + blockUUID + ", " : "") +
            (mediaId != null ? "mediaId=" + mediaId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
