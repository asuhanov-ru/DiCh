package org.jhipster.dich.repository;

import java.util.List;
import java.util.Optional;
import org.jhipster.dich.domain.Media;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Media entity.
 */
@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {
    default Optional<Media> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Media> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Media> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct media from Media media left join fetch media.collections",
        countQuery = "select count(distinct media) from Media media"
    )
    Page<Media> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct media from Media media left join fetch media.collections")
    List<Media> findAllWithToOneRelationships();

    @Query("select media from Media media left join fetch media.collections where media.id =:id")
    Optional<Media> findOneWithToOneRelationships(@Param("id") Long id);
}
