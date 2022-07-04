package org.jhipster.dich.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
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

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @OneToMany(mappedBy = "pageImage")
    @JsonIgnoreProperties(value = { "pageImage" }, allowSetters = true)
    private Set<PageWord> pageWords = new HashSet<>();

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

    public byte[] getImage() {
        return this.image;
    }

    public PageImage image(byte[] image) {
        this.setImage(image);
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return this.imageContentType;
    }

    public PageImage imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public Set<PageWord> getPageWords() {
        return this.pageWords;
    }

    public void setPageWords(Set<PageWord> pageWords) {
        if (this.pageWords != null) {
            this.pageWords.forEach(i -> i.setPageImage(null));
        }
        if (pageWords != null) {
            pageWords.forEach(i -> i.setPageImage(this));
        }
        this.pageWords = pageWords;
    }

    public PageImage pageWords(Set<PageWord> pageWords) {
        this.setPageWords(pageWords);
        return this;
    }

    public PageImage addPageWord(PageWord pageWord) {
        this.pageWords.add(pageWord);
        pageWord.setPageImage(this);
        return this;
    }

    public PageImage removePageWord(PageWord pageWord) {
        this.pageWords.remove(pageWord);
        pageWord.setPageImage(null);
        return this;
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
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            "}";
    }
}
