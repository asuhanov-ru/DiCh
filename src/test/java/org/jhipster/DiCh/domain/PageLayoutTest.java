package org.jhipster.dich.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.jhipster.dich.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PageLayoutTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PageLayout.class);
        PageLayout pageLayout1 = new PageLayout();
        pageLayout1.setId(1L);
        PageLayout pageLayout2 = new PageLayout();
        pageLayout2.setId(pageLayout1.getId());
        assertThat(pageLayout1).isEqualTo(pageLayout2);
        pageLayout2.setId(2L);
        assertThat(pageLayout1).isNotEqualTo(pageLayout2);
        pageLayout1.setId(null);
        assertThat(pageLayout1).isNotEqualTo(pageLayout2);
    }
}
