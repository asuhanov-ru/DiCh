package org.jhipster.dich.repository;

import java.util.List;
import java.util.Optional;
import org.jhipster.dich.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Book entity.
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
    default Optional<Book> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Book> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Book> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct book from Book book left join fetch book.author left join fetch book.media",
        countQuery = "select count(distinct book) from Book book"
    )
    Page<Book> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct book from Book book left join fetch book.author left join fetch book.media")
    List<Book> findAllWithToOneRelationships();

    @Query("select book from Book book left join fetch book.author left join fetch book.media where book.id =:id")
    Optional<Book> findOneWithToOneRelationships(@Param("id") Long id);
}
