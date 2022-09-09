package org.jhipster.dich.service.mapper;

import org.jhipster.dich.domain.OcrTasks;
import org.jhipster.dich.service.dto.OcrTasksDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OcrTasks} and its DTO {@link OcrTasksDTO}.
 */
@Mapper(componentModel = "spring")
public interface OcrTasksMapper extends EntityMapper<OcrTasksDTO, OcrTasks> {}
