package org.jhipster.dich.repository;

import java.util.List;
import java.util.Optional;
import org.jhipster.dich.domain.MediaStructure;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the MediaStructure entity.
 */
@Repository
public interface MediaStructureRepository extends JpaRepository<MediaStructure, Long> {
    default Optional<MediaStructure> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<MediaStructure> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<MediaStructure> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct mediaStructure from MediaStructure mediaStructure left join fetch mediaStructure.media",
        countQuery = "select count(distinct mediaStructure) from MediaStructure mediaStructure"
    )
    Page<MediaStructure> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct mediaStructure from MediaStructure mediaStructure left join fetch mediaStructure.media")
    List<MediaStructure> findAllWithToOneRelationships();

    @Query("select mediaStructure from MediaStructure mediaStructure left join fetch mediaStructure.media where mediaStructure.id =:id")
    Optional<MediaStructure> findOneWithToOneRelationships(@Param("id") Long id);
}
