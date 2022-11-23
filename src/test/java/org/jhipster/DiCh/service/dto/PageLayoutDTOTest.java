package org.jhipster.dich.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.jhipster.dich.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PageLayoutDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PageLayoutDTO.class);
        PageLayoutDTO pageLayoutDTO1 = new PageLayoutDTO();
        pageLayoutDTO1.setId(1L);
        PageLayoutDTO pageLayoutDTO2 = new PageLayoutDTO();
        assertThat(pageLayoutDTO1).isNotEqualTo(pageLayoutDTO2);
        pageLayoutDTO2.setId(pageLayoutDTO1.getId());
        assertThat(pageLayoutDTO1).isEqualTo(pageLayoutDTO2);
        pageLayoutDTO2.setId(2L);
        assertThat(pageLayoutDTO1).isNotEqualTo(pageLayoutDTO2);
        pageLayoutDTO1.setId(null);
        assertThat(pageLayoutDTO1).isNotEqualTo(pageLayoutDTO2);
    }
}
