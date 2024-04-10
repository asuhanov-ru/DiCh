package org.jhipster.dich.service.dto;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link org.jhipster.dich.domain.BookMark} entity.
 */
public class BookMarkDTO implements Serializable {

    private Long id;

    private UUID bookMarkUUID;

    private Long mediaId;

    private Integer pageNumber;

    private UUID textBlockUUID;

    private Integer anchor;

    private String label;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getBookMarkUUID() {
        return bookMarkUUID;
    }

    public void setBookMarkUUID(UUID bookMarkUUID) {
        this.bookMarkUUID = bookMarkUUID;
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

    public UUID getTextBlockUUID() {
        return textBlockUUID;
    }

    public void setTextBlockUUID(UUID textBlockUUID) {
        this.textBlockUUID = textBlockUUID;
    }

    public Integer getAnchor() {
        return anchor;
    }

    public void setAnchor(Integer anchor) {
        this.anchor = anchor;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BookMarkDTO)) {
            return false;
        }

        BookMarkDTO bookMarkDTO = (BookMarkDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, bookMarkDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BookMarkDTO{" +
            "id=" + getId() +
            ", bookMarkUUID='" + getBookMarkUUID() + "'" +
            ", mediaId=" + getMediaId() +
            ", pageNumber=" + getPageNumber() +
            ", textBlockUUID='" + getTextBlockUUID() + "'" +
            ", anchor=" + getAnchor() +
            ", label='" + getLabel() + "'" +
            "}";
    }
}
