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
 * Criteria class for the {@link org.jhipster.dich.domain.OcrTasks} entity. This class is used
 * in {@link org.jhipster.dich.web.rest.OcrTasksResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /ocr-tasks?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class OcrTasksCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter mediaId;

    private IntegerFilter pageNumber;

    private StringFilter jobStatus;

    private ZonedDateTimeFilter createTime;

    private ZonedDateTimeFilter startTime;

    private ZonedDateTimeFilter stopTime;

    private Boolean distinct;

    public OcrTasksCriteria() {}

    public OcrTasksCriteria(OcrTasksCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.mediaId = other.mediaId == null ? null : other.mediaId.copy();
        this.pageNumber = other.pageNumber == null ? null : other.pageNumber.copy();
        this.jobStatus = other.jobStatus == null ? null : other.jobStatus.copy();
        this.createTime = other.createTime == null ? null : other.createTime.copy();
        this.startTime = other.startTime == null ? null : other.startTime.copy();
        this.stopTime = other.stopTime == null ? null : other.stopTime.copy();
        this.distinct = other.distinct;
    }

    @Override
    public OcrTasksCriteria copy() {
        return new OcrTasksCriteria(this);
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

    public StringFilter getJobStatus() {
        return jobStatus;
    }

    public StringFilter jobStatus() {
        if (jobStatus == null) {
            jobStatus = new StringFilter();
        }
        return jobStatus;
    }

    public void setJobStatus(StringFilter jobStatus) {
        this.jobStatus = jobStatus;
    }

    public ZonedDateTimeFilter getCreateTime() {
        return createTime;
    }

    public ZonedDateTimeFilter createTime() {
        if (createTime == null) {
            createTime = new ZonedDateTimeFilter();
        }
        return createTime;
    }

    public void setCreateTime(ZonedDateTimeFilter createTime) {
        this.createTime = createTime;
    }

    public ZonedDateTimeFilter getStartTime() {
        return startTime;
    }

    public ZonedDateTimeFilter startTime() {
        if (startTime == null) {
            startTime = new ZonedDateTimeFilter();
        }
        return startTime;
    }

    public void setStartTime(ZonedDateTimeFilter startTime) {
        this.startTime = startTime;
    }

    public ZonedDateTimeFilter getStopTime() {
        return stopTime;
    }

    public ZonedDateTimeFilter stopTime() {
        if (stopTime == null) {
            stopTime = new ZonedDateTimeFilter();
        }
        return stopTime;
    }

    public void setStopTime(ZonedDateTimeFilter stopTime) {
        this.stopTime = stopTime;
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
        final OcrTasksCriteria that = (OcrTasksCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(mediaId, that.mediaId) &&
            Objects.equals(pageNumber, that.pageNumber) &&
            Objects.equals(jobStatus, that.jobStatus) &&
            Objects.equals(createTime, that.createTime) &&
            Objects.equals(startTime, that.startTime) &&
            Objects.equals(stopTime, that.stopTime) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, mediaId, pageNumber, jobStatus, createTime, startTime, stopTime, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OcrTasksCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (mediaId != null ? "mediaId=" + mediaId + ", " : "") +
            (pageNumber != null ? "pageNumber=" + pageNumber + ", " : "") +
            (jobStatus != null ? "jobStatus=" + jobStatus + ", " : "") +
            (createTime != null ? "createTime=" + createTime + ", " : "") +
            (startTime != null ? "startTime=" + startTime + ", " : "") +
            (stopTime != null ? "stopTime=" + stopTime + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
