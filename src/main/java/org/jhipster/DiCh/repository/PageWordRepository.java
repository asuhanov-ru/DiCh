package org.jhipster.dich.repository;

import org.jhipster.dich.domain.PageWord;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PageWord entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PageWordRepository extends JpaRepository<PageWord, Long>, JpaSpecificationExecutor<PageWord> {
    int deleteByMediaIdAndPageNumber(Long mediaId, int pageNumber);
}
