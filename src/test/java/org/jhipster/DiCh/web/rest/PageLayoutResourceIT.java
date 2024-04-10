package org.jhipster.dich.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.jhipster.dich.web.rest.TestUtil.sameNumber;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.jhipster.dich.IntegrationTest;
import org.jhipster.dich.domain.PageLayout;
import org.jhipster.dich.repository.PageLayoutRepository;
import org.jhipster.dich.service.criteria.PageLayoutCriteria;
import org.jhipster.dich.service.dto.PageLayoutDTO;
import org.jhipster.dich.service.mapper.PageLayoutMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PageLayoutResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PageLayoutResourceIT {

    private static final Long DEFAULT_MEDIA_ID = 1L;
    private static final Long UPDATED_MEDIA_ID = 2L;
    private static final Long SMALLER_MEDIA_ID = 1L - 1L;

    private static final Integer DEFAULT_PAGE_NUMBER = 1;
    private static final Integer UPDATED_PAGE_NUMBER = 2;
    private static final Integer SMALLER_PAGE_NUMBER = 1 - 1;

    private static final String DEFAULT_ITERATOR_LEVEL = "AAAAAAAAAA";
    private static final String UPDATED_ITERATOR_LEVEL = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_RECT_TOP = new BigDecimal(1);
    private static final BigDecimal UPDATED_RECT_TOP = new BigDecimal(2);
    private static final BigDecimal SMALLER_RECT_TOP = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_RECT_LEFT = new BigDecimal(1);
    private static final BigDecimal UPDATED_RECT_LEFT = new BigDecimal(2);
    private static final BigDecimal SMALLER_RECT_LEFT = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_RECT_RIGHT = new BigDecimal(1);
    private static final BigDecimal UPDATED_RECT_RIGHT = new BigDecimal(2);
    private static final BigDecimal SMALLER_RECT_RIGHT = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_RECT_BOTTOM = new BigDecimal(1);
    private static final BigDecimal UPDATED_RECT_BOTTOM = new BigDecimal(2);
    private static final BigDecimal SMALLER_RECT_BOTTOM = new BigDecimal(1 - 1);

    private static final Integer DEFAULT_PARENT_ID = 1;
    private static final Integer UPDATED_PARENT_ID = 2;
    private static final Integer SMALLER_PARENT_ID = 1 - 1;

    private static final UUID DEFAULT_ITEM_GUID = UUID.randomUUID();
    private static final UUID UPDATED_ITEM_GUID = UUID.randomUUID();

    private static final UUID DEFAULT_PARENT_GUID = UUID.randomUUID();
    private static final UUID UPDATED_PARENT_GUID = UUID.randomUUID();

    private static final String ENTITY_API_URL = "/api/page-layouts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PageLayoutRepository pageLayoutRepository;

    @Autowired
    private PageLayoutMapper pageLayoutMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPageLayoutMockMvc;

    private PageLayout pageLayout;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PageLayout createEntity(EntityManager em) {
        PageLayout pageLayout = new PageLayout()
            .mediaId(DEFAULT_MEDIA_ID)
            .pageNumber(DEFAULT_PAGE_NUMBER)
            .iterator_level(DEFAULT_ITERATOR_LEVEL)
            .rect_top(DEFAULT_RECT_TOP)
            .rect_left(DEFAULT_RECT_LEFT)
            .rect_right(DEFAULT_RECT_RIGHT)
            .rect_bottom(DEFAULT_RECT_BOTTOM)
            .parent_id(DEFAULT_PARENT_ID)
            .itemGUID(DEFAULT_ITEM_GUID)
            .parentGUID(DEFAULT_PARENT_GUID);
        return pageLayout;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PageLayout createUpdatedEntity(EntityManager em) {
        PageLayout pageLayout = new PageLayout()
            .mediaId(UPDATED_MEDIA_ID)
            .pageNumber(UPDATED_PAGE_NUMBER)
            .iterator_level(UPDATED_ITERATOR_LEVEL)
            .rect_top(UPDATED_RECT_TOP)
            .rect_left(UPDATED_RECT_LEFT)
            .rect_right(UPDATED_RECT_RIGHT)
            .rect_bottom(UPDATED_RECT_BOTTOM)
            .parent_id(UPDATED_PARENT_ID)
            .itemGUID(UPDATED_ITEM_GUID)
            .parentGUID(UPDATED_PARENT_GUID);
        return pageLayout;
    }

    @BeforeEach
    public void initTest() {
        pageLayout = createEntity(em);
    }

    @Test
    @Transactional
    void createPageLayout() throws Exception {
        int databaseSizeBeforeCreate = pageLayoutRepository.findAll().size();
        // Create the PageLayout
        PageLayoutDTO pageLayoutDTO = pageLayoutMapper.toDto(pageLayout);
        restPageLayoutMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pageLayoutDTO)))
            .andExpect(status().isCreated());

        // Validate the PageLayout in the database
        List<PageLayout> pageLayoutList = pageLayoutRepository.findAll();
        assertThat(pageLayoutList).hasSize(databaseSizeBeforeCreate + 1);
        PageLayout testPageLayout = pageLayoutList.get(pageLayoutList.size() - 1);
        assertThat(testPageLayout.getMediaId()).isEqualTo(DEFAULT_MEDIA_ID);
        assertThat(testPageLayout.getPageNumber()).isEqualTo(DEFAULT_PAGE_NUMBER);
        assertThat(testPageLayout.getIterator_level()).isEqualTo(DEFAULT_ITERATOR_LEVEL);
        assertThat(testPageLayout.getRect_top()).isEqualByComparingTo(DEFAULT_RECT_TOP);
        assertThat(testPageLayout.getRect_left()).isEqualByComparingTo(DEFAULT_RECT_LEFT);
        assertThat(testPageLayout.getRect_right()).isEqualByComparingTo(DEFAULT_RECT_RIGHT);
        assertThat(testPageLayout.getRect_bottom()).isEqualByComparingTo(DEFAULT_RECT_BOTTOM);
        assertThat(testPageLayout.getParent_id()).isEqualTo(DEFAULT_PARENT_ID);
        assertThat(testPageLayout.getItemGUID()).isEqualTo(DEFAULT_ITEM_GUID);
        assertThat(testPageLayout.getParentGUID()).isEqualTo(DEFAULT_PARENT_GUID);
    }

    @Test
    @Transactional
    void createPageLayoutWithExistingId() throws Exception {
        // Create the PageLayout with an existing ID
        pageLayout.setId(1L);
        PageLayoutDTO pageLayoutDTO = pageLayoutMapper.toDto(pageLayout);

        int databaseSizeBeforeCreate = pageLayoutRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPageLayoutMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pageLayoutDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PageLayout in the database
        List<PageLayout> pageLayoutList = pageLayoutRepository.findAll();
        assertThat(pageLayoutList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPageLayouts() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList
        restPageLayoutMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pageLayout.getId().intValue())))
            .andExpect(jsonPath("$.[*].mediaId").value(hasItem(DEFAULT_MEDIA_ID.intValue())))
            .andExpect(jsonPath("$.[*].pageNumber").value(hasItem(DEFAULT_PAGE_NUMBER)))
            .andExpect(jsonPath("$.[*].iterator_level").value(hasItem(DEFAULT_ITERATOR_LEVEL)))
            .andExpect(jsonPath("$.[*].rect_top").value(hasItem(sameNumber(DEFAULT_RECT_TOP))))
            .andExpect(jsonPath("$.[*].rect_left").value(hasItem(sameNumber(DEFAULT_RECT_LEFT))))
            .andExpect(jsonPath("$.[*].rect_right").value(hasItem(sameNumber(DEFAULT_RECT_RIGHT))))
            .andExpect(jsonPath("$.[*].rect_bottom").value(hasItem(sameNumber(DEFAULT_RECT_BOTTOM))))
            .andExpect(jsonPath("$.[*].parent_id").value(hasItem(DEFAULT_PARENT_ID)))
            .andExpect(jsonPath("$.[*].itemGUID").value(hasItem(DEFAULT_ITEM_GUID.toString())))
            .andExpect(jsonPath("$.[*].parentGUID").value(hasItem(DEFAULT_PARENT_GUID.toString())));
    }

    @Test
    @Transactional
    void getPageLayout() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get the pageLayout
        restPageLayoutMockMvc
            .perform(get(ENTITY_API_URL_ID, pageLayout.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pageLayout.getId().intValue()))
            .andExpect(jsonPath("$.mediaId").value(DEFAULT_MEDIA_ID.intValue()))
            .andExpect(jsonPath("$.pageNumber").value(DEFAULT_PAGE_NUMBER))
            .andExpect(jsonPath("$.iterator_level").value(DEFAULT_ITERATOR_LEVEL))
            .andExpect(jsonPath("$.rect_top").value(sameNumber(DEFAULT_RECT_TOP)))
            .andExpect(jsonPath("$.rect_left").value(sameNumber(DEFAULT_RECT_LEFT)))
            .andExpect(jsonPath("$.rect_right").value(sameNumber(DEFAULT_RECT_RIGHT)))
            .andExpect(jsonPath("$.rect_bottom").value(sameNumber(DEFAULT_RECT_BOTTOM)))
            .andExpect(jsonPath("$.parent_id").value(DEFAULT_PARENT_ID))
            .andExpect(jsonPath("$.itemGUID").value(DEFAULT_ITEM_GUID.toString()))
            .andExpect(jsonPath("$.parentGUID").value(DEFAULT_PARENT_GUID.toString()));
    }

    @Test
    @Transactional
    void getPageLayoutsByIdFiltering() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        Long id = pageLayout.getId();

        defaultPageLayoutShouldBeFound("id.equals=" + id);
        defaultPageLayoutShouldNotBeFound("id.notEquals=" + id);

        defaultPageLayoutShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPageLayoutShouldNotBeFound("id.greaterThan=" + id);

        defaultPageLayoutShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPageLayoutShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPageLayoutsByMediaIdIsEqualToSomething() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where mediaId equals to DEFAULT_MEDIA_ID
        defaultPageLayoutShouldBeFound("mediaId.equals=" + DEFAULT_MEDIA_ID);

        // Get all the pageLayoutList where mediaId equals to UPDATED_MEDIA_ID
        defaultPageLayoutShouldNotBeFound("mediaId.equals=" + UPDATED_MEDIA_ID);
    }

    @Test
    @Transactional
    void getAllPageLayoutsByMediaIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where mediaId not equals to DEFAULT_MEDIA_ID
        defaultPageLayoutShouldNotBeFound("mediaId.notEquals=" + DEFAULT_MEDIA_ID);

        // Get all the pageLayoutList where mediaId not equals to UPDATED_MEDIA_ID
        defaultPageLayoutShouldBeFound("mediaId.notEquals=" + UPDATED_MEDIA_ID);
    }

    @Test
    @Transactional
    void getAllPageLayoutsByMediaIdIsInShouldWork() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where mediaId in DEFAULT_MEDIA_ID or UPDATED_MEDIA_ID
        defaultPageLayoutShouldBeFound("mediaId.in=" + DEFAULT_MEDIA_ID + "," + UPDATED_MEDIA_ID);

        // Get all the pageLayoutList where mediaId equals to UPDATED_MEDIA_ID
        defaultPageLayoutShouldNotBeFound("mediaId.in=" + UPDATED_MEDIA_ID);
    }

    @Test
    @Transactional
    void getAllPageLayoutsByMediaIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where mediaId is not null
        defaultPageLayoutShouldBeFound("mediaId.specified=true");

        // Get all the pageLayoutList where mediaId is null
        defaultPageLayoutShouldNotBeFound("mediaId.specified=false");
    }

    @Test
    @Transactional
    void getAllPageLayoutsByMediaIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where mediaId is greater than or equal to DEFAULT_MEDIA_ID
        defaultPageLayoutShouldBeFound("mediaId.greaterThanOrEqual=" + DEFAULT_MEDIA_ID);

        // Get all the pageLayoutList where mediaId is greater than or equal to UPDATED_MEDIA_ID
        defaultPageLayoutShouldNotBeFound("mediaId.greaterThanOrEqual=" + UPDATED_MEDIA_ID);
    }

    @Test
    @Transactional
    void getAllPageLayoutsByMediaIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where mediaId is less than or equal to DEFAULT_MEDIA_ID
        defaultPageLayoutShouldBeFound("mediaId.lessThanOrEqual=" + DEFAULT_MEDIA_ID);

        // Get all the pageLayoutList where mediaId is less than or equal to SMALLER_MEDIA_ID
        defaultPageLayoutShouldNotBeFound("mediaId.lessThanOrEqual=" + SMALLER_MEDIA_ID);
    }

    @Test
    @Transactional
    void getAllPageLayoutsByMediaIdIsLessThanSomething() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where mediaId is less than DEFAULT_MEDIA_ID
        defaultPageLayoutShouldNotBeFound("mediaId.lessThan=" + DEFAULT_MEDIA_ID);

        // Get all the pageLayoutList where mediaId is less than UPDATED_MEDIA_ID
        defaultPageLayoutShouldBeFound("mediaId.lessThan=" + UPDATED_MEDIA_ID);
    }

    @Test
    @Transactional
    void getAllPageLayoutsByMediaIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where mediaId is greater than DEFAULT_MEDIA_ID
        defaultPageLayoutShouldNotBeFound("mediaId.greaterThan=" + DEFAULT_MEDIA_ID);

        // Get all the pageLayoutList where mediaId is greater than SMALLER_MEDIA_ID
        defaultPageLayoutShouldBeFound("mediaId.greaterThan=" + SMALLER_MEDIA_ID);
    }

    @Test
    @Transactional
    void getAllPageLayoutsByPageNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where pageNumber equals to DEFAULT_PAGE_NUMBER
        defaultPageLayoutShouldBeFound("pageNumber.equals=" + DEFAULT_PAGE_NUMBER);

        // Get all the pageLayoutList where pageNumber equals to UPDATED_PAGE_NUMBER
        defaultPageLayoutShouldNotBeFound("pageNumber.equals=" + UPDATED_PAGE_NUMBER);
    }

    @Test
    @Transactional
    void getAllPageLayoutsByPageNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where pageNumber not equals to DEFAULT_PAGE_NUMBER
        defaultPageLayoutShouldNotBeFound("pageNumber.notEquals=" + DEFAULT_PAGE_NUMBER);

        // Get all the pageLayoutList where pageNumber not equals to UPDATED_PAGE_NUMBER
        defaultPageLayoutShouldBeFound("pageNumber.notEquals=" + UPDATED_PAGE_NUMBER);
    }

    @Test
    @Transactional
    void getAllPageLayoutsByPageNumberIsInShouldWork() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where pageNumber in DEFAULT_PAGE_NUMBER or UPDATED_PAGE_NUMBER
        defaultPageLayoutShouldBeFound("pageNumber.in=" + DEFAULT_PAGE_NUMBER + "," + UPDATED_PAGE_NUMBER);

        // Get all the pageLayoutList where pageNumber equals to UPDATED_PAGE_NUMBER
        defaultPageLayoutShouldNotBeFound("pageNumber.in=" + UPDATED_PAGE_NUMBER);
    }

    @Test
    @Transactional
    void getAllPageLayoutsByPageNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where pageNumber is not null
        defaultPageLayoutShouldBeFound("pageNumber.specified=true");

        // Get all the pageLayoutList where pageNumber is null
        defaultPageLayoutShouldNotBeFound("pageNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllPageLayoutsByPageNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where pageNumber is greater than or equal to DEFAULT_PAGE_NUMBER
        defaultPageLayoutShouldBeFound("pageNumber.greaterThanOrEqual=" + DEFAULT_PAGE_NUMBER);

        // Get all the pageLayoutList where pageNumber is greater than or equal to UPDATED_PAGE_NUMBER
        defaultPageLayoutShouldNotBeFound("pageNumber.greaterThanOrEqual=" + UPDATED_PAGE_NUMBER);
    }

    @Test
    @Transactional
    void getAllPageLayoutsByPageNumberIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where pageNumber is less than or equal to DEFAULT_PAGE_NUMBER
        defaultPageLayoutShouldBeFound("pageNumber.lessThanOrEqual=" + DEFAULT_PAGE_NUMBER);

        // Get all the pageLayoutList where pageNumber is less than or equal to SMALLER_PAGE_NUMBER
        defaultPageLayoutShouldNotBeFound("pageNumber.lessThanOrEqual=" + SMALLER_PAGE_NUMBER);
    }

    @Test
    @Transactional
    void getAllPageLayoutsByPageNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where pageNumber is less than DEFAULT_PAGE_NUMBER
        defaultPageLayoutShouldNotBeFound("pageNumber.lessThan=" + DEFAULT_PAGE_NUMBER);

        // Get all the pageLayoutList where pageNumber is less than UPDATED_PAGE_NUMBER
        defaultPageLayoutShouldBeFound("pageNumber.lessThan=" + UPDATED_PAGE_NUMBER);
    }

    @Test
    @Transactional
    void getAllPageLayoutsByPageNumberIsGreaterThanSomething() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where pageNumber is greater than DEFAULT_PAGE_NUMBER
        defaultPageLayoutShouldNotBeFound("pageNumber.greaterThan=" + DEFAULT_PAGE_NUMBER);

        // Get all the pageLayoutList where pageNumber is greater than SMALLER_PAGE_NUMBER
        defaultPageLayoutShouldBeFound("pageNumber.greaterThan=" + SMALLER_PAGE_NUMBER);
    }

    @Test
    @Transactional
    void getAllPageLayoutsByIterator_levelIsEqualToSomething() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where iterator_level equals to DEFAULT_ITERATOR_LEVEL
        defaultPageLayoutShouldBeFound("iterator_level.equals=" + DEFAULT_ITERATOR_LEVEL);

        // Get all the pageLayoutList where iterator_level equals to UPDATED_ITERATOR_LEVEL
        defaultPageLayoutShouldNotBeFound("iterator_level.equals=" + UPDATED_ITERATOR_LEVEL);
    }

    @Test
    @Transactional
    void getAllPageLayoutsByIterator_levelIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where iterator_level not equals to DEFAULT_ITERATOR_LEVEL
        defaultPageLayoutShouldNotBeFound("iterator_level.notEquals=" + DEFAULT_ITERATOR_LEVEL);

        // Get all the pageLayoutList where iterator_level not equals to UPDATED_ITERATOR_LEVEL
        defaultPageLayoutShouldBeFound("iterator_level.notEquals=" + UPDATED_ITERATOR_LEVEL);
    }

    @Test
    @Transactional
    void getAllPageLayoutsByIterator_levelIsInShouldWork() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where iterator_level in DEFAULT_ITERATOR_LEVEL or UPDATED_ITERATOR_LEVEL
        defaultPageLayoutShouldBeFound("iterator_level.in=" + DEFAULT_ITERATOR_LEVEL + "," + UPDATED_ITERATOR_LEVEL);

        // Get all the pageLayoutList where iterator_level equals to UPDATED_ITERATOR_LEVEL
        defaultPageLayoutShouldNotBeFound("iterator_level.in=" + UPDATED_ITERATOR_LEVEL);
    }

    @Test
    @Transactional
    void getAllPageLayoutsByIterator_levelIsNullOrNotNull() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where iterator_level is not null
        defaultPageLayoutShouldBeFound("iterator_level.specified=true");

        // Get all the pageLayoutList where iterator_level is null
        defaultPageLayoutShouldNotBeFound("iterator_level.specified=false");
    }

    @Test
    @Transactional
    void getAllPageLayoutsByIterator_levelContainsSomething() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where iterator_level contains DEFAULT_ITERATOR_LEVEL
        defaultPageLayoutShouldBeFound("iterator_level.contains=" + DEFAULT_ITERATOR_LEVEL);

        // Get all the pageLayoutList where iterator_level contains UPDATED_ITERATOR_LEVEL
        defaultPageLayoutShouldNotBeFound("iterator_level.contains=" + UPDATED_ITERATOR_LEVEL);
    }

    @Test
    @Transactional
    void getAllPageLayoutsByIterator_levelNotContainsSomething() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where iterator_level does not contain DEFAULT_ITERATOR_LEVEL
        defaultPageLayoutShouldNotBeFound("iterator_level.doesNotContain=" + DEFAULT_ITERATOR_LEVEL);

        // Get all the pageLayoutList where iterator_level does not contain UPDATED_ITERATOR_LEVEL
        defaultPageLayoutShouldBeFound("iterator_level.doesNotContain=" + UPDATED_ITERATOR_LEVEL);
    }

    @Test
    @Transactional
    void getAllPageLayoutsByRect_topIsEqualToSomething() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where rect_top equals to DEFAULT_RECT_TOP
        defaultPageLayoutShouldBeFound("rect_top.equals=" + DEFAULT_RECT_TOP);

        // Get all the pageLayoutList where rect_top equals to UPDATED_RECT_TOP
        defaultPageLayoutShouldNotBeFound("rect_top.equals=" + UPDATED_RECT_TOP);
    }

    @Test
    @Transactional
    void getAllPageLayoutsByRect_topIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where rect_top not equals to DEFAULT_RECT_TOP
        defaultPageLayoutShouldNotBeFound("rect_top.notEquals=" + DEFAULT_RECT_TOP);

        // Get all the pageLayoutList where rect_top not equals to UPDATED_RECT_TOP
        defaultPageLayoutShouldBeFound("rect_top.notEquals=" + UPDATED_RECT_TOP);
    }

    @Test
    @Transactional
    void getAllPageLayoutsByRect_topIsInShouldWork() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where rect_top in DEFAULT_RECT_TOP or UPDATED_RECT_TOP
        defaultPageLayoutShouldBeFound("rect_top.in=" + DEFAULT_RECT_TOP + "," + UPDATED_RECT_TOP);

        // Get all the pageLayoutList where rect_top equals to UPDATED_RECT_TOP
        defaultPageLayoutShouldNotBeFound("rect_top.in=" + UPDATED_RECT_TOP);
    }

    @Test
    @Transactional
    void getAllPageLayoutsByRect_topIsNullOrNotNull() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where rect_top is not null
        defaultPageLayoutShouldBeFound("rect_top.specified=true");

        // Get all the pageLayoutList where rect_top is null
        defaultPageLayoutShouldNotBeFound("rect_top.specified=false");
    }

    @Test
    @Transactional
    void getAllPageLayoutsByRect_topIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where rect_top is greater than or equal to DEFAULT_RECT_TOP
        defaultPageLayoutShouldBeFound("rect_top.greaterThanOrEqual=" + DEFAULT_RECT_TOP);

        // Get all the pageLayoutList where rect_top is greater than or equal to UPDATED_RECT_TOP
        defaultPageLayoutShouldNotBeFound("rect_top.greaterThanOrEqual=" + UPDATED_RECT_TOP);
    }

    @Test
    @Transactional
    void getAllPageLayoutsByRect_topIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where rect_top is less than or equal to DEFAULT_RECT_TOP
        defaultPageLayoutShouldBeFound("rect_top.lessThanOrEqual=" + DEFAULT_RECT_TOP);

        // Get all the pageLayoutList where rect_top is less than or equal to SMALLER_RECT_TOP
        defaultPageLayoutShouldNotBeFound("rect_top.lessThanOrEqual=" + SMALLER_RECT_TOP);
    }

    @Test
    @Transactional
    void getAllPageLayoutsByRect_topIsLessThanSomething() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where rect_top is less than DEFAULT_RECT_TOP
        defaultPageLayoutShouldNotBeFound("rect_top.lessThan=" + DEFAULT_RECT_TOP);

        // Get all the pageLayoutList where rect_top is less than UPDATED_RECT_TOP
        defaultPageLayoutShouldBeFound("rect_top.lessThan=" + UPDATED_RECT_TOP);
    }

    @Test
    @Transactional
    void getAllPageLayoutsByRect_topIsGreaterThanSomething() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where rect_top is greater than DEFAULT_RECT_TOP
        defaultPageLayoutShouldNotBeFound("rect_top.greaterThan=" + DEFAULT_RECT_TOP);

        // Get all the pageLayoutList where rect_top is greater than SMALLER_RECT_TOP
        defaultPageLayoutShouldBeFound("rect_top.greaterThan=" + SMALLER_RECT_TOP);
    }

    @Test
    @Transactional
    void getAllPageLayoutsByRect_leftIsEqualToSomething() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where rect_left equals to DEFAULT_RECT_LEFT
        defaultPageLayoutShouldBeFound("rect_left.equals=" + DEFAULT_RECT_LEFT);

        // Get all the pageLayoutList where rect_left equals to UPDATED_RECT_LEFT
        defaultPageLayoutShouldNotBeFound("rect_left.equals=" + UPDATED_RECT_LEFT);
    }

    @Test
    @Transactional
    void getAllPageLayoutsByRect_leftIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where rect_left not equals to DEFAULT_RECT_LEFT
        defaultPageLayoutShouldNotBeFound("rect_left.notEquals=" + DEFAULT_RECT_LEFT);

        // Get all the pageLayoutList where rect_left not equals to UPDATED_RECT_LEFT
        defaultPageLayoutShouldBeFound("rect_left.notEquals=" + UPDATED_RECT_LEFT);
    }

    @Test
    @Transactional
    void getAllPageLayoutsByRect_leftIsInShouldWork() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where rect_left in DEFAULT_RECT_LEFT or UPDATED_RECT_LEFT
        defaultPageLayoutShouldBeFound("rect_left.in=" + DEFAULT_RECT_LEFT + "," + UPDATED_RECT_LEFT);

        // Get all the pageLayoutList where rect_left equals to UPDATED_RECT_LEFT
        defaultPageLayoutShouldNotBeFound("rect_left.in=" + UPDATED_RECT_LEFT);
    }

    @Test
    @Transactional
    void getAllPageLayoutsByRect_leftIsNullOrNotNull() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where rect_left is not null
        defaultPageLayoutShouldBeFound("rect_left.specified=true");

        // Get all the pageLayoutList where rect_left is null
        defaultPageLayoutShouldNotBeFound("rect_left.specified=false");
    }

    @Test
    @Transactional
    void getAllPageLayoutsByRect_leftIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where rect_left is greater than or equal to DEFAULT_RECT_LEFT
        defaultPageLayoutShouldBeFound("rect_left.greaterThanOrEqual=" + DEFAULT_RECT_LEFT);

        // Get all the pageLayoutList where rect_left is greater than or equal to UPDATED_RECT_LEFT
        defaultPageLayoutShouldNotBeFound("rect_left.greaterThanOrEqual=" + UPDATED_RECT_LEFT);
    }

    @Test
    @Transactional
    void getAllPageLayoutsByRect_leftIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where rect_left is less than or equal to DEFAULT_RECT_LEFT
        defaultPageLayoutShouldBeFound("rect_left.lessThanOrEqual=" + DEFAULT_RECT_LEFT);

        // Get all the pageLayoutList where rect_left is less than or equal to SMALLER_RECT_LEFT
        defaultPageLayoutShouldNotBeFound("rect_left.lessThanOrEqual=" + SMALLER_RECT_LEFT);
    }

    @Test
    @Transactional
    void getAllPageLayoutsByRect_leftIsLessThanSomething() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where rect_left is less than DEFAULT_RECT_LEFT
        defaultPageLayoutShouldNotBeFound("rect_left.lessThan=" + DEFAULT_RECT_LEFT);

        // Get all the pageLayoutList where rect_left is less than UPDATED_RECT_LEFT
        defaultPageLayoutShouldBeFound("rect_left.lessThan=" + UPDATED_RECT_LEFT);
    }

    @Test
    @Transactional
    void getAllPageLayoutsByRect_leftIsGreaterThanSomething() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where rect_left is greater than DEFAULT_RECT_LEFT
        defaultPageLayoutShouldNotBeFound("rect_left.greaterThan=" + DEFAULT_RECT_LEFT);

        // Get all the pageLayoutList where rect_left is greater than SMALLER_RECT_LEFT
        defaultPageLayoutShouldBeFound("rect_left.greaterThan=" + SMALLER_RECT_LEFT);
    }

    @Test
    @Transactional
    void getAllPageLayoutsByRect_rightIsEqualToSomething() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where rect_right equals to DEFAULT_RECT_RIGHT
        defaultPageLayoutShouldBeFound("rect_right.equals=" + DEFAULT_RECT_RIGHT);

        // Get all the pageLayoutList where rect_right equals to UPDATED_RECT_RIGHT
        defaultPageLayoutShouldNotBeFound("rect_right.equals=" + UPDATED_RECT_RIGHT);
    }

    @Test
    @Transactional
    void getAllPageLayoutsByRect_rightIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where rect_right not equals to DEFAULT_RECT_RIGHT
        defaultPageLayoutShouldNotBeFound("rect_right.notEquals=" + DEFAULT_RECT_RIGHT);

        // Get all the pageLayoutList where rect_right not equals to UPDATED_RECT_RIGHT
        defaultPageLayoutShouldBeFound("rect_right.notEquals=" + UPDATED_RECT_RIGHT);
    }

    @Test
    @Transactional
    void getAllPageLayoutsByRect_rightIsInShouldWork() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where rect_right in DEFAULT_RECT_RIGHT or UPDATED_RECT_RIGHT
        defaultPageLayoutShouldBeFound("rect_right.in=" + DEFAULT_RECT_RIGHT + "," + UPDATED_RECT_RIGHT);

        // Get all the pageLayoutList where rect_right equals to UPDATED_RECT_RIGHT
        defaultPageLayoutShouldNotBeFound("rect_right.in=" + UPDATED_RECT_RIGHT);
    }

    @Test
    @Transactional
    void getAllPageLayoutsByRect_rightIsNullOrNotNull() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where rect_right is not null
        defaultPageLayoutShouldBeFound("rect_right.specified=true");

        // Get all the pageLayoutList where rect_right is null
        defaultPageLayoutShouldNotBeFound("rect_right.specified=false");
    }

    @Test
    @Transactional
    void getAllPageLayoutsByRect_rightIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where rect_right is greater than or equal to DEFAULT_RECT_RIGHT
        defaultPageLayoutShouldBeFound("rect_right.greaterThanOrEqual=" + DEFAULT_RECT_RIGHT);

        // Get all the pageLayoutList where rect_right is greater than or equal to UPDATED_RECT_RIGHT
        defaultPageLayoutShouldNotBeFound("rect_right.greaterThanOrEqual=" + UPDATED_RECT_RIGHT);
    }

    @Test
    @Transactional
    void getAllPageLayoutsByRect_rightIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where rect_right is less than or equal to DEFAULT_RECT_RIGHT
        defaultPageLayoutShouldBeFound("rect_right.lessThanOrEqual=" + DEFAULT_RECT_RIGHT);

        // Get all the pageLayoutList where rect_right is less than or equal to SMALLER_RECT_RIGHT
        defaultPageLayoutShouldNotBeFound("rect_right.lessThanOrEqual=" + SMALLER_RECT_RIGHT);
    }

    @Test
    @Transactional
    void getAllPageLayoutsByRect_rightIsLessThanSomething() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where rect_right is less than DEFAULT_RECT_RIGHT
        defaultPageLayoutShouldNotBeFound("rect_right.lessThan=" + DEFAULT_RECT_RIGHT);

        // Get all the pageLayoutList where rect_right is less than UPDATED_RECT_RIGHT
        defaultPageLayoutShouldBeFound("rect_right.lessThan=" + UPDATED_RECT_RIGHT);
    }

    @Test
    @Transactional
    void getAllPageLayoutsByRect_rightIsGreaterThanSomething() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where rect_right is greater than DEFAULT_RECT_RIGHT
        defaultPageLayoutShouldNotBeFound("rect_right.greaterThan=" + DEFAULT_RECT_RIGHT);

        // Get all the pageLayoutList where rect_right is greater than SMALLER_RECT_RIGHT
        defaultPageLayoutShouldBeFound("rect_right.greaterThan=" + SMALLER_RECT_RIGHT);
    }

    @Test
    @Transactional
    void getAllPageLayoutsByRect_bottomIsEqualToSomething() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where rect_bottom equals to DEFAULT_RECT_BOTTOM
        defaultPageLayoutShouldBeFound("rect_bottom.equals=" + DEFAULT_RECT_BOTTOM);

        // Get all the pageLayoutList where rect_bottom equals to UPDATED_RECT_BOTTOM
        defaultPageLayoutShouldNotBeFound("rect_bottom.equals=" + UPDATED_RECT_BOTTOM);
    }

    @Test
    @Transactional
    void getAllPageLayoutsByRect_bottomIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where rect_bottom not equals to DEFAULT_RECT_BOTTOM
        defaultPageLayoutShouldNotBeFound("rect_bottom.notEquals=" + DEFAULT_RECT_BOTTOM);

        // Get all the pageLayoutList where rect_bottom not equals to UPDATED_RECT_BOTTOM
        defaultPageLayoutShouldBeFound("rect_bottom.notEquals=" + UPDATED_RECT_BOTTOM);
    }

    @Test
    @Transactional
    void getAllPageLayoutsByRect_bottomIsInShouldWork() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where rect_bottom in DEFAULT_RECT_BOTTOM or UPDATED_RECT_BOTTOM
        defaultPageLayoutShouldBeFound("rect_bottom.in=" + DEFAULT_RECT_BOTTOM + "," + UPDATED_RECT_BOTTOM);

        // Get all the pageLayoutList where rect_bottom equals to UPDATED_RECT_BOTTOM
        defaultPageLayoutShouldNotBeFound("rect_bottom.in=" + UPDATED_RECT_BOTTOM);
    }

    @Test
    @Transactional
    void getAllPageLayoutsByRect_bottomIsNullOrNotNull() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where rect_bottom is not null
        defaultPageLayoutShouldBeFound("rect_bottom.specified=true");

        // Get all the pageLayoutList where rect_bottom is null
        defaultPageLayoutShouldNotBeFound("rect_bottom.specified=false");
    }

    @Test
    @Transactional
    void getAllPageLayoutsByRect_bottomIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where rect_bottom is greater than or equal to DEFAULT_RECT_BOTTOM
        defaultPageLayoutShouldBeFound("rect_bottom.greaterThanOrEqual=" + DEFAULT_RECT_BOTTOM);

        // Get all the pageLayoutList where rect_bottom is greater than or equal to UPDATED_RECT_BOTTOM
        defaultPageLayoutShouldNotBeFound("rect_bottom.greaterThanOrEqual=" + UPDATED_RECT_BOTTOM);
    }

    @Test
    @Transactional
    void getAllPageLayoutsByRect_bottomIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where rect_bottom is less than or equal to DEFAULT_RECT_BOTTOM
        defaultPageLayoutShouldBeFound("rect_bottom.lessThanOrEqual=" + DEFAULT_RECT_BOTTOM);

        // Get all the pageLayoutList where rect_bottom is less than or equal to SMALLER_RECT_BOTTOM
        defaultPageLayoutShouldNotBeFound("rect_bottom.lessThanOrEqual=" + SMALLER_RECT_BOTTOM);
    }

    @Test
    @Transactional
    void getAllPageLayoutsByRect_bottomIsLessThanSomething() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where rect_bottom is less than DEFAULT_RECT_BOTTOM
        defaultPageLayoutShouldNotBeFound("rect_bottom.lessThan=" + DEFAULT_RECT_BOTTOM);

        // Get all the pageLayoutList where rect_bottom is less than UPDATED_RECT_BOTTOM
        defaultPageLayoutShouldBeFound("rect_bottom.lessThan=" + UPDATED_RECT_BOTTOM);
    }

    @Test
    @Transactional
    void getAllPageLayoutsByRect_bottomIsGreaterThanSomething() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where rect_bottom is greater than DEFAULT_RECT_BOTTOM
        defaultPageLayoutShouldNotBeFound("rect_bottom.greaterThan=" + DEFAULT_RECT_BOTTOM);

        // Get all the pageLayoutList where rect_bottom is greater than SMALLER_RECT_BOTTOM
        defaultPageLayoutShouldBeFound("rect_bottom.greaterThan=" + SMALLER_RECT_BOTTOM);
    }

    @Test
    @Transactional
    void getAllPageLayoutsByParent_idIsEqualToSomething() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where parent_id equals to DEFAULT_PARENT_ID
        defaultPageLayoutShouldBeFound("parent_id.equals=" + DEFAULT_PARENT_ID);

        // Get all the pageLayoutList where parent_id equals to UPDATED_PARENT_ID
        defaultPageLayoutShouldNotBeFound("parent_id.equals=" + UPDATED_PARENT_ID);
    }

    @Test
    @Transactional
    void getAllPageLayoutsByParent_idIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where parent_id not equals to DEFAULT_PARENT_ID
        defaultPageLayoutShouldNotBeFound("parent_id.notEquals=" + DEFAULT_PARENT_ID);

        // Get all the pageLayoutList where parent_id not equals to UPDATED_PARENT_ID
        defaultPageLayoutShouldBeFound("parent_id.notEquals=" + UPDATED_PARENT_ID);
    }

    @Test
    @Transactional
    void getAllPageLayoutsByParent_idIsInShouldWork() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where parent_id in DEFAULT_PARENT_ID or UPDATED_PARENT_ID
        defaultPageLayoutShouldBeFound("parent_id.in=" + DEFAULT_PARENT_ID + "," + UPDATED_PARENT_ID);

        // Get all the pageLayoutList where parent_id equals to UPDATED_PARENT_ID
        defaultPageLayoutShouldNotBeFound("parent_id.in=" + UPDATED_PARENT_ID);
    }

    @Test
    @Transactional
    void getAllPageLayoutsByParent_idIsNullOrNotNull() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where parent_id is not null
        defaultPageLayoutShouldBeFound("parent_id.specified=true");

        // Get all the pageLayoutList where parent_id is null
        defaultPageLayoutShouldNotBeFound("parent_id.specified=false");
    }

    @Test
    @Transactional
    void getAllPageLayoutsByParent_idIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where parent_id is greater than or equal to DEFAULT_PARENT_ID
        defaultPageLayoutShouldBeFound("parent_id.greaterThanOrEqual=" + DEFAULT_PARENT_ID);

        // Get all the pageLayoutList where parent_id is greater than or equal to UPDATED_PARENT_ID
        defaultPageLayoutShouldNotBeFound("parent_id.greaterThanOrEqual=" + UPDATED_PARENT_ID);
    }

    @Test
    @Transactional
    void getAllPageLayoutsByParent_idIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where parent_id is less than or equal to DEFAULT_PARENT_ID
        defaultPageLayoutShouldBeFound("parent_id.lessThanOrEqual=" + DEFAULT_PARENT_ID);

        // Get all the pageLayoutList where parent_id is less than or equal to SMALLER_PARENT_ID
        defaultPageLayoutShouldNotBeFound("parent_id.lessThanOrEqual=" + SMALLER_PARENT_ID);
    }

    @Test
    @Transactional
    void getAllPageLayoutsByParent_idIsLessThanSomething() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where parent_id is less than DEFAULT_PARENT_ID
        defaultPageLayoutShouldNotBeFound("parent_id.lessThan=" + DEFAULT_PARENT_ID);

        // Get all the pageLayoutList where parent_id is less than UPDATED_PARENT_ID
        defaultPageLayoutShouldBeFound("parent_id.lessThan=" + UPDATED_PARENT_ID);
    }

    @Test
    @Transactional
    void getAllPageLayoutsByParent_idIsGreaterThanSomething() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where parent_id is greater than DEFAULT_PARENT_ID
        defaultPageLayoutShouldNotBeFound("parent_id.greaterThan=" + DEFAULT_PARENT_ID);

        // Get all the pageLayoutList where parent_id is greater than SMALLER_PARENT_ID
        defaultPageLayoutShouldBeFound("parent_id.greaterThan=" + SMALLER_PARENT_ID);
    }

    @Test
    @Transactional
    void getAllPageLayoutsByItemGUIDIsEqualToSomething() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where itemGUID equals to DEFAULT_ITEM_GUID
        defaultPageLayoutShouldBeFound("itemGUID.equals=" + DEFAULT_ITEM_GUID);

        // Get all the pageLayoutList where itemGUID equals to UPDATED_ITEM_GUID
        defaultPageLayoutShouldNotBeFound("itemGUID.equals=" + UPDATED_ITEM_GUID);
    }

    @Test
    @Transactional
    void getAllPageLayoutsByItemGUIDIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where itemGUID not equals to DEFAULT_ITEM_GUID
        defaultPageLayoutShouldNotBeFound("itemGUID.notEquals=" + DEFAULT_ITEM_GUID);

        // Get all the pageLayoutList where itemGUID not equals to UPDATED_ITEM_GUID
        defaultPageLayoutShouldBeFound("itemGUID.notEquals=" + UPDATED_ITEM_GUID);
    }

    @Test
    @Transactional
    void getAllPageLayoutsByItemGUIDIsInShouldWork() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where itemGUID in DEFAULT_ITEM_GUID or UPDATED_ITEM_GUID
        defaultPageLayoutShouldBeFound("itemGUID.in=" + DEFAULT_ITEM_GUID + "," + UPDATED_ITEM_GUID);

        // Get all the pageLayoutList where itemGUID equals to UPDATED_ITEM_GUID
        defaultPageLayoutShouldNotBeFound("itemGUID.in=" + UPDATED_ITEM_GUID);
    }

    @Test
    @Transactional
    void getAllPageLayoutsByItemGUIDIsNullOrNotNull() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where itemGUID is not null
        defaultPageLayoutShouldBeFound("itemGUID.specified=true");

        // Get all the pageLayoutList where itemGUID is null
        defaultPageLayoutShouldNotBeFound("itemGUID.specified=false");
    }

    @Test
    @Transactional
    void getAllPageLayoutsByParentGUIDIsEqualToSomething() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where parentGUID equals to DEFAULT_PARENT_GUID
        defaultPageLayoutShouldBeFound("parentGUID.equals=" + DEFAULT_PARENT_GUID);

        // Get all the pageLayoutList where parentGUID equals to UPDATED_PARENT_GUID
        defaultPageLayoutShouldNotBeFound("parentGUID.equals=" + UPDATED_PARENT_GUID);
    }

    @Test
    @Transactional
    void getAllPageLayoutsByParentGUIDIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where parentGUID not equals to DEFAULT_PARENT_GUID
        defaultPageLayoutShouldNotBeFound("parentGUID.notEquals=" + DEFAULT_PARENT_GUID);

        // Get all the pageLayoutList where parentGUID not equals to UPDATED_PARENT_GUID
        defaultPageLayoutShouldBeFound("parentGUID.notEquals=" + UPDATED_PARENT_GUID);
    }

    @Test
    @Transactional
    void getAllPageLayoutsByParentGUIDIsInShouldWork() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where parentGUID in DEFAULT_PARENT_GUID or UPDATED_PARENT_GUID
        defaultPageLayoutShouldBeFound("parentGUID.in=" + DEFAULT_PARENT_GUID + "," + UPDATED_PARENT_GUID);

        // Get all the pageLayoutList where parentGUID equals to UPDATED_PARENT_GUID
        defaultPageLayoutShouldNotBeFound("parentGUID.in=" + UPDATED_PARENT_GUID);
    }

    @Test
    @Transactional
    void getAllPageLayoutsByParentGUIDIsNullOrNotNull() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where parentGUID is not null
        defaultPageLayoutShouldBeFound("parentGUID.specified=true");

        // Get all the pageLayoutList where parentGUID is null
        defaultPageLayoutShouldNotBeFound("parentGUID.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPageLayoutShouldBeFound(String filter) throws Exception {
        restPageLayoutMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pageLayout.getId().intValue())))
            .andExpect(jsonPath("$.[*].mediaId").value(hasItem(DEFAULT_MEDIA_ID.intValue())))
            .andExpect(jsonPath("$.[*].pageNumber").value(hasItem(DEFAULT_PAGE_NUMBER)))
            .andExpect(jsonPath("$.[*].iterator_level").value(hasItem(DEFAULT_ITERATOR_LEVEL)))
            .andExpect(jsonPath("$.[*].rect_top").value(hasItem(sameNumber(DEFAULT_RECT_TOP))))
            .andExpect(jsonPath("$.[*].rect_left").value(hasItem(sameNumber(DEFAULT_RECT_LEFT))))
            .andExpect(jsonPath("$.[*].rect_right").value(hasItem(sameNumber(DEFAULT_RECT_RIGHT))))
            .andExpect(jsonPath("$.[*].rect_bottom").value(hasItem(sameNumber(DEFAULT_RECT_BOTTOM))))
            .andExpect(jsonPath("$.[*].parent_id").value(hasItem(DEFAULT_PARENT_ID)))
            .andExpect(jsonPath("$.[*].itemGUID").value(hasItem(DEFAULT_ITEM_GUID.toString())))
            .andExpect(jsonPath("$.[*].parentGUID").value(hasItem(DEFAULT_PARENT_GUID.toString())));

        // Check, that the count call also returns 1
        restPageLayoutMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPageLayoutShouldNotBeFound(String filter) throws Exception {
        restPageLayoutMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPageLayoutMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPageLayout() throws Exception {
        // Get the pageLayout
        restPageLayoutMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPageLayout() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        int databaseSizeBeforeUpdate = pageLayoutRepository.findAll().size();

        // Update the pageLayout
        PageLayout updatedPageLayout = pageLayoutRepository.findById(pageLayout.getId()).get();
        // Disconnect from session so that the updates on updatedPageLayout are not directly saved in db
        em.detach(updatedPageLayout);
        updatedPageLayout
            .mediaId(UPDATED_MEDIA_ID)
            .pageNumber(UPDATED_PAGE_NUMBER)
            .iterator_level(UPDATED_ITERATOR_LEVEL)
            .rect_top(UPDATED_RECT_TOP)
            .rect_left(UPDATED_RECT_LEFT)
            .rect_right(UPDATED_RECT_RIGHT)
            .rect_bottom(UPDATED_RECT_BOTTOM)
            .parent_id(UPDATED_PARENT_ID)
            .itemGUID(UPDATED_ITEM_GUID)
            .parentGUID(UPDATED_PARENT_GUID);
        PageLayoutDTO pageLayoutDTO = pageLayoutMapper.toDto(updatedPageLayout);

        restPageLayoutMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pageLayoutDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pageLayoutDTO))
            )
            .andExpect(status().isOk());

        // Validate the PageLayout in the database
        List<PageLayout> pageLayoutList = pageLayoutRepository.findAll();
        assertThat(pageLayoutList).hasSize(databaseSizeBeforeUpdate);
        PageLayout testPageLayout = pageLayoutList.get(pageLayoutList.size() - 1);
        assertThat(testPageLayout.getMediaId()).isEqualTo(UPDATED_MEDIA_ID);
        assertThat(testPageLayout.getPageNumber()).isEqualTo(UPDATED_PAGE_NUMBER);
        assertThat(testPageLayout.getIterator_level()).isEqualTo(UPDATED_ITERATOR_LEVEL);
        assertThat(testPageLayout.getRect_top()).isEqualByComparingTo(UPDATED_RECT_TOP);
        assertThat(testPageLayout.getRect_left()).isEqualByComparingTo(UPDATED_RECT_LEFT);
        assertThat(testPageLayout.getRect_right()).isEqualByComparingTo(UPDATED_RECT_RIGHT);
        assertThat(testPageLayout.getRect_bottom()).isEqualByComparingTo(UPDATED_RECT_BOTTOM);
        assertThat(testPageLayout.getParent_id()).isEqualTo(UPDATED_PARENT_ID);
        assertThat(testPageLayout.getItemGUID()).isEqualTo(UPDATED_ITEM_GUID);
        assertThat(testPageLayout.getParentGUID()).isEqualTo(UPDATED_PARENT_GUID);
    }

    @Test
    @Transactional
    void putNonExistingPageLayout() throws Exception {
        int databaseSizeBeforeUpdate = pageLayoutRepository.findAll().size();
        pageLayout.setId(count.incrementAndGet());

        // Create the PageLayout
        PageLayoutDTO pageLayoutDTO = pageLayoutMapper.toDto(pageLayout);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPageLayoutMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pageLayoutDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pageLayoutDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PageLayout in the database
        List<PageLayout> pageLayoutList = pageLayoutRepository.findAll();
        assertThat(pageLayoutList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPageLayout() throws Exception {
        int databaseSizeBeforeUpdate = pageLayoutRepository.findAll().size();
        pageLayout.setId(count.incrementAndGet());

        // Create the PageLayout
        PageLayoutDTO pageLayoutDTO = pageLayoutMapper.toDto(pageLayout);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPageLayoutMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pageLayoutDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PageLayout in the database
        List<PageLayout> pageLayoutList = pageLayoutRepository.findAll();
        assertThat(pageLayoutList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPageLayout() throws Exception {
        int databaseSizeBeforeUpdate = pageLayoutRepository.findAll().size();
        pageLayout.setId(count.incrementAndGet());

        // Create the PageLayout
        PageLayoutDTO pageLayoutDTO = pageLayoutMapper.toDto(pageLayout);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPageLayoutMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pageLayoutDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PageLayout in the database
        List<PageLayout> pageLayoutList = pageLayoutRepository.findAll();
        assertThat(pageLayoutList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePageLayoutWithPatch() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        int databaseSizeBeforeUpdate = pageLayoutRepository.findAll().size();

        // Update the pageLayout using partial update
        PageLayout partialUpdatedPageLayout = new PageLayout();
        partialUpdatedPageLayout.setId(pageLayout.getId());

        partialUpdatedPageLayout
            .pageNumber(UPDATED_PAGE_NUMBER)
            .rect_right(UPDATED_RECT_RIGHT)
            .rect_bottom(UPDATED_RECT_BOTTOM)
            .itemGUID(UPDATED_ITEM_GUID)
            .parentGUID(UPDATED_PARENT_GUID);

        restPageLayoutMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPageLayout.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPageLayout))
            )
            .andExpect(status().isOk());

        // Validate the PageLayout in the database
        List<PageLayout> pageLayoutList = pageLayoutRepository.findAll();
        assertThat(pageLayoutList).hasSize(databaseSizeBeforeUpdate);
        PageLayout testPageLayout = pageLayoutList.get(pageLayoutList.size() - 1);
        assertThat(testPageLayout.getMediaId()).isEqualTo(DEFAULT_MEDIA_ID);
        assertThat(testPageLayout.getPageNumber()).isEqualTo(UPDATED_PAGE_NUMBER);
        assertThat(testPageLayout.getIterator_level()).isEqualTo(DEFAULT_ITERATOR_LEVEL);
        assertThat(testPageLayout.getRect_top()).isEqualByComparingTo(DEFAULT_RECT_TOP);
        assertThat(testPageLayout.getRect_left()).isEqualByComparingTo(DEFAULT_RECT_LEFT);
        assertThat(testPageLayout.getRect_right()).isEqualByComparingTo(UPDATED_RECT_RIGHT);
        assertThat(testPageLayout.getRect_bottom()).isEqualByComparingTo(UPDATED_RECT_BOTTOM);
        assertThat(testPageLayout.getParent_id()).isEqualTo(DEFAULT_PARENT_ID);
        assertThat(testPageLayout.getItemGUID()).isEqualTo(UPDATED_ITEM_GUID);
        assertThat(testPageLayout.getParentGUID()).isEqualTo(UPDATED_PARENT_GUID);
    }

    @Test
    @Transactional
    void fullUpdatePageLayoutWithPatch() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        int databaseSizeBeforeUpdate = pageLayoutRepository.findAll().size();

        // Update the pageLayout using partial update
        PageLayout partialUpdatedPageLayout = new PageLayout();
        partialUpdatedPageLayout.setId(pageLayout.getId());

        partialUpdatedPageLayout
            .mediaId(UPDATED_MEDIA_ID)
            .pageNumber(UPDATED_PAGE_NUMBER)
            .iterator_level(UPDATED_ITERATOR_LEVEL)
            .rect_top(UPDATED_RECT_TOP)
            .rect_left(UPDATED_RECT_LEFT)
            .rect_right(UPDATED_RECT_RIGHT)
            .rect_bottom(UPDATED_RECT_BOTTOM)
            .parent_id(UPDATED_PARENT_ID)
            .itemGUID(UPDATED_ITEM_GUID)
            .parentGUID(UPDATED_PARENT_GUID);

        restPageLayoutMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPageLayout.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPageLayout))
            )
            .andExpect(status().isOk());

        // Validate the PageLayout in the database
        List<PageLayout> pageLayoutList = pageLayoutRepository.findAll();
        assertThat(pageLayoutList).hasSize(databaseSizeBeforeUpdate);
        PageLayout testPageLayout = pageLayoutList.get(pageLayoutList.size() - 1);
        assertThat(testPageLayout.getMediaId()).isEqualTo(UPDATED_MEDIA_ID);
        assertThat(testPageLayout.getPageNumber()).isEqualTo(UPDATED_PAGE_NUMBER);
        assertThat(testPageLayout.getIterator_level()).isEqualTo(UPDATED_ITERATOR_LEVEL);
        assertThat(testPageLayout.getRect_top()).isEqualByComparingTo(UPDATED_RECT_TOP);
        assertThat(testPageLayout.getRect_left()).isEqualByComparingTo(UPDATED_RECT_LEFT);
        assertThat(testPageLayout.getRect_right()).isEqualByComparingTo(UPDATED_RECT_RIGHT);
        assertThat(testPageLayout.getRect_bottom()).isEqualByComparingTo(UPDATED_RECT_BOTTOM);
        assertThat(testPageLayout.getParent_id()).isEqualTo(UPDATED_PARENT_ID);
        assertThat(testPageLayout.getItemGUID()).isEqualTo(UPDATED_ITEM_GUID);
        assertThat(testPageLayout.getParentGUID()).isEqualTo(UPDATED_PARENT_GUID);
    }

    @Test
    @Transactional
    void patchNonExistingPageLayout() throws Exception {
        int databaseSizeBeforeUpdate = pageLayoutRepository.findAll().size();
        pageLayout.setId(count.incrementAndGet());

        // Create the PageLayout
        PageLayoutDTO pageLayoutDTO = pageLayoutMapper.toDto(pageLayout);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPageLayoutMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pageLayoutDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pageLayoutDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PageLayout in the database
        List<PageLayout> pageLayoutList = pageLayoutRepository.findAll();
        assertThat(pageLayoutList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPageLayout() throws Exception {
        int databaseSizeBeforeUpdate = pageLayoutRepository.findAll().size();
        pageLayout.setId(count.incrementAndGet());

        // Create the PageLayout
        PageLayoutDTO pageLayoutDTO = pageLayoutMapper.toDto(pageLayout);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPageLayoutMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pageLayoutDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PageLayout in the database
        List<PageLayout> pageLayoutList = pageLayoutRepository.findAll();
        assertThat(pageLayoutList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPageLayout() throws Exception {
        int databaseSizeBeforeUpdate = pageLayoutRepository.findAll().size();
        pageLayout.setId(count.incrementAndGet());

        // Create the PageLayout
        PageLayoutDTO pageLayoutDTO = pageLayoutMapper.toDto(pageLayout);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPageLayoutMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(pageLayoutDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PageLayout in the database
        List<PageLayout> pageLayoutList = pageLayoutRepository.findAll();
        assertThat(pageLayoutList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePageLayout() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        int databaseSizeBeforeDelete = pageLayoutRepository.findAll().size();

        // Delete the pageLayout
        restPageLayoutMockMvc
            .perform(delete(ENTITY_API_URL_ID, pageLayout.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PageLayout> pageLayoutList = pageLayoutRepository.findAll();
        assertThat(pageLayoutList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
