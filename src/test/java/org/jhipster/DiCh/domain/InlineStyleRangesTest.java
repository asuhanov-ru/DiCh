package org.jhipster.dich.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.jhipster.dich.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InlineStyleRangesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InlineStyleRanges.class);
        InlineStyleRanges inlineStyleRanges1 = new InlineStyleRanges();
        inlineStyleRanges1.setId(1L);
        InlineStyleRanges inlineStyleRanges2 = new InlineStyleRanges();
        inlineStyleRanges2.setId(inlineStyleRanges1.getId());
        assertThat(inlineStyleRanges1).isEqualTo(inlineStyleRanges2);
        inlineStyleRanges2.setId(2L);
        assertThat(inlineStyleRanges1).isNotEqualTo(inlineStyleRanges2);
        inlineStyleRanges1.setId(null);
        assertThat(inlineStyleRanges1).isNotEqualTo(inlineStyleRanges2);
    }
}
