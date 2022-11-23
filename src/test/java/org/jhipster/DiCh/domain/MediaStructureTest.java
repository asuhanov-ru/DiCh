package org.jhipster.dich.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.jhipster.dich.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MediaStructureTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MediaStructure.class);
        MediaStructure mediaStructure1 = new MediaStructure();
        mediaStructure1.setId(1L);
        MediaStructure mediaStructure2 = new MediaStructure();
        mediaStructure2.setId(mediaStructure1.getId());
        assertThat(mediaStructure1).isEqualTo(mediaStructure2);
        mediaStructure2.setId(2L);
        assertThat(mediaStructure1).isNotEqualTo(mediaStructure2);
        mediaStructure1.setId(null);
        assertThat(mediaStructure1).isNotEqualTo(mediaStructure2);
    }
}
