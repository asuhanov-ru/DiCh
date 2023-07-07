package org.jhipster.dich.service.mapper;

import org.jhipster.dich.domain.BookMark;
import org.jhipster.dich.service.dto.BookMarkDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BookMark} and its DTO {@link BookMarkDTO}.
 */
@Mapper(componentModel = "spring")
public interface BookMarkMapper extends EntityMapper<BookMarkDTO, BookMark> {}
