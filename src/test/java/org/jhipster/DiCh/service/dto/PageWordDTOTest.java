package org.jhipster.dich.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.jhipster.dich.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PageWordDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PageWordDTO.class);
        PageWordDTO pageWordDTO1 = new PageWordDTO();
        pageWordDTO1.setId(1L);
        PageWordDTO pageWordDTO2 = new PageWordDTO();
        assertThat(pageWordDTO1).isNotEqualTo(pageWordDTO2);
        pageWordDTO2.setId(pageWordDTO1.getId());
        assertThat(pageWordDTO1).isEqualTo(pageWordDTO2);
        pageWordDTO2.setId(2L);
        assertThat(pageWordDTO1).isNotEqualTo(pageWordDTO2);
        pageWordDTO1.setId(null);
        assertThat(pageWordDTO1).isNotEqualTo(pageWordDTO2);
    }
}
