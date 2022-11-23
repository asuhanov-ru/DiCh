package org.jhipster.dich.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.jhipster.dich.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PageTextTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PageText.class);
        PageText pageText1 = new PageText();
        pageText1.setId(1L);
        PageText pageText2 = new PageText();
        pageText2.setId(pageText1.getId());
        assertThat(pageText1).isEqualTo(pageText2);
        pageText2.setId(2L);
        assertThat(pageText1).isNotEqualTo(pageText2);
        pageText1.setId(null);
        assertThat(pageText1).isNotEqualTo(pageText2);
    }
}
