package org.jhipster.dich.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link org.jhipster.dich.domain.PageImage} entity.
 */
public class PageImageDTO implements Serializable {

    private Long id;

    private String image_file_name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImage_file_name() {
        return image_file_name;
    }

    public void setImage_file_name(String image_file_name) {
        this.image_file_name = image_file_name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PageImageDTO)) {
            return false;
        }

        PageImageDTO pageImageDTO = (PageImageDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, pageImageDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PageImageDTO{" +
            "id=" + getId() +
            ", image_file_name='" + getImage_file_name() + "'" +
            "}";
    }
}
