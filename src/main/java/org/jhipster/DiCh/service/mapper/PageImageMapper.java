package org.jhipster.dich.service.mapper;

import org.jhipster.dich.domain.PageImage;
import org.jhipster.dich.service.dto.PageImageDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PageImage} and its DTO {@link PageImageDTO}.
 */
@Mapper(componentModel = "spring")
public interface PageImageMapper extends EntityMapper<PageImageDTO, PageImage> {}
