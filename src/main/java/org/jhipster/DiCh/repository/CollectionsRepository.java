package org.jhipster.dich.repository;

import org.jhipster.dich.domain.Collections;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Collections entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CollectionsRepository extends JpaRepository<Collections, Long> {

}
