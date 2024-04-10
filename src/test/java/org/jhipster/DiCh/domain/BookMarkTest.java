package org.jhipster.dich.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.jhipster.dich.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BookMarkTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BookMark.class);
        BookMark bookMark1 = new BookMark();
        bookMark1.setId(1L);
        BookMark bookMark2 = new BookMark();
        bookMark2.setId(bookMark1.getId());
        assertThat(bookMark1).isEqualTo(bookMark2);
        bookMark2.setId(2L);
        assertThat(bookMark1).isNotEqualTo(bookMark2);
        bookMark1.setId(null);
        assertThat(bookMark1).isNotEqualTo(bookMark2);
    }
}
