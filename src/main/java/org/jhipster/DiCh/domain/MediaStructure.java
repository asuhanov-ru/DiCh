package org.jhipster.dich.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A MediaStructure.
 */
@Entity
@Table(name = "media_structure")
public class MediaStructure implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "obj_name")
    private String objName;

    @Column(name = "obj_type")
    private String objType;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "tag")
    private String tag;

    @ManyToOne
    @JsonIgnoreProperties(value = { "collections" }, allowSetters = true)
    private Media media;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MediaStructure id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getObjName() {
        return this.objName;
    }

    public MediaStructure objName(String objName) {
        this.setObjName(objName);
        return this;
    }

    public void setObjName(String objName) {
        this.objName = objName;
    }

    public String getObjType() {
        return this.objType;
    }

    public MediaStructure objType(String objType) {
        this.setObjType(objType);
        return this;
    }

    public void setObjType(String objType) {
        this.objType = objType;
    }

    public Long getParentId() {
        return this.parentId;
    }

    public MediaStructure parentId(Long parentId) {
        this.setParentId(parentId);
        return this;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getTag() {
        return this.tag;
    }

    public MediaStructure tag(String tag) {
        this.setTag(tag);
        return this;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Media getMedia() {
        return this.media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public MediaStructure media(Media media) {
        this.setMedia(media);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MediaStructure)) {
            return false;
        }
        return id != null && id.equals(((MediaStructure) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MediaStructure{" +
            "id=" + getId() +
            ", objName='" + getObjName() + "'" +
            ", objType='" + getObjType() + "'" +
            ", parentId=" + getParentId() +
            ", tag='" + getTag() + "'" +
            "}";
    }
}
