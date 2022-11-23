package org.jhipster.dich.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link org.jhipster.dich.domain.OcrTasks} entity.
 */
public class OcrTasksDTO implements Serializable {

    private Long id;

    private Long mediaId;

    private Integer pageNumber;

    private String jobStatus;

    private ZonedDateTime createTime;

    private ZonedDateTime startTime;

    private ZonedDateTime stopTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMediaId() {
        return mediaId;
    }

    public void setMediaId(Long mediaId) {
        this.mediaId = mediaId;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    public ZonedDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(ZonedDateTime createTime) {
        this.createTime = createTime;
    }

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    public ZonedDateTime getStopTime() {
        return stopTime;
    }

    public void setStopTime(ZonedDateTime stopTime) {
        this.stopTime = stopTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OcrTasksDTO)) {
            return false;
        }

        OcrTasksDTO ocrTasksDTO = (OcrTasksDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ocrTasksDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OcrTasksDTO{" +
            "id=" + getId() +
            ", mediaId=" + getMediaId() +
            ", pageNumber=" + getPageNumber() +
            ", jobStatus='" + getJobStatus() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", startTime='" + getStartTime() + "'" +
            ", stopTime='" + getStopTime() + "'" +
            "}";
    }
}
