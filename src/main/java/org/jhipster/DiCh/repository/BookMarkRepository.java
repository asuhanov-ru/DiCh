package org.jhipster.dich.repository;

import org.jhipster.dich.domain.BookMark;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the BookMark entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BookMarkRepository extends JpaRepository<BookMark, Long>, JpaSpecificationExecutor<BookMark> {}
