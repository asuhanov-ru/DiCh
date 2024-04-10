package org.jhipster.dich.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link org.jhipster.dich.domain.InlineStyleRanges} entity.
 */
public class InlineStyleRangesDTO implements Serializable {

    private Long id;

    private Integer startPos;

    private Integer stopPos;

    private TextBlockDTO textBlock;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStartPos() {
        return startPos;
    }

    public void setStartPos(Integer startPos) {
        this.startPos = startPos;
    }

    public Integer getStopPos() {
        return stopPos;
    }

    public void setStopPos(Integer stopPos) {
        this.stopPos = stopPos;
    }

    public TextBlockDTO getTextBlock() {
        return textBlock;
    }

    public void setTextBlock(TextBlockDTO textBlock) {
        this.textBlock = textBlock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InlineStyleRangesDTO)) {
            return false;
        }

        InlineStyleRangesDTO inlineStyleRangesDTO = (InlineStyleRangesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, inlineStyleRangesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InlineStyleRangesDTO{" +
            "id=" + getId() +
            ", startPos=" + getStartPos() +
            ", stopPos=" + getStopPos() +
            ", textBlock=" + getTextBlock() +
            "}";
    }
}
