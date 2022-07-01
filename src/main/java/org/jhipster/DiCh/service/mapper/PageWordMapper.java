package org.jhipster.dich.service.mapper;

import org.jhipster.dich.domain.PageImage;
import org.jhipster.dich.domain.PageWord;
import org.jhipster.dich.service.dto.PageImageDTO;
import org.jhipster.dich.service.dto.PageWordDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PageWord} and its DTO {@link PageWordDTO}.
 */
@Mapper(componentModel = "spring")
public interface PageWordMapper extends EntityMapper<PageWordDTO, PageWord> {
    @Mapping(target = "pageImage", source = "pageImage", qualifiedByName = "pageImageId")
    PageWordDTO toDto(PageWord s);

    @Named("pageImageId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PageImageDTO toDtoPageImageId(PageImage pageImage);
}
