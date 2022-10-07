package org.jhipster.dich.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link org.jhipster.dich.domain.PageLayout} entity.
 */
public class PageLayoutDTO implements Serializable {

    private Long id;

    private Long mediaId;

    private Integer pageNumber;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PageLayoutDTO)) {
            return false;
        }

        PageLayoutDTO pageLayoutDTO = (PageLayoutDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, pageLayoutDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PageLayoutDTO{" +
            "id=" + getId() +
            ", mediaId=" + getMediaId() +
            ", pageNumber=" + getPageNumber() +
            "}";
    }
}
