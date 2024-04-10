package org.jhipster.dich.service.mapper;

import org.jhipster.dich.domain.Collections;
import org.jhipster.dich.service.dto.CollectionsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Collections} and its DTO {@link CollectionsDTO}.
 */
@Mapper(componentModel = "spring")
public interface CollectionsMapper extends EntityMapper<CollectionsDTO, Collections> {}
