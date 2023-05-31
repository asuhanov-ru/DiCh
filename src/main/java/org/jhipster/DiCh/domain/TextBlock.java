package org.jhipster.dich.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
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

    @Column(name = "page_number")
    private Integer pageNumber;

    @Column(name = "block_index")
    private Integer blockIndex;

    @ManyToOne
    @JsonIgnoreProperties(value = { "collections", "books" }, allowSetters = true)
    private Media media;

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

    public Media getMedia() {
        return this.media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public TextBlock media(Media media) {
        this.setMedia(media);
        return this;
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
            ", pageNumber=" + getPageNumber() +
            ", blockIndex=" + getBlockIndex() +
            "}";
    }
}
