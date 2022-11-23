package org.jhipster.dich.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.jhipster.dich.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PageTextDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PageTextDTO.class);
        PageTextDTO pageTextDTO1 = new PageTextDTO();
        pageTextDTO1.setId(1L);
        PageTextDTO pageTextDTO2 = new PageTextDTO();
        assertThat(pageTextDTO1).isNotEqualTo(pageTextDTO2);
        pageTextDTO2.setId(pageTextDTO1.getId());
        assertThat(pageTextDTO1).isEqualTo(pageTextDTO2);
        pageTextDTO2.setId(2L);
        assertThat(pageTextDTO1).isNotEqualTo(pageTextDTO2);
        pageTextDTO1.setId(null);
        assertThat(pageTextDTO1).isNotEqualTo(pageTextDTO2);
    }
}
