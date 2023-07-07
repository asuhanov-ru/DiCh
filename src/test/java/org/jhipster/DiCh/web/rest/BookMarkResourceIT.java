package org.jhipster.dich.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.jhipster.dich.IntegrationTest;
import org.jhipster.dich.domain.BookMark;
import org.jhipster.dich.repository.BookMarkRepository;
import org.jhipster.dich.service.criteria.BookMarkCriteria;
import org.jhipster.dich.service.dto.BookMarkDTO;
import org.jhipster.dich.service.mapper.BookMarkMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BookMarkResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BookMarkResourceIT {

    private static final UUID DEFAULT_BOOK_MARK_UUID = UUID.randomUUID();
    private static final UUID UPDATED_BOOK_MARK_UUID = UUID.randomUUID();

    private static final Long DEFAULT_MEDIA_ID = 1L;
    private static final Long UPDATED_MEDIA_ID = 2L;
    private static final Long SMALLER_MEDIA_ID = 1L - 1L;

    private static final Integer DEFAULT_PAGE_NUMBER = 1;
    private static final Integer UPDATED_PAGE_NUMBER = 2;
    private static final Integer SMALLER_PAGE_NUMBER = 1 - 1;

    private static final UUID DEFAULT_TEXT_BLOCK_UUID = UUID.randomUUID();
    private static final UUID UPDATED_TEXT_BLOCK_UUID = UUID.randomUUID();

    private static final Integer DEFAULT_ANCHOR = 1;
    private static final Integer UPDATED_ANCHOR = 2;
    private static final Integer SMALLER_ANCHOR = 1 - 1;

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/book-marks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BookMarkRepository bookMarkRepository;

    @Autowired
    private BookMarkMapper bookMarkMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBookMarkMockMvc;

    private BookMark bookMark;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BookMark createEntity(EntityManager em) {
        BookMark bookMark = new BookMark()
            .bookMarkUUID(DEFAULT_BOOK_MARK_UUID)
            .mediaId(DEFAULT_MEDIA_ID)
            .pageNumber(DEFAULT_PAGE_NUMBER)
            .textBlockUUID(DEFAULT_TEXT_BLOCK_UUID)
            .anchor(DEFAULT_ANCHOR)
            .label(DEFAULT_LABEL);
        return bookMark;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BookMark createUpdatedEntity(EntityManager em) {
        BookMark bookMark = new BookMark()
            .bookMarkUUID(UPDATED_BOOK_MARK_UUID)
            .mediaId(UPDATED_MEDIA_ID)
            .pageNumber(UPDATED_PAGE_NUMBER)
            .textBlockUUID(UPDATED_TEXT_BLOCK_UUID)
            .anchor(UPDATED_ANCHOR)
            .label(UPDATED_LABEL);
        return bookMark;
    }

    @BeforeEach
    public void initTest() {
        bookMark = createEntity(em);
    }

    @Test
    @Transactional
    void createBookMark() throws Exception {
        int databaseSizeBeforeCreate = bookMarkRepository.findAll().size();
        // Create the BookMark
        BookMarkDTO bookMarkDTO = bookMarkMapper.toDto(bookMark);
        restBookMarkMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bookMarkDTO)))
            .andExpect(status().isCreated());

        // Validate the BookMark in the database
        List<BookMark> bookMarkList = bookMarkRepository.findAll();
        assertThat(bookMarkList).hasSize(databaseSizeBeforeCreate + 1);
        BookMark testBookMark = bookMarkList.get(bookMarkList.size() - 1);
        assertThat(testBookMark.getBookMarkUUID()).isEqualTo(DEFAULT_BOOK_MARK_UUID);
        assertThat(testBookMark.getMediaId()).isEqualTo(DEFAULT_MEDIA_ID);
        assertThat(testBookMark.getPageNumber()).isEqualTo(DEFAULT_PAGE_NUMBER);
        assertThat(testBookMark.getTextBlockUUID()).isEqualTo(DEFAULT_TEXT_BLOCK_UUID);
        assertThat(testBookMark.getAnchor()).isEqualTo(DEFAULT_ANCHOR);
        assertThat(testBookMark.getLabel()).isEqualTo(DEFAULT_LABEL);
    }

    @Test
    @Transactional
    void createBookMarkWithExistingId() throws Exception {
        // Create the BookMark with an existing ID
        bookMark.setId(1L);
        BookMarkDTO bookMarkDTO = bookMarkMapper.toDto(bookMark);

        int databaseSizeBeforeCreate = bookMarkRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBookMarkMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bookMarkDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BookMark in the database
        List<BookMark> bookMarkList = bookMarkRepository.findAll();
        assertThat(bookMarkList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBookMarks() throws Exception {
        // Initialize the database
        bookMarkRepository.saveAndFlush(bookMark);

        // Get all the bookMarkList
        restBookMarkMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bookMark.getId().intValue())))
            .andExpect(jsonPath("$.[*].bookMarkUUID").value(hasItem(DEFAULT_BOOK_MARK_UUID.toString())))
            .andExpect(jsonPath("$.[*].mediaId").value(hasItem(DEFAULT_MEDIA_ID.intValue())))
            .andExpect(jsonPath("$.[*].pageNumber").value(hasItem(DEFAULT_PAGE_NUMBER)))
            .andExpect(jsonPath("$.[*].textBlockUUID").value(hasItem(DEFAULT_TEXT_BLOCK_UUID.toString())))
            .andExpect(jsonPath("$.[*].anchor").value(hasItem(DEFAULT_ANCHOR)))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL)));
    }

    @Test
    @Transactional
    void getBookMark() throws Exception {
        // Initialize the database
        bookMarkRepository.saveAndFlush(bookMark);

        // Get the bookMark
        restBookMarkMockMvc
            .perform(get(ENTITY_API_URL_ID, bookMark.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bookMark.getId().intValue()))
            .andExpect(jsonPath("$.bookMarkUUID").value(DEFAULT_BOOK_MARK_UUID.toString()))
            .andExpect(jsonPath("$.mediaId").value(DEFAULT_MEDIA_ID.intValue()))
            .andExpect(jsonPath("$.pageNumber").value(DEFAULT_PAGE_NUMBER))
            .andExpect(jsonPath("$.textBlockUUID").value(DEFAULT_TEXT_BLOCK_UUID.toString()))
            .andExpect(jsonPath("$.anchor").value(DEFAULT_ANCHOR))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL));
    }

    @Test
    @Transactional
    void getBookMarksByIdFiltering() throws Exception {
        // Initialize the database
        bookMarkRepository.saveAndFlush(bookMark);

        Long id = bookMark.getId();

        defaultBookMarkShouldBeFound("id.equals=" + id);
        defaultBookMarkShouldNotBeFound("id.notEquals=" + id);

        defaultBookMarkShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultBookMarkShouldNotBeFound("id.greaterThan=" + id);

        defaultBookMarkShouldBeFound("id.lessThanOrEqual=" + id);
        defaultBookMarkShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllBookMarksByBookMarkUUIDIsEqualToSomething() throws Exception {
        // Initialize the database
        bookMarkRepository.saveAndFlush(bookMark);

        // Get all the bookMarkList where bookMarkUUID equals to DEFAULT_BOOK_MARK_UUID
        defaultBookMarkShouldBeFound("bookMarkUUID.equals=" + DEFAULT_BOOK_MARK_UUID);

        // Get all the bookMarkList where bookMarkUUID equals to UPDATED_BOOK_MARK_UUID
        defaultBookMarkShouldNotBeFound("bookMarkUUID.equals=" + UPDATED_BOOK_MARK_UUID);
    }

    @Test
    @Transactional
    void getAllBookMarksByBookMarkUUIDIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookMarkRepository.saveAndFlush(bookMark);

        // Get all the bookMarkList where bookMarkUUID not equals to DEFAULT_BOOK_MARK_UUID
        defaultBookMarkShouldNotBeFound("bookMarkUUID.notEquals=" + DEFAULT_BOOK_MARK_UUID);

        // Get all the bookMarkList where bookMarkUUID not equals to UPDATED_BOOK_MARK_UUID
        defaultBookMarkShouldBeFound("bookMarkUUID.notEquals=" + UPDATED_BOOK_MARK_UUID);
    }

    @Test
    @Transactional
    void getAllBookMarksByBookMarkUUIDIsInShouldWork() throws Exception {
        // Initialize the database
        bookMarkRepository.saveAndFlush(bookMark);

        // Get all the bookMarkList where bookMarkUUID in DEFAULT_BOOK_MARK_UUID or UPDATED_BOOK_MARK_UUID
        defaultBookMarkShouldBeFound("bookMarkUUID.in=" + DEFAULT_BOOK_MARK_UUID + "," + UPDATED_BOOK_MARK_UUID);

        // Get all the bookMarkList where bookMarkUUID equals to UPDATED_BOOK_MARK_UUID
        defaultBookMarkShouldNotBeFound("bookMarkUUID.in=" + UPDATED_BOOK_MARK_UUID);
    }

    @Test
    @Transactional
    void getAllBookMarksByBookMarkUUIDIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookMarkRepository.saveAndFlush(bookMark);

        // Get all the bookMarkList where bookMarkUUID is not null
        defaultBookMarkShouldBeFound("bookMarkUUID.specified=true");

        // Get all the bookMarkList where bookMarkUUID is null
        defaultBookMarkShouldNotBeFound("bookMarkUUID.specified=false");
    }

    @Test
    @Transactional
    void getAllBookMarksByMediaIdIsEqualToSomething() throws Exception {
        // Initialize the database
        bookMarkRepository.saveAndFlush(bookMark);

        // Get all the bookMarkList where mediaId equals to DEFAULT_MEDIA_ID
        defaultBookMarkShouldBeFound("mediaId.equals=" + DEFAULT_MEDIA_ID);

        // Get all the bookMarkList where mediaId equals to UPDATED_MEDIA_ID
        defaultBookMarkShouldNotBeFound("mediaId.equals=" + UPDATED_MEDIA_ID);
    }

    @Test
    @Transactional
    void getAllBookMarksByMediaIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookMarkRepository.saveAndFlush(bookMark);

        // Get all the bookMarkList where mediaId not equals to DEFAULT_MEDIA_ID
        defaultBookMarkShouldNotBeFound("mediaId.notEquals=" + DEFAULT_MEDIA_ID);

        // Get all the bookMarkList where mediaId not equals to UPDATED_MEDIA_ID
        defaultBookMarkShouldBeFound("mediaId.notEquals=" + UPDATED_MEDIA_ID);
    }

    @Test
    @Transactional
    void getAllBookMarksByMediaIdIsInShouldWork() throws Exception {
        // Initialize the database
        bookMarkRepository.saveAndFlush(bookMark);

        // Get all the bookMarkList where mediaId in DEFAULT_MEDIA_ID or UPDATED_MEDIA_ID
        defaultBookMarkShouldBeFound("mediaId.in=" + DEFAULT_MEDIA_ID + "," + UPDATED_MEDIA_ID);

        // Get all the bookMarkList where mediaId equals to UPDATED_MEDIA_ID
        defaultBookMarkShouldNotBeFound("mediaId.in=" + UPDATED_MEDIA_ID);
    }

    @Test
    @Transactional
    void getAllBookMarksByMediaIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookMarkRepository.saveAndFlush(bookMark);

        // Get all the bookMarkList where mediaId is not null
        defaultBookMarkShouldBeFound("mediaId.specified=true");

        // Get all the bookMarkList where mediaId is null
        defaultBookMarkShouldNotBeFound("mediaId.specified=false");
    }

    @Test
    @Transactional
    void getAllBookMarksByMediaIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bookMarkRepository.saveAndFlush(bookMark);

        // Get all the bookMarkList where mediaId is greater than or equal to DEFAULT_MEDIA_ID
        defaultBookMarkShouldBeFound("mediaId.greaterThanOrEqual=" + DEFAULT_MEDIA_ID);

        // Get all the bookMarkList where mediaId is greater than or equal to UPDATED_MEDIA_ID
        defaultBookMarkShouldNotBeFound("mediaId.greaterThanOrEqual=" + UPDATED_MEDIA_ID);
    }

    @Test
    @Transactional
    void getAllBookMarksByMediaIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bookMarkRepository.saveAndFlush(bookMark);

        // Get all the bookMarkList where mediaId is less than or equal to DEFAULT_MEDIA_ID
        defaultBookMarkShouldBeFound("mediaId.lessThanOrEqual=" + DEFAULT_MEDIA_ID);

        // Get all the bookMarkList where mediaId is less than or equal to SMALLER_MEDIA_ID
        defaultBookMarkShouldNotBeFound("mediaId.lessThanOrEqual=" + SMALLER_MEDIA_ID);
    }

    @Test
    @Transactional
    void getAllBookMarksByMediaIdIsLessThanSomething() throws Exception {
        // Initialize the database
        bookMarkRepository.saveAndFlush(bookMark);

        // Get all the bookMarkList where mediaId is less than DEFAULT_MEDIA_ID
        defaultBookMarkShouldNotBeFound("mediaId.lessThan=" + DEFAULT_MEDIA_ID);

        // Get all the bookMarkList where mediaId is less than UPDATED_MEDIA_ID
        defaultBookMarkShouldBeFound("mediaId.lessThan=" + UPDATED_MEDIA_ID);
    }

    @Test
    @Transactional
    void getAllBookMarksByMediaIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bookMarkRepository.saveAndFlush(bookMark);

        // Get all the bookMarkList where mediaId is greater than DEFAULT_MEDIA_ID
        defaultBookMarkShouldNotBeFound("mediaId.greaterThan=" + DEFAULT_MEDIA_ID);

        // Get all the bookMarkList where mediaId is greater than SMALLER_MEDIA_ID
        defaultBookMarkShouldBeFound("mediaId.greaterThan=" + SMALLER_MEDIA_ID);
    }

    @Test
    @Transactional
    void getAllBookMarksByPageNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        bookMarkRepository.saveAndFlush(bookMark);

        // Get all the bookMarkList where pageNumber equals to DEFAULT_PAGE_NUMBER
        defaultBookMarkShouldBeFound("pageNumber.equals=" + DEFAULT_PAGE_NUMBER);

        // Get all the bookMarkList where pageNumber equals to UPDATED_PAGE_NUMBER
        defaultBookMarkShouldNotBeFound("pageNumber.equals=" + UPDATED_PAGE_NUMBER);
    }

    @Test
    @Transactional
    void getAllBookMarksByPageNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookMarkRepository.saveAndFlush(bookMark);

        // Get all the bookMarkList where pageNumber not equals to DEFAULT_PAGE_NUMBER
        defaultBookMarkShouldNotBeFound("pageNumber.notEquals=" + DEFAULT_PAGE_NUMBER);

        // Get all the bookMarkList where pageNumber not equals to UPDATED_PAGE_NUMBER
        defaultBookMarkShouldBeFound("pageNumber.notEquals=" + UPDATED_PAGE_NUMBER);
    }

    @Test
    @Transactional
    void getAllBookMarksByPageNumberIsInShouldWork() throws Exception {
        // Initialize the database
        bookMarkRepository.saveAndFlush(bookMark);

        // Get all the bookMarkList where pageNumber in DEFAULT_PAGE_NUMBER or UPDATED_PAGE_NUMBER
        defaultBookMarkShouldBeFound("pageNumber.in=" + DEFAULT_PAGE_NUMBER + "," + UPDATED_PAGE_NUMBER);

        // Get all the bookMarkList where pageNumber equals to UPDATED_PAGE_NUMBER
        defaultBookMarkShouldNotBeFound("pageNumber.in=" + UPDATED_PAGE_NUMBER);
    }

    @Test
    @Transactional
    void getAllBookMarksByPageNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookMarkRepository.saveAndFlush(bookMark);

        // Get all the bookMarkList where pageNumber is not null
        defaultBookMarkShouldBeFound("pageNumber.specified=true");

        // Get all the bookMarkList where pageNumber is null
        defaultBookMarkShouldNotBeFound("pageNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllBookMarksByPageNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bookMarkRepository.saveAndFlush(bookMark);

        // Get all the bookMarkList where pageNumber is greater than or equal to DEFAULT_PAGE_NUMBER
        defaultBookMarkShouldBeFound("pageNumber.greaterThanOrEqual=" + DEFAULT_PAGE_NUMBER);

        // Get all the bookMarkList where pageNumber is greater than or equal to UPDATED_PAGE_NUMBER
        defaultBookMarkShouldNotBeFound("pageNumber.greaterThanOrEqual=" + UPDATED_PAGE_NUMBER);
    }

    @Test
    @Transactional
    void getAllBookMarksByPageNumberIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bookMarkRepository.saveAndFlush(bookMark);

        // Get all the bookMarkList where pageNumber is less than or equal to DEFAULT_PAGE_NUMBER
        defaultBookMarkShouldBeFound("pageNumber.lessThanOrEqual=" + DEFAULT_PAGE_NUMBER);

        // Get all the bookMarkList where pageNumber is less than or equal to SMALLER_PAGE_NUMBER
        defaultBookMarkShouldNotBeFound("pageNumber.lessThanOrEqual=" + SMALLER_PAGE_NUMBER);
    }

    @Test
    @Transactional
    void getAllBookMarksByPageNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        bookMarkRepository.saveAndFlush(bookMark);

        // Get all the bookMarkList where pageNumber is less than DEFAULT_PAGE_NUMBER
        defaultBookMarkShouldNotBeFound("pageNumber.lessThan=" + DEFAULT_PAGE_NUMBER);

        // Get all the bookMarkList where pageNumber is less than UPDATED_PAGE_NUMBER
        defaultBookMarkShouldBeFound("pageNumber.lessThan=" + UPDATED_PAGE_NUMBER);
    }

    @Test
    @Transactional
    void getAllBookMarksByPageNumberIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bookMarkRepository.saveAndFlush(bookMark);

        // Get all the bookMarkList where pageNumber is greater than DEFAULT_PAGE_NUMBER
        defaultBookMarkShouldNotBeFound("pageNumber.greaterThan=" + DEFAULT_PAGE_NUMBER);

        // Get all the bookMarkList where pageNumber is greater than SMALLER_PAGE_NUMBER
        defaultBookMarkShouldBeFound("pageNumber.greaterThan=" + SMALLER_PAGE_NUMBER);
    }

    @Test
    @Transactional
    void getAllBookMarksByTextBlockUUIDIsEqualToSomething() throws Exception {
        // Initialize the database
        bookMarkRepository.saveAndFlush(bookMark);

        // Get all the bookMarkList where textBlockUUID equals to DEFAULT_TEXT_BLOCK_UUID
        defaultBookMarkShouldBeFound("textBlockUUID.equals=" + DEFAULT_TEXT_BLOCK_UUID);

        // Get all the bookMarkList where textBlockUUID equals to UPDATED_TEXT_BLOCK_UUID
        defaultBookMarkShouldNotBeFound("textBlockUUID.equals=" + UPDATED_TEXT_BLOCK_UUID);
    }

    @Test
    @Transactional
    void getAllBookMarksByTextBlockUUIDIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookMarkRepository.saveAndFlush(bookMark);

        // Get all the bookMarkList where textBlockUUID not equals to DEFAULT_TEXT_BLOCK_UUID
        defaultBookMarkShouldNotBeFound("textBlockUUID.notEquals=" + DEFAULT_TEXT_BLOCK_UUID);

        // Get all the bookMarkList where textBlockUUID not equals to UPDATED_TEXT_BLOCK_UUID
        defaultBookMarkShouldBeFound("textBlockUUID.notEquals=" + UPDATED_TEXT_BLOCK_UUID);
    }

    @Test
    @Transactional
    void getAllBookMarksByTextBlockUUIDIsInShouldWork() throws Exception {
        // Initialize the database
        bookMarkRepository.saveAndFlush(bookMark);

        // Get all the bookMarkList where textBlockUUID in DEFAULT_TEXT_BLOCK_UUID or UPDATED_TEXT_BLOCK_UUID
        defaultBookMarkShouldBeFound("textBlockUUID.in=" + DEFAULT_TEXT_BLOCK_UUID + "," + UPDATED_TEXT_BLOCK_UUID);

        // Get all the bookMarkList where textBlockUUID equals to UPDATED_TEXT_BLOCK_UUID
        defaultBookMarkShouldNotBeFound("textBlockUUID.in=" + UPDATED_TEXT_BLOCK_UUID);
    }

    @Test
    @Transactional
    void getAllBookMarksByTextBlockUUIDIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookMarkRepository.saveAndFlush(bookMark);

        // Get all the bookMarkList where textBlockUUID is not null
        defaultBookMarkShouldBeFound("textBlockUUID.specified=true");

        // Get all the bookMarkList where textBlockUUID is null
        defaultBookMarkShouldNotBeFound("textBlockUUID.specified=false");
    }

    @Test
    @Transactional
    void getAllBookMarksByAnchorIsEqualToSomething() throws Exception {
        // Initialize the database
        bookMarkRepository.saveAndFlush(bookMark);

        // Get all the bookMarkList where anchor equals to DEFAULT_ANCHOR
        defaultBookMarkShouldBeFound("anchor.equals=" + DEFAULT_ANCHOR);

        // Get all the bookMarkList where anchor equals to UPDATED_ANCHOR
        defaultBookMarkShouldNotBeFound("anchor.equals=" + UPDATED_ANCHOR);
    }

    @Test
    @Transactional
    void getAllBookMarksByAnchorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookMarkRepository.saveAndFlush(bookMark);

        // Get all the bookMarkList where anchor not equals to DEFAULT_ANCHOR
        defaultBookMarkShouldNotBeFound("anchor.notEquals=" + DEFAULT_ANCHOR);

        // Get all the bookMarkList where anchor not equals to UPDATED_ANCHOR
        defaultBookMarkShouldBeFound("anchor.notEquals=" + UPDATED_ANCHOR);
    }

    @Test
    @Transactional
    void getAllBookMarksByAnchorIsInShouldWork() throws Exception {
        // Initialize the database
        bookMarkRepository.saveAndFlush(bookMark);

        // Get all the bookMarkList where anchor in DEFAULT_ANCHOR or UPDATED_ANCHOR
        defaultBookMarkShouldBeFound("anchor.in=" + DEFAULT_ANCHOR + "," + UPDATED_ANCHOR);

        // Get all the bookMarkList where anchor equals to UPDATED_ANCHOR
        defaultBookMarkShouldNotBeFound("anchor.in=" + UPDATED_ANCHOR);
    }

    @Test
    @Transactional
    void getAllBookMarksByAnchorIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookMarkRepository.saveAndFlush(bookMark);

        // Get all the bookMarkList where anchor is not null
        defaultBookMarkShouldBeFound("anchor.specified=true");

        // Get all the bookMarkList where anchor is null
        defaultBookMarkShouldNotBeFound("anchor.specified=false");
    }

    @Test
    @Transactional
    void getAllBookMarksByAnchorIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bookMarkRepository.saveAndFlush(bookMark);

        // Get all the bookMarkList where anchor is greater than or equal to DEFAULT_ANCHOR
        defaultBookMarkShouldBeFound("anchor.greaterThanOrEqual=" + DEFAULT_ANCHOR);

        // Get all the bookMarkList where anchor is greater than or equal to UPDATED_ANCHOR
        defaultBookMarkShouldNotBeFound("anchor.greaterThanOrEqual=" + UPDATED_ANCHOR);
    }

    @Test
    @Transactional
    void getAllBookMarksByAnchorIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bookMarkRepository.saveAndFlush(bookMark);

        // Get all the bookMarkList where anchor is less than or equal to DEFAULT_ANCHOR
        defaultBookMarkShouldBeFound("anchor.lessThanOrEqual=" + DEFAULT_ANCHOR);

        // Get all the bookMarkList where anchor is less than or equal to SMALLER_ANCHOR
        defaultBookMarkShouldNotBeFound("anchor.lessThanOrEqual=" + SMALLER_ANCHOR);
    }

    @Test
    @Transactional
    void getAllBookMarksByAnchorIsLessThanSomething() throws Exception {
        // Initialize the database
        bookMarkRepository.saveAndFlush(bookMark);

        // Get all the bookMarkList where anchor is less than DEFAULT_ANCHOR
        defaultBookMarkShouldNotBeFound("anchor.lessThan=" + DEFAULT_ANCHOR);

        // Get all the bookMarkList where anchor is less than UPDATED_ANCHOR
        defaultBookMarkShouldBeFound("anchor.lessThan=" + UPDATED_ANCHOR);
    }

    @Test
    @Transactional
    void getAllBookMarksByAnchorIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bookMarkRepository.saveAndFlush(bookMark);

        // Get all the bookMarkList where anchor is greater than DEFAULT_ANCHOR
        defaultBookMarkShouldNotBeFound("anchor.greaterThan=" + DEFAULT_ANCHOR);

        // Get all the bookMarkList where anchor is greater than SMALLER_ANCHOR
        defaultBookMarkShouldBeFound("anchor.greaterThan=" + SMALLER_ANCHOR);
    }

    @Test
    @Transactional
    void getAllBookMarksByLabelIsEqualToSomething() throws Exception {
        // Initialize the database
        bookMarkRepository.saveAndFlush(bookMark);

        // Get all the bookMarkList where label equals to DEFAULT_LABEL
        defaultBookMarkShouldBeFound("label.equals=" + DEFAULT_LABEL);

        // Get all the bookMarkList where label equals to UPDATED_LABEL
        defaultBookMarkShouldNotBeFound("label.equals=" + UPDATED_LABEL);
    }

    @Test
    @Transactional
    void getAllBookMarksByLabelIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookMarkRepository.saveAndFlush(bookMark);

        // Get all the bookMarkList where label not equals to DEFAULT_LABEL
        defaultBookMarkShouldNotBeFound("label.notEquals=" + DEFAULT_LABEL);

        // Get all the bookMarkList where label not equals to UPDATED_LABEL
        defaultBookMarkShouldBeFound("label.notEquals=" + UPDATED_LABEL);
    }

    @Test
    @Transactional
    void getAllBookMarksByLabelIsInShouldWork() throws Exception {
        // Initialize the database
        bookMarkRepository.saveAndFlush(bookMark);

        // Get all the bookMarkList where label in DEFAULT_LABEL or UPDATED_LABEL
        defaultBookMarkShouldBeFound("label.in=" + DEFAULT_LABEL + "," + UPDATED_LABEL);

        // Get all the bookMarkList where label equals to UPDATED_LABEL
        defaultBookMarkShouldNotBeFound("label.in=" + UPDATED_LABEL);
    }

    @Test
    @Transactional
    void getAllBookMarksByLabelIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookMarkRepository.saveAndFlush(bookMark);

        // Get all the bookMarkList where label is not null
        defaultBookMarkShouldBeFound("label.specified=true");

        // Get all the bookMarkList where label is null
        defaultBookMarkShouldNotBeFound("label.specified=false");
    }

    @Test
    @Transactional
    void getAllBookMarksByLabelContainsSomething() throws Exception {
        // Initialize the database
        bookMarkRepository.saveAndFlush(bookMark);

        // Get all the bookMarkList where label contains DEFAULT_LABEL
        defaultBookMarkShouldBeFound("label.contains=" + DEFAULT_LABEL);

        // Get all the bookMarkList where label contains UPDATED_LABEL
        defaultBookMarkShouldNotBeFound("label.contains=" + UPDATED_LABEL);
    }

    @Test
    @Transactional
    void getAllBookMarksByLabelNotContainsSomething() throws Exception {
        // Initialize the database
        bookMarkRepository.saveAndFlush(bookMark);

        // Get all the bookMarkList where label does not contain DEFAULT_LABEL
        defaultBookMarkShouldNotBeFound("label.doesNotContain=" + DEFAULT_LABEL);

        // Get all the bookMarkList where label does not contain UPDATED_LABEL
        defaultBookMarkShouldBeFound("label.doesNotContain=" + UPDATED_LABEL);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBookMarkShouldBeFound(String filter) throws Exception {
        restBookMarkMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bookMark.getId().intValue())))
            .andExpect(jsonPath("$.[*].bookMarkUUID").value(hasItem(DEFAULT_BOOK_MARK_UUID.toString())))
            .andExpect(jsonPath("$.[*].mediaId").value(hasItem(DEFAULT_MEDIA_ID.intValue())))
            .andExpect(jsonPath("$.[*].pageNumber").value(hasItem(DEFAULT_PAGE_NUMBER)))
            .andExpect(jsonPath("$.[*].textBlockUUID").value(hasItem(DEFAULT_TEXT_BLOCK_UUID.toString())))
            .andExpect(jsonPath("$.[*].anchor").value(hasItem(DEFAULT_ANCHOR)))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL)));

        // Check, that the count call also returns 1
        restBookMarkMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBookMarkShouldNotBeFound(String filter) throws Exception {
        restBookMarkMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBookMarkMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingBookMark() throws Exception {
        // Get the bookMark
        restBookMarkMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBookMark() throws Exception {
        // Initialize the database
        bookMarkRepository.saveAndFlush(bookMark);

        int databaseSizeBeforeUpdate = bookMarkRepository.findAll().size();

        // Update the bookMark
        BookMark updatedBookMark = bookMarkRepository.findById(bookMark.getId()).get();
        // Disconnect from session so that the updates on updatedBookMark are not directly saved in db
        em.detach(updatedBookMark);
        updatedBookMark
            .bookMarkUUID(UPDATED_BOOK_MARK_UUID)
            .mediaId(UPDATED_MEDIA_ID)
            .pageNumber(UPDATED_PAGE_NUMBER)
            .textBlockUUID(UPDATED_TEXT_BLOCK_UUID)
            .anchor(UPDATED_ANCHOR)
            .label(UPDATED_LABEL);
        BookMarkDTO bookMarkDTO = bookMarkMapper.toDto(updatedBookMark);

        restBookMarkMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bookMarkDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bookMarkDTO))
            )
            .andExpect(status().isOk());

        // Validate the BookMark in the database
        List<BookMark> bookMarkList = bookMarkRepository.findAll();
        assertThat(bookMarkList).hasSize(databaseSizeBeforeUpdate);
        BookMark testBookMark = bookMarkList.get(bookMarkList.size() - 1);
        assertThat(testBookMark.getBookMarkUUID()).isEqualTo(UPDATED_BOOK_MARK_UUID);
        assertThat(testBookMark.getMediaId()).isEqualTo(UPDATED_MEDIA_ID);
        assertThat(testBookMark.getPageNumber()).isEqualTo(UPDATED_PAGE_NUMBER);
        assertThat(testBookMark.getTextBlockUUID()).isEqualTo(UPDATED_TEXT_BLOCK_UUID);
        assertThat(testBookMark.getAnchor()).isEqualTo(UPDATED_ANCHOR);
        assertThat(testBookMark.getLabel()).isEqualTo(UPDATED_LABEL);
    }

    @Test
    @Transactional
    void putNonExistingBookMark() throws Exception {
        int databaseSizeBeforeUpdate = bookMarkRepository.findAll().size();
        bookMark.setId(count.incrementAndGet());

        // Create the BookMark
        BookMarkDTO bookMarkDTO = bookMarkMapper.toDto(bookMark);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBookMarkMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bookMarkDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bookMarkDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BookMark in the database
        List<BookMark> bookMarkList = bookMarkRepository.findAll();
        assertThat(bookMarkList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBookMark() throws Exception {
        int databaseSizeBeforeUpdate = bookMarkRepository.findAll().size();
        bookMark.setId(count.incrementAndGet());

        // Create the BookMark
        BookMarkDTO bookMarkDTO = bookMarkMapper.toDto(bookMark);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBookMarkMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bookMarkDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BookMark in the database
        List<BookMark> bookMarkList = bookMarkRepository.findAll();
        assertThat(bookMarkList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBookMark() throws Exception {
        int databaseSizeBeforeUpdate = bookMarkRepository.findAll().size();
        bookMark.setId(count.incrementAndGet());

        // Create the BookMark
        BookMarkDTO bookMarkDTO = bookMarkMapper.toDto(bookMark);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBookMarkMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bookMarkDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BookMark in the database
        List<BookMark> bookMarkList = bookMarkRepository.findAll();
        assertThat(bookMarkList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBookMarkWithPatch() throws Exception {
        // Initialize the database
        bookMarkRepository.saveAndFlush(bookMark);

        int databaseSizeBeforeUpdate = bookMarkRepository.findAll().size();

        // Update the bookMark using partial update
        BookMark partialUpdatedBookMark = new BookMark();
        partialUpdatedBookMark.setId(bookMark.getId());

        partialUpdatedBookMark.bookMarkUUID(UPDATED_BOOK_MARK_UUID).pageNumber(UPDATED_PAGE_NUMBER);

        restBookMarkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBookMark.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBookMark))
            )
            .andExpect(status().isOk());

        // Validate the BookMark in the database
        List<BookMark> bookMarkList = bookMarkRepository.findAll();
        assertThat(bookMarkList).hasSize(databaseSizeBeforeUpdate);
        BookMark testBookMark = bookMarkList.get(bookMarkList.size() - 1);
        assertThat(testBookMark.getBookMarkUUID()).isEqualTo(UPDATED_BOOK_MARK_UUID);
        assertThat(testBookMark.getMediaId()).isEqualTo(DEFAULT_MEDIA_ID);
        assertThat(testBookMark.getPageNumber()).isEqualTo(UPDATED_PAGE_NUMBER);
        assertThat(testBookMark.getTextBlockUUID()).isEqualTo(DEFAULT_TEXT_BLOCK_UUID);
        assertThat(testBookMark.getAnchor()).isEqualTo(DEFAULT_ANCHOR);
        assertThat(testBookMark.getLabel()).isEqualTo(DEFAULT_LABEL);
    }

    @Test
    @Transactional
    void fullUpdateBookMarkWithPatch() throws Exception {
        // Initialize the database
        bookMarkRepository.saveAndFlush(bookMark);

        int databaseSizeBeforeUpdate = bookMarkRepository.findAll().size();

        // Update the bookMark using partial update
        BookMark partialUpdatedBookMark = new BookMark();
        partialUpdatedBookMark.setId(bookMark.getId());

        partialUpdatedBookMark
            .bookMarkUUID(UPDATED_BOOK_MARK_UUID)
            .mediaId(UPDATED_MEDIA_ID)
            .pageNumber(UPDATED_PAGE_NUMBER)
            .textBlockUUID(UPDATED_TEXT_BLOCK_UUID)
            .anchor(UPDATED_ANCHOR)
            .label(UPDATED_LABEL);

        restBookMarkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBookMark.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBookMark))
            )
            .andExpect(status().isOk());

        // Validate the BookMark in the database
        List<BookMark> bookMarkList = bookMarkRepository.findAll();
        assertThat(bookMarkList).hasSize(databaseSizeBeforeUpdate);
        BookMark testBookMark = bookMarkList.get(bookMarkList.size() - 1);
        assertThat(testBookMark.getBookMarkUUID()).isEqualTo(UPDATED_BOOK_MARK_UUID);
        assertThat(testBookMark.getMediaId()).isEqualTo(UPDATED_MEDIA_ID);
        assertThat(testBookMark.getPageNumber()).isEqualTo(UPDATED_PAGE_NUMBER);
        assertThat(testBookMark.getTextBlockUUID()).isEqualTo(UPDATED_TEXT_BLOCK_UUID);
        assertThat(testBookMark.getAnchor()).isEqualTo(UPDATED_ANCHOR);
        assertThat(testBookMark.getLabel()).isEqualTo(UPDATED_LABEL);
    }

    @Test
    @Transactional
    void patchNonExistingBookMark() throws Exception {
        int databaseSizeBeforeUpdate = bookMarkRepository.findAll().size();
        bookMark.setId(count.incrementAndGet());

        // Create the BookMark
        BookMarkDTO bookMarkDTO = bookMarkMapper.toDto(bookMark);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBookMarkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bookMarkDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bookMarkDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BookMark in the database
        List<BookMark> bookMarkList = bookMarkRepository.findAll();
        assertThat(bookMarkList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBookMark() throws Exception {
        int databaseSizeBeforeUpdate = bookMarkRepository.findAll().size();
        bookMark.setId(count.incrementAndGet());

        // Create the BookMark
        BookMarkDTO bookMarkDTO = bookMarkMapper.toDto(bookMark);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBookMarkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bookMarkDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BookMark in the database
        List<BookMark> bookMarkList = bookMarkRepository.findAll();
        assertThat(bookMarkList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBookMark() throws Exception {
        int databaseSizeBeforeUpdate = bookMarkRepository.findAll().size();
        bookMark.setId(count.incrementAndGet());

        // Create the BookMark
        BookMarkDTO bookMarkDTO = bookMarkMapper.toDto(bookMark);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBookMarkMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(bookMarkDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BookMark in the database
        List<BookMark> bookMarkList = bookMarkRepository.findAll();
        assertThat(bookMarkList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBookMark() throws Exception {
        // Initialize the database
        bookMarkRepository.saveAndFlush(bookMark);

        int databaseSizeBeforeDelete = bookMarkRepository.findAll().size();

        // Delete the bookMark
        restBookMarkMockMvc
            .perform(delete(ENTITY_API_URL_ID, bookMark.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BookMark> bookMarkList = bookMarkRepository.findAll();
        assertThat(bookMarkList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
