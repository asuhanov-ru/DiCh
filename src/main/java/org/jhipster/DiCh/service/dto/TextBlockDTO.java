package org.jhipster.dich.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link org.jhipster.dich.domain.TextBlock} entity.
 */
public class TextBlockDTO implements Serializable {

    private Long id;

    private Integer pageNumber;

    private Integer blockIndex;

    private MediaDTO media;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getBlockIndex() {
        return blockIndex;
    }

    public void setBlockIndex(Integer blockIndex) {
        this.blockIndex = blockIndex;
    }

    public MediaDTO getMedia() {
        return media;
    }

    public void setMedia(MediaDTO media) {
        this.media = media;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TextBlockDTO)) {
            return false;
        }

        TextBlockDTO textBlockDTO = (TextBlockDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, textBlockDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TextBlockDTO{" +
            "id=" + getId() +
            ", pageNumber=" + getPageNumber() +
            ", blockIndex=" + getBlockIndex() +
            ", media=" + getMedia() +
            "}";
    }
}
