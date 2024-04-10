package org.jhipster.dich.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.jhipster.dich.IntegrationTest;
import org.jhipster.dich.domain.Author;
import org.jhipster.dich.domain.Book;
import org.jhipster.dich.domain.Media;
import org.jhipster.dich.repository.BookRepository;
import org.jhipster.dich.service.BookService;
import org.jhipster.dich.service.criteria.BookCriteria;
import org.jhipster.dich.service.dto.BookDTO;
import org.jhipster.dich.service.mapper.BookMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link BookResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class BookResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_MEDIA_START_PAGE = 1;
    private static final Integer UPDATED_MEDIA_START_PAGE = 2;
    private static final Integer SMALLER_MEDIA_START_PAGE = 1 - 1;

    private static final Integer DEFAULT_MEDIA_END_PAGE = 1;
    private static final Integer UPDATED_MEDIA_END_PAGE = 2;
    private static final Integer SMALLER_MEDIA_END_PAGE = 1 - 1;

    private static final String ENTITY_API_URL = "/api/books";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BookRepository bookRepository;

    @Mock
    private BookRepository bookRepositoryMock;

    @Autowired
    private BookMapper bookMapper;

    @Mock
    private BookService bookServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBookMockMvc;

    private Book book;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Book createEntity(EntityManager em) {
        Book book = new Book()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .mediaStartPage(DEFAULT_MEDIA_START_PAGE)
            .mediaEndPage(DEFAULT_MEDIA_END_PAGE);
        return book;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Book createUpdatedEntity(EntityManager em) {
        Book book = new Book()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .mediaStartPage(UPDATED_MEDIA_START_PAGE)
            .mediaEndPage(UPDATED_MEDIA_END_PAGE);
        return book;
    }

    @BeforeEach
    public void initTest() {
        book = createEntity(em);
    }

    @Test
    @Transactional
    void createBook() throws Exception {
        int databaseSizeBeforeCreate = bookRepository.findAll().size();
        // Create the Book
        BookDTO bookDTO = bookMapper.toDto(book);
        restBookMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bookDTO)))
            .andExpect(status().isCreated());

        // Validate the Book in the database
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeCreate + 1);
        Book testBook = bookList.get(bookList.size() - 1);
        assertThat(testBook.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBook.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testBook.getMediaStartPage()).isEqualTo(DEFAULT_MEDIA_START_PAGE);
        assertThat(testBook.getMediaEndPage()).isEqualTo(DEFAULT_MEDIA_END_PAGE);
    }

    @Test
    @Transactional
    void createBookWithExistingId() throws Exception {
        // Create the Book with an existing ID
        book.setId(1L);
        BookDTO bookDTO = bookMapper.toDto(book);

        int databaseSizeBeforeCreate = bookRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBookMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bookDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Book in the database
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBooks() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList
        restBookMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(book.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].mediaStartPage").value(hasItem(DEFAULT_MEDIA_START_PAGE)))
            .andExpect(jsonPath("$.[*].mediaEndPage").value(hasItem(DEFAULT_MEDIA_END_PAGE)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllBooksWithEagerRelationshipsIsEnabled() throws Exception {
        when(bookServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restBookMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(bookServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllBooksWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(bookServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restBookMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(bookServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getBook() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get the book
        restBookMockMvc
            .perform(get(ENTITY_API_URL_ID, book.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(book.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.mediaStartPage").value(DEFAULT_MEDIA_START_PAGE))
            .andExpect(jsonPath("$.mediaEndPage").value(DEFAULT_MEDIA_END_PAGE));
    }

    @Test
    @Transactional
    void getBooksByIdFiltering() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        Long id = book.getId();

        defaultBookShouldBeFound("id.equals=" + id);
        defaultBookShouldNotBeFound("id.notEquals=" + id);

        defaultBookShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultBookShouldNotBeFound("id.greaterThan=" + id);

        defaultBookShouldBeFound("id.lessThanOrEqual=" + id);
        defaultBookShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllBooksByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where name equals to DEFAULT_NAME
        defaultBookShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the bookList where name equals to UPDATED_NAME
        defaultBookShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllBooksByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where name not equals to DEFAULT_NAME
        defaultBookShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the bookList where name not equals to UPDATED_NAME
        defaultBookShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllBooksByNameIsInShouldWork() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where name in DEFAULT_NAME or UPDATED_NAME
        defaultBookShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the bookList where name equals to UPDATED_NAME
        defaultBookShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllBooksByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where name is not null
        defaultBookShouldBeFound("name.specified=true");

        // Get all the bookList where name is null
        defaultBookShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllBooksByNameContainsSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where name contains DEFAULT_NAME
        defaultBookShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the bookList where name contains UPDATED_NAME
        defaultBookShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllBooksByNameNotContainsSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where name does not contain DEFAULT_NAME
        defaultBookShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the bookList where name does not contain UPDATED_NAME
        defaultBookShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllBooksByMediaStartPageIsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where mediaStartPage equals to DEFAULT_MEDIA_START_PAGE
        defaultBookShouldBeFound("mediaStartPage.equals=" + DEFAULT_MEDIA_START_PAGE);

        // Get all the bookList where mediaStartPage equals to UPDATED_MEDIA_START_PAGE
        defaultBookShouldNotBeFound("mediaStartPage.equals=" + UPDATED_MEDIA_START_PAGE);
    }

    @Test
    @Transactional
    void getAllBooksByMediaStartPageIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where mediaStartPage not equals to DEFAULT_MEDIA_START_PAGE
        defaultBookShouldNotBeFound("mediaStartPage.notEquals=" + DEFAULT_MEDIA_START_PAGE);

        // Get all the bookList where mediaStartPage not equals to UPDATED_MEDIA_START_PAGE
        defaultBookShouldBeFound("mediaStartPage.notEquals=" + UPDATED_MEDIA_START_PAGE);
    }

    @Test
    @Transactional
    void getAllBooksByMediaStartPageIsInShouldWork() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where mediaStartPage in DEFAULT_MEDIA_START_PAGE or UPDATED_MEDIA_START_PAGE
        defaultBookShouldBeFound("mediaStartPage.in=" + DEFAULT_MEDIA_START_PAGE + "," + UPDATED_MEDIA_START_PAGE);

        // Get all the bookList where mediaStartPage equals to UPDATED_MEDIA_START_PAGE
        defaultBookShouldNotBeFound("mediaStartPage.in=" + UPDATED_MEDIA_START_PAGE);
    }

    @Test
    @Transactional
    void getAllBooksByMediaStartPageIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where mediaStartPage is not null
        defaultBookShouldBeFound("mediaStartPage.specified=true");

        // Get all the bookList where mediaStartPage is null
        defaultBookShouldNotBeFound("mediaStartPage.specified=false");
    }

    @Test
    @Transactional
    void getAllBooksByMediaStartPageIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where mediaStartPage is greater than or equal to DEFAULT_MEDIA_START_PAGE
        defaultBookShouldBeFound("mediaStartPage.greaterThanOrEqual=" + DEFAULT_MEDIA_START_PAGE);

        // Get all the bookList where mediaStartPage is greater than or equal to UPDATED_MEDIA_START_PAGE
        defaultBookShouldNotBeFound("mediaStartPage.greaterThanOrEqual=" + UPDATED_MEDIA_START_PAGE);
    }

    @Test
    @Transactional
    void getAllBooksByMediaStartPageIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where mediaStartPage is less than or equal to DEFAULT_MEDIA_START_PAGE
        defaultBookShouldBeFound("mediaStartPage.lessThanOrEqual=" + DEFAULT_MEDIA_START_PAGE);

        // Get all the bookList where mediaStartPage is less than or equal to SMALLER_MEDIA_START_PAGE
        defaultBookShouldNotBeFound("mediaStartPage.lessThanOrEqual=" + SMALLER_MEDIA_START_PAGE);
    }

    @Test
    @Transactional
    void getAllBooksByMediaStartPageIsLessThanSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where mediaStartPage is less than DEFAULT_MEDIA_START_PAGE
        defaultBookShouldNotBeFound("mediaStartPage.lessThan=" + DEFAULT_MEDIA_START_PAGE);

        // Get all the bookList where mediaStartPage is less than UPDATED_MEDIA_START_PAGE
        defaultBookShouldBeFound("mediaStartPage.lessThan=" + UPDATED_MEDIA_START_PAGE);
    }

    @Test
    @Transactional
    void getAllBooksByMediaStartPageIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where mediaStartPage is greater than DEFAULT_MEDIA_START_PAGE
        defaultBookShouldNotBeFound("mediaStartPage.greaterThan=" + DEFAULT_MEDIA_START_PAGE);

        // Get all the bookList where mediaStartPage is greater than SMALLER_MEDIA_START_PAGE
        defaultBookShouldBeFound("mediaStartPage.greaterThan=" + SMALLER_MEDIA_START_PAGE);
    }

    @Test
    @Transactional
    void getAllBooksByMediaEndPageIsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where mediaEndPage equals to DEFAULT_MEDIA_END_PAGE
        defaultBookShouldBeFound("mediaEndPage.equals=" + DEFAULT_MEDIA_END_PAGE);

        // Get all the bookList where mediaEndPage equals to UPDATED_MEDIA_END_PAGE
        defaultBookShouldNotBeFound("mediaEndPage.equals=" + UPDATED_MEDIA_END_PAGE);
    }

    @Test
    @Transactional
    void getAllBooksByMediaEndPageIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where mediaEndPage not equals to DEFAULT_MEDIA_END_PAGE
        defaultBookShouldNotBeFound("mediaEndPage.notEquals=" + DEFAULT_MEDIA_END_PAGE);

        // Get all the bookList where mediaEndPage not equals to UPDATED_MEDIA_END_PAGE
        defaultBookShouldBeFound("mediaEndPage.notEquals=" + UPDATED_MEDIA_END_PAGE);
    }

    @Test
    @Transactional
    void getAllBooksByMediaEndPageIsInShouldWork() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where mediaEndPage in DEFAULT_MEDIA_END_PAGE or UPDATED_MEDIA_END_PAGE
        defaultBookShouldBeFound("mediaEndPage.in=" + DEFAULT_MEDIA_END_PAGE + "," + UPDATED_MEDIA_END_PAGE);

        // Get all the bookList where mediaEndPage equals to UPDATED_MEDIA_END_PAGE
        defaultBookShouldNotBeFound("mediaEndPage.in=" + UPDATED_MEDIA_END_PAGE);
    }

    @Test
    @Transactional
    void getAllBooksByMediaEndPageIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where mediaEndPage is not null
        defaultBookShouldBeFound("mediaEndPage.specified=true");

        // Get all the bookList where mediaEndPage is null
        defaultBookShouldNotBeFound("mediaEndPage.specified=false");
    }

    @Test
    @Transactional
    void getAllBooksByMediaEndPageIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where mediaEndPage is greater than or equal to DEFAULT_MEDIA_END_PAGE
        defaultBookShouldBeFound("mediaEndPage.greaterThanOrEqual=" + DEFAULT_MEDIA_END_PAGE);

        // Get all the bookList where mediaEndPage is greater than or equal to UPDATED_MEDIA_END_PAGE
        defaultBookShouldNotBeFound("mediaEndPage.greaterThanOrEqual=" + UPDATED_MEDIA_END_PAGE);
    }

    @Test
    @Transactional
    void getAllBooksByMediaEndPageIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where mediaEndPage is less than or equal to DEFAULT_MEDIA_END_PAGE
        defaultBookShouldBeFound("mediaEndPage.lessThanOrEqual=" + DEFAULT_MEDIA_END_PAGE);

        // Get all the bookList where mediaEndPage is less than or equal to SMALLER_MEDIA_END_PAGE
        defaultBookShouldNotBeFound("mediaEndPage.lessThanOrEqual=" + SMALLER_MEDIA_END_PAGE);
    }

    @Test
    @Transactional
    void getAllBooksByMediaEndPageIsLessThanSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where mediaEndPage is less than DEFAULT_MEDIA_END_PAGE
        defaultBookShouldNotBeFound("mediaEndPage.lessThan=" + DEFAULT_MEDIA_END_PAGE);

        // Get all the bookList where mediaEndPage is less than UPDATED_MEDIA_END_PAGE
        defaultBookShouldBeFound("mediaEndPage.lessThan=" + UPDATED_MEDIA_END_PAGE);
    }

    @Test
    @Transactional
    void getAllBooksByMediaEndPageIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where mediaEndPage is greater than DEFAULT_MEDIA_END_PAGE
        defaultBookShouldNotBeFound("mediaEndPage.greaterThan=" + DEFAULT_MEDIA_END_PAGE);

        // Get all the bookList where mediaEndPage is greater than SMALLER_MEDIA_END_PAGE
        defaultBookShouldBeFound("mediaEndPage.greaterThan=" + SMALLER_MEDIA_END_PAGE);
    }

    @Test
    @Transactional
    void getAllBooksByAuthorIsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);
        Author author;
        if (TestUtil.findAll(em, Author.class).isEmpty()) {
            author = AuthorResourceIT.createEntity(em);
            em.persist(author);
            em.flush();
        } else {
            author = TestUtil.findAll(em, Author.class).get(0);
        }
        em.persist(author);
        em.flush();
        book.setAuthor(author);
        bookRepository.saveAndFlush(book);
        Long authorId = author.getId();

        // Get all the bookList where author equals to authorId
        defaultBookShouldBeFound("authorId.equals=" + authorId);

        // Get all the bookList where author equals to (authorId + 1)
        defaultBookShouldNotBeFound("authorId.equals=" + (authorId + 1));
    }

    @Test
    @Transactional
    void getAllBooksByMediaIsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);
        Media media;
        if (TestUtil.findAll(em, Media.class).isEmpty()) {
            media = MediaResourceIT.createEntity(em);
            em.persist(media);
            em.flush();
        } else {
            media = TestUtil.findAll(em, Media.class).get(0);
        }
        em.persist(media);
        em.flush();
        book.setMedia(media);
        bookRepository.saveAndFlush(book);
        Long mediaId = media.getId();

        // Get all the bookList where media equals to mediaId
        defaultBookShouldBeFound("mediaId.equals=" + mediaId);

        // Get all the bookList where media equals to (mediaId + 1)
        defaultBookShouldNotBeFound("mediaId.equals=" + (mediaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBookShouldBeFound(String filter) throws Exception {
        restBookMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(book.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].mediaStartPage").value(hasItem(DEFAULT_MEDIA_START_PAGE)))
            .andExpect(jsonPath("$.[*].mediaEndPage").value(hasItem(DEFAULT_MEDIA_END_PAGE)));

        // Check, that the count call also returns 1
        restBookMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBookShouldNotBeFound(String filter) throws Exception {
        restBookMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBookMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingBook() throws Exception {
        // Get the book
        restBookMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBook() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        int databaseSizeBeforeUpdate = bookRepository.findAll().size();

        // Update the book
        Book updatedBook = bookRepository.findById(book.getId()).get();
        // Disconnect from session so that the updates on updatedBook are not directly saved in db
        em.detach(updatedBook);
        updatedBook
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .mediaStartPage(UPDATED_MEDIA_START_PAGE)
            .mediaEndPage(UPDATED_MEDIA_END_PAGE);
        BookDTO bookDTO = bookMapper.toDto(updatedBook);

        restBookMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bookDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bookDTO))
            )
            .andExpect(status().isOk());

        // Validate the Book in the database
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeUpdate);
        Book testBook = bookList.get(bookList.size() - 1);
        assertThat(testBook.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBook.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBook.getMediaStartPage()).isEqualTo(UPDATED_MEDIA_START_PAGE);
        assertThat(testBook.getMediaEndPage()).isEqualTo(UPDATED_MEDIA_END_PAGE);
    }

    @Test
    @Transactional
    void putNonExistingBook() throws Exception {
        int databaseSizeBeforeUpdate = bookRepository.findAll().size();
        book.setId(count.incrementAndGet());

        // Create the Book
        BookDTO bookDTO = bookMapper.toDto(book);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBookMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bookDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bookDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Book in the database
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBook() throws Exception {
        int databaseSizeBeforeUpdate = bookRepository.findAll().size();
        book.setId(count.incrementAndGet());

        // Create the Book
        BookDTO bookDTO = bookMapper.toDto(book);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBookMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bookDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Book in the database
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBook() throws Exception {
        int databaseSizeBeforeUpdate = bookRepository.findAll().size();
        book.setId(count.incrementAndGet());

        // Create the Book
        BookDTO bookDTO = bookMapper.toDto(book);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBookMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bookDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Book in the database
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBookWithPatch() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        int databaseSizeBeforeUpdate = bookRepository.findAll().size();

        // Update the book using partial update
        Book partialUpdatedBook = new Book();
        partialUpdatedBook.setId(book.getId());

        restBookMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBook.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBook))
            )
            .andExpect(status().isOk());

        // Validate the Book in the database
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeUpdate);
        Book testBook = bookList.get(bookList.size() - 1);
        assertThat(testBook.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBook.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testBook.getMediaStartPage()).isEqualTo(DEFAULT_MEDIA_START_PAGE);
        assertThat(testBook.getMediaEndPage()).isEqualTo(DEFAULT_MEDIA_END_PAGE);
    }

    @Test
    @Transactional
    void fullUpdateBookWithPatch() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        int databaseSizeBeforeUpdate = bookRepository.findAll().size();

        // Update the book using partial update
        Book partialUpdatedBook = new Book();
        partialUpdatedBook.setId(book.getId());

        partialUpdatedBook
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .mediaStartPage(UPDATED_MEDIA_START_PAGE)
            .mediaEndPage(UPDATED_MEDIA_END_PAGE);

        restBookMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBook.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBook))
            )
            .andExpect(status().isOk());

        // Validate the Book in the database
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeUpdate);
        Book testBook = bookList.get(bookList.size() - 1);
        assertThat(testBook.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBook.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBook.getMediaStartPage()).isEqualTo(UPDATED_MEDIA_START_PAGE);
        assertThat(testBook.getMediaEndPage()).isEqualTo(UPDATED_MEDIA_END_PAGE);
    }

    @Test
    @Transactional
    void patchNonExistingBook() throws Exception {
        int databaseSizeBeforeUpdate = bookRepository.findAll().size();
        book.setId(count.incrementAndGet());

        // Create the Book
        BookDTO bookDTO = bookMapper.toDto(book);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBookMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bookDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bookDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Book in the database
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBook() throws Exception {
        int databaseSizeBeforeUpdate = bookRepository.findAll().size();
        book.setId(count.incrementAndGet());

        // Create the Book
        BookDTO bookDTO = bookMapper.toDto(book);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBookMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bookDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Book in the database
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBook() throws Exception {
        int databaseSizeBeforeUpdate = bookRepository.findAll().size();
        book.setId(count.incrementAndGet());

        // Create the Book
        BookDTO bookDTO = bookMapper.toDto(book);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBookMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(bookDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Book in the database
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBook() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        int databaseSizeBeforeDelete = bookRepository.findAll().size();

        // Delete the book
        restBookMockMvc
            .perform(delete(ENTITY_API_URL_ID, book.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
