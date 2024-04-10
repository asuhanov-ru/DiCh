package org.jhipster.dich.repository;

import org.jhipster.dich.domain.PageLayout;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PageLayout entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PageLayoutRepository extends JpaRepository<PageLayout, Long>, JpaSpecificationExecutor<PageLayout> {
    int deleteByMediaIdAndPageNumber(Long mediaId, int pageNumber);
}
