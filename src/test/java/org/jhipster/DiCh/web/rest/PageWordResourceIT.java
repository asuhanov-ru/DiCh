package org.jhipster.dich.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.jhipster.dich.web.rest.TestUtil.sameInstant;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.jhipster.dich.IntegrationTest;
import org.jhipster.dich.domain.PageWord;
import org.jhipster.dich.repository.PageWordRepository;
import org.jhipster.dich.service.criteria.PageWordCriteria;
import org.jhipster.dich.service.dto.PageWordDTO;
import org.jhipster.dich.service.mapper.PageWordMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PageWordResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PageWordResourceIT {

    private static final String DEFAULT_S_WORD = "AAAAAAAAAA";
    private static final String UPDATED_S_WORD = "BBBBBBBBBB";

    private static final Long DEFAULT_N_TOP = 1L;
    private static final Long UPDATED_N_TOP = 2L;
    private static final Long SMALLER_N_TOP = 1L - 1L;

    private static final Long DEFAULT_N_LEFT = 1L;
    private static final Long UPDATED_N_LEFT = 2L;
    private static final Long SMALLER_N_LEFT = 1L - 1L;

    private static final Long DEFAULT_N_HEIGTH = 1L;
    private static final Long UPDATED_N_HEIGTH = 2L;
    private static final Long SMALLER_N_HEIGTH = 1L - 1L;

    private static final Long DEFAULT_N_WIDTH = 1L;
    private static final Long UPDATED_N_WIDTH = 2L;
    private static final Long SMALLER_N_WIDTH = 1L - 1L;

    private static final Long DEFAULT_N_IDX = 1L;
    private static final Long UPDATED_N_IDX = 2L;
    private static final Long SMALLER_N_IDX = 1L - 1L;

    private static final Long DEFAULT_MEDIA_ID = 1L;
    private static final Long UPDATED_MEDIA_ID = 2L;
    private static final Long SMALLER_MEDIA_ID = 1L - 1L;

    private static final Integer DEFAULT_PAGE_NUMBER = 1;
    private static final Integer UPDATED_PAGE_NUMBER = 2;
    private static final Integer SMALLER_PAGE_NUMBER = 1 - 1;

    private static final ZonedDateTime DEFAULT_VERSION = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_VERSION = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_VERSION = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String DEFAULT_OCR_LANG = "AAAAAAAAAA";
    private static final String UPDATED_OCR_LANG = "BBBBBBBBBB";

    private static final UUID DEFAULT_TEXT_LINE_UUID = UUID.randomUUID();
    private static final UUID UPDATED_TEXT_LINE_UUID = UUID.randomUUID();

    private static final UUID DEFAULT_TEXT_BLOCK_UUID = UUID.randomUUID();
    private static final UUID UPDATED_TEXT_BLOCK_UUID = UUID.randomUUID();

    private static final String ENTITY_API_URL = "/api/page-words";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PageWordRepository pageWordRepository;

    @Autowired
    private PageWordMapper pageWordMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPageWordMockMvc;

    private PageWord pageWord;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PageWord createEntity(EntityManager em) {
        PageWord pageWord = new PageWord()
            .s_word(DEFAULT_S_WORD)
            .n_top(DEFAULT_N_TOP)
            .n_left(DEFAULT_N_LEFT)
            .n_heigth(DEFAULT_N_HEIGTH)
            .n_width(DEFAULT_N_WIDTH)
            .n_idx(DEFAULT_N_IDX)
            .mediaId(DEFAULT_MEDIA_ID)
            .pageNumber(DEFAULT_PAGE_NUMBER)
            .version(DEFAULT_VERSION)
            .ocrLang(DEFAULT_OCR_LANG)
            .textLineUUID(DEFAULT_TEXT_LINE_UUID)
            .textBlockUUID(DEFAULT_TEXT_BLOCK_UUID);
        return pageWord;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PageWord createUpdatedEntity(EntityManager em) {
        PageWord pageWord = new PageWord()
            .s_word(UPDATED_S_WORD)
            .n_top(UPDATED_N_TOP)
            .n_left(UPDATED_N_LEFT)
            .n_heigth(UPDATED_N_HEIGTH)
            .n_width(UPDATED_N_WIDTH)
            .n_idx(UPDATED_N_IDX)
            .mediaId(UPDATED_MEDIA_ID)
            .pageNumber(UPDATED_PAGE_NUMBER)
            .version(UPDATED_VERSION)
            .ocrLang(UPDATED_OCR_LANG)
            .textLineUUID(UPDATED_TEXT_LINE_UUID)
            .textBlockUUID(UPDATED_TEXT_BLOCK_UUID);
        return pageWord;
    }

    @BeforeEach
    public void initTest() {
        pageWord = createEntity(em);
    }

    @Test
    @Transactional
    void createPageWord() throws Exception {
        int databaseSizeBeforeCreate = pageWordRepository.findAll().size();
        // Create the PageWord
        PageWordDTO pageWordDTO = pageWordMapper.toDto(pageWord);
        restPageWordMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pageWordDTO)))
            .andExpect(status().isCreated());

        // Validate the PageWord in the database
        List<PageWord> pageWordList = pageWordRepository.findAll();
        assertThat(pageWordList).hasSize(databaseSizeBeforeCreate + 1);
        PageWord testPageWord = pageWordList.get(pageWordList.size() - 1);
        assertThat(testPageWord.gets_word()).isEqualTo(DEFAULT_S_WORD);
        assertThat(testPageWord.getn_top()).isEqualTo(DEFAULT_N_TOP);
        assertThat(testPageWord.getn_left()).isEqualTo(DEFAULT_N_LEFT);
        assertThat(testPageWord.getn_heigth()).isEqualTo(DEFAULT_N_HEIGTH);
        assertThat(testPageWord.getn_width()).isEqualTo(DEFAULT_N_WIDTH);
        assertThat(testPageWord.getn_idx()).isEqualTo(DEFAULT_N_IDX);
        assertThat(testPageWord.getMediaId()).isEqualTo(DEFAULT_MEDIA_ID);
        assertThat(testPageWord.getPageNumber()).isEqualTo(DEFAULT_PAGE_NUMBER);
        assertThat(testPageWord.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testPageWord.getOcrLang()).isEqualTo(DEFAULT_OCR_LANG);
        assertThat(testPageWord.getTextLineUUID()).isEqualTo(DEFAULT_TEXT_LINE_UUID);
        assertThat(testPageWord.getTextBlockUUID()).isEqualTo(DEFAULT_TEXT_BLOCK_UUID);
    }

    @Test
    @Transactional
    void createPageWordWithExistingId() throws Exception {
        // Create the PageWord with an existing ID
        pageWord.setId(1L);
        PageWordDTO pageWordDTO = pageWordMapper.toDto(pageWord);

        int databaseSizeBeforeCreate = pageWordRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPageWordMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pageWordDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PageWord in the database
        List<PageWord> pageWordList = pageWordRepository.findAll();
        assertThat(pageWordList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPageWords() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList
        restPageWordMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pageWord.getId().intValue())))
            .andExpect(jsonPath("$.[*].s_word").value(hasItem(DEFAULT_S_WORD)))
            .andExpect(jsonPath("$.[*].n_top").value(hasItem(DEFAULT_N_TOP.intValue())))
            .andExpect(jsonPath("$.[*].n_left").value(hasItem(DEFAULT_N_LEFT.intValue())))
            .andExpect(jsonPath("$.[*].n_heigth").value(hasItem(DEFAULT_N_HEIGTH.intValue())))
            .andExpect(jsonPath("$.[*].n_width").value(hasItem(DEFAULT_N_WIDTH.intValue())))
            .andExpect(jsonPath("$.[*].n_idx").value(hasItem(DEFAULT_N_IDX.intValue())))
            .andExpect(jsonPath("$.[*].mediaId").value(hasItem(DEFAULT_MEDIA_ID.intValue())))
            .andExpect(jsonPath("$.[*].pageNumber").value(hasItem(DEFAULT_PAGE_NUMBER)))
            .andExpect(jsonPath("$.[*].version").value(hasItem(sameInstant(DEFAULT_VERSION))))
            .andExpect(jsonPath("$.[*].ocrLang").value(hasItem(DEFAULT_OCR_LANG)))
            .andExpect(jsonPath("$.[*].textLineUUID").value(hasItem(DEFAULT_TEXT_LINE_UUID.toString())))
            .andExpect(jsonPath("$.[*].textBlockUUID").value(hasItem(DEFAULT_TEXT_BLOCK_UUID.toString())));
    }

    @Test
    @Transactional
    void getPageWord() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get the pageWord
        restPageWordMockMvc
            .perform(get(ENTITY_API_URL_ID, pageWord.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pageWord.getId().intValue()))
            .andExpect(jsonPath("$.s_word").value(DEFAULT_S_WORD))
            .andExpect(jsonPath("$.n_top").value(DEFAULT_N_TOP.intValue()))
            .andExpect(jsonPath("$.n_left").value(DEFAULT_N_LEFT.intValue()))
            .andExpect(jsonPath("$.n_heigth").value(DEFAULT_N_HEIGTH.intValue()))
            .andExpect(jsonPath("$.n_width").value(DEFAULT_N_WIDTH.intValue()))
            .andExpect(jsonPath("$.n_idx").value(DEFAULT_N_IDX.intValue()))
            .andExpect(jsonPath("$.mediaId").value(DEFAULT_MEDIA_ID.intValue()))
            .andExpect(jsonPath("$.pageNumber").value(DEFAULT_PAGE_NUMBER))
            .andExpect(jsonPath("$.version").value(sameInstant(DEFAULT_VERSION)))
            .andExpect(jsonPath("$.ocrLang").value(DEFAULT_OCR_LANG))
            .andExpect(jsonPath("$.textLineUUID").value(DEFAULT_TEXT_LINE_UUID.toString()))
            .andExpect(jsonPath("$.textBlockUUID").value(DEFAULT_TEXT_BLOCK_UUID.toString()));
    }

    @Test
    @Transactional
    void getPageWordsByIdFiltering() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        Long id = pageWord.getId();

        defaultPageWordShouldBeFound("id.equals=" + id);
        defaultPageWordShouldNotBeFound("id.notEquals=" + id);

        defaultPageWordShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPageWordShouldNotBeFound("id.greaterThan=" + id);

        defaultPageWordShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPageWordShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPageWordsBys_wordIsEqualToSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where s_word equals to DEFAULT_S_WORD
        defaultPageWordShouldBeFound("s_word.equals=" + DEFAULT_S_WORD);

        // Get all the pageWordList where s_word equals to UPDATED_S_WORD
        defaultPageWordShouldNotBeFound("s_word.equals=" + UPDATED_S_WORD);
    }

    @Test
    @Transactional
    void getAllPageWordsBys_wordIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where s_word not equals to DEFAULT_S_WORD
        defaultPageWordShouldNotBeFound("s_word.notEquals=" + DEFAULT_S_WORD);

        // Get all the pageWordList where s_word not equals to UPDATED_S_WORD
        defaultPageWordShouldBeFound("s_word.notEquals=" + UPDATED_S_WORD);
    }

    @Test
    @Transactional
    void getAllPageWordsBys_wordIsInShouldWork() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where s_word in DEFAULT_S_WORD or UPDATED_S_WORD
        defaultPageWordShouldBeFound("s_word.in=" + DEFAULT_S_WORD + "," + UPDATED_S_WORD);

        // Get all the pageWordList where s_word equals to UPDATED_S_WORD
        defaultPageWordShouldNotBeFound("s_word.in=" + UPDATED_S_WORD);
    }

    @Test
    @Transactional
    void getAllPageWordsBys_wordIsNullOrNotNull() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where s_word is not null
        defaultPageWordShouldBeFound("s_word.specified=true");

        // Get all the pageWordList where s_word is null
        defaultPageWordShouldNotBeFound("s_word.specified=false");
    }

    @Test
    @Transactional
    void getAllPageWordsBys_wordContainsSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where s_word contains DEFAULT_S_WORD
        defaultPageWordShouldBeFound("s_word.contains=" + DEFAULT_S_WORD);

        // Get all the pageWordList where s_word contains UPDATED_S_WORD
        defaultPageWordShouldNotBeFound("s_word.contains=" + UPDATED_S_WORD);
    }

    @Test
    @Transactional
    void getAllPageWordsBys_wordNotContainsSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where s_word does not contain DEFAULT_S_WORD
        defaultPageWordShouldNotBeFound("s_word.doesNotContain=" + DEFAULT_S_WORD);

        // Get all the pageWordList where s_word does not contain UPDATED_S_WORD
        defaultPageWordShouldBeFound("s_word.doesNotContain=" + UPDATED_S_WORD);
    }

    @Test
    @Transactional
    void getAllPageWordsByn_topIsEqualToSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where n_top equals to DEFAULT_N_TOP
        defaultPageWordShouldBeFound("n_top.equals=" + DEFAULT_N_TOP);

        // Get all the pageWordList where n_top equals to UPDATED_N_TOP
        defaultPageWordShouldNotBeFound("n_top.equals=" + UPDATED_N_TOP);
    }

    @Test
    @Transactional
    void getAllPageWordsByn_topIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where n_top not equals to DEFAULT_N_TOP
        defaultPageWordShouldNotBeFound("n_top.notEquals=" + DEFAULT_N_TOP);

        // Get all the pageWordList where n_top not equals to UPDATED_N_TOP
        defaultPageWordShouldBeFound("n_top.notEquals=" + UPDATED_N_TOP);
    }

    @Test
    @Transactional
    void getAllPageWordsByn_topIsInShouldWork() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where n_top in DEFAULT_N_TOP or UPDATED_N_TOP
        defaultPageWordShouldBeFound("n_top.in=" + DEFAULT_N_TOP + "," + UPDATED_N_TOP);

        // Get all the pageWordList where n_top equals to UPDATED_N_TOP
        defaultPageWordShouldNotBeFound("n_top.in=" + UPDATED_N_TOP);
    }

    @Test
    @Transactional
    void getAllPageWordsByn_topIsNullOrNotNull() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where n_top is not null
        defaultPageWordShouldBeFound("n_top.specified=true");

        // Get all the pageWordList where n_top is null
        defaultPageWordShouldNotBeFound("n_top.specified=false");
    }

    @Test
    @Transactional
    void getAllPageWordsByn_topIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where n_top is greater than or equal to DEFAULT_N_TOP
        defaultPageWordShouldBeFound("n_top.greaterThanOrEqual=" + DEFAULT_N_TOP);

        // Get all the pageWordList where n_top is greater than or equal to UPDATED_N_TOP
        defaultPageWordShouldNotBeFound("n_top.greaterThanOrEqual=" + UPDATED_N_TOP);
    }

    @Test
    @Transactional
    void getAllPageWordsByn_topIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where n_top is less than or equal to DEFAULT_N_TOP
        defaultPageWordShouldBeFound("n_top.lessThanOrEqual=" + DEFAULT_N_TOP);

        // Get all the pageWordList where n_top is less than or equal to SMALLER_N_TOP
        defaultPageWordShouldNotBeFound("n_top.lessThanOrEqual=" + SMALLER_N_TOP);
    }

    @Test
    @Transactional
    void getAllPageWordsByn_topIsLessThanSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where n_top is less than DEFAULT_N_TOP
        defaultPageWordShouldNotBeFound("n_top.lessThan=" + DEFAULT_N_TOP);

        // Get all the pageWordList where n_top is less than UPDATED_N_TOP
        defaultPageWordShouldBeFound("n_top.lessThan=" + UPDATED_N_TOP);
    }

    @Test
    @Transactional
    void getAllPageWordsByn_topIsGreaterThanSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where n_top is greater than DEFAULT_N_TOP
        defaultPageWordShouldNotBeFound("n_top.greaterThan=" + DEFAULT_N_TOP);

        // Get all the pageWordList where n_top is greater than SMALLER_N_TOP
        defaultPageWordShouldBeFound("n_top.greaterThan=" + SMALLER_N_TOP);
    }

    @Test
    @Transactional
    void getAllPageWordsByn_leftIsEqualToSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where n_left equals to DEFAULT_N_LEFT
        defaultPageWordShouldBeFound("n_left.equals=" + DEFAULT_N_LEFT);

        // Get all the pageWordList where n_left equals to UPDATED_N_LEFT
        defaultPageWordShouldNotBeFound("n_left.equals=" + UPDATED_N_LEFT);
    }

    @Test
    @Transactional
    void getAllPageWordsByn_leftIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where n_left not equals to DEFAULT_N_LEFT
        defaultPageWordShouldNotBeFound("n_left.notEquals=" + DEFAULT_N_LEFT);

        // Get all the pageWordList where n_left not equals to UPDATED_N_LEFT
        defaultPageWordShouldBeFound("n_left.notEquals=" + UPDATED_N_LEFT);
    }

    @Test
    @Transactional
    void getAllPageWordsByn_leftIsInShouldWork() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where n_left in DEFAULT_N_LEFT or UPDATED_N_LEFT
        defaultPageWordShouldBeFound("n_left.in=" + DEFAULT_N_LEFT + "," + UPDATED_N_LEFT);

        // Get all the pageWordList where n_left equals to UPDATED_N_LEFT
        defaultPageWordShouldNotBeFound("n_left.in=" + UPDATED_N_LEFT);
    }

    @Test
    @Transactional
    void getAllPageWordsByn_leftIsNullOrNotNull() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where n_left is not null
        defaultPageWordShouldBeFound("n_left.specified=true");

        // Get all the pageWordList where n_left is null
        defaultPageWordShouldNotBeFound("n_left.specified=false");
    }

    @Test
    @Transactional
    void getAllPageWordsByn_leftIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where n_left is greater than or equal to DEFAULT_N_LEFT
        defaultPageWordShouldBeFound("n_left.greaterThanOrEqual=" + DEFAULT_N_LEFT);

        // Get all the pageWordList where n_left is greater than or equal to UPDATED_N_LEFT
        defaultPageWordShouldNotBeFound("n_left.greaterThanOrEqual=" + UPDATED_N_LEFT);
    }

    @Test
    @Transactional
    void getAllPageWordsByn_leftIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where n_left is less than or equal to DEFAULT_N_LEFT
        defaultPageWordShouldBeFound("n_left.lessThanOrEqual=" + DEFAULT_N_LEFT);

        // Get all the pageWordList where n_left is less than or equal to SMALLER_N_LEFT
        defaultPageWordShouldNotBeFound("n_left.lessThanOrEqual=" + SMALLER_N_LEFT);
    }

    @Test
    @Transactional
    void getAllPageWordsByn_leftIsLessThanSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where n_left is less than DEFAULT_N_LEFT
        defaultPageWordShouldNotBeFound("n_left.lessThan=" + DEFAULT_N_LEFT);

        // Get all the pageWordList where n_left is less than UPDATED_N_LEFT
        defaultPageWordShouldBeFound("n_left.lessThan=" + UPDATED_N_LEFT);
    }

    @Test
    @Transactional
    void getAllPageWordsByn_leftIsGreaterThanSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where n_left is greater than DEFAULT_N_LEFT
        defaultPageWordShouldNotBeFound("n_left.greaterThan=" + DEFAULT_N_LEFT);

        // Get all the pageWordList where n_left is greater than SMALLER_N_LEFT
        defaultPageWordShouldBeFound("n_left.greaterThan=" + SMALLER_N_LEFT);
    }

    @Test
    @Transactional
    void getAllPageWordsByn_heigthIsEqualToSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where n_heigth equals to DEFAULT_N_HEIGTH
        defaultPageWordShouldBeFound("n_heigth.equals=" + DEFAULT_N_HEIGTH);

        // Get all the pageWordList where n_heigth equals to UPDATED_N_HEIGTH
        defaultPageWordShouldNotBeFound("n_heigth.equals=" + UPDATED_N_HEIGTH);
    }

    @Test
    @Transactional
    void getAllPageWordsByn_heigthIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where n_heigth not equals to DEFAULT_N_HEIGTH
        defaultPageWordShouldNotBeFound("n_heigth.notEquals=" + DEFAULT_N_HEIGTH);

        // Get all the pageWordList where n_heigth not equals to UPDATED_N_HEIGTH
        defaultPageWordShouldBeFound("n_heigth.notEquals=" + UPDATED_N_HEIGTH);
    }

    @Test
    @Transactional
    void getAllPageWordsByn_heigthIsInShouldWork() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where n_heigth in DEFAULT_N_HEIGTH or UPDATED_N_HEIGTH
        defaultPageWordShouldBeFound("n_heigth.in=" + DEFAULT_N_HEIGTH + "," + UPDATED_N_HEIGTH);

        // Get all the pageWordList where n_heigth equals to UPDATED_N_HEIGTH
        defaultPageWordShouldNotBeFound("n_heigth.in=" + UPDATED_N_HEIGTH);
    }

    @Test
    @Transactional
    void getAllPageWordsByn_heigthIsNullOrNotNull() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where n_heigth is not null
        defaultPageWordShouldBeFound("n_heigth.specified=true");

        // Get all the pageWordList where n_heigth is null
        defaultPageWordShouldNotBeFound("n_heigth.specified=false");
    }

    @Test
    @Transactional
    void getAllPageWordsByn_heigthIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where n_heigth is greater than or equal to DEFAULT_N_HEIGTH
        defaultPageWordShouldBeFound("n_heigth.greaterThanOrEqual=" + DEFAULT_N_HEIGTH);

        // Get all the pageWordList where n_heigth is greater than or equal to UPDATED_N_HEIGTH
        defaultPageWordShouldNotBeFound("n_heigth.greaterThanOrEqual=" + UPDATED_N_HEIGTH);
    }

    @Test
    @Transactional
    void getAllPageWordsByn_heigthIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where n_heigth is less than or equal to DEFAULT_N_HEIGTH
        defaultPageWordShouldBeFound("n_heigth.lessThanOrEqual=" + DEFAULT_N_HEIGTH);

        // Get all the pageWordList where n_heigth is less than or equal to SMALLER_N_HEIGTH
        defaultPageWordShouldNotBeFound("n_heigth.lessThanOrEqual=" + SMALLER_N_HEIGTH);
    }

    @Test
    @Transactional
    void getAllPageWordsByn_heigthIsLessThanSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where n_heigth is less than DEFAULT_N_HEIGTH
        defaultPageWordShouldNotBeFound("n_heigth.lessThan=" + DEFAULT_N_HEIGTH);

        // Get all the pageWordList where n_heigth is less than UPDATED_N_HEIGTH
        defaultPageWordShouldBeFound("n_heigth.lessThan=" + UPDATED_N_HEIGTH);
    }

    @Test
    @Transactional
    void getAllPageWordsByn_heigthIsGreaterThanSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where n_heigth is greater than DEFAULT_N_HEIGTH
        defaultPageWordShouldNotBeFound("n_heigth.greaterThan=" + DEFAULT_N_HEIGTH);

        // Get all the pageWordList where n_heigth is greater than SMALLER_N_HEIGTH
        defaultPageWordShouldBeFound("n_heigth.greaterThan=" + SMALLER_N_HEIGTH);
    }

    @Test
    @Transactional
    void getAllPageWordsByn_widthIsEqualToSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where n_width equals to DEFAULT_N_WIDTH
        defaultPageWordShouldBeFound("n_width.equals=" + DEFAULT_N_WIDTH);

        // Get all the pageWordList where n_width equals to UPDATED_N_WIDTH
        defaultPageWordShouldNotBeFound("n_width.equals=" + UPDATED_N_WIDTH);
    }

    @Test
    @Transactional
    void getAllPageWordsByn_widthIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where n_width not equals to DEFAULT_N_WIDTH
        defaultPageWordShouldNotBeFound("n_width.notEquals=" + DEFAULT_N_WIDTH);

        // Get all the pageWordList where n_width not equals to UPDATED_N_WIDTH
        defaultPageWordShouldBeFound("n_width.notEquals=" + UPDATED_N_WIDTH);
    }

    @Test
    @Transactional
    void getAllPageWordsByn_widthIsInShouldWork() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where n_width in DEFAULT_N_WIDTH or UPDATED_N_WIDTH
        defaultPageWordShouldBeFound("n_width.in=" + DEFAULT_N_WIDTH + "," + UPDATED_N_WIDTH);

        // Get all the pageWordList where n_width equals to UPDATED_N_WIDTH
        defaultPageWordShouldNotBeFound("n_width.in=" + UPDATED_N_WIDTH);
    }

    @Test
    @Transactional
    void getAllPageWordsByn_widthIsNullOrNotNull() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where n_width is not null
        defaultPageWordShouldBeFound("n_width.specified=true");

        // Get all the pageWordList where n_width is null
        defaultPageWordShouldNotBeFound("n_width.specified=false");
    }

    @Test
    @Transactional
    void getAllPageWordsByn_widthIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where n_width is greater than or equal to DEFAULT_N_WIDTH
        defaultPageWordShouldBeFound("n_width.greaterThanOrEqual=" + DEFAULT_N_WIDTH);

        // Get all the pageWordList where n_width is greater than or equal to UPDATED_N_WIDTH
        defaultPageWordShouldNotBeFound("n_width.greaterThanOrEqual=" + UPDATED_N_WIDTH);
    }

    @Test
    @Transactional
    void getAllPageWordsByn_widthIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where n_width is less than or equal to DEFAULT_N_WIDTH
        defaultPageWordShouldBeFound("n_width.lessThanOrEqual=" + DEFAULT_N_WIDTH);

        // Get all the pageWordList where n_width is less than or equal to SMALLER_N_WIDTH
        defaultPageWordShouldNotBeFound("n_width.lessThanOrEqual=" + SMALLER_N_WIDTH);
    }

    @Test
    @Transactional
    void getAllPageWordsByn_widthIsLessThanSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where n_width is less than DEFAULT_N_WIDTH
        defaultPageWordShouldNotBeFound("n_width.lessThan=" + DEFAULT_N_WIDTH);

        // Get all the pageWordList where n_width is less than UPDATED_N_WIDTH
        defaultPageWordShouldBeFound("n_width.lessThan=" + UPDATED_N_WIDTH);
    }

    @Test
    @Transactional
    void getAllPageWordsByn_widthIsGreaterThanSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where n_width is greater than DEFAULT_N_WIDTH
        defaultPageWordShouldNotBeFound("n_width.greaterThan=" + DEFAULT_N_WIDTH);

        // Get all the pageWordList where n_width is greater than SMALLER_N_WIDTH
        defaultPageWordShouldBeFound("n_width.greaterThan=" + SMALLER_N_WIDTH);
    }

    @Test
    @Transactional
    void getAllPageWordsByn_idxIsEqualToSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where n_idx equals to DEFAULT_N_IDX
        defaultPageWordShouldBeFound("n_idx.equals=" + DEFAULT_N_IDX);

        // Get all the pageWordList where n_idx equals to UPDATED_N_IDX
        defaultPageWordShouldNotBeFound("n_idx.equals=" + UPDATED_N_IDX);
    }

    @Test
    @Transactional
    void getAllPageWordsByn_idxIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where n_idx not equals to DEFAULT_N_IDX
        defaultPageWordShouldNotBeFound("n_idx.notEquals=" + DEFAULT_N_IDX);

        // Get all the pageWordList where n_idx not equals to UPDATED_N_IDX
        defaultPageWordShouldBeFound("n_idx.notEquals=" + UPDATED_N_IDX);
    }

    @Test
    @Transactional
    void getAllPageWordsByn_idxIsInShouldWork() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where n_idx in DEFAULT_N_IDX or UPDATED_N_IDX
        defaultPageWordShouldBeFound("n_idx.in=" + DEFAULT_N_IDX + "," + UPDATED_N_IDX);

        // Get all the pageWordList where n_idx equals to UPDATED_N_IDX
        defaultPageWordShouldNotBeFound("n_idx.in=" + UPDATED_N_IDX);
    }

    @Test
    @Transactional
    void getAllPageWordsByn_idxIsNullOrNotNull() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where n_idx is not null
        defaultPageWordShouldBeFound("n_idx.specified=true");

        // Get all the pageWordList where n_idx is null
        defaultPageWordShouldNotBeFound("n_idx.specified=false");
    }

    @Test
    @Transactional
    void getAllPageWordsByn_idxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where n_idx is greater than or equal to DEFAULT_N_IDX
        defaultPageWordShouldBeFound("n_idx.greaterThanOrEqual=" + DEFAULT_N_IDX);

        // Get all the pageWordList where n_idx is greater than or equal to UPDATED_N_IDX
        defaultPageWordShouldNotBeFound("n_idx.greaterThanOrEqual=" + UPDATED_N_IDX);
    }

    @Test
    @Transactional
    void getAllPageWordsByn_idxIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where n_idx is less than or equal to DEFAULT_N_IDX
        defaultPageWordShouldBeFound("n_idx.lessThanOrEqual=" + DEFAULT_N_IDX);

        // Get all the pageWordList where n_idx is less than or equal to SMALLER_N_IDX
        defaultPageWordShouldNotBeFound("n_idx.lessThanOrEqual=" + SMALLER_N_IDX);
    }

    @Test
    @Transactional
    void getAllPageWordsByn_idxIsLessThanSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where n_idx is less than DEFAULT_N_IDX
        defaultPageWordShouldNotBeFound("n_idx.lessThan=" + DEFAULT_N_IDX);

        // Get all the pageWordList where n_idx is less than UPDATED_N_IDX
        defaultPageWordShouldBeFound("n_idx.lessThan=" + UPDATED_N_IDX);
    }

    @Test
    @Transactional
    void getAllPageWordsByn_idxIsGreaterThanSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where n_idx is greater than DEFAULT_N_IDX
        defaultPageWordShouldNotBeFound("n_idx.greaterThan=" + DEFAULT_N_IDX);

        // Get all the pageWordList where n_idx is greater than SMALLER_N_IDX
        defaultPageWordShouldBeFound("n_idx.greaterThan=" + SMALLER_N_IDX);
    }

    @Test
    @Transactional
    void getAllPageWordsByMediaIdIsEqualToSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where mediaId equals to DEFAULT_MEDIA_ID
        defaultPageWordShouldBeFound("mediaId.equals=" + DEFAULT_MEDIA_ID);

        // Get all the pageWordList where mediaId equals to UPDATED_MEDIA_ID
        defaultPageWordShouldNotBeFound("mediaId.equals=" + UPDATED_MEDIA_ID);
    }

    @Test
    @Transactional
    void getAllPageWordsByMediaIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where mediaId not equals to DEFAULT_MEDIA_ID
        defaultPageWordShouldNotBeFound("mediaId.notEquals=" + DEFAULT_MEDIA_ID);

        // Get all the pageWordList where mediaId not equals to UPDATED_MEDIA_ID
        defaultPageWordShouldBeFound("mediaId.notEquals=" + UPDATED_MEDIA_ID);
    }

    @Test
    @Transactional
    void getAllPageWordsByMediaIdIsInShouldWork() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where mediaId in DEFAULT_MEDIA_ID or UPDATED_MEDIA_ID
        defaultPageWordShouldBeFound("mediaId.in=" + DEFAULT_MEDIA_ID + "," + UPDATED_MEDIA_ID);

        // Get all the pageWordList where mediaId equals to UPDATED_MEDIA_ID
        defaultPageWordShouldNotBeFound("mediaId.in=" + UPDATED_MEDIA_ID);
    }

    @Test
    @Transactional
    void getAllPageWordsByMediaIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where mediaId is not null
        defaultPageWordShouldBeFound("mediaId.specified=true");

        // Get all the pageWordList where mediaId is null
        defaultPageWordShouldNotBeFound("mediaId.specified=false");
    }

    @Test
    @Transactional
    void getAllPageWordsByMediaIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where mediaId is greater than or equal to DEFAULT_MEDIA_ID
        defaultPageWordShouldBeFound("mediaId.greaterThanOrEqual=" + DEFAULT_MEDIA_ID);

        // Get all the pageWordList where mediaId is greater than or equal to UPDATED_MEDIA_ID
        defaultPageWordShouldNotBeFound("mediaId.greaterThanOrEqual=" + UPDATED_MEDIA_ID);
    }

    @Test
    @Transactional
    void getAllPageWordsByMediaIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where mediaId is less than or equal to DEFAULT_MEDIA_ID
        defaultPageWordShouldBeFound("mediaId.lessThanOrEqual=" + DEFAULT_MEDIA_ID);

        // Get all the pageWordList where mediaId is less than or equal to SMALLER_MEDIA_ID
        defaultPageWordShouldNotBeFound("mediaId.lessThanOrEqual=" + SMALLER_MEDIA_ID);
    }

    @Test
    @Transactional
    void getAllPageWordsByMediaIdIsLessThanSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where mediaId is less than DEFAULT_MEDIA_ID
        defaultPageWordShouldNotBeFound("mediaId.lessThan=" + DEFAULT_MEDIA_ID);

        // Get all the pageWordList where mediaId is less than UPDATED_MEDIA_ID
        defaultPageWordShouldBeFound("mediaId.lessThan=" + UPDATED_MEDIA_ID);
    }

    @Test
    @Transactional
    void getAllPageWordsByMediaIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where mediaId is greater than DEFAULT_MEDIA_ID
        defaultPageWordShouldNotBeFound("mediaId.greaterThan=" + DEFAULT_MEDIA_ID);

        // Get all the pageWordList where mediaId is greater than SMALLER_MEDIA_ID
        defaultPageWordShouldBeFound("mediaId.greaterThan=" + SMALLER_MEDIA_ID);
    }

    @Test
    @Transactional
    void getAllPageWordsByPageNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where pageNumber equals to DEFAULT_PAGE_NUMBER
        defaultPageWordShouldBeFound("pageNumber.equals=" + DEFAULT_PAGE_NUMBER);

        // Get all the pageWordList where pageNumber equals to UPDATED_PAGE_NUMBER
        defaultPageWordShouldNotBeFound("pageNumber.equals=" + UPDATED_PAGE_NUMBER);
    }

    @Test
    @Transactional
    void getAllPageWordsByPageNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where pageNumber not equals to DEFAULT_PAGE_NUMBER
        defaultPageWordShouldNotBeFound("pageNumber.notEquals=" + DEFAULT_PAGE_NUMBER);

        // Get all the pageWordList where pageNumber not equals to UPDATED_PAGE_NUMBER
        defaultPageWordShouldBeFound("pageNumber.notEquals=" + UPDATED_PAGE_NUMBER);
    }

    @Test
    @Transactional
    void getAllPageWordsByPageNumberIsInShouldWork() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where pageNumber in DEFAULT_PAGE_NUMBER or UPDATED_PAGE_NUMBER
        defaultPageWordShouldBeFound("pageNumber.in=" + DEFAULT_PAGE_NUMBER + "," + UPDATED_PAGE_NUMBER);

        // Get all the pageWordList where pageNumber equals to UPDATED_PAGE_NUMBER
        defaultPageWordShouldNotBeFound("pageNumber.in=" + UPDATED_PAGE_NUMBER);
    }

    @Test
    @Transactional
    void getAllPageWordsByPageNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where pageNumber is not null
        defaultPageWordShouldBeFound("pageNumber.specified=true");

        // Get all the pageWordList where pageNumber is null
        defaultPageWordShouldNotBeFound("pageNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllPageWordsByPageNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where pageNumber is greater than or equal to DEFAULT_PAGE_NUMBER
        defaultPageWordShouldBeFound("pageNumber.greaterThanOrEqual=" + DEFAULT_PAGE_NUMBER);

        // Get all the pageWordList where pageNumber is greater than or equal to UPDATED_PAGE_NUMBER
        defaultPageWordShouldNotBeFound("pageNumber.greaterThanOrEqual=" + UPDATED_PAGE_NUMBER);
    }

    @Test
    @Transactional
    void getAllPageWordsByPageNumberIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where pageNumber is less than or equal to DEFAULT_PAGE_NUMBER
        defaultPageWordShouldBeFound("pageNumber.lessThanOrEqual=" + DEFAULT_PAGE_NUMBER);

        // Get all the pageWordList where pageNumber is less than or equal to SMALLER_PAGE_NUMBER
        defaultPageWordShouldNotBeFound("pageNumber.lessThanOrEqual=" + SMALLER_PAGE_NUMBER);
    }

    @Test
    @Transactional
    void getAllPageWordsByPageNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where pageNumber is less than DEFAULT_PAGE_NUMBER
        defaultPageWordShouldNotBeFound("pageNumber.lessThan=" + DEFAULT_PAGE_NUMBER);

        // Get all the pageWordList where pageNumber is less than UPDATED_PAGE_NUMBER
        defaultPageWordShouldBeFound("pageNumber.lessThan=" + UPDATED_PAGE_NUMBER);
    }

    @Test
    @Transactional
    void getAllPageWordsByPageNumberIsGreaterThanSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where pageNumber is greater than DEFAULT_PAGE_NUMBER
        defaultPageWordShouldNotBeFound("pageNumber.greaterThan=" + DEFAULT_PAGE_NUMBER);

        // Get all the pageWordList where pageNumber is greater than SMALLER_PAGE_NUMBER
        defaultPageWordShouldBeFound("pageNumber.greaterThan=" + SMALLER_PAGE_NUMBER);
    }

    @Test
    @Transactional
    void getAllPageWordsByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where version equals to DEFAULT_VERSION
        defaultPageWordShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the pageWordList where version equals to UPDATED_VERSION
        defaultPageWordShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllPageWordsByVersionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where version not equals to DEFAULT_VERSION
        defaultPageWordShouldNotBeFound("version.notEquals=" + DEFAULT_VERSION);

        // Get all the pageWordList where version not equals to UPDATED_VERSION
        defaultPageWordShouldBeFound("version.notEquals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllPageWordsByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultPageWordShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the pageWordList where version equals to UPDATED_VERSION
        defaultPageWordShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllPageWordsByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where version is not null
        defaultPageWordShouldBeFound("version.specified=true");

        // Get all the pageWordList where version is null
        defaultPageWordShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    void getAllPageWordsByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where version is greater than or equal to DEFAULT_VERSION
        defaultPageWordShouldBeFound("version.greaterThanOrEqual=" + DEFAULT_VERSION);

        // Get all the pageWordList where version is greater than or equal to UPDATED_VERSION
        defaultPageWordShouldNotBeFound("version.greaterThanOrEqual=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllPageWordsByVersionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where version is less than or equal to DEFAULT_VERSION
        defaultPageWordShouldBeFound("version.lessThanOrEqual=" + DEFAULT_VERSION);

        // Get all the pageWordList where version is less than or equal to SMALLER_VERSION
        defaultPageWordShouldNotBeFound("version.lessThanOrEqual=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllPageWordsByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where version is less than DEFAULT_VERSION
        defaultPageWordShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the pageWordList where version is less than UPDATED_VERSION
        defaultPageWordShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllPageWordsByVersionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where version is greater than DEFAULT_VERSION
        defaultPageWordShouldNotBeFound("version.greaterThan=" + DEFAULT_VERSION);

        // Get all the pageWordList where version is greater than SMALLER_VERSION
        defaultPageWordShouldBeFound("version.greaterThan=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllPageWordsByOcrLangIsEqualToSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where ocrLang equals to DEFAULT_OCR_LANG
        defaultPageWordShouldBeFound("ocrLang.equals=" + DEFAULT_OCR_LANG);

        // Get all the pageWordList where ocrLang equals to UPDATED_OCR_LANG
        defaultPageWordShouldNotBeFound("ocrLang.equals=" + UPDATED_OCR_LANG);
    }

    @Test
    @Transactional
    void getAllPageWordsByOcrLangIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where ocrLang not equals to DEFAULT_OCR_LANG
        defaultPageWordShouldNotBeFound("ocrLang.notEquals=" + DEFAULT_OCR_LANG);

        // Get all the pageWordList where ocrLang not equals to UPDATED_OCR_LANG
        defaultPageWordShouldBeFound("ocrLang.notEquals=" + UPDATED_OCR_LANG);
    }

    @Test
    @Transactional
    void getAllPageWordsByOcrLangIsInShouldWork() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where ocrLang in DEFAULT_OCR_LANG or UPDATED_OCR_LANG
        defaultPageWordShouldBeFound("ocrLang.in=" + DEFAULT_OCR_LANG + "," + UPDATED_OCR_LANG);

        // Get all the pageWordList where ocrLang equals to UPDATED_OCR_LANG
        defaultPageWordShouldNotBeFound("ocrLang.in=" + UPDATED_OCR_LANG);
    }

    @Test
    @Transactional
    void getAllPageWordsByOcrLangIsNullOrNotNull() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where ocrLang is not null
        defaultPageWordShouldBeFound("ocrLang.specified=true");

        // Get all the pageWordList where ocrLang is null
        defaultPageWordShouldNotBeFound("ocrLang.specified=false");
    }

    @Test
    @Transactional
    void getAllPageWordsByOcrLangContainsSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where ocrLang contains DEFAULT_OCR_LANG
        defaultPageWordShouldBeFound("ocrLang.contains=" + DEFAULT_OCR_LANG);

        // Get all the pageWordList where ocrLang contains UPDATED_OCR_LANG
        defaultPageWordShouldNotBeFound("ocrLang.contains=" + UPDATED_OCR_LANG);
    }

    @Test
    @Transactional
    void getAllPageWordsByOcrLangNotContainsSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where ocrLang does not contain DEFAULT_OCR_LANG
        defaultPageWordShouldNotBeFound("ocrLang.doesNotContain=" + DEFAULT_OCR_LANG);

        // Get all the pageWordList where ocrLang does not contain UPDATED_OCR_LANG
        defaultPageWordShouldBeFound("ocrLang.doesNotContain=" + UPDATED_OCR_LANG);
    }

    @Test
    @Transactional
    void getAllPageWordsByTextLineUUIDIsEqualToSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where textLineUUID equals to DEFAULT_TEXT_LINE_UUID
        defaultPageWordShouldBeFound("textLineUUID.equals=" + DEFAULT_TEXT_LINE_UUID);

        // Get all the pageWordList where textLineUUID equals to UPDATED_TEXT_LINE_UUID
        defaultPageWordShouldNotBeFound("textLineUUID.equals=" + UPDATED_TEXT_LINE_UUID);
    }

    @Test
    @Transactional
    void getAllPageWordsByTextLineUUIDIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where textLineUUID not equals to DEFAULT_TEXT_LINE_UUID
        defaultPageWordShouldNotBeFound("textLineUUID.notEquals=" + DEFAULT_TEXT_LINE_UUID);

        // Get all the pageWordList where textLineUUID not equals to UPDATED_TEXT_LINE_UUID
        defaultPageWordShouldBeFound("textLineUUID.notEquals=" + UPDATED_TEXT_LINE_UUID);
    }

    @Test
    @Transactional
    void getAllPageWordsByTextLineUUIDIsInShouldWork() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where textLineUUID in DEFAULT_TEXT_LINE_UUID or UPDATED_TEXT_LINE_UUID
        defaultPageWordShouldBeFound("textLineUUID.in=" + DEFAULT_TEXT_LINE_UUID + "," + UPDATED_TEXT_LINE_UUID);

        // Get all the pageWordList where textLineUUID equals to UPDATED_TEXT_LINE_UUID
        defaultPageWordShouldNotBeFound("textLineUUID.in=" + UPDATED_TEXT_LINE_UUID);
    }

    @Test
    @Transactional
    void getAllPageWordsByTextLineUUIDIsNullOrNotNull() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where textLineUUID is not null
        defaultPageWordShouldBeFound("textLineUUID.specified=true");

        // Get all the pageWordList where textLineUUID is null
        defaultPageWordShouldNotBeFound("textLineUUID.specified=false");
    }

    @Test
    @Transactional
    void getAllPageWordsByTextBlockUUIDIsEqualToSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where textBlockUUID equals to DEFAULT_TEXT_BLOCK_UUID
        defaultPageWordShouldBeFound("textBlockUUID.equals=" + DEFAULT_TEXT_BLOCK_UUID);

        // Get all the pageWordList where textBlockUUID equals to UPDATED_TEXT_BLOCK_UUID
        defaultPageWordShouldNotBeFound("textBlockUUID.equals=" + UPDATED_TEXT_BLOCK_UUID);
    }

    @Test
    @Transactional
    void getAllPageWordsByTextBlockUUIDIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where textBlockUUID not equals to DEFAULT_TEXT_BLOCK_UUID
        defaultPageWordShouldNotBeFound("textBlockUUID.notEquals=" + DEFAULT_TEXT_BLOCK_UUID);

        // Get all the pageWordList where textBlockUUID not equals to UPDATED_TEXT_BLOCK_UUID
        defaultPageWordShouldBeFound("textBlockUUID.notEquals=" + UPDATED_TEXT_BLOCK_UUID);
    }

    @Test
    @Transactional
    void getAllPageWordsByTextBlockUUIDIsInShouldWork() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where textBlockUUID in DEFAULT_TEXT_BLOCK_UUID or UPDATED_TEXT_BLOCK_UUID
        defaultPageWordShouldBeFound("textBlockUUID.in=" + DEFAULT_TEXT_BLOCK_UUID + "," + UPDATED_TEXT_BLOCK_UUID);

        // Get all the pageWordList where textBlockUUID equals to UPDATED_TEXT_BLOCK_UUID
        defaultPageWordShouldNotBeFound("textBlockUUID.in=" + UPDATED_TEXT_BLOCK_UUID);
    }

    @Test
    @Transactional
    void getAllPageWordsByTextBlockUUIDIsNullOrNotNull() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where textBlockUUID is not null
        defaultPageWordShouldBeFound("textBlockUUID.specified=true");

        // Get all the pageWordList where textBlockUUID is null
        defaultPageWordShouldNotBeFound("textBlockUUID.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPageWordShouldBeFound(String filter) throws Exception {
        restPageWordMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pageWord.getId().intValue())))
            .andExpect(jsonPath("$.[*].s_word").value(hasItem(DEFAULT_S_WORD)))
            .andExpect(jsonPath("$.[*].n_top").value(hasItem(DEFAULT_N_TOP.intValue())))
            .andExpect(jsonPath("$.[*].n_left").value(hasItem(DEFAULT_N_LEFT.intValue())))
            .andExpect(jsonPath("$.[*].n_heigth").value(hasItem(DEFAULT_N_HEIGTH.intValue())))
            .andExpect(jsonPath("$.[*].n_width").value(hasItem(DEFAULT_N_WIDTH.intValue())))
            .andExpect(jsonPath("$.[*].n_idx").value(hasItem(DEFAULT_N_IDX.intValue())))
            .andExpect(jsonPath("$.[*].mediaId").value(hasItem(DEFAULT_MEDIA_ID.intValue())))
            .andExpect(jsonPath("$.[*].pageNumber").value(hasItem(DEFAULT_PAGE_NUMBER)))
            .andExpect(jsonPath("$.[*].version").value(hasItem(sameInstant(DEFAULT_VERSION))))
            .andExpect(jsonPath("$.[*].ocrLang").value(hasItem(DEFAULT_OCR_LANG)))
            .andExpect(jsonPath("$.[*].textLineUUID").value(hasItem(DEFAULT_TEXT_LINE_UUID.toString())))
            .andExpect(jsonPath("$.[*].textBlockUUID").value(hasItem(DEFAULT_TEXT_BLOCK_UUID.toString())));

        // Check, that the count call also returns 1
        restPageWordMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPageWordShouldNotBeFound(String filter) throws Exception {
        restPageWordMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPageWordMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPageWord() throws Exception {
        // Get the pageWord
        restPageWordMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPageWord() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        int databaseSizeBeforeUpdate = pageWordRepository.findAll().size();

        // Update the pageWord
        PageWord updatedPageWord = pageWordRepository.findById(pageWord.getId()).get();
        // Disconnect from session so that the updates on updatedPageWord are not directly saved in db
        em.detach(updatedPageWord);
        updatedPageWord
            .s_word(UPDATED_S_WORD)
            .n_top(UPDATED_N_TOP)
            .n_left(UPDATED_N_LEFT)
            .n_heigth(UPDATED_N_HEIGTH)
            .n_width(UPDATED_N_WIDTH)
            .n_idx(UPDATED_N_IDX)
            .mediaId(UPDATED_MEDIA_ID)
            .pageNumber(UPDATED_PAGE_NUMBER)
            .version(UPDATED_VERSION)
            .ocrLang(UPDATED_OCR_LANG)
            .textLineUUID(UPDATED_TEXT_LINE_UUID)
            .textBlockUUID(UPDATED_TEXT_BLOCK_UUID);
        PageWordDTO pageWordDTO = pageWordMapper.toDto(updatedPageWord);

        restPageWordMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pageWordDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pageWordDTO))
            )
            .andExpect(status().isOk());

        // Validate the PageWord in the database
        List<PageWord> pageWordList = pageWordRepository.findAll();
        assertThat(pageWordList).hasSize(databaseSizeBeforeUpdate);
        PageWord testPageWord = pageWordList.get(pageWordList.size() - 1);
        assertThat(testPageWord.gets_word()).isEqualTo(UPDATED_S_WORD);
        assertThat(testPageWord.getn_top()).isEqualTo(UPDATED_N_TOP);
        assertThat(testPageWord.getn_left()).isEqualTo(UPDATED_N_LEFT);
        assertThat(testPageWord.getn_heigth()).isEqualTo(UPDATED_N_HEIGTH);
        assertThat(testPageWord.getn_width()).isEqualTo(UPDATED_N_WIDTH);
        assertThat(testPageWord.getn_idx()).isEqualTo(UPDATED_N_IDX);
        assertThat(testPageWord.getMediaId()).isEqualTo(UPDATED_MEDIA_ID);
        assertThat(testPageWord.getPageNumber()).isEqualTo(UPDATED_PAGE_NUMBER);
        assertThat(testPageWord.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testPageWord.getOcrLang()).isEqualTo(UPDATED_OCR_LANG);
        assertThat(testPageWord.getTextLineUUID()).isEqualTo(UPDATED_TEXT_LINE_UUID);
        assertThat(testPageWord.getTextBlockUUID()).isEqualTo(UPDATED_TEXT_BLOCK_UUID);
    }

    @Test
    @Transactional
    void putNonExistingPageWord() throws Exception {
        int databaseSizeBeforeUpdate = pageWordRepository.findAll().size();
        pageWord.setId(count.incrementAndGet());

        // Create the PageWord
        PageWordDTO pageWordDTO = pageWordMapper.toDto(pageWord);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPageWordMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pageWordDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pageWordDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PageWord in the database
        List<PageWord> pageWordList = pageWordRepository.findAll();
        assertThat(pageWordList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPageWord() throws Exception {
        int databaseSizeBeforeUpdate = pageWordRepository.findAll().size();
        pageWord.setId(count.incrementAndGet());

        // Create the PageWord
        PageWordDTO pageWordDTO = pageWordMapper.toDto(pageWord);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPageWordMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pageWordDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PageWord in the database
        List<PageWord> pageWordList = pageWordRepository.findAll();
        assertThat(pageWordList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPageWord() throws Exception {
        int databaseSizeBeforeUpdate = pageWordRepository.findAll().size();
        pageWord.setId(count.incrementAndGet());

        // Create the PageWord
        PageWordDTO pageWordDTO = pageWordMapper.toDto(pageWord);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPageWordMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pageWordDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PageWord in the database
        List<PageWord> pageWordList = pageWordRepository.findAll();
        assertThat(pageWordList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePageWordWithPatch() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        int databaseSizeBeforeUpdate = pageWordRepository.findAll().size();

        // Update the pageWord using partial update
        PageWord partialUpdatedPageWord = new PageWord();
        partialUpdatedPageWord.setId(pageWord.getId());

        partialUpdatedPageWord.version(UPDATED_VERSION);

        restPageWordMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPageWord.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPageWord))
            )
            .andExpect(status().isOk());

        // Validate the PageWord in the database
        List<PageWord> pageWordList = pageWordRepository.findAll();
        assertThat(pageWordList).hasSize(databaseSizeBeforeUpdate);
        PageWord testPageWord = pageWordList.get(pageWordList.size() - 1);
        assertThat(testPageWord.gets_word()).isEqualTo(DEFAULT_S_WORD);
        assertThat(testPageWord.getn_top()).isEqualTo(DEFAULT_N_TOP);
        assertThat(testPageWord.getn_left()).isEqualTo(DEFAULT_N_LEFT);
        assertThat(testPageWord.getn_heigth()).isEqualTo(DEFAULT_N_HEIGTH);
        assertThat(testPageWord.getn_width()).isEqualTo(DEFAULT_N_WIDTH);
        assertThat(testPageWord.getn_idx()).isEqualTo(DEFAULT_N_IDX);
        assertThat(testPageWord.getMediaId()).isEqualTo(DEFAULT_MEDIA_ID);
        assertThat(testPageWord.getPageNumber()).isEqualTo(DEFAULT_PAGE_NUMBER);
        assertThat(testPageWord.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testPageWord.getOcrLang()).isEqualTo(DEFAULT_OCR_LANG);
        assertThat(testPageWord.getTextLineUUID()).isEqualTo(DEFAULT_TEXT_LINE_UUID);
        assertThat(testPageWord.getTextBlockUUID()).isEqualTo(DEFAULT_TEXT_BLOCK_UUID);
    }

    @Test
    @Transactional
    void fullUpdatePageWordWithPatch() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        int databaseSizeBeforeUpdate = pageWordRepository.findAll().size();

        // Update the pageWord using partial update
        PageWord partialUpdatedPageWord = new PageWord();
        partialUpdatedPageWord.setId(pageWord.getId());

        partialUpdatedPageWord
            .s_word(UPDATED_S_WORD)
            .n_top(UPDATED_N_TOP)
            .n_left(UPDATED_N_LEFT)
            .n_heigth(UPDATED_N_HEIGTH)
            .n_width(UPDATED_N_WIDTH)
            .n_idx(UPDATED_N_IDX)
            .mediaId(UPDATED_MEDIA_ID)
            .pageNumber(UPDATED_PAGE_NUMBER)
            .version(UPDATED_VERSION)
            .ocrLang(UPDATED_OCR_LANG)
            .textLineUUID(UPDATED_TEXT_LINE_UUID)
            .textBlockUUID(UPDATED_TEXT_BLOCK_UUID);

        restPageWordMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPageWord.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPageWord))
            )
            .andExpect(status().isOk());

        // Validate the PageWord in the database
        List<PageWord> pageWordList = pageWordRepository.findAll();
        assertThat(pageWordList).hasSize(databaseSizeBeforeUpdate);
        PageWord testPageWord = pageWordList.get(pageWordList.size() - 1);
        assertThat(testPageWord.gets_word()).isEqualTo(UPDATED_S_WORD);
        assertThat(testPageWord.getn_top()).isEqualTo(UPDATED_N_TOP);
        assertThat(testPageWord.getn_left()).isEqualTo(UPDATED_N_LEFT);
        assertThat(testPageWord.getn_heigth()).isEqualTo(UPDATED_N_HEIGTH);
        assertThat(testPageWord.getn_width()).isEqualTo(UPDATED_N_WIDTH);
        assertThat(testPageWord.getn_idx()).isEqualTo(UPDATED_N_IDX);
        assertThat(testPageWord.getMediaId()).isEqualTo(UPDATED_MEDIA_ID);
        assertThat(testPageWord.getPageNumber()).isEqualTo(UPDATED_PAGE_NUMBER);
        assertThat(testPageWord.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testPageWord.getOcrLang()).isEqualTo(UPDATED_OCR_LANG);
        assertThat(testPageWord.getTextLineUUID()).isEqualTo(UPDATED_TEXT_LINE_UUID);
        assertThat(testPageWord.getTextBlockUUID()).isEqualTo(UPDATED_TEXT_BLOCK_UUID);
    }

    @Test
    @Transactional
    void patchNonExistingPageWord() throws Exception {
        int databaseSizeBeforeUpdate = pageWordRepository.findAll().size();
        pageWord.setId(count.incrementAndGet());

        // Create the PageWord
        PageWordDTO pageWordDTO = pageWordMapper.toDto(pageWord);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPageWordMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pageWordDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pageWordDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PageWord in the database
        List<PageWord> pageWordList = pageWordRepository.findAll();
        assertThat(pageWordList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPageWord() throws Exception {
        int databaseSizeBeforeUpdate = pageWordRepository.findAll().size();
        pageWord.setId(count.incrementAndGet());

        // Create the PageWord
        PageWordDTO pageWordDTO = pageWordMapper.toDto(pageWord);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPageWordMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pageWordDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PageWord in the database
        List<PageWord> pageWordList = pageWordRepository.findAll();
        assertThat(pageWordList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPageWord() throws Exception {
        int databaseSizeBeforeUpdate = pageWordRepository.findAll().size();
        pageWord.setId(count.incrementAndGet());

        // Create the PageWord
        PageWordDTO pageWordDTO = pageWordMapper.toDto(pageWord);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPageWordMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(pageWordDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PageWord in the database
        List<PageWord> pageWordList = pageWordRepository.findAll();
        assertThat(pageWordList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePageWord() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        int databaseSizeBeforeDelete = pageWordRepository.findAll().size();

        // Delete the pageWord
        restPageWordMockMvc
            .perform(delete(ENTITY_API_URL_ID, pageWord.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PageWord> pageWordList = pageWordRepository.findAll();
        assertThat(pageWordList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
