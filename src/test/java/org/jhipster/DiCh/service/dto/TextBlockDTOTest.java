package org.jhipster.dich.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.jhipster.dich.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TextBlockDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TextBlockDTO.class);
        TextBlockDTO textBlockDTO1 = new TextBlockDTO();
        textBlockDTO1.setId(1L);
        TextBlockDTO textBlockDTO2 = new TextBlockDTO();
        assertThat(textBlockDTO1).isNotEqualTo(textBlockDTO2);
        textBlockDTO2.setId(textBlockDTO1.getId());
        assertThat(textBlockDTO1).isEqualTo(textBlockDTO2);
        textBlockDTO2.setId(2L);
        assertThat(textBlockDTO1).isNotEqualTo(textBlockDTO2);
        textBlockDTO1.setId(null);
        assertThat(textBlockDTO1).isNotEqualTo(textBlockDTO2);
    }
}
