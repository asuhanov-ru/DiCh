package org.jhipster.dich.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A PageLayout.
 */
@Entity
@Table(name = "page_layout")
public class PageLayout implements Serializable {

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

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PageLayout id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMediaId() {
        return this.mediaId;
    }

    public PageLayout mediaId(Long mediaId) {
        this.setMediaId(mediaId);
        return this;
    }

    public void setMediaId(Long mediaId) {
        this.mediaId = mediaId;
    }

    public Integer getPageNumber() {
        return this.pageNumber;
    }

    public PageLayout pageNumber(Integer pageNumber) {
        this.setPageNumber(pageNumber);
        return this;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PageLayout)) {
            return false;
        }
        return id != null && id.equals(((PageLayout) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PageLayout{" +
            "id=" + getId() +
            ", mediaId=" + getMediaId() +
            ", pageNumber=" + getPageNumber() +
            "}";
    }
}
