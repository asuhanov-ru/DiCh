package org.jhipster.dich.repository;

import org.jhipster.dich.domain.OcrTasks;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the OcrTasks entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OcrTasksRepository extends JpaRepository<OcrTasks, Long>, JpaSpecificationExecutor<OcrTasks> {}
