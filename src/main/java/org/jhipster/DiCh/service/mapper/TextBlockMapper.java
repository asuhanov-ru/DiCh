package org.jhipster.dich.service.mapper;

import org.jhipster.dich.domain.TextBlock;
import org.jhipster.dich.service.dto.TextBlockDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TextBlock} and its DTO {@link TextBlockDTO}.
 */
@Mapper(componentModel = "spring")
public interface TextBlockMapper extends EntityMapper<TextBlockDTO, TextBlock> {}
