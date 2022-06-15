package org.jhipster.dich.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.jhipster.dich.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CollectionsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Collections.class);
        Collections collections1 = new Collections();
        collections1.setId(1L);
        Collections collections2 = new Collections();
        collections2.setId(collections1.getId());
        assertThat(collections1).isEqualTo(collections2);
        collections2.setId(2L);
        assertThat(collections1).isNotEqualTo(collections2);
        collections1.setId(null);
        assertThat(collections1).isNotEqualTo(collections2);
    }
}
