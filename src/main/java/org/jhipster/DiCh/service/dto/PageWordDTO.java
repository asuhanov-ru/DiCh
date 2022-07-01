package org.jhipster.dich.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link org.jhipster.dich.domain.PageWord} entity.
 */
public class PageWordDTO implements Serializable {

    private Long id;

    private String word;

    private Long left;

    private Long top;

    private Long right;

    private Long bottom;

    private PageImageDTO pageImage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Long getLeft() {
        return left;
    }

    public void setLeft(Long left) {
        this.left = left;
    }

    public Long getTop() {
        return top;
    }

    public void setTop(Long top) {
        this.top = top;
    }

    public Long getRight() {
        return right;
    }

    public void setRight(Long right) {
        this.right = right;
    }

    public Long getBottom() {
        return bottom;
    }

    public void setBottom(Long bottom) {
        this.bottom = bottom;
    }

    public PageImageDTO getPageImage() {
        return pageImage;
    }

    public void setPageImage(PageImageDTO pageImage) {
        this.pageImage = pageImage;
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
            ", word='" + getWord() + "'" +
            ", left=" + getLeft() +
            ", top=" + getTop() +
            ", right=" + getRight() +
            ", bottom=" + getBottom() +
            ", pageImage=" + getPageImage() +
            "}";
    }
}
