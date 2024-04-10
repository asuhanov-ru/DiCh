package org.jhipster.dich.service.mapper;

import org.jhipster.dich.domain.InlineStyleRanges;
import org.jhipster.dich.domain.TextBlock;
import org.jhipster.dich.service.dto.InlineStyleRangesDTO;
import org.jhipster.dich.service.dto.TextBlockDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link InlineStyleRanges} and its DTO {@link InlineStyleRangesDTO}.
 */
@Mapper(componentModel = "spring")
public interface InlineStyleRangesMapper extends EntityMapper<InlineStyleRangesDTO, InlineStyleRanges> {
    @Mapping(target = "textBlock", source = "textBlock", qualifiedByName = "textBlockId")
    InlineStyleRangesDTO toDto(InlineStyleRanges s);

    @Named("textBlockId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TextBlockDTO toDtoTextBlockId(TextBlock textBlock);
}
