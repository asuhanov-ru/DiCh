package org.jhipster.dich.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.jhipster.dich.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InlineStyleRangesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InlineStyleRangesDTO.class);
        InlineStyleRangesDTO inlineStyleRangesDTO1 = new InlineStyleRangesDTO();
        inlineStyleRangesDTO1.setId(1L);
        InlineStyleRangesDTO inlineStyleRangesDTO2 = new InlineStyleRangesDTO();
        assertThat(inlineStyleRangesDTO1).isNotEqualTo(inlineStyleRangesDTO2);
        inlineStyleRangesDTO2.setId(inlineStyleRangesDTO1.getId());
        assertThat(inlineStyleRangesDTO1).isEqualTo(inlineStyleRangesDTO2);
        inlineStyleRangesDTO2.setId(2L);
        assertThat(inlineStyleRangesDTO1).isNotEqualTo(inlineStyleRangesDTO2);
        inlineStyleRangesDTO1.setId(null);
        assertThat(inlineStyleRangesDTO1).isNotEqualTo(inlineStyleRangesDTO2);
    }
}
