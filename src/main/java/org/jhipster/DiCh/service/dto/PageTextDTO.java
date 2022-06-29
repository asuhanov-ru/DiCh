package org.jhipster.dich.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link org.jhipster.dich.domain.PageText} entity.
 */
public class PageTextDTO implements Serializable {

    private Long id;

    private String page_id;

    @Lob
    private String text;

    private PageImageDTO pageImage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPage_id() {
        return page_id;
    }

    public void setPage_id(String page_id) {
        this.page_id = page_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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
        if (!(o instanceof PageTextDTO)) {
            return false;
        }

        PageTextDTO pageTextDTO = (PageTextDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, pageTextDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PageTextDTO{" +
            "id=" + getId() +
            ", page_id='" + getPage_id() + "'" +
            ", text='" + getText() + "'" +
            ", pageImage=" + getPageImage() +
            "}";
    }
}
