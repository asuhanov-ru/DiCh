package org.jhipster.dich.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Type;

/**
 * A Book.
 */
@Entity
@Table(name = "book")
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "description")
    private String description;

    @Column(name = "media_start_page")
    private Integer mediaStartPage;

    @Column(name = "media_end_page")
    private Integer mediaEndPage;

    @ManyToOne
    @JsonIgnoreProperties(value = { "books" }, allowSetters = true)
    private Author author;

    @ManyToOne
    @JsonIgnoreProperties(value = { "collections", "books" }, allowSetters = true)
    private Media media;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Book id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Book name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public Book description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getMediaStartPage() {
        return this.mediaStartPage;
    }

    public Book mediaStartPage(Integer mediaStartPage) {
        this.setMediaStartPage(mediaStartPage);
        return this;
    }

    public void setMediaStartPage(Integer mediaStartPage) {
        this.mediaStartPage = mediaStartPage;
    }

    public Integer getMediaEndPage() {
        return this.mediaEndPage;
    }

    public Book mediaEndPage(Integer mediaEndPage) {
        this.setMediaEndPage(mediaEndPage);
        return this;
    }

    public void setMediaEndPage(Integer mediaEndPage) {
        this.mediaEndPage = mediaEndPage;
    }

    public Author getAuthor() {
        return this.author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Book author(Author author) {
        this.setAuthor(author);
        return this;
    }

    public Media getMedia() {
        return this.media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public Book media(Media media) {
        this.setMedia(media);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Book)) {
            return false;
        }
        return id != null && id.equals(((Book) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Book{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", mediaStartPage=" + getMediaStartPage() +
            ", mediaEndPage=" + getMediaEndPage() +
            "}";
    }
}
