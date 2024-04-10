package org.jhipster.dich.repository;

import org.jhipster.dich.domain.InlineStyleRanges;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the InlineStyleRanges entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InlineStyleRangesRepository extends JpaRepository<InlineStyleRanges, Long>, JpaSpecificationExecutor<InlineStyleRanges> {}
