package org.jhipster.dich.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.jhipster.dich.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MediaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MediaDTO.class);
        MediaDTO mediaDTO1 = new MediaDTO();
        mediaDTO1.setId(1L);
        MediaDTO mediaDTO2 = new MediaDTO();
        assertThat(mediaDTO1).isNotEqualTo(mediaDTO2);
        mediaDTO2.setId(mediaDTO1.getId());
        assertThat(mediaDTO1).isEqualTo(mediaDTO2);
        mediaDTO2.setId(2L);
        assertThat(mediaDTO1).isNotEqualTo(mediaDTO2);
        mediaDTO1.setId(null);
        assertThat(mediaDTO1).isNotEqualTo(mediaDTO2);
    }
}
