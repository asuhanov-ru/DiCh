package org.jhipster.dich.service.mapper;

import org.jhipster.dich.domain.Media;
import org.jhipster.dich.domain.TextBlock;
import org.jhipster.dich.service.dto.MediaDTO;
import org.jhipster.dich.service.dto.TextBlockDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TextBlock} and its DTO {@link TextBlockDTO}.
 */
@Mapper(componentModel = "spring")
public interface TextBlockMapper extends EntityMapper<TextBlockDTO, TextBlock> {
    @Mapping(target = "media", source = "media", qualifiedByName = "mediaFileName")
    TextBlockDTO toDto(TextBlock s);

    @Named("mediaFileName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "fileName", source = "fileName")
    MediaDTO toDtoMediaFileName(Media media);
}
