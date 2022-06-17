package org.jhipster.dich.repository;

import org.jhipster.dich.domain.PageImage;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PageImage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PageImageRepository extends JpaRepository<PageImage, Long>, JpaSpecificationExecutor<PageImage> {}
