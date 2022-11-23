package org.jhipster.dich.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.jhipster.dich.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OcrTasksTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OcrTasks.class);
        OcrTasks ocrTasks1 = new OcrTasks();
        ocrTasks1.setId(1L);
        OcrTasks ocrTasks2 = new OcrTasks();
        ocrTasks2.setId(ocrTasks1.getId());
        assertThat(ocrTasks1).isEqualTo(ocrTasks2);
        ocrTasks2.setId(2L);
        assertThat(ocrTasks1).isNotEqualTo(ocrTasks2);
        ocrTasks1.setId(null);
        assertThat(ocrTasks1).isNotEqualTo(ocrTasks2);
    }
}
