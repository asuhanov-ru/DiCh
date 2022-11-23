package org.jhipster.dich.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A Translation.
 */
@Entity
@Table(name = "translation")
public class Translation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "lang")
    private String lang;

    @Column(name = "n_version")
    private Integer n_version;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Translation id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLang() {
        return this.lang;
    }

    public Translation lang(String lang) {
        this.setLang(lang);
        return this;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public Integer getn_version() {
        return this.n_version;
    }

    public Translation n_version(Integer n_version) {
        this.setn_version(n_version);
        return this;
    }

    public void setn_version(Integer n_version) {
        this.n_version = n_version;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Translation)) {
            return false;
        }
        return id != null && id.equals(((Translation) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Translation{" +
            "id=" + getId() +
            ", lang='" + getLang() + "'" +
            ", n_version=" + getn_version() +
            "}";
    }
}
