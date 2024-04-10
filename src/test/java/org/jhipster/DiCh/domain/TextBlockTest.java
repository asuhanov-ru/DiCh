package org.jhipster.dich.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.jhipster.dich.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TextBlockTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TextBlock.class);
        TextBlock textBlock1 = new TextBlock();
        textBlock1.setId(1L);
        TextBlock textBlock2 = new TextBlock();
        textBlock2.setId(textBlock1.getId());
        assertThat(textBlock1).isEqualTo(textBlock2);
        textBlock2.setId(2L);
        assertThat(textBlock1).isNotEqualTo(textBlock2);
        textBlock1.setId(null);
        assertThat(textBlock1).isNotEqualTo(textBlock2);
    }
}
