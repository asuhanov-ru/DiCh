package org.jhipster.dich.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
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

    @Column(name = "media_id")
    private Long mediaId;

    @Column(name = "page_number")
    private Integer pageNumber;

    @Column(name = "version")
    private ZonedDateTime version;

    @Column(name = "block_id")
    private Long blockId;

    @Column(name = "line_id")
    private Long lineId;

    @Column(name = "paragraph_id")
    private Long paragraphId;

    @Column(name = "ocr_lang")
    private String ocrLang;

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

    public Long getMediaId() {
        return this.mediaId;
    }

    public PageWord mediaId(Long mediaId) {
        this.setMediaId(mediaId);
        return this;
    }

    public void setMediaId(Long mediaId) {
        this.mediaId = mediaId;
    }

    public Integer getPageNumber() {
        return this.pageNumber;
    }

    public PageWord pageNumber(Integer pageNumber) {
        this.setPageNumber(pageNumber);
        return this;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public ZonedDateTime getVersion() {
        return this.version;
    }

    public PageWord version(ZonedDateTime version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(ZonedDateTime version) {
        this.version = version;
    }

    public Long getBlockId() {
        return this.blockId;
    }

    public PageWord blockId(Long blockId) {
        this.setBlockId(blockId);
        return this;
    }

    public void setBlockId(Long blockId) {
        this.blockId = blockId;
    }

    public Long getLineId() {
        return this.lineId;
    }

    public PageWord lineId(Long lineId) {
        this.setLineId(lineId);
        return this;
    }

    public void setLineId(Long lineId) {
        this.lineId = lineId;
    }

    public Long getParagraphId() {
        return this.paragraphId;
    }

    public PageWord paragraphId(Long paragraphId) {
        this.setParagraphId(paragraphId);
        return this;
    }

    public void setParagraphId(Long paragraphId) {
        this.paragraphId = paragraphId;
    }

    public String getOcrLang() {
        return this.ocrLang;
    }

    public PageWord ocrLang(String ocrLang) {
        this.setOcrLang(ocrLang);
        return this;
    }

    public void setOcrLang(String ocrLang) {
        this.ocrLang = ocrLang;
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
            ", mediaId=" + getMediaId() +
            ", pageNumber=" + getPageNumber() +
            ", version='" + getVersion() + "'" +
            ", blockId=" + getBlockId() +
            ", lineId=" + getLineId() +
            ", paragraphId=" + getParagraphId() +
            ", ocrLang='" + getOcrLang() + "'" +
            "}";
    }
}
