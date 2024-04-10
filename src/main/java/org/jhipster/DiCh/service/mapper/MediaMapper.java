package org.jhipster.dich.service.mapper;

import org.jhipster.dich.domain.Collections;
import org.jhipster.dich.domain.Media;
import org.jhipster.dich.service.dto.CollectionsDTO;
import org.jhipster.dich.service.dto.MediaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Media} and its DTO {@link MediaDTO}.
 */
@Mapper(componentModel = "spring")
public interface MediaMapper extends EntityMapper<MediaDTO, Media> {
    @Mapping(target = "collections", source = "collections", qualifiedByName = "collectionsName")
    MediaDTO toDto(Media s);

    @Named("collectionsName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    CollectionsDTO toDtoCollectionsName(Collections collections);
}
