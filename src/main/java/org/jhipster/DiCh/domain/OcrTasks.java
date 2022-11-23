package org.jhipster.dich.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;

/**
 * A OcrTasks.
 */
@Entity
@Table(name = "ocr_tasks")
public class OcrTasks implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "media_id")
    private Long mediaId;

    @Column(name = "page_number")
    private Integer pageNumber;

    @Column(name = "job_status")
    private String jobStatus;

    @Column(name = "create_time")
    private ZonedDateTime createTime;

    @Column(name = "start_time")
    private ZonedDateTime startTime;

    @Column(name = "stop_time")
    private ZonedDateTime stopTime;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public OcrTasks id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMediaId() {
        return this.mediaId;
    }

    public OcrTasks mediaId(Long mediaId) {
        this.setMediaId(mediaId);
        return this;
    }

    public void setMediaId(Long mediaId) {
        this.mediaId = mediaId;
    }

    public Integer getPageNumber() {
        return this.pageNumber;
    }

    public OcrTasks pageNumber(Integer pageNumber) {
        this.setPageNumber(pageNumber);
        return this;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getJobStatus() {
        return this.jobStatus;
    }

    public OcrTasks jobStatus(String jobStatus) {
        this.setJobStatus(jobStatus);
        return this;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    public ZonedDateTime getCreateTime() {
        return this.createTime;
    }

    public OcrTasks createTime(ZonedDateTime createTime) {
        this.setCreateTime(createTime);
        return this;
    }

    public void setCreateTime(ZonedDateTime createTime) {
        this.createTime = createTime;
    }

    public ZonedDateTime getStartTime() {
        return this.startTime;
    }

    public OcrTasks startTime(ZonedDateTime startTime) {
        this.setStartTime(startTime);
        return this;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    public ZonedDateTime getStopTime() {
        return this.stopTime;
    }

    public OcrTasks stopTime(ZonedDateTime stopTime) {
        this.setStopTime(stopTime);
        return this;
    }

    public void setStopTime(ZonedDateTime stopTime) {
        this.stopTime = stopTime;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OcrTasks)) {
            return false;
        }
        return id != null && id.equals(((OcrTasks) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OcrTasks{" +
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
