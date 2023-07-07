package org.jhipster.dich.domain;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.*;

/**
 * A TextBlock.
 */
@Entity
@Table(name = "text_block")
public class TextBlock implements Serializable {

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

    @Column(name = "block_index")
    private Integer blockIndex;

    @Column(name = "block_uuid")
    private UUID blockUUID;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TextBlock id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMediaId() {
        return this.mediaId;
    }

    public TextBlock mediaId(Long mediaId) {
        this.setMediaId(mediaId);
        return this;
    }

    public void setMediaId(Long mediaId) {
        this.mediaId = mediaId;
    }

    public Integer getPageNumber() {
        return this.pageNumber;
    }

    public TextBlock pageNumber(Integer pageNumber) {
        this.setPageNumber(pageNumber);
        return this;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getBlockIndex() {
        return this.blockIndex;
    }

    public TextBlock blockIndex(Integer blockIndex) {
        this.setBlockIndex(blockIndex);
        return this;
    }

    public void setBlockIndex(Integer blockIndex) {
        this.blockIndex = blockIndex;
    }

    public UUID getBlockUUID() {
        return this.blockUUID;
    }

    public TextBlock blockUUID(UUID blockUUID) {
        this.setBlockUUID(blockUUID);
        return this;
    }

    public void setBlockUUID(UUID blockUUID) {
        this.blockUUID = blockUUID;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TextBlock)) {
            return false;
        }
        return id != null && id.equals(((TextBlock) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TextBlock{" +
            "id=" + getId() +
            ", mediaId=" + getMediaId() +
            ", pageNumber=" + getPageNumber() +
            ", blockIndex=" + getBlockIndex() +
            ", blockUUID='" + getBlockUUID() + "'" +
            "}";
    }
}
