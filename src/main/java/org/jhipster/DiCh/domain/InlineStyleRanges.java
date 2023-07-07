package org.jhipster.dich.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A InlineStyleRanges.
 */
@Entity
@Table(name = "inline_style_ranges")
public class InlineStyleRanges implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "start_pos")
    private Integer startPos;

    @Column(name = "stop_pos")
    private Integer stopPos;

    @ManyToOne
    private TextBlock textBlock;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public InlineStyleRanges id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStartPos() {
        return this.startPos;
    }

    public InlineStyleRanges startPos(Integer startPos) {
        this.setStartPos(startPos);
        return this;
    }

    public void setStartPos(Integer startPos) {
        this.startPos = startPos;
    }

    public Integer getStopPos() {
        return this.stopPos;
    }

    public InlineStyleRanges stopPos(Integer stopPos) {
        this.setStopPos(stopPos);
        return this;
    }

    public void setStopPos(Integer stopPos) {
        this.stopPos = stopPos;
    }

    public TextBlock getTextBlock() {
        return this.textBlock;
    }

    public void setTextBlock(TextBlock textBlock) {
        this.textBlock = textBlock;
    }

    public InlineStyleRanges textBlock(TextBlock textBlock) {
        this.setTextBlock(textBlock);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InlineStyleRanges)) {
            return false;
        }
        return id != null && id.equals(((InlineStyleRanges) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InlineStyleRanges{" +
            "id=" + getId() +
            ", startPos=" + getStartPos() +
            ", stopPos=" + getStopPos() +
            "}";
    }
}
