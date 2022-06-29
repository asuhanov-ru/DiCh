package org.jhipster.dich.repository;

import org.jhipster.dich.domain.PageText;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PageText entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PageTextRepository extends JpaRepository<PageText, Long>, JpaSpecificationExecutor<PageText> {}
