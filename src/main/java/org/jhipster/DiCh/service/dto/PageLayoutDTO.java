package org.jhipster.dich.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link org.jhipster.dich.domain.PageLayout} entity.
 */
public class PageLayoutDTO implements Serializable {

    private Long id;

    private Long mediaId;

    private Integer pageNumber;

    private String iterator_level;

    private BigDecimal rect_top;

    private BigDecimal rect_left;

    private BigDecimal rect_right;

    private BigDecimal rect_bottom;

    private Integer parent_id;

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

    public String getIterator_level() {
        return iterator_level;
    }

    public void setIterator_level(String iterator_level) {
        this.iterator_level = iterator_level;
    }

    public BigDecimal getRect_top() {
        return rect_top;
    }

    public void setRect_top(BigDecimal rect_top) {
        this.rect_top = rect_top;
    }

    public BigDecimal getRect_left() {
        return rect_left;
    }

    public void setRect_left(BigDecimal rect_left) {
        this.rect_left = rect_left;
    }

    public BigDecimal getRect_right() {
        return rect_right;
    }

    public void setRect_right(BigDecimal rect_right) {
        this.rect_right = rect_right;
    }

    public BigDecimal getRect_bottom() {
        return rect_bottom;
    }

    public void setRect_bottom(BigDecimal rect_bottom) {
        this.rect_bottom = rect_bottom;
    }

    public Integer getParent_id() {
        return parent_id;
    }

    public void setParent_id(Integer parent_id) {
        this.parent_id = parent_id;
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
            ", iterator_level='" + getIterator_level() + "'" +
            ", rect_top=" + getRect_top() +
            ", rect_left=" + getRect_left() +
            ", rect_right=" + getRect_right() +
            ", rect_bottom=" + getRect_bottom() +
            ", parent_id=" + getParent_id() +
            "}";
    }
}
