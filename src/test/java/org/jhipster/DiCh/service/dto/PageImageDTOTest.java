package org.jhipster.dich.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.jhipster.dich.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PageImageDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PageImageDTO.class);
        PageImageDTO pageImageDTO1 = new PageImageDTO();
        pageImageDTO1.setId(1L);
        PageImageDTO pageImageDTO2 = new PageImageDTO();
        assertThat(pageImageDTO1).isNotEqualTo(pageImageDTO2);
        pageImageDTO2.setId(pageImageDTO1.getId());
        assertThat(pageImageDTO1).isEqualTo(pageImageDTO2);
        pageImageDTO2.setId(2L);
        assertThat(pageImageDTO1).isNotEqualTo(pageImageDTO2);
        pageImageDTO1.setId(null);
        assertThat(pageImageDTO1).isNotEqualTo(pageImageDTO2);
    }
}
