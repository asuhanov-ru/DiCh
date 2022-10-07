package org.jhipster.dich.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Type;

/**
 * A PageText.
 */
@Entity
@Table(name = "page_text")
public class PageText implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "page_id")
    private String page_id;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "text")
    private String text;

    @ManyToOne
    private PageImage pageImage;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PageText id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPage_id() {
        return this.page_id;
    }

    public PageText page_id(String page_id) {
        this.setPage_id(page_id);
        return this;
    }

    public void setPage_id(String page_id) {
        this.page_id = page_id;
    }

    public String getText() {
        return this.text;
    }

    public PageText text(String text) {
        this.setText(text);
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public PageImage getPageImage() {
        return this.pageImage;
    }

    public void setPageImage(PageImage pageImage) {
        this.pageImage = pageImage;
    }

    public PageText pageImage(PageImage pageImage) {
        this.setPageImage(pageImage);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PageText)) {
            return false;
        }
        return id != null && id.equals(((PageText) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PageText{" +
            "id=" + getId() +
            ", page_id='" + getPage_id() + "'" +
            ", text='" + getText() + "'" +
            "}";
    }
}
