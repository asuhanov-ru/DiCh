package org.jhipster.dich.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BookMarkMapperTest {

    private BookMarkMapper bookMarkMapper;

    @BeforeEach
    public void setUp() {
        bookMarkMapper = new BookMarkMapperImpl();
    }
}
