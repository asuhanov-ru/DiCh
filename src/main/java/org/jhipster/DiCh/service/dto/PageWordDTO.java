package org.jhipster.dich.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link org.jhipster.dich.domain.PageWord} entity.
 */
public class PageWordDTO implements Serializable {

    private Long id;

    private String s_word;

    private Long n_top;

    private Long n_left;

    private Long n_heigth;

    private Long n_width;

    private Long n_idx;

    private Long mediaId;

    private Integer pageNumber;

    private ZonedDateTime version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String gets_word() {
        return s_word;
    }

    public void sets_word(String s_word) {
        this.s_word = s_word;
    }

    public Long getn_top() {
        return n_top;
    }

    public void setn_top(Long n_top) {
        this.n_top = n_top;
    }

    public Long getn_left() {
        return n_left;
    }

    public void setn_left(Long n_left) {
        this.n_left = n_left;
    }

    public Long getn_heigth() {
        return n_heigth;
    }

    public void setn_heigth(Long n_heigth) {
        this.n_heigth = n_heigth;
    }

    public Long getn_width() {
        return n_width;
    }

    public void setn_width(Long n_width) {
        this.n_width = n_width;
    }

    public Long getn_idx() {
        return n_idx;
    }

    public void setn_idx(Long n_idx) {
        this.n_idx = n_idx;
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

    public ZonedDateTime getVersion() {
        return version;
    }

    public void setVersion(ZonedDateTime version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PageWordDTO)) {
            return false;
        }

        PageWordDTO pageWordDTO = (PageWordDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, pageWordDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PageWordDTO{" +
            "id=" + getId() +
            ", s_word='" + gets_word() + "'" +
            ", n_top=" + getn_top() +
            ", n_left=" + getn_left() +
            ", n_heigth=" + getn_heigth() +
            ", n_width=" + getn_width() +
            ", n_idx=" + getn_idx() +
            ", mediaId=" + getMediaId() +
            ", pageNumber=" + getPageNumber() +
            ", version='" + getVersion() + "'" +
            "}";
    }
}
