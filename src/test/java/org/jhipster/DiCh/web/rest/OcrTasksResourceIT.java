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
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.jhipster.dich.IntegrationTest;
import org.jhipster.dich.domain.OcrTasks;
import org.jhipster.dich.repository.OcrTasksRepository;
import org.jhipster.dich.service.criteria.OcrTasksCriteria;
import org.jhipster.dich.service.dto.OcrTasksDTO;
import org.jhipster.dich.service.mapper.OcrTasksMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link OcrTasksResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OcrTasksResourceIT {

    private static final Long DEFAULT_MEDIA_ID = 1L;
    private static final Long UPDATED_MEDIA_ID = 2L;
    private static final Long SMALLER_MEDIA_ID = 1L - 1L;

    private static final Integer DEFAULT_PAGE_NUMBER = 1;
    private static final Integer UPDATED_PAGE_NUMBER = 2;
    private static final Integer SMALLER_PAGE_NUMBER = 1 - 1;

    private static final String DEFAULT_JOB_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_JOB_STATUS = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_CREATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_START_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_START_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_START_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_STOP_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_STOP_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_STOP_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String ENTITY_API_URL = "/api/ocr-tasks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OcrTasksRepository ocrTasksRepository;

    @Autowired
    private OcrTasksMapper ocrTasksMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOcrTasksMockMvc;

    private OcrTasks ocrTasks;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OcrTasks createEntity(EntityManager em) {
        OcrTasks ocrTasks = new OcrTasks()
            .mediaId(DEFAULT_MEDIA_ID)
            .pageNumber(DEFAULT_PAGE_NUMBER)
            .jobStatus(DEFAULT_JOB_STATUS)
            .createTime(DEFAULT_CREATE_TIME)
            .startTime(DEFAULT_START_TIME)
            .stopTime(DEFAULT_STOP_TIME);
        return ocrTasks;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OcrTasks createUpdatedEntity(EntityManager em) {
        OcrTasks ocrTasks = new OcrTasks()
            .mediaId(UPDATED_MEDIA_ID)
            .pageNumber(UPDATED_PAGE_NUMBER)
            .jobStatus(UPDATED_JOB_STATUS)
            .createTime(UPDATED_CREATE_TIME)
            .startTime(UPDATED_START_TIME)
            .stopTime(UPDATED_STOP_TIME);
        return ocrTasks;
    }

    @BeforeEach
    public void initTest() {
        ocrTasks = createEntity(em);
    }

    @Test
    @Transactional
    void createOcrTasks() throws Exception {
        int databaseSizeBeforeCreate = ocrTasksRepository.findAll().size();
        // Create the OcrTasks
        OcrTasksDTO ocrTasksDTO = ocrTasksMapper.toDto(ocrTasks);
        restOcrTasksMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ocrTasksDTO)))
            .andExpect(status().isCreated());

        // Validate the OcrTasks in the database
        List<OcrTasks> ocrTasksList = ocrTasksRepository.findAll();
        assertThat(ocrTasksList).hasSize(databaseSizeBeforeCreate + 1);
        OcrTasks testOcrTasks = ocrTasksList.get(ocrTasksList.size() - 1);
        assertThat(testOcrTasks.getMediaId()).isEqualTo(DEFAULT_MEDIA_ID);
        assertThat(testOcrTasks.getPageNumber()).isEqualTo(DEFAULT_PAGE_NUMBER);
        assertThat(testOcrTasks.getJobStatus()).isEqualTo(DEFAULT_JOB_STATUS);
        assertThat(testOcrTasks.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testOcrTasks.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testOcrTasks.getStopTime()).isEqualTo(DEFAULT_STOP_TIME);
    }

    @Test
    @Transactional
    void createOcrTasksWithExistingId() throws Exception {
        // Create the OcrTasks with an existing ID
        ocrTasks.setId(1L);
        OcrTasksDTO ocrTasksDTO = ocrTasksMapper.toDto(ocrTasks);

        int databaseSizeBeforeCreate = ocrTasksRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOcrTasksMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ocrTasksDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OcrTasks in the database
        List<OcrTasks> ocrTasksList = ocrTasksRepository.findAll();
        assertThat(ocrTasksList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOcrTasks() throws Exception {
        // Initialize the database
        ocrTasksRepository.saveAndFlush(ocrTasks);

        // Get all the ocrTasksList
        restOcrTasksMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ocrTasks.getId().intValue())))
            .andExpect(jsonPath("$.[*].mediaId").value(hasItem(DEFAULT_MEDIA_ID.intValue())))
            .andExpect(jsonPath("$.[*].pageNumber").value(hasItem(DEFAULT_PAGE_NUMBER)))
            .andExpect(jsonPath("$.[*].jobStatus").value(hasItem(DEFAULT_JOB_STATUS)))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(sameInstant(DEFAULT_CREATE_TIME))))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(sameInstant(DEFAULT_START_TIME))))
            .andExpect(jsonPath("$.[*].stopTime").value(hasItem(sameInstant(DEFAULT_STOP_TIME))));
    }

    @Test
    @Transactional
    void getOcrTasks() throws Exception {
        // Initialize the database
        ocrTasksRepository.saveAndFlush(ocrTasks);

        // Get the ocrTasks
        restOcrTasksMockMvc
            .perform(get(ENTITY_API_URL_ID, ocrTasks.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ocrTasks.getId().intValue()))
            .andExpect(jsonPath("$.mediaId").value(DEFAULT_MEDIA_ID.intValue()))
            .andExpect(jsonPath("$.pageNumber").value(DEFAULT_PAGE_NUMBER))
            .andExpect(jsonPath("$.jobStatus").value(DEFAULT_JOB_STATUS))
            .andExpect(jsonPath("$.createTime").value(sameInstant(DEFAULT_CREATE_TIME)))
            .andExpect(jsonPath("$.startTime").value(sameInstant(DEFAULT_START_TIME)))
            .andExpect(jsonPath("$.stopTime").value(sameInstant(DEFAULT_STOP_TIME)));
    }

    @Test
    @Transactional
    void getOcrTasksByIdFiltering() throws Exception {
        // Initialize the database
        ocrTasksRepository.saveAndFlush(ocrTasks);

        Long id = ocrTasks.getId();

        defaultOcrTasksShouldBeFound("id.equals=" + id);
        defaultOcrTasksShouldNotBeFound("id.notEquals=" + id);

        defaultOcrTasksShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultOcrTasksShouldNotBeFound("id.greaterThan=" + id);

        defaultOcrTasksShouldBeFound("id.lessThanOrEqual=" + id);
        defaultOcrTasksShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllOcrTasksByMediaIdIsEqualToSomething() throws Exception {
        // Initialize the database
        ocrTasksRepository.saveAndFlush(ocrTasks);

        // Get all the ocrTasksList where mediaId equals to DEFAULT_MEDIA_ID
        defaultOcrTasksShouldBeFound("mediaId.equals=" + DEFAULT_MEDIA_ID);

        // Get all the ocrTasksList where mediaId equals to UPDATED_MEDIA_ID
        defaultOcrTasksShouldNotBeFound("mediaId.equals=" + UPDATED_MEDIA_ID);
    }

    @Test
    @Transactional
    void getAllOcrTasksByMediaIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ocrTasksRepository.saveAndFlush(ocrTasks);

        // Get all the ocrTasksList where mediaId not equals to DEFAULT_MEDIA_ID
        defaultOcrTasksShouldNotBeFound("mediaId.notEquals=" + DEFAULT_MEDIA_ID);

        // Get all the ocrTasksList where mediaId not equals to UPDATED_MEDIA_ID
        defaultOcrTasksShouldBeFound("mediaId.notEquals=" + UPDATED_MEDIA_ID);
    }

    @Test
    @Transactional
    void getAllOcrTasksByMediaIdIsInShouldWork() throws Exception {
        // Initialize the database
        ocrTasksRepository.saveAndFlush(ocrTasks);

        // Get all the ocrTasksList where mediaId in DEFAULT_MEDIA_ID or UPDATED_MEDIA_ID
        defaultOcrTasksShouldBeFound("mediaId.in=" + DEFAULT_MEDIA_ID + "," + UPDATED_MEDIA_ID);

        // Get all the ocrTasksList where mediaId equals to UPDATED_MEDIA_ID
        defaultOcrTasksShouldNotBeFound("mediaId.in=" + UPDATED_MEDIA_ID);
    }

    @Test
    @Transactional
    void getAllOcrTasksByMediaIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        ocrTasksRepository.saveAndFlush(ocrTasks);

        // Get all the ocrTasksList where mediaId is not null
        defaultOcrTasksShouldBeFound("mediaId.specified=true");

        // Get all the ocrTasksList where mediaId is null
        defaultOcrTasksShouldNotBeFound("mediaId.specified=false");
    }

    @Test
    @Transactional
    void getAllOcrTasksByMediaIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ocrTasksRepository.saveAndFlush(ocrTasks);

        // Get all the ocrTasksList where mediaId is greater than or equal to DEFAULT_MEDIA_ID
        defaultOcrTasksShouldBeFound("mediaId.greaterThanOrEqual=" + DEFAULT_MEDIA_ID);

        // Get all the ocrTasksList where mediaId is greater than or equal to UPDATED_MEDIA_ID
        defaultOcrTasksShouldNotBeFound("mediaId.greaterThanOrEqual=" + UPDATED_MEDIA_ID);
    }

    @Test
    @Transactional
    void getAllOcrTasksByMediaIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ocrTasksRepository.saveAndFlush(ocrTasks);

        // Get all the ocrTasksList where mediaId is less than or equal to DEFAULT_MEDIA_ID
        defaultOcrTasksShouldBeFound("mediaId.lessThanOrEqual=" + DEFAULT_MEDIA_ID);

        // Get all the ocrTasksList where mediaId is less than or equal to SMALLER_MEDIA_ID
        defaultOcrTasksShouldNotBeFound("mediaId.lessThanOrEqual=" + SMALLER_MEDIA_ID);
    }

    @Test
    @Transactional
    void getAllOcrTasksByMediaIdIsLessThanSomething() throws Exception {
        // Initialize the database
        ocrTasksRepository.saveAndFlush(ocrTasks);

        // Get all the ocrTasksList where mediaId is less than DEFAULT_MEDIA_ID
        defaultOcrTasksShouldNotBeFound("mediaId.lessThan=" + DEFAULT_MEDIA_ID);

        // Get all the ocrTasksList where mediaId is less than UPDATED_MEDIA_ID
        defaultOcrTasksShouldBeFound("mediaId.lessThan=" + UPDATED_MEDIA_ID);
    }

    @Test
    @Transactional
    void getAllOcrTasksByMediaIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ocrTasksRepository.saveAndFlush(ocrTasks);

        // Get all the ocrTasksList where mediaId is greater than DEFAULT_MEDIA_ID
        defaultOcrTasksShouldNotBeFound("mediaId.greaterThan=" + DEFAULT_MEDIA_ID);

        // Get all the ocrTasksList where mediaId is greater than SMALLER_MEDIA_ID
        defaultOcrTasksShouldBeFound("mediaId.greaterThan=" + SMALLER_MEDIA_ID);
    }

    @Test
    @Transactional
    void getAllOcrTasksByPageNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        ocrTasksRepository.saveAndFlush(ocrTasks);

        // Get all the ocrTasksList where pageNumber equals to DEFAULT_PAGE_NUMBER
        defaultOcrTasksShouldBeFound("pageNumber.equals=" + DEFAULT_PAGE_NUMBER);

        // Get all the ocrTasksList where pageNumber equals to UPDATED_PAGE_NUMBER
        defaultOcrTasksShouldNotBeFound("pageNumber.equals=" + UPDATED_PAGE_NUMBER);
    }

    @Test
    @Transactional
    void getAllOcrTasksByPageNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ocrTasksRepository.saveAndFlush(ocrTasks);

        // Get all the ocrTasksList where pageNumber not equals to DEFAULT_PAGE_NUMBER
        defaultOcrTasksShouldNotBeFound("pageNumber.notEquals=" + DEFAULT_PAGE_NUMBER);

        // Get all the ocrTasksList where pageNumber not equals to UPDATED_PAGE_NUMBER
        defaultOcrTasksShouldBeFound("pageNumber.notEquals=" + UPDATED_PAGE_NUMBER);
    }

    @Test
    @Transactional
    void getAllOcrTasksByPageNumberIsInShouldWork() throws Exception {
        // Initialize the database
        ocrTasksRepository.saveAndFlush(ocrTasks);

        // Get all the ocrTasksList where pageNumber in DEFAULT_PAGE_NUMBER or UPDATED_PAGE_NUMBER
        defaultOcrTasksShouldBeFound("pageNumber.in=" + DEFAULT_PAGE_NUMBER + "," + UPDATED_PAGE_NUMBER);

        // Get all the ocrTasksList where pageNumber equals to UPDATED_PAGE_NUMBER
        defaultOcrTasksShouldNotBeFound("pageNumber.in=" + UPDATED_PAGE_NUMBER);
    }

    @Test
    @Transactional
    void getAllOcrTasksByPageNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        ocrTasksRepository.saveAndFlush(ocrTasks);

        // Get all the ocrTasksList where pageNumber is not null
        defaultOcrTasksShouldBeFound("pageNumber.specified=true");

        // Get all the ocrTasksList where pageNumber is null
        defaultOcrTasksShouldNotBeFound("pageNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllOcrTasksByPageNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ocrTasksRepository.saveAndFlush(ocrTasks);

        // Get all the ocrTasksList where pageNumber is greater than or equal to DEFAULT_PAGE_NUMBER
        defaultOcrTasksShouldBeFound("pageNumber.greaterThanOrEqual=" + DEFAULT_PAGE_NUMBER);

        // Get all the ocrTasksList where pageNumber is greater than or equal to UPDATED_PAGE_NUMBER
        defaultOcrTasksShouldNotBeFound("pageNumber.greaterThanOrEqual=" + UPDATED_PAGE_NUMBER);
    }

    @Test
    @Transactional
    void getAllOcrTasksByPageNumberIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ocrTasksRepository.saveAndFlush(ocrTasks);

        // Get all the ocrTasksList where pageNumber is less than or equal to DEFAULT_PAGE_NUMBER
        defaultOcrTasksShouldBeFound("pageNumber.lessThanOrEqual=" + DEFAULT_PAGE_NUMBER);

        // Get all the ocrTasksList where pageNumber is less than or equal to SMALLER_PAGE_NUMBER
        defaultOcrTasksShouldNotBeFound("pageNumber.lessThanOrEqual=" + SMALLER_PAGE_NUMBER);
    }

    @Test
    @Transactional
    void getAllOcrTasksByPageNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        ocrTasksRepository.saveAndFlush(ocrTasks);

        // Get all the ocrTasksList where pageNumber is less than DEFAULT_PAGE_NUMBER
        defaultOcrTasksShouldNotBeFound("pageNumber.lessThan=" + DEFAULT_PAGE_NUMBER);

        // Get all the ocrTasksList where pageNumber is less than UPDATED_PAGE_NUMBER
        defaultOcrTasksShouldBeFound("pageNumber.lessThan=" + UPDATED_PAGE_NUMBER);
    }

    @Test
    @Transactional
    void getAllOcrTasksByPageNumberIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ocrTasksRepository.saveAndFlush(ocrTasks);

        // Get all the ocrTasksList where pageNumber is greater than DEFAULT_PAGE_NUMBER
        defaultOcrTasksShouldNotBeFound("pageNumber.greaterThan=" + DEFAULT_PAGE_NUMBER);

        // Get all the ocrTasksList where pageNumber is greater than SMALLER_PAGE_NUMBER
        defaultOcrTasksShouldBeFound("pageNumber.greaterThan=" + SMALLER_PAGE_NUMBER);
    }

    @Test
    @Transactional
    void getAllOcrTasksByJobStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        ocrTasksRepository.saveAndFlush(ocrTasks);

        // Get all the ocrTasksList where jobStatus equals to DEFAULT_JOB_STATUS
        defaultOcrTasksShouldBeFound("jobStatus.equals=" + DEFAULT_JOB_STATUS);

        // Get all the ocrTasksList where jobStatus equals to UPDATED_JOB_STATUS
        defaultOcrTasksShouldNotBeFound("jobStatus.equals=" + UPDATED_JOB_STATUS);
    }

    @Test
    @Transactional
    void getAllOcrTasksByJobStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ocrTasksRepository.saveAndFlush(ocrTasks);

        // Get all the ocrTasksList where jobStatus not equals to DEFAULT_JOB_STATUS
        defaultOcrTasksShouldNotBeFound("jobStatus.notEquals=" + DEFAULT_JOB_STATUS);

        // Get all the ocrTasksList where jobStatus not equals to UPDATED_JOB_STATUS
        defaultOcrTasksShouldBeFound("jobStatus.notEquals=" + UPDATED_JOB_STATUS);
    }

    @Test
    @Transactional
    void getAllOcrTasksByJobStatusIsInShouldWork() throws Exception {
        // Initialize the database
        ocrTasksRepository.saveAndFlush(ocrTasks);

        // Get all the ocrTasksList where jobStatus in DEFAULT_JOB_STATUS or UPDATED_JOB_STATUS
        defaultOcrTasksShouldBeFound("jobStatus.in=" + DEFAULT_JOB_STATUS + "," + UPDATED_JOB_STATUS);

        // Get all the ocrTasksList where jobStatus equals to UPDATED_JOB_STATUS
        defaultOcrTasksShouldNotBeFound("jobStatus.in=" + UPDATED_JOB_STATUS);
    }

    @Test
    @Transactional
    void getAllOcrTasksByJobStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        ocrTasksRepository.saveAndFlush(ocrTasks);

        // Get all the ocrTasksList where jobStatus is not null
        defaultOcrTasksShouldBeFound("jobStatus.specified=true");

        // Get all the ocrTasksList where jobStatus is null
        defaultOcrTasksShouldNotBeFound("jobStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllOcrTasksByJobStatusContainsSomething() throws Exception {
        // Initialize the database
        ocrTasksRepository.saveAndFlush(ocrTasks);

        // Get all the ocrTasksList where jobStatus contains DEFAULT_JOB_STATUS
        defaultOcrTasksShouldBeFound("jobStatus.contains=" + DEFAULT_JOB_STATUS);

        // Get all the ocrTasksList where jobStatus contains UPDATED_JOB_STATUS
        defaultOcrTasksShouldNotBeFound("jobStatus.contains=" + UPDATED_JOB_STATUS);
    }

    @Test
    @Transactional
    void getAllOcrTasksByJobStatusNotContainsSomething() throws Exception {
        // Initialize the database
        ocrTasksRepository.saveAndFlush(ocrTasks);

        // Get all the ocrTasksList where jobStatus does not contain DEFAULT_JOB_STATUS
        defaultOcrTasksShouldNotBeFound("jobStatus.doesNotContain=" + DEFAULT_JOB_STATUS);

        // Get all the ocrTasksList where jobStatus does not contain UPDATED_JOB_STATUS
        defaultOcrTasksShouldBeFound("jobStatus.doesNotContain=" + UPDATED_JOB_STATUS);
    }

    @Test
    @Transactional
    void getAllOcrTasksByCreateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        ocrTasksRepository.saveAndFlush(ocrTasks);

        // Get all the ocrTasksList where createTime equals to DEFAULT_CREATE_TIME
        defaultOcrTasksShouldBeFound("createTime.equals=" + DEFAULT_CREATE_TIME);

        // Get all the ocrTasksList where createTime equals to UPDATED_CREATE_TIME
        defaultOcrTasksShouldNotBeFound("createTime.equals=" + UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    void getAllOcrTasksByCreateTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ocrTasksRepository.saveAndFlush(ocrTasks);

        // Get all the ocrTasksList where createTime not equals to DEFAULT_CREATE_TIME
        defaultOcrTasksShouldNotBeFound("createTime.notEquals=" + DEFAULT_CREATE_TIME);

        // Get all the ocrTasksList where createTime not equals to UPDATED_CREATE_TIME
        defaultOcrTasksShouldBeFound("createTime.notEquals=" + UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    void getAllOcrTasksByCreateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        ocrTasksRepository.saveAndFlush(ocrTasks);

        // Get all the ocrTasksList where createTime in DEFAULT_CREATE_TIME or UPDATED_CREATE_TIME
        defaultOcrTasksShouldBeFound("createTime.in=" + DEFAULT_CREATE_TIME + "," + UPDATED_CREATE_TIME);

        // Get all the ocrTasksList where createTime equals to UPDATED_CREATE_TIME
        defaultOcrTasksShouldNotBeFound("createTime.in=" + UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    void getAllOcrTasksByCreateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        ocrTasksRepository.saveAndFlush(ocrTasks);

        // Get all the ocrTasksList where createTime is not null
        defaultOcrTasksShouldBeFound("createTime.specified=true");

        // Get all the ocrTasksList where createTime is null
        defaultOcrTasksShouldNotBeFound("createTime.specified=false");
    }

    @Test
    @Transactional
    void getAllOcrTasksByCreateTimeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ocrTasksRepository.saveAndFlush(ocrTasks);

        // Get all the ocrTasksList where createTime is greater than or equal to DEFAULT_CREATE_TIME
        defaultOcrTasksShouldBeFound("createTime.greaterThanOrEqual=" + DEFAULT_CREATE_TIME);

        // Get all the ocrTasksList where createTime is greater than or equal to UPDATED_CREATE_TIME
        defaultOcrTasksShouldNotBeFound("createTime.greaterThanOrEqual=" + UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    void getAllOcrTasksByCreateTimeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ocrTasksRepository.saveAndFlush(ocrTasks);

        // Get all the ocrTasksList where createTime is less than or equal to DEFAULT_CREATE_TIME
        defaultOcrTasksShouldBeFound("createTime.lessThanOrEqual=" + DEFAULT_CREATE_TIME);

        // Get all the ocrTasksList where createTime is less than or equal to SMALLER_CREATE_TIME
        defaultOcrTasksShouldNotBeFound("createTime.lessThanOrEqual=" + SMALLER_CREATE_TIME);
    }

    @Test
    @Transactional
    void getAllOcrTasksByCreateTimeIsLessThanSomething() throws Exception {
        // Initialize the database
        ocrTasksRepository.saveAndFlush(ocrTasks);

        // Get all the ocrTasksList where createTime is less than DEFAULT_CREATE_TIME
        defaultOcrTasksShouldNotBeFound("createTime.lessThan=" + DEFAULT_CREATE_TIME);

        // Get all the ocrTasksList where createTime is less than UPDATED_CREATE_TIME
        defaultOcrTasksShouldBeFound("createTime.lessThan=" + UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    void getAllOcrTasksByCreateTimeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ocrTasksRepository.saveAndFlush(ocrTasks);

        // Get all the ocrTasksList where createTime is greater than DEFAULT_CREATE_TIME
        defaultOcrTasksShouldNotBeFound("createTime.greaterThan=" + DEFAULT_CREATE_TIME);

        // Get all the ocrTasksList where createTime is greater than SMALLER_CREATE_TIME
        defaultOcrTasksShouldBeFound("createTime.greaterThan=" + SMALLER_CREATE_TIME);
    }

    @Test
    @Transactional
    void getAllOcrTasksByStartTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        ocrTasksRepository.saveAndFlush(ocrTasks);

        // Get all the ocrTasksList where startTime equals to DEFAULT_START_TIME
        defaultOcrTasksShouldBeFound("startTime.equals=" + DEFAULT_START_TIME);

        // Get all the ocrTasksList where startTime equals to UPDATED_START_TIME
        defaultOcrTasksShouldNotBeFound("startTime.equals=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    void getAllOcrTasksByStartTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ocrTasksRepository.saveAndFlush(ocrTasks);

        // Get all the ocrTasksList where startTime not equals to DEFAULT_START_TIME
        defaultOcrTasksShouldNotBeFound("startTime.notEquals=" + DEFAULT_START_TIME);

        // Get all the ocrTasksList where startTime not equals to UPDATED_START_TIME
        defaultOcrTasksShouldBeFound("startTime.notEquals=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    void getAllOcrTasksByStartTimeIsInShouldWork() throws Exception {
        // Initialize the database
        ocrTasksRepository.saveAndFlush(ocrTasks);

        // Get all the ocrTasksList where startTime in DEFAULT_START_TIME or UPDATED_START_TIME
        defaultOcrTasksShouldBeFound("startTime.in=" + DEFAULT_START_TIME + "," + UPDATED_START_TIME);

        // Get all the ocrTasksList where startTime equals to UPDATED_START_TIME
        defaultOcrTasksShouldNotBeFound("startTime.in=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    void getAllOcrTasksByStartTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        ocrTasksRepository.saveAndFlush(ocrTasks);

        // Get all the ocrTasksList where startTime is not null
        defaultOcrTasksShouldBeFound("startTime.specified=true");

        // Get all the ocrTasksList where startTime is null
        defaultOcrTasksShouldNotBeFound("startTime.specified=false");
    }

    @Test
    @Transactional
    void getAllOcrTasksByStartTimeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ocrTasksRepository.saveAndFlush(ocrTasks);

        // Get all the ocrTasksList where startTime is greater than or equal to DEFAULT_START_TIME
        defaultOcrTasksShouldBeFound("startTime.greaterThanOrEqual=" + DEFAULT_START_TIME);

        // Get all the ocrTasksList where startTime is greater than or equal to UPDATED_START_TIME
        defaultOcrTasksShouldNotBeFound("startTime.greaterThanOrEqual=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    void getAllOcrTasksByStartTimeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ocrTasksRepository.saveAndFlush(ocrTasks);

        // Get all the ocrTasksList where startTime is less than or equal to DEFAULT_START_TIME
        defaultOcrTasksShouldBeFound("startTime.lessThanOrEqual=" + DEFAULT_START_TIME);

        // Get all the ocrTasksList where startTime is less than or equal to SMALLER_START_TIME
        defaultOcrTasksShouldNotBeFound("startTime.lessThanOrEqual=" + SMALLER_START_TIME);
    }

    @Test
    @Transactional
    void getAllOcrTasksByStartTimeIsLessThanSomething() throws Exception {
        // Initialize the database
        ocrTasksRepository.saveAndFlush(ocrTasks);

        // Get all the ocrTasksList where startTime is less than DEFAULT_START_TIME
        defaultOcrTasksShouldNotBeFound("startTime.lessThan=" + DEFAULT_START_TIME);

        // Get all the ocrTasksList where startTime is less than UPDATED_START_TIME
        defaultOcrTasksShouldBeFound("startTime.lessThan=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    void getAllOcrTasksByStartTimeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ocrTasksRepository.saveAndFlush(ocrTasks);

        // Get all the ocrTasksList where startTime is greater than DEFAULT_START_TIME
        defaultOcrTasksShouldNotBeFound("startTime.greaterThan=" + DEFAULT_START_TIME);

        // Get all the ocrTasksList where startTime is greater than SMALLER_START_TIME
        defaultOcrTasksShouldBeFound("startTime.greaterThan=" + SMALLER_START_TIME);
    }

    @Test
    @Transactional
    void getAllOcrTasksByStopTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        ocrTasksRepository.saveAndFlush(ocrTasks);

        // Get all the ocrTasksList where stopTime equals to DEFAULT_STOP_TIME
        defaultOcrTasksShouldBeFound("stopTime.equals=" + DEFAULT_STOP_TIME);

        // Get all the ocrTasksList where stopTime equals to UPDATED_STOP_TIME
        defaultOcrTasksShouldNotBeFound("stopTime.equals=" + UPDATED_STOP_TIME);
    }

    @Test
    @Transactional
    void getAllOcrTasksByStopTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ocrTasksRepository.saveAndFlush(ocrTasks);

        // Get all the ocrTasksList where stopTime not equals to DEFAULT_STOP_TIME
        defaultOcrTasksShouldNotBeFound("stopTime.notEquals=" + DEFAULT_STOP_TIME);

        // Get all the ocrTasksList where stopTime not equals to UPDATED_STOP_TIME
        defaultOcrTasksShouldBeFound("stopTime.notEquals=" + UPDATED_STOP_TIME);
    }

    @Test
    @Transactional
    void getAllOcrTasksByStopTimeIsInShouldWork() throws Exception {
        // Initialize the database
        ocrTasksRepository.saveAndFlush(ocrTasks);

        // Get all the ocrTasksList where stopTime in DEFAULT_STOP_TIME or UPDATED_STOP_TIME
        defaultOcrTasksShouldBeFound("stopTime.in=" + DEFAULT_STOP_TIME + "," + UPDATED_STOP_TIME);

        // Get all the ocrTasksList where stopTime equals to UPDATED_STOP_TIME
        defaultOcrTasksShouldNotBeFound("stopTime.in=" + UPDATED_STOP_TIME);
    }

    @Test
    @Transactional
    void getAllOcrTasksByStopTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        ocrTasksRepository.saveAndFlush(ocrTasks);

        // Get all the ocrTasksList where stopTime is not null
        defaultOcrTasksShouldBeFound("stopTime.specified=true");

        // Get all the ocrTasksList where stopTime is null
        defaultOcrTasksShouldNotBeFound("stopTime.specified=false");
    }

    @Test
    @Transactional
    void getAllOcrTasksByStopTimeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ocrTasksRepository.saveAndFlush(ocrTasks);

        // Get all the ocrTasksList where stopTime is greater than or equal to DEFAULT_STOP_TIME
        defaultOcrTasksShouldBeFound("stopTime.greaterThanOrEqual=" + DEFAULT_STOP_TIME);

        // Get all the ocrTasksList where stopTime is greater than or equal to UPDATED_STOP_TIME
        defaultOcrTasksShouldNotBeFound("stopTime.greaterThanOrEqual=" + UPDATED_STOP_TIME);
    }

    @Test
    @Transactional
    void getAllOcrTasksByStopTimeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ocrTasksRepository.saveAndFlush(ocrTasks);

        // Get all the ocrTasksList where stopTime is less than or equal to DEFAULT_STOP_TIME
        defaultOcrTasksShouldBeFound("stopTime.lessThanOrEqual=" + DEFAULT_STOP_TIME);

        // Get all the ocrTasksList where stopTime is less than or equal to SMALLER_STOP_TIME
        defaultOcrTasksShouldNotBeFound("stopTime.lessThanOrEqual=" + SMALLER_STOP_TIME);
    }

    @Test
    @Transactional
    void getAllOcrTasksByStopTimeIsLessThanSomething() throws Exception {
        // Initialize the database
        ocrTasksRepository.saveAndFlush(ocrTasks);

        // Get all the ocrTasksList where stopTime is less than DEFAULT_STOP_TIME
        defaultOcrTasksShouldNotBeFound("stopTime.lessThan=" + DEFAULT_STOP_TIME);

        // Get all the ocrTasksList where stopTime is less than UPDATED_STOP_TIME
        defaultOcrTasksShouldBeFound("stopTime.lessThan=" + UPDATED_STOP_TIME);
    }

    @Test
    @Transactional
    void getAllOcrTasksByStopTimeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ocrTasksRepository.saveAndFlush(ocrTasks);

        // Get all the ocrTasksList where stopTime is greater than DEFAULT_STOP_TIME
        defaultOcrTasksShouldNotBeFound("stopTime.greaterThan=" + DEFAULT_STOP_TIME);

        // Get all the ocrTasksList where stopTime is greater than SMALLER_STOP_TIME
        defaultOcrTasksShouldBeFound("stopTime.greaterThan=" + SMALLER_STOP_TIME);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOcrTasksShouldBeFound(String filter) throws Exception {
        restOcrTasksMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ocrTasks.getId().intValue())))
            .andExpect(jsonPath("$.[*].mediaId").value(hasItem(DEFAULT_MEDIA_ID.intValue())))
            .andExpect(jsonPath("$.[*].pageNumber").value(hasItem(DEFAULT_PAGE_NUMBER)))
            .andExpect(jsonPath("$.[*].jobStatus").value(hasItem(DEFAULT_JOB_STATUS)))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(sameInstant(DEFAULT_CREATE_TIME))))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(sameInstant(DEFAULT_START_TIME))))
            .andExpect(jsonPath("$.[*].stopTime").value(hasItem(sameInstant(DEFAULT_STOP_TIME))));

        // Check, that the count call also returns 1
        restOcrTasksMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOcrTasksShouldNotBeFound(String filter) throws Exception {
        restOcrTasksMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOcrTasksMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingOcrTasks() throws Exception {
        // Get the ocrTasks
        restOcrTasksMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewOcrTasks() throws Exception {
        // Initialize the database
        ocrTasksRepository.saveAndFlush(ocrTasks);

        int databaseSizeBeforeUpdate = ocrTasksRepository.findAll().size();

        // Update the ocrTasks
        OcrTasks updatedOcrTasks = ocrTasksRepository.findById(ocrTasks.getId()).get();
        // Disconnect from session so that the updates on updatedOcrTasks are not directly saved in db
        em.detach(updatedOcrTasks);
        updatedOcrTasks
            .mediaId(UPDATED_MEDIA_ID)
            .pageNumber(UPDATED_PAGE_NUMBER)
            .jobStatus(UPDATED_JOB_STATUS)
            .createTime(UPDATED_CREATE_TIME)
            .startTime(UPDATED_START_TIME)
            .stopTime(UPDATED_STOP_TIME);
        OcrTasksDTO ocrTasksDTO = ocrTasksMapper.toDto(updatedOcrTasks);

        restOcrTasksMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ocrTasksDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ocrTasksDTO))
            )
            .andExpect(status().isOk());

        // Validate the OcrTasks in the database
        List<OcrTasks> ocrTasksList = ocrTasksRepository.findAll();
        assertThat(ocrTasksList).hasSize(databaseSizeBeforeUpdate);
        OcrTasks testOcrTasks = ocrTasksList.get(ocrTasksList.size() - 1);
        assertThat(testOcrTasks.getMediaId()).isEqualTo(UPDATED_MEDIA_ID);
        assertThat(testOcrTasks.getPageNumber()).isEqualTo(UPDATED_PAGE_NUMBER);
        assertThat(testOcrTasks.getJobStatus()).isEqualTo(UPDATED_JOB_STATUS);
        assertThat(testOcrTasks.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testOcrTasks.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testOcrTasks.getStopTime()).isEqualTo(UPDATED_STOP_TIME);
    }

    @Test
    @Transactional
    void putNonExistingOcrTasks() throws Exception {
        int databaseSizeBeforeUpdate = ocrTasksRepository.findAll().size();
        ocrTasks.setId(count.incrementAndGet());

        // Create the OcrTasks
        OcrTasksDTO ocrTasksDTO = ocrTasksMapper.toDto(ocrTasks);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOcrTasksMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ocrTasksDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ocrTasksDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OcrTasks in the database
        List<OcrTasks> ocrTasksList = ocrTasksRepository.findAll();
        assertThat(ocrTasksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOcrTasks() throws Exception {
        int databaseSizeBeforeUpdate = ocrTasksRepository.findAll().size();
        ocrTasks.setId(count.incrementAndGet());

        // Create the OcrTasks
        OcrTasksDTO ocrTasksDTO = ocrTasksMapper.toDto(ocrTasks);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOcrTasksMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ocrTasksDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OcrTasks in the database
        List<OcrTasks> ocrTasksList = ocrTasksRepository.findAll();
        assertThat(ocrTasksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOcrTasks() throws Exception {
        int databaseSizeBeforeUpdate = ocrTasksRepository.findAll().size();
        ocrTasks.setId(count.incrementAndGet());

        // Create the OcrTasks
        OcrTasksDTO ocrTasksDTO = ocrTasksMapper.toDto(ocrTasks);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOcrTasksMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ocrTasksDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the OcrTasks in the database
        List<OcrTasks> ocrTasksList = ocrTasksRepository.findAll();
        assertThat(ocrTasksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOcrTasksWithPatch() throws Exception {
        // Initialize the database
        ocrTasksRepository.saveAndFlush(ocrTasks);

        int databaseSizeBeforeUpdate = ocrTasksRepository.findAll().size();

        // Update the ocrTasks using partial update
        OcrTasks partialUpdatedOcrTasks = new OcrTasks();
        partialUpdatedOcrTasks.setId(ocrTasks.getId());

        partialUpdatedOcrTasks.mediaId(UPDATED_MEDIA_ID).pageNumber(UPDATED_PAGE_NUMBER).jobStatus(UPDATED_JOB_STATUS);

        restOcrTasksMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOcrTasks.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOcrTasks))
            )
            .andExpect(status().isOk());

        // Validate the OcrTasks in the database
        List<OcrTasks> ocrTasksList = ocrTasksRepository.findAll();
        assertThat(ocrTasksList).hasSize(databaseSizeBeforeUpdate);
        OcrTasks testOcrTasks = ocrTasksList.get(ocrTasksList.size() - 1);
        assertThat(testOcrTasks.getMediaId()).isEqualTo(UPDATED_MEDIA_ID);
        assertThat(testOcrTasks.getPageNumber()).isEqualTo(UPDATED_PAGE_NUMBER);
        assertThat(testOcrTasks.getJobStatus()).isEqualTo(UPDATED_JOB_STATUS);
        assertThat(testOcrTasks.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testOcrTasks.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testOcrTasks.getStopTime()).isEqualTo(DEFAULT_STOP_TIME);
    }

    @Test
    @Transactional
    void fullUpdateOcrTasksWithPatch() throws Exception {
        // Initialize the database
        ocrTasksRepository.saveAndFlush(ocrTasks);

        int databaseSizeBeforeUpdate = ocrTasksRepository.findAll().size();

        // Update the ocrTasks using partial update
        OcrTasks partialUpdatedOcrTasks = new OcrTasks();
        partialUpdatedOcrTasks.setId(ocrTasks.getId());

        partialUpdatedOcrTasks
            .mediaId(UPDATED_MEDIA_ID)
            .pageNumber(UPDATED_PAGE_NUMBER)
            .jobStatus(UPDATED_JOB_STATUS)
            .createTime(UPDATED_CREATE_TIME)
            .startTime(UPDATED_START_TIME)
            .stopTime(UPDATED_STOP_TIME);

        restOcrTasksMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOcrTasks.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOcrTasks))
            )
            .andExpect(status().isOk());

        // Validate the OcrTasks in the database
        List<OcrTasks> ocrTasksList = ocrTasksRepository.findAll();
        assertThat(ocrTasksList).hasSize(databaseSizeBeforeUpdate);
        OcrTasks testOcrTasks = ocrTasksList.get(ocrTasksList.size() - 1);
        assertThat(testOcrTasks.getMediaId()).isEqualTo(UPDATED_MEDIA_ID);
        assertThat(testOcrTasks.getPageNumber()).isEqualTo(UPDATED_PAGE_NUMBER);
        assertThat(testOcrTasks.getJobStatus()).isEqualTo(UPDATED_JOB_STATUS);
        assertThat(testOcrTasks.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testOcrTasks.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testOcrTasks.getStopTime()).isEqualTo(UPDATED_STOP_TIME);
    }

    @Test
    @Transactional
    void patchNonExistingOcrTasks() throws Exception {
        int databaseSizeBeforeUpdate = ocrTasksRepository.findAll().size();
        ocrTasks.setId(count.incrementAndGet());

        // Create the OcrTasks
        OcrTasksDTO ocrTasksDTO = ocrTasksMapper.toDto(ocrTasks);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOcrTasksMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ocrTasksDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ocrTasksDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OcrTasks in the database
        List<OcrTasks> ocrTasksList = ocrTasksRepository.findAll();
        assertThat(ocrTasksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOcrTasks() throws Exception {
        int databaseSizeBeforeUpdate = ocrTasksRepository.findAll().size();
        ocrTasks.setId(count.incrementAndGet());

        // Create the OcrTasks
        OcrTasksDTO ocrTasksDTO = ocrTasksMapper.toDto(ocrTasks);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOcrTasksMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ocrTasksDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OcrTasks in the database
        List<OcrTasks> ocrTasksList = ocrTasksRepository.findAll();
        assertThat(ocrTasksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOcrTasks() throws Exception {
        int databaseSizeBeforeUpdate = ocrTasksRepository.findAll().size();
        ocrTasks.setId(count.incrementAndGet());

        // Create the OcrTasks
        OcrTasksDTO ocrTasksDTO = ocrTasksMapper.toDto(ocrTasks);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOcrTasksMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(ocrTasksDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OcrTasks in the database
        List<OcrTasks> ocrTasksList = ocrTasksRepository.findAll();
        assertThat(ocrTasksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOcrTasks() throws Exception {
        // Initialize the database
        ocrTasksRepository.saveAndFlush(ocrTasks);

        int databaseSizeBeforeDelete = ocrTasksRepository.findAll().size();

        // Delete the ocrTasks
        restOcrTasksMockMvc
            .perform(delete(ENTITY_API_URL_ID, ocrTasks.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OcrTasks> ocrTasksList = ocrTasksRepository.findAll();
        assertThat(ocrTasksList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
