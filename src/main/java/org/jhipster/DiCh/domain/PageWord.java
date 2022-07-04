package org.jhipster.dich.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    @Column(name = "s_word")
    private String s_word;

    @Column(name = "n_top")
    private Long n_top;

    @Column(name = "n_left")
    private Long n_left;

    @Column(name = "n_heigth")
    private Long n_heigth;

    @Column(name = "n_width")
    private Long n_width;

    @Column(name = "n_idx")
    private Long n_idx;

    @ManyToOne
    @JsonIgnoreProperties(value = { "pageWords" }, allowSetters = true)
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

    public String gets_word() {
        return this.s_word;
    }

    public PageWord s_word(String s_word) {
        this.sets_word(s_word);
        return this;
    }

    public void sets_word(String s_word) {
        this.s_word = s_word;
    }

    public Long getn_top() {
        return this.n_top;
    }

    public PageWord n_top(Long n_top) {
        this.setn_top(n_top);
        return this;
    }

    public void setn_top(Long n_top) {
        this.n_top = n_top;
    }

    public Long getn_left() {
        return this.n_left;
    }

    public PageWord n_left(Long n_left) {
        this.setn_left(n_left);
        return this;
    }

    public void setn_left(Long n_left) {
        this.n_left = n_left;
    }

    public Long getn_heigth() {
        return this.n_heigth;
    }

    public PageWord n_heigth(Long n_heigth) {
        this.setn_heigth(n_heigth);
        return this;
    }

    public void setn_heigth(Long n_heigth) {
        this.n_heigth = n_heigth;
    }

    public Long getn_width() {
        return this.n_width;
    }

    public PageWord n_width(Long n_width) {
        this.setn_width(n_width);
        return this;
    }

    public void setn_width(Long n_width) {
        this.n_width = n_width;
    }

    public Long getn_idx() {
        return this.n_idx;
    }

    public PageWord n_idx(Long n_idx) {
        this.setn_idx(n_idx);
        return this;
    }

    public void setn_idx(Long n_idx) {
        this.n_idx = n_idx;
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
            ", s_word='" + gets_word() + "'" +
            ", n_top=" + getn_top() +
            ", n_left=" + getn_left() +
            ", n_heigth=" + getn_heigth() +
            ", n_width=" + getn_width() +
            ", n_idx=" + getn_idx() +
            "}";
    }
}
