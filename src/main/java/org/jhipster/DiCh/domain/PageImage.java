package org.jhipster.dich.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A PageImage.
 */
@Entity
@Table(name = "page_image")
public class PageImage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "image_file_name")
    private String image_file_name;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PageImage id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImage_file_name() {
        return this.image_file_name;
    }

    public PageImage image_file_name(String image_file_name) {
        this.setImage_file_name(image_file_name);
        return this;
    }

    public void setImage_file_name(String image_file_name) {
        this.image_file_name = image_file_name;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PageImage)) {
            return false;
        }
        return id != null && id.equals(((PageImage) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PageImage{" +
            "id=" + getId() +
            ", image_file_name='" + getImage_file_name() + "'" +
            "}";
    }
}
