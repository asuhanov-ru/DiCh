package org.jhipster.dich.service;

import java.util.Optional;
import org.jhipster.dich.service.dto.PageTextDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link org.jhipster.dich.domain.PageText}.
 */
public interface PageTextService {
    /**
     * Save a pageText.
     *
     * @param pageTextDTO the entity to save.
     * @return the persisted entity.
     */
    PageTextDTO save(PageTextDTO pageTextDTO);

    /**
     * Updates a pageText.
     *
     * @param pageTextDTO the entity to update.
     * @return the persisted entity.
     */
    PageTextDTO update(PageTextDTO pageTextDTO);

    /**
     * Partially updates a pageText.
     *
     * @param pageTextDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PageTextDTO> partialUpdate(PageTextDTO pageTextDTO);

    /**
     * Get all the pageTexts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PageTextDTO> findAll(Pageable pageable);

    /**
     * Get the "id" pageText.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PageTextDTO> findOne(Long id);

    /**
     * Delete the "id" pageText.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
