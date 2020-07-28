package org.jhipster.dich.repository;

import org.jhipster.dich.domain.MediaStructure;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MediaStructure entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MediaStructureRepository extends JpaRepository<MediaStructure, Long> {

}
