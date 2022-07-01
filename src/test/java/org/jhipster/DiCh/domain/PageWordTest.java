package org.jhipster.dich.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.jhipster.dich.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PageWordTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PageWord.class);
        PageWord pageWord1 = new PageWord();
        pageWord1.setId(1L);
        PageWord pageWord2 = new PageWord();
        pageWord2.setId(pageWord1.getId());
        assertThat(pageWord1).isEqualTo(pageWord2);
        pageWord2.setId(2L);
        assertThat(pageWord1).isNotEqualTo(pageWord2);
        pageWord1.setId(null);
        assertThat(pageWord1).isNotEqualTo(pageWord2);
    }
}
