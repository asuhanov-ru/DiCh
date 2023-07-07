package org.jhipster.dich.service;

import java.util.Optional;
import org.jhipster.dich.domain.BookMark;
import org.jhipster.dich.repository.BookMarkRepository;
import org.jhipster.dich.service.dto.BookMarkDTO;
import org.jhipster.dich.service.mapper.BookMarkMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BookMark}.
 */
@Service
@Transactional
public class BookMarkService {

    private final Logger log = LoggerFactory.getLogger(BookMarkService.class);

    private final BookMarkRepository bookMarkRepository;

    private final BookMarkMapper bookMarkMapper;

    public BookMarkService(BookMarkRepository bookMarkRepository, BookMarkMapper bookMarkMapper) {
        this.bookMarkRepository = bookMarkRepository;
        this.bookMarkMapper = bookMarkMapper;
    }

    /**
     * Save a bookMark.
     *
     * @param bookMarkDTO the entity to save.
     * @return the persisted entity.
     */
    public BookMarkDTO save(BookMarkDTO bookMarkDTO) {
        log.debug("Request to save BookMark : {}", bookMarkDTO);
        BookMark bookMark = bookMarkMapper.toEntity(bookMarkDTO);
        bookMark = bookMarkRepository.save(bookMark);
        return bookMarkMapper.toDto(bookMark);
    }

    /**
     * Update a bookMark.
     *
     * @param bookMarkDTO the entity to save.
     * @return the persisted entity.
     */
    public BookMarkDTO update(BookMarkDTO bookMarkDTO) {
        log.debug("Request to save BookMark : {}", bookMarkDTO);
        BookMark bookMark = bookMarkMapper.toEntity(bookMarkDTO);
        bookMark = bookMarkRepository.save(bookMark);
        return bookMarkMapper.toDto(bookMark);
    }

    /**
     * Partially update a bookMark.
     *
     * @param bookMarkDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BookMarkDTO> partialUpdate(BookMarkDTO bookMarkDTO) {
        log.debug("Request to partially update BookMark : {}", bookMarkDTO);

        return bookMarkRepository
            .findById(bookMarkDTO.getId())
            .map(existingBookMark -> {
                bookMarkMapper.partialUpdate(existingBookMark, bookMarkDTO);

                return existingBookMark;
            })
            .map(bookMarkRepository::save)
            .map(bookMarkMapper::toDto);
    }

    /**
     * Get all the bookMarks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BookMarkDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BookMarks");
        return bookMarkRepository.findAll(pageable).map(bookMarkMapper::toDto);
    }

    /**
     * Get one bookMark by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BookMarkDTO> findOne(Long id) {
        log.debug("Request to get BookMark : {}", id);
        return bookMarkRepository.findById(id).map(bookMarkMapper::toDto);
    }

    /**
     * Delete the bookMark by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BookMark : {}", id);
        bookMarkRepository.deleteById(id);
    }
}
