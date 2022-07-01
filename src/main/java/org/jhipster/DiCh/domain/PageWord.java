package org.jhipster.dich.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A PageWord.
 */
@Entity
@Table(name = "page_word")
public class PageWord implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "word")
    private String word;

    @Column(name = "jhi_left")
    private Long left;

    @Column(name = "top")
    private Long top;

    @Column(name = "jhi_right")
    private Long right;

    @Column(name = "bottom")
    private Long bottom;

    @ManyToOne
    private PageImage pageImage;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PageWord id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWord() {
        return this.word;
    }

    public PageWord word(String word) {
        this.setWord(word);
        return this;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Long getLeft() {
        return this.left;
    }

    public PageWord left(Long left) {
        this.setLeft(left);
        return this;
    }

    public void setLeft(Long left) {
        this.left = left;
    }

    public Long getTop() {
        return this.top;
    }

    public PageWord top(Long top) {
        this.setTop(top);
        return this;
    }

    public void setTop(Long top) {
        this.top = top;
    }

    public Long getRight() {
        return this.right;
    }

    public PageWord right(Long right) {
        this.setRight(right);
        return this;
    }

    public void setRight(Long right) {
        this.right = right;
    }

    public Long getBottom() {
        return this.bottom;
    }

    public PageWord bottom(Long bottom) {
        this.setBottom(bottom);
        return this;
    }

    public void setBottom(Long bottom) {
        this.bottom = bottom;
    }

    public PageImage getPageImage() {
        return this.pageImage;
    }

    public void setPageImage(PageImage pageImage) {
        this.pageImage = pageImage;
    }

    public PageWord pageImage(PageImage pageImage) {
        this.setPageImage(pageImage);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PageWord)) {
            return false;
        }
        return id != null && id.equals(((PageWord) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PageWord{" +
            "id=" + getId() +
            ", word='" + getWord() + "'" +
            ", left=" + getLeft() +
            ", top=" + getTop() +
            ", right=" + getRight() +
            ", bottom=" + getBottom() +
            "}";
    }
}
