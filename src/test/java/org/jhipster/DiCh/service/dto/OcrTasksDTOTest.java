package org.jhipster.dich.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.jhipster.dich.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OcrTasksDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OcrTasksDTO.class);
        OcrTasksDTO ocrTasksDTO1 = new OcrTasksDTO();
        ocrTasksDTO1.setId(1L);
        OcrTasksDTO ocrTasksDTO2 = new OcrTasksDTO();
        assertThat(ocrTasksDTO1).isNotEqualTo(ocrTasksDTO2);
        ocrTasksDTO2.setId(ocrTasksDTO1.getId());
        assertThat(ocrTasksDTO1).isEqualTo(ocrTasksDTO2);
        ocrTasksDTO2.setId(2L);
        assertThat(ocrTasksDTO1).isNotEqualTo(ocrTasksDTO2);
        ocrTasksDTO1.setId(null);
        assertThat(ocrTasksDTO1).isNotEqualTo(ocrTasksDTO2);
    }
}
