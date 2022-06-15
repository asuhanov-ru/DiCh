package org.jhipster.dich.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;

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
    @JsonIgnoreProperties("mediaStructures")
    private Media media;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getObjName() {
        return objName;
    }

    public MediaStructure objName(String objName) {
        this.objName = objName;
        return this;
    }

    public void setObjName(String objName) {
        this.objName = objName;
    }

    public String getObjType() {
        return objType;
    }

    public MediaStructure objType(String objType) {
        this.objType = objType;
        return this;
    }

    public void setObjType(String objType) {
        this.objType = objType;
    }

    public Long getParentId() {
        return parentId;
    }

    public MediaStructure parentId(Long parentId) {
        this.parentId = parentId;
        return this;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getTag() {
        return tag;
    }

    public MediaStructure tag(String tag) {
        this.tag = tag;
        return this;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Media getMedia() {
        return media;
    }

    public MediaStructure media(Media media) {
        this.media = media;
        return this;
    }

    public void setMedia(Media media) {
        this.media = media;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

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
        return 31;
    }

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
