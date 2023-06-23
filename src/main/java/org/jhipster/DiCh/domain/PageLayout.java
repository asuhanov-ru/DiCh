package org.jhipster.dich.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;
import javax.persistence.*;

/**
 * A PageLayout.
 */
@Entity
@Table(name = "page_layout")
public class PageLayout implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "media_id")
    private Long mediaId;

    @Column(name = "page_number")
    private Integer pageNumber;

    @Column(name = "iterator_level")
    private String iterator_level;

    @Column(name = "rect_top", precision = 21, scale = 2)
    private BigDecimal rect_top;

    @Column(name = "rect_left", precision = 21, scale = 2)
    private BigDecimal rect_left;

    @Column(name = "rect_right", precision = 21, scale = 2)
    private BigDecimal rect_right;

    @Column(name = "rect_bottom", precision = 21, scale = 2)
    private BigDecimal rect_bottom;

    @Column(name = "parent_id")
    private Integer parent_id;

    @Column(name = "item_guid")
    private UUID itemGUID;

    @Column(name = "parent_guid")
    private UUID parentGUID;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PageLayout id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMediaId() {
        return this.mediaId;
    }

    public PageLayout mediaId(Long mediaId) {
        this.setMediaId(mediaId);
        return this;
    }

    public void setMediaId(Long mediaId) {
        this.mediaId = mediaId;
    }

    public Integer getPageNumber() {
        return this.pageNumber;
    }

    public PageLayout pageNumber(Integer pageNumber) {
        this.setPageNumber(pageNumber);
        return this;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getIterator_level() {
        return this.iterator_level;
    }

    public PageLayout iterator_level(String iterator_level) {
        this.setIterator_level(iterator_level);
        return this;
    }

    public void setIterator_level(String iterator_level) {
        this.iterator_level = iterator_level;
    }

    public BigDecimal getRect_top() {
        return this.rect_top;
    }

    public PageLayout rect_top(BigDecimal rect_top) {
        this.setRect_top(rect_top);
        return this;
    }

    public void setRect_top(BigDecimal rect_top) {
        this.rect_top = rect_top;
    }

    public BigDecimal getRect_left() {
        return this.rect_left;
    }

    public PageLayout rect_left(BigDecimal rect_left) {
        this.setRect_left(rect_left);
        return this;
    }

    public void setRect_left(BigDecimal rect_left) {
        this.rect_left = rect_left;
    }

    public BigDecimal getRect_right() {
        return this.rect_right;
    }

    public PageLayout rect_right(BigDecimal rect_right) {
        this.setRect_right(rect_right);
        return this;
    }

    public void setRect_right(BigDecimal rect_right) {
        this.rect_right = rect_right;
    }

    public BigDecimal getRect_bottom() {
        return this.rect_bottom;
    }

    public PageLayout rect_bottom(BigDecimal rect_bottom) {
        this.setRect_bottom(rect_bottom);
        return this;
    }

    public void setRect_bottom(BigDecimal rect_bottom) {
        this.rect_bottom = rect_bottom;
    }

    public Integer getParent_id() {
        return this.parent_id;
    }

    public PageLayout parent_id(Integer parent_id) {
        this.setParent_id(parent_id);
        return this;
    }

    public void setParent_id(Integer parent_id) {
        this.parent_id = parent_id;
    }

    public UUID getItemGUID() {
        return this.itemGUID;
    }

    public PageLayout itemGUID(UUID itemGUID) {
        this.setItemGUID(itemGUID);
        return this;
    }

    public void setItemGUID(UUID itemGUID) {
        this.itemGUID = itemGUID;
    }

    public UUID getParentGUID() {
        return this.parentGUID;
    }

    public PageLayout parentGUID(UUID parentGUID) {
        this.setParentGUID(parentGUID);
        return this;
    }

    public void setParentGUID(UUID parentGUID) {
        this.parentGUID = parentGUID;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PageLayout)) {
            return false;
        }
        return id != null && id.equals(((PageLayout) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PageLayout{" +
            "id=" + getId() +
            ", mediaId=" + getMediaId() +
            ", pageNumber=" + getPageNumber() +
            ", iterator_level='" + getIterator_level() + "'" +
            ", rect_top=" + getRect_top() +
            ", rect_left=" + getRect_left() +
            ", rect_right=" + getRect_right() +
            ", rect_bottom=" + getRect_bottom() +
            ", parent_id=" + getParent_id() +
            ", itemGUID='" + getItemGUID() + "'" +
            ", parentGUID='" + getParentGUID() + "'" +
            "}";
    }
}
