package org.jhipster.dich.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.jhipster.dich.IntegrationTest;
import org.jhipster.dich.domain.PageImage;
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
            .n_idx(DEFAULT_N_IDX);
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
            .n_idx(UPDATED_N_IDX);
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
            .andExpect(jsonPath("$.[*].n_idx").value(hasItem(DEFAULT_N_IDX.intValue())));
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
            .andExpect(jsonPath("$.n_idx").value(DEFAULT_N_IDX.intValue()));
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
    void getAllPageWordsByPageImageIsEqualToSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);
        PageImage pageImage;
        if (TestUtil.findAll(em, PageImage.class).isEmpty()) {
            pageImage = PageImageResourceIT.createEntity(em);
            em.persist(pageImage);
            em.flush();
        } else {
            pageImage = TestUtil.findAll(em, PageImage.class).get(0);
        }
        em.persist(pageImage);
        em.flush();
        pageWord.setPageImage(pageImage);
        pageWordRepository.saveAndFlush(pageWord);
        Long pageImageId = pageImage.getId();

        // Get all the pageWordList where pageImage equals to pageImageId
        defaultPageWordShouldBeFound("pageImageId.equals=" + pageImageId);

        // Get all the pageWordList where pageImage equals to (pageImageId + 1)
        defaultPageWordShouldNotBeFound("pageImageId.equals=" + (pageImageId + 1));
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
            .andExpect(jsonPath("$.[*].n_idx").value(hasItem(DEFAULT_N_IDX.intValue())));

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
            .n_idx(UPDATED_N_IDX);
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
            .n_idx(UPDATED_N_IDX);

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