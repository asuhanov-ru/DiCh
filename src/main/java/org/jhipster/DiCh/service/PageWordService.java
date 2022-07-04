package org.jhipster.dich.service;

import java.util.Optional;
import org.jhipster.dich.service.dto.PageWordDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link org.jhipster.dich.domain.PageWord}.
 */
public interface PageWordService {
    /**
     * Save a pageWord.
     *
     * @param pageWordDTO the entity to save.
     * @return the persisted entity.
     */
    PageWordDTO save(PageWordDTO pageWordDTO);

    /**
     * Updates a pageWord.
     *
     * @param pageWordDTO the entity to update.
     * @return the persisted entity.
     */
    PageWordDTO update(PageWordDTO pageWordDTO);

    /**
     * Partially updates a pageWord.
     *
     * @param pageWordDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PageWordDTO> partialUpdate(PageWordDTO pageWordDTO);

    /**
     * Get all the pageWords.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PageWordDTO> findAll(Pageable pageable);

    /**
     * Get the "id" pageWord.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PageWordDTO> findOne(Long id);

    /**
     * Delete the "id" pageWord.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
