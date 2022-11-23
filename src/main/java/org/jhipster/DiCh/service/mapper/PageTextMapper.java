package org.jhipster.dich.service.mapper;

import org.jhipster.dich.domain.PageImage;
import org.jhipster.dich.domain.PageText;
import org.jhipster.dich.service.dto.PageImageDTO;
import org.jhipster.dich.service.dto.PageTextDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PageText} and its DTO {@link PageTextDTO}.
 */
@Mapper(componentModel = "spring")
public interface PageTextMapper extends EntityMapper<PageTextDTO, PageText> {
    @Mapping(target = "pageImage", source = "pageImage", qualifiedByName = "pageImageId")
    PageTextDTO toDto(PageText s);

    @Named("pageImageId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PageImageDTO toDtoPageImageId(PageImage pageImage);
}
