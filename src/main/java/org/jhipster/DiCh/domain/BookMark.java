package org.jhipster.dich.domain;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.*;

/**
 * A BookMark.
 */
@Entity
@Table(name = "book_mark")
public class BookMark implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "book_mark_uuid")
    private UUID bookMarkUUID;

    @Column(name = "media_id")
    private Long mediaId;

    @Column(name = "page_number")
    private Integer pageNumber;

    @Column(name = "text_block_uuid")
    private UUID textBlockUUID;

    @Column(name = "anchor")
    private Integer anchor;

    @Column(name = "label")
    private String label;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BookMark id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getBookMarkUUID() {
        return this.bookMarkUUID;
    }

    public BookMark bookMarkUUID(UUID bookMarkUUID) {
        this.setBookMarkUUID(bookMarkUUID);
        return this;
    }

    public void setBookMarkUUID(UUID bookMarkUUID) {
        this.bookMarkUUID = bookMarkUUID;
    }

    public Long getMediaId() {
        return this.mediaId;
    }

    public BookMark mediaId(Long mediaId) {
        this.setMediaId(mediaId);
        return this;
    }

    public void setMediaId(Long mediaId) {
        this.mediaId = mediaId;
    }

    public Integer getPageNumber() {
        return this.pageNumber;
    }

    public BookMark pageNumber(Integer pageNumber) {
        this.setPageNumber(pageNumber);
        return this;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public UUID getTextBlockUUID() {
        return this.textBlockUUID;
    }

    public BookMark textBlockUUID(UUID textBlockUUID) {
        this.setTextBlockUUID(textBlockUUID);
        return this;
    }

    public void setTextBlockUUID(UUID textBlockUUID) {
        this.textBlockUUID = textBlockUUID;
    }

    public Integer getAnchor() {
        return this.anchor;
    }

    public BookMark anchor(Integer anchor) {
        this.setAnchor(anchor);
        return this;
    }

    public void setAnchor(Integer anchor) {
        this.anchor = anchor;
    }

    public String getLabel() {
        return this.label;
    }

    public BookMark label(String label) {
        this.setLabel(label);
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BookMark)) {
            return false;
        }
        return id != null && id.equals(((BookMark) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BookMark{" +
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
