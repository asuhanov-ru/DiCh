package org.jhipster.dich.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.jhipster.dich.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PageImageTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PageImage.class);
        PageImage pageImage1 = new PageImage();
        pageImage1.setId(1L);
        PageImage pageImage2 = new PageImage();
        pageImage2.setId(pageImage1.getId());
        assertThat(pageImage1).isEqualTo(pageImage2);
        pageImage2.setId(2L);
        assertThat(pageImage1).isNotEqualTo(pageImage2);
        pageImage1.setId(null);
        assertThat(pageImage1).isNotEqualTo(pageImage2);
    }
}
