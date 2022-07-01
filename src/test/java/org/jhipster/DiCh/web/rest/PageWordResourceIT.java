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

    private static final String DEFAULT_WORD = "AAAAAAAAAA";
    private static final String UPDATED_WORD = "BBBBBBBBBB";

    private static final Long DEFAULT_LEFT = 1L;
    private static final Long UPDATED_LEFT = 2L;
    private static final Long SMALLER_LEFT = 1L - 1L;

    private static final Long DEFAULT_TOP = 1L;
    private static final Long UPDATED_TOP = 2L;
    private static final Long SMALLER_TOP = 1L - 1L;

    private static final Long DEFAULT_RIGHT = 1L;
    private static final Long UPDATED_RIGHT = 2L;
    private static final Long SMALLER_RIGHT = 1L - 1L;

    private static final Long DEFAULT_BOTTOM = 1L;
    private static final Long UPDATED_BOTTOM = 2L;
    private static final Long SMALLER_BOTTOM = 1L - 1L;

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
            .word(DEFAULT_WORD)
            .left(DEFAULT_LEFT)
            .top(DEFAULT_TOP)
            .right(DEFAULT_RIGHT)
            .bottom(DEFAULT_BOTTOM);
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
            .word(UPDATED_WORD)
            .left(UPDATED_LEFT)
            .top(UPDATED_TOP)
            .right(UPDATED_RIGHT)
            .bottom(UPDATED_BOTTOM);
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
        assertThat(testPageWord.getWord()).isEqualTo(DEFAULT_WORD);
        assertThat(testPageWord.getLeft()).isEqualTo(DEFAULT_LEFT);
        assertThat(testPageWord.getTop()).isEqualTo(DEFAULT_TOP);
        assertThat(testPageWord.getRight()).isEqualTo(DEFAULT_RIGHT);
        assertThat(testPageWord.getBottom()).isEqualTo(DEFAULT_BOTTOM);
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
            .andExpect(jsonPath("$.[*].word").value(hasItem(DEFAULT_WORD)))
            .andExpect(jsonPath("$.[*].left").value(hasItem(DEFAULT_LEFT.intValue())))
            .andExpect(jsonPath("$.[*].top").value(hasItem(DEFAULT_TOP.intValue())))
            .andExpect(jsonPath("$.[*].right").value(hasItem(DEFAULT_RIGHT.intValue())))
            .andExpect(jsonPath("$.[*].bottom").value(hasItem(DEFAULT_BOTTOM.intValue())));
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
            .andExpect(jsonPath("$.word").value(DEFAULT_WORD))
            .andExpect(jsonPath("$.left").value(DEFAULT_LEFT.intValue()))
            .andExpect(jsonPath("$.top").value(DEFAULT_TOP.intValue()))
            .andExpect(jsonPath("$.right").value(DEFAULT_RIGHT.intValue()))
            .andExpect(jsonPath("$.bottom").value(DEFAULT_BOTTOM.intValue()));
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
    void getAllPageWordsByWordIsEqualToSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where word equals to DEFAULT_WORD
        defaultPageWordShouldBeFound("word.equals=" + DEFAULT_WORD);

        // Get all the pageWordList where word equals to UPDATED_WORD
        defaultPageWordShouldNotBeFound("word.equals=" + UPDATED_WORD);
    }

    @Test
    @Transactional
    void getAllPageWordsByWordIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where word not equals to DEFAULT_WORD
        defaultPageWordShouldNotBeFound("word.notEquals=" + DEFAULT_WORD);

        // Get all the pageWordList where word not equals to UPDATED_WORD
        defaultPageWordShouldBeFound("word.notEquals=" + UPDATED_WORD);
    }

    @Test
    @Transactional
    void getAllPageWordsByWordIsInShouldWork() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where word in DEFAULT_WORD or UPDATED_WORD
        defaultPageWordShouldBeFound("word.in=" + DEFAULT_WORD + "," + UPDATED_WORD);

        // Get all the pageWordList where word equals to UPDATED_WORD
        defaultPageWordShouldNotBeFound("word.in=" + UPDATED_WORD);
    }

    @Test
    @Transactional
    void getAllPageWordsByWordIsNullOrNotNull() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where word is not null
        defaultPageWordShouldBeFound("word.specified=true");

        // Get all the pageWordList where word is null
        defaultPageWordShouldNotBeFound("word.specified=false");
    }

    @Test
    @Transactional
    void getAllPageWordsByWordContainsSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where word contains DEFAULT_WORD
        defaultPageWordShouldBeFound("word.contains=" + DEFAULT_WORD);

        // Get all the pageWordList where word contains UPDATED_WORD
        defaultPageWordShouldNotBeFound("word.contains=" + UPDATED_WORD);
    }

    @Test
    @Transactional
    void getAllPageWordsByWordNotContainsSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where word does not contain DEFAULT_WORD
        defaultPageWordShouldNotBeFound("word.doesNotContain=" + DEFAULT_WORD);

        // Get all the pageWordList where word does not contain UPDATED_WORD
        defaultPageWordShouldBeFound("word.doesNotContain=" + UPDATED_WORD);
    }

    @Test
    @Transactional
    void getAllPageWordsByLeftIsEqualToSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where left equals to DEFAULT_LEFT
        defaultPageWordShouldBeFound("left.equals=" + DEFAULT_LEFT);

        // Get all the pageWordList where left equals to UPDATED_LEFT
        defaultPageWordShouldNotBeFound("left.equals=" + UPDATED_LEFT);
    }

    @Test
    @Transactional
    void getAllPageWordsByLeftIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where left not equals to DEFAULT_LEFT
        defaultPageWordShouldNotBeFound("left.notEquals=" + DEFAULT_LEFT);

        // Get all the pageWordList where left not equals to UPDATED_LEFT
        defaultPageWordShouldBeFound("left.notEquals=" + UPDATED_LEFT);
    }

    @Test
    @Transactional
    void getAllPageWordsByLeftIsInShouldWork() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where left in DEFAULT_LEFT or UPDATED_LEFT
        defaultPageWordShouldBeFound("left.in=" + DEFAULT_LEFT + "," + UPDATED_LEFT);

        // Get all the pageWordList where left equals to UPDATED_LEFT
        defaultPageWordShouldNotBeFound("left.in=" + UPDATED_LEFT);
    }

    @Test
    @Transactional
    void getAllPageWordsByLeftIsNullOrNotNull() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where left is not null
        defaultPageWordShouldBeFound("left.specified=true");

        // Get all the pageWordList where left is null
        defaultPageWordShouldNotBeFound("left.specified=false");
    }

    @Test
    @Transactional
    void getAllPageWordsByLeftIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where left is greater than or equal to DEFAULT_LEFT
        defaultPageWordShouldBeFound("left.greaterThanOrEqual=" + DEFAULT_LEFT);

        // Get all the pageWordList where left is greater than or equal to UPDATED_LEFT
        defaultPageWordShouldNotBeFound("left.greaterThanOrEqual=" + UPDATED_LEFT);
    }

    @Test
    @Transactional
    void getAllPageWordsByLeftIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where left is less than or equal to DEFAULT_LEFT
        defaultPageWordShouldBeFound("left.lessThanOrEqual=" + DEFAULT_LEFT);

        // Get all the pageWordList where left is less than or equal to SMALLER_LEFT
        defaultPageWordShouldNotBeFound("left.lessThanOrEqual=" + SMALLER_LEFT);
    }

    @Test
    @Transactional
    void getAllPageWordsByLeftIsLessThanSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where left is less than DEFAULT_LEFT
        defaultPageWordShouldNotBeFound("left.lessThan=" + DEFAULT_LEFT);

        // Get all the pageWordList where left is less than UPDATED_LEFT
        defaultPageWordShouldBeFound("left.lessThan=" + UPDATED_LEFT);
    }

    @Test
    @Transactional
    void getAllPageWordsByLeftIsGreaterThanSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where left is greater than DEFAULT_LEFT
        defaultPageWordShouldNotBeFound("left.greaterThan=" + DEFAULT_LEFT);

        // Get all the pageWordList where left is greater than SMALLER_LEFT
        defaultPageWordShouldBeFound("left.greaterThan=" + SMALLER_LEFT);
    }

    @Test
    @Transactional
    void getAllPageWordsByTopIsEqualToSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where top equals to DEFAULT_TOP
        defaultPageWordShouldBeFound("top.equals=" + DEFAULT_TOP);

        // Get all the pageWordList where top equals to UPDATED_TOP
        defaultPageWordShouldNotBeFound("top.equals=" + UPDATED_TOP);
    }

    @Test
    @Transactional
    void getAllPageWordsByTopIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where top not equals to DEFAULT_TOP
        defaultPageWordShouldNotBeFound("top.notEquals=" + DEFAULT_TOP);

        // Get all the pageWordList where top not equals to UPDATED_TOP
        defaultPageWordShouldBeFound("top.notEquals=" + UPDATED_TOP);
    }

    @Test
    @Transactional
    void getAllPageWordsByTopIsInShouldWork() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where top in DEFAULT_TOP or UPDATED_TOP
        defaultPageWordShouldBeFound("top.in=" + DEFAULT_TOP + "," + UPDATED_TOP);

        // Get all the pageWordList where top equals to UPDATED_TOP
        defaultPageWordShouldNotBeFound("top.in=" + UPDATED_TOP);
    }

    @Test
    @Transactional
    void getAllPageWordsByTopIsNullOrNotNull() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where top is not null
        defaultPageWordShouldBeFound("top.specified=true");

        // Get all the pageWordList where top is null
        defaultPageWordShouldNotBeFound("top.specified=false");
    }

    @Test
    @Transactional
    void getAllPageWordsByTopIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where top is greater than or equal to DEFAULT_TOP
        defaultPageWordShouldBeFound("top.greaterThanOrEqual=" + DEFAULT_TOP);

        // Get all the pageWordList where top is greater than or equal to UPDATED_TOP
        defaultPageWordShouldNotBeFound("top.greaterThanOrEqual=" + UPDATED_TOP);
    }

    @Test
    @Transactional
    void getAllPageWordsByTopIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where top is less than or equal to DEFAULT_TOP
        defaultPageWordShouldBeFound("top.lessThanOrEqual=" + DEFAULT_TOP);

        // Get all the pageWordList where top is less than or equal to SMALLER_TOP
        defaultPageWordShouldNotBeFound("top.lessThanOrEqual=" + SMALLER_TOP);
    }

    @Test
    @Transactional
    void getAllPageWordsByTopIsLessThanSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where top is less than DEFAULT_TOP
        defaultPageWordShouldNotBeFound("top.lessThan=" + DEFAULT_TOP);

        // Get all the pageWordList where top is less than UPDATED_TOP
        defaultPageWordShouldBeFound("top.lessThan=" + UPDATED_TOP);
    }

    @Test
    @Transactional
    void getAllPageWordsByTopIsGreaterThanSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where top is greater than DEFAULT_TOP
        defaultPageWordShouldNotBeFound("top.greaterThan=" + DEFAULT_TOP);

        // Get all the pageWordList where top is greater than SMALLER_TOP
        defaultPageWordShouldBeFound("top.greaterThan=" + SMALLER_TOP);
    }

    @Test
    @Transactional
    void getAllPageWordsByRightIsEqualToSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where right equals to DEFAULT_RIGHT
        defaultPageWordShouldBeFound("right.equals=" + DEFAULT_RIGHT);

        // Get all the pageWordList where right equals to UPDATED_RIGHT
        defaultPageWordShouldNotBeFound("right.equals=" + UPDATED_RIGHT);
    }

    @Test
    @Transactional
    void getAllPageWordsByRightIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where right not equals to DEFAULT_RIGHT
        defaultPageWordShouldNotBeFound("right.notEquals=" + DEFAULT_RIGHT);

        // Get all the pageWordList where right not equals to UPDATED_RIGHT
        defaultPageWordShouldBeFound("right.notEquals=" + UPDATED_RIGHT);
    }

    @Test
    @Transactional
    void getAllPageWordsByRightIsInShouldWork() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where right in DEFAULT_RIGHT or UPDATED_RIGHT
        defaultPageWordShouldBeFound("right.in=" + DEFAULT_RIGHT + "," + UPDATED_RIGHT);

        // Get all the pageWordList where right equals to UPDATED_RIGHT
        defaultPageWordShouldNotBeFound("right.in=" + UPDATED_RIGHT);
    }

    @Test
    @Transactional
    void getAllPageWordsByRightIsNullOrNotNull() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where right is not null
        defaultPageWordShouldBeFound("right.specified=true");

        // Get all the pageWordList where right is null
        defaultPageWordShouldNotBeFound("right.specified=false");
    }

    @Test
    @Transactional
    void getAllPageWordsByRightIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where right is greater than or equal to DEFAULT_RIGHT
        defaultPageWordShouldBeFound("right.greaterThanOrEqual=" + DEFAULT_RIGHT);

        // Get all the pageWordList where right is greater than or equal to UPDATED_RIGHT
        defaultPageWordShouldNotBeFound("right.greaterThanOrEqual=" + UPDATED_RIGHT);
    }

    @Test
    @Transactional
    void getAllPageWordsByRightIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where right is less than or equal to DEFAULT_RIGHT
        defaultPageWordShouldBeFound("right.lessThanOrEqual=" + DEFAULT_RIGHT);

        // Get all the pageWordList where right is less than or equal to SMALLER_RIGHT
        defaultPageWordShouldNotBeFound("right.lessThanOrEqual=" + SMALLER_RIGHT);
    }

    @Test
    @Transactional
    void getAllPageWordsByRightIsLessThanSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where right is less than DEFAULT_RIGHT
        defaultPageWordShouldNotBeFound("right.lessThan=" + DEFAULT_RIGHT);

        // Get all the pageWordList where right is less than UPDATED_RIGHT
        defaultPageWordShouldBeFound("right.lessThan=" + UPDATED_RIGHT);
    }

    @Test
    @Transactional
    void getAllPageWordsByRightIsGreaterThanSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where right is greater than DEFAULT_RIGHT
        defaultPageWordShouldNotBeFound("right.greaterThan=" + DEFAULT_RIGHT);

        // Get all the pageWordList where right is greater than SMALLER_RIGHT
        defaultPageWordShouldBeFound("right.greaterThan=" + SMALLER_RIGHT);
    }

    @Test
    @Transactional
    void getAllPageWordsByBottomIsEqualToSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where bottom equals to DEFAULT_BOTTOM
        defaultPageWordShouldBeFound("bottom.equals=" + DEFAULT_BOTTOM);

        // Get all the pageWordList where bottom equals to UPDATED_BOTTOM
        defaultPageWordShouldNotBeFound("bottom.equals=" + UPDATED_BOTTOM);
    }

    @Test
    @Transactional
    void getAllPageWordsByBottomIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where bottom not equals to DEFAULT_BOTTOM
        defaultPageWordShouldNotBeFound("bottom.notEquals=" + DEFAULT_BOTTOM);

        // Get all the pageWordList where bottom not equals to UPDATED_BOTTOM
        defaultPageWordShouldBeFound("bottom.notEquals=" + UPDATED_BOTTOM);
    }

    @Test
    @Transactional
    void getAllPageWordsByBottomIsInShouldWork() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where bottom in DEFAULT_BOTTOM or UPDATED_BOTTOM
        defaultPageWordShouldBeFound("bottom.in=" + DEFAULT_BOTTOM + "," + UPDATED_BOTTOM);

        // Get all the pageWordList where bottom equals to UPDATED_BOTTOM
        defaultPageWordShouldNotBeFound("bottom.in=" + UPDATED_BOTTOM);
    }

    @Test
    @Transactional
    void getAllPageWordsByBottomIsNullOrNotNull() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where bottom is not null
        defaultPageWordShouldBeFound("bottom.specified=true");

        // Get all the pageWordList where bottom is null
        defaultPageWordShouldNotBeFound("bottom.specified=false");
    }

    @Test
    @Transactional
    void getAllPageWordsByBottomIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where bottom is greater than or equal to DEFAULT_BOTTOM
        defaultPageWordShouldBeFound("bottom.greaterThanOrEqual=" + DEFAULT_BOTTOM);

        // Get all the pageWordList where bottom is greater than or equal to UPDATED_BOTTOM
        defaultPageWordShouldNotBeFound("bottom.greaterThanOrEqual=" + UPDATED_BOTTOM);
    }

    @Test
    @Transactional
    void getAllPageWordsByBottomIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where bottom is less than or equal to DEFAULT_BOTTOM
        defaultPageWordShouldBeFound("bottom.lessThanOrEqual=" + DEFAULT_BOTTOM);

        // Get all the pageWordList where bottom is less than or equal to SMALLER_BOTTOM
        defaultPageWordShouldNotBeFound("bottom.lessThanOrEqual=" + SMALLER_BOTTOM);
    }

    @Test
    @Transactional
    void getAllPageWordsByBottomIsLessThanSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where bottom is less than DEFAULT_BOTTOM
        defaultPageWordShouldNotBeFound("bottom.lessThan=" + DEFAULT_BOTTOM);

        // Get all the pageWordList where bottom is less than UPDATED_BOTTOM
        defaultPageWordShouldBeFound("bottom.lessThan=" + UPDATED_BOTTOM);
    }

    @Test
    @Transactional
    void getAllPageWordsByBottomIsGreaterThanSomething() throws Exception {
        // Initialize the database
        pageWordRepository.saveAndFlush(pageWord);

        // Get all the pageWordList where bottom is greater than DEFAULT_BOTTOM
        defaultPageWordShouldNotBeFound("bottom.greaterThan=" + DEFAULT_BOTTOM);

        // Get all the pageWordList where bottom is greater than SMALLER_BOTTOM
        defaultPageWordShouldBeFound("bottom.greaterThan=" + SMALLER_BOTTOM);
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
            .andExpect(jsonPath("$.[*].word").value(hasItem(DEFAULT_WORD)))
            .andExpect(jsonPath("$.[*].left").value(hasItem(DEFAULT_LEFT.intValue())))
            .andExpect(jsonPath("$.[*].top").value(hasItem(DEFAULT_TOP.intValue())))
            .andExpect(jsonPath("$.[*].right").value(hasItem(DEFAULT_RIGHT.intValue())))
            .andExpect(jsonPath("$.[*].bottom").value(hasItem(DEFAULT_BOTTOM.intValue())));

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
        updatedPageWord.word(UPDATED_WORD).left(UPDATED_LEFT).top(UPDATED_TOP).right(UPDATED_RIGHT).bottom(UPDATED_BOTTOM);
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
        assertThat(testPageWord.getWord()).isEqualTo(UPDATED_WORD);
        assertThat(testPageWord.getLeft()).isEqualTo(UPDATED_LEFT);
        assertThat(testPageWord.getTop()).isEqualTo(UPDATED_TOP);
        assertThat(testPageWord.getRight()).isEqualTo(UPDATED_RIGHT);
        assertThat(testPageWord.getBottom()).isEqualTo(UPDATED_BOTTOM);
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
        assertThat(testPageWord.getWord()).isEqualTo(DEFAULT_WORD);
        assertThat(testPageWord.getLeft()).isEqualTo(DEFAULT_LEFT);
        assertThat(testPageWord.getTop()).isEqualTo(DEFAULT_TOP);
        assertThat(testPageWord.getRight()).isEqualTo(DEFAULT_RIGHT);
        assertThat(testPageWord.getBottom()).isEqualTo(DEFAULT_BOTTOM);
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

        partialUpdatedPageWord.word(UPDATED_WORD).left(UPDATED_LEFT).top(UPDATED_TOP).right(UPDATED_RIGHT).bottom(UPDATED_BOTTOM);

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
        assertThat(testPageWord.getWord()).isEqualTo(UPDATED_WORD);
        assertThat(testPageWord.getLeft()).isEqualTo(UPDATED_LEFT);
        assertThat(testPageWord.getTop()).isEqualTo(UPDATED_TOP);
        assertThat(testPageWord.getRight()).isEqualTo(UPDATED_RIGHT);
        assertThat(testPageWord.getBottom()).isEqualTo(UPDATED_BOTTOM);
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
