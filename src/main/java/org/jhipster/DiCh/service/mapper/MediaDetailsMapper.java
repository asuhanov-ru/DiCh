package org.jhipster.dich.service.mapper;

import org.jhipster.dich.domain.Media;
import org.jhipster.dich.service.dto.MediaDetailsDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MediaDetailsMapper extends EntityMapper<MediaDetailsDto, Media> {}
