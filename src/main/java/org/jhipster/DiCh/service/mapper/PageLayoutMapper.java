package org.jhipster.dich.service.mapper;

import org.jhipster.dich.domain.PageLayout;
import org.jhipster.dich.service.dto.PageLayoutDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PageLayout} and its DTO {@link PageLayoutDTO}.
 */
@Mapper(componentModel = "spring")
public interface PageLayoutMapper extends EntityMapper<PageLayoutDTO, PageLayout> {}
