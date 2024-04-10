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
import org.jhipster.dich.domain.InlineStyleRanges;
import org.jhipster.dich.domain.TextBlock;
import org.jhipster.dich.repository.InlineStyleRangesRepository;
import org.jhipster.dich.service.criteria.InlineStyleRangesCriteria;
import org.jhipster.dich.service.dto.InlineStyleRangesDTO;
import org.jhipster.dich.service.mapper.InlineStyleRangesMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link InlineStyleRangesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class InlineStyleRangesResourceIT {

    private static final Integer DEFAULT_START_POS = 1;
    private static final Integer UPDATED_START_POS = 2;
    private static final Integer SMALLER_START_POS = 1 - 1;

    private static final Integer DEFAULT_STOP_POS = 1;
    private static final Integer UPDATED_STOP_POS = 2;
    private static final Integer SMALLER_STOP_POS = 1 - 1;

    private static final String ENTITY_API_URL = "/api/inline-style-ranges";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private InlineStyleRangesRepository inlineStyleRangesRepository;

    @Autowired
    private InlineStyleRangesMapper inlineStyleRangesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInlineStyleRangesMockMvc;

    private InlineStyleRanges inlineStyleRanges;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InlineStyleRanges createEntity(EntityManager em) {
        InlineStyleRanges inlineStyleRanges = new InlineStyleRanges().startPos(DEFAULT_START_POS).stopPos(DEFAULT_STOP_POS);
        return inlineStyleRanges;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InlineStyleRanges createUpdatedEntity(EntityManager em) {
        InlineStyleRanges inlineStyleRanges = new InlineStyleRanges().startPos(UPDATED_START_POS).stopPos(UPDATED_STOP_POS);
        return inlineStyleRanges;
    }

    @BeforeEach
    public void initTest() {
        inlineStyleRanges = createEntity(em);
    }

    @Test
    @Transactional
    void createInlineStyleRanges() throws Exception {
        int databaseSizeBeforeCreate = inlineStyleRangesRepository.findAll().size();
        // Create the InlineStyleRanges
        InlineStyleRangesDTO inlineStyleRangesDTO = inlineStyleRangesMapper.toDto(inlineStyleRanges);
        restInlineStyleRangesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(inlineStyleRangesDTO))
            )
            .andExpect(status().isCreated());

        // Validate the InlineStyleRanges in the database
        List<InlineStyleRanges> inlineStyleRangesList = inlineStyleRangesRepository.findAll();
        assertThat(inlineStyleRangesList).hasSize(databaseSizeBeforeCreate + 1);
        InlineStyleRanges testInlineStyleRanges = inlineStyleRangesList.get(inlineStyleRangesList.size() - 1);
        assertThat(testInlineStyleRanges.getStartPos()).isEqualTo(DEFAULT_START_POS);
        assertThat(testInlineStyleRanges.getStopPos()).isEqualTo(DEFAULT_STOP_POS);
    }

    @Test
    @Transactional
    void createInlineStyleRangesWithExistingId() throws Exception {
        // Create the InlineStyleRanges with an existing ID
        inlineStyleRanges.setId(1L);
        InlineStyleRangesDTO inlineStyleRangesDTO = inlineStyleRangesMapper.toDto(inlineStyleRanges);

        int databaseSizeBeforeCreate = inlineStyleRangesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInlineStyleRangesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(inlineStyleRangesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the InlineStyleRanges in the database
        List<InlineStyleRanges> inlineStyleRangesList = inlineStyleRangesRepository.findAll();
        assertThat(inlineStyleRangesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllInlineStyleRanges() throws Exception {
        // Initialize the database
        inlineStyleRangesRepository.saveAndFlush(inlineStyleRanges);

        // Get all the inlineStyleRangesList
        restInlineStyleRangesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inlineStyleRanges.getId().intValue())))
            .andExpect(jsonPath("$.[*].startPos").value(hasItem(DEFAULT_START_POS)))
            .andExpect(jsonPath("$.[*].stopPos").value(hasItem(DEFAULT_STOP_POS)));
    }

    @Test
    @Transactional
    void getInlineStyleRanges() throws Exception {
        // Initialize the database
        inlineStyleRangesRepository.saveAndFlush(inlineStyleRanges);

        // Get the inlineStyleRanges
        restInlineStyleRangesMockMvc
            .perform(get(ENTITY_API_URL_ID, inlineStyleRanges.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(inlineStyleRanges.getId().intValue()))
            .andExpect(jsonPath("$.startPos").value(DEFAULT_START_POS))
            .andExpect(jsonPath("$.stopPos").value(DEFAULT_STOP_POS));
    }

    @Test
    @Transactional
    void getInlineStyleRangesByIdFiltering() throws Exception {
        // Initialize the database
        inlineStyleRangesRepository.saveAndFlush(inlineStyleRanges);

        Long id = inlineStyleRanges.getId();

        defaultInlineStyleRangesShouldBeFound("id.equals=" + id);
        defaultInlineStyleRangesShouldNotBeFound("id.notEquals=" + id);

        defaultInlineStyleRangesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultInlineStyleRangesShouldNotBeFound("id.greaterThan=" + id);

        defaultInlineStyleRangesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultInlineStyleRangesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllInlineStyleRangesByStartPosIsEqualToSomething() throws Exception {
        // Initialize the database
        inlineStyleRangesRepository.saveAndFlush(inlineStyleRanges);

        // Get all the inlineStyleRangesList where startPos equals to DEFAULT_START_POS
        defaultInlineStyleRangesShouldBeFound("startPos.equals=" + DEFAULT_START_POS);

        // Get all the inlineStyleRangesList where startPos equals to UPDATED_START_POS
        defaultInlineStyleRangesShouldNotBeFound("startPos.equals=" + UPDATED_START_POS);
    }

    @Test
    @Transactional
    void getAllInlineStyleRangesByStartPosIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inlineStyleRangesRepository.saveAndFlush(inlineStyleRanges);

        // Get all the inlineStyleRangesList where startPos not equals to DEFAULT_START_POS
        defaultInlineStyleRangesShouldNotBeFound("startPos.notEquals=" + DEFAULT_START_POS);

        // Get all the inlineStyleRangesList where startPos not equals to UPDATED_START_POS
        defaultInlineStyleRangesShouldBeFound("startPos.notEquals=" + UPDATED_START_POS);
    }

    @Test
    @Transactional
    void getAllInlineStyleRangesByStartPosIsInShouldWork() throws Exception {
        // Initialize the database
        inlineStyleRangesRepository.saveAndFlush(inlineStyleRanges);

        // Get all the inlineStyleRangesList where startPos in DEFAULT_START_POS or UPDATED_START_POS
        defaultInlineStyleRangesShouldBeFound("startPos.in=" + DEFAULT_START_POS + "," + UPDATED_START_POS);

        // Get all the inlineStyleRangesList where startPos equals to UPDATED_START_POS
        defaultInlineStyleRangesShouldNotBeFound("startPos.in=" + UPDATED_START_POS);
    }

    @Test
    @Transactional
    void getAllInlineStyleRangesByStartPosIsNullOrNotNull() throws Exception {
        // Initialize the database
        inlineStyleRangesRepository.saveAndFlush(inlineStyleRanges);

        // Get all the inlineStyleRangesList where startPos is not null
        defaultInlineStyleRangesShouldBeFound("startPos.specified=true");

        // Get all the inlineStyleRangesList where startPos is null
        defaultInlineStyleRangesShouldNotBeFound("startPos.specified=false");
    }

    @Test
    @Transactional
    void getAllInlineStyleRangesByStartPosIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inlineStyleRangesRepository.saveAndFlush(inlineStyleRanges);

        // Get all the inlineStyleRangesList where startPos is greater than or equal to DEFAULT_START_POS
        defaultInlineStyleRangesShouldBeFound("startPos.greaterThanOrEqual=" + DEFAULT_START_POS);

        // Get all the inlineStyleRangesList where startPos is greater than or equal to UPDATED_START_POS
        defaultInlineStyleRangesShouldNotBeFound("startPos.greaterThanOrEqual=" + UPDATED_START_POS);
    }

    @Test
    @Transactional
    void getAllInlineStyleRangesByStartPosIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inlineStyleRangesRepository.saveAndFlush(inlineStyleRanges);

        // Get all the inlineStyleRangesList where startPos is less than or equal to DEFAULT_START_POS
        defaultInlineStyleRangesShouldBeFound("startPos.lessThanOrEqual=" + DEFAULT_START_POS);

        // Get all the inlineStyleRangesList where startPos is less than or equal to SMALLER_START_POS
        defaultInlineStyleRangesShouldNotBeFound("startPos.lessThanOrEqual=" + SMALLER_START_POS);
    }

    @Test
    @Transactional
    void getAllInlineStyleRangesByStartPosIsLessThanSomething() throws Exception {
        // Initialize the database
        inlineStyleRangesRepository.saveAndFlush(inlineStyleRanges);

        // Get all the inlineStyleRangesList where startPos is less than DEFAULT_START_POS
        defaultInlineStyleRangesShouldNotBeFound("startPos.lessThan=" + DEFAULT_START_POS);

        // Get all the inlineStyleRangesList where startPos is less than UPDATED_START_POS
        defaultInlineStyleRangesShouldBeFound("startPos.lessThan=" + UPDATED_START_POS);
    }

    @Test
    @Transactional
    void getAllInlineStyleRangesByStartPosIsGreaterThanSomething() throws Exception {
        // Initialize the database
        inlineStyleRangesRepository.saveAndFlush(inlineStyleRanges);

        // Get all the inlineStyleRangesList where startPos is greater than DEFAULT_START_POS
        defaultInlineStyleRangesShouldNotBeFound("startPos.greaterThan=" + DEFAULT_START_POS);

        // Get all the inlineStyleRangesList where startPos is greater than SMALLER_START_POS
        defaultInlineStyleRangesShouldBeFound("startPos.greaterThan=" + SMALLER_START_POS);
    }

    @Test
    @Transactional
    void getAllInlineStyleRangesByStopPosIsEqualToSomething() throws Exception {
        // Initialize the database
        inlineStyleRangesRepository.saveAndFlush(inlineStyleRanges);

        // Get all the inlineStyleRangesList where stopPos equals to DEFAULT_STOP_POS
        defaultInlineStyleRangesShouldBeFound("stopPos.equals=" + DEFAULT_STOP_POS);

        // Get all the inlineStyleRangesList where stopPos equals to UPDATED_STOP_POS
        defaultInlineStyleRangesShouldNotBeFound("stopPos.equals=" + UPDATED_STOP_POS);
    }

    @Test
    @Transactional
    void getAllInlineStyleRangesByStopPosIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inlineStyleRangesRepository.saveAndFlush(inlineStyleRanges);

        // Get all the inlineStyleRangesList where stopPos not equals to DEFAULT_STOP_POS
        defaultInlineStyleRangesShouldNotBeFound("stopPos.notEquals=" + DEFAULT_STOP_POS);

        // Get all the inlineStyleRangesList where stopPos not equals to UPDATED_STOP_POS
        defaultInlineStyleRangesShouldBeFound("stopPos.notEquals=" + UPDATED_STOP_POS);
    }

    @Test
    @Transactional
    void getAllInlineStyleRangesByStopPosIsInShouldWork() throws Exception {
        // Initialize the database
        inlineStyleRangesRepository.saveAndFlush(inlineStyleRanges);

        // Get all the inlineStyleRangesList where stopPos in DEFAULT_STOP_POS or UPDATED_STOP_POS
        defaultInlineStyleRangesShouldBeFound("stopPos.in=" + DEFAULT_STOP_POS + "," + UPDATED_STOP_POS);

        // Get all the inlineStyleRangesList where stopPos equals to UPDATED_STOP_POS
        defaultInlineStyleRangesShouldNotBeFound("stopPos.in=" + UPDATED_STOP_POS);
    }

    @Test
    @Transactional
    void getAllInlineStyleRangesByStopPosIsNullOrNotNull() throws Exception {
        // Initialize the database
        inlineStyleRangesRepository.saveAndFlush(inlineStyleRanges);

        // Get all the inlineStyleRangesList where stopPos is not null
        defaultInlineStyleRangesShouldBeFound("stopPos.specified=true");

        // Get all the inlineStyleRangesList where stopPos is null
        defaultInlineStyleRangesShouldNotBeFound("stopPos.specified=false");
    }

    @Test
    @Transactional
    void getAllInlineStyleRangesByStopPosIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inlineStyleRangesRepository.saveAndFlush(inlineStyleRanges);

        // Get all the inlineStyleRangesList where stopPos is greater than or equal to DEFAULT_STOP_POS
        defaultInlineStyleRangesShouldBeFound("stopPos.greaterThanOrEqual=" + DEFAULT_STOP_POS);

        // Get all the inlineStyleRangesList where stopPos is greater than or equal to UPDATED_STOP_POS
        defaultInlineStyleRangesShouldNotBeFound("stopPos.greaterThanOrEqual=" + UPDATED_STOP_POS);
    }

    @Test
    @Transactional
    void getAllInlineStyleRangesByStopPosIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inlineStyleRangesRepository.saveAndFlush(inlineStyleRanges);

        // Get all the inlineStyleRangesList where stopPos is less than or equal to DEFAULT_STOP_POS
        defaultInlineStyleRangesShouldBeFound("stopPos.lessThanOrEqual=" + DEFAULT_STOP_POS);

        // Get all the inlineStyleRangesList where stopPos is less than or equal to SMALLER_STOP_POS
        defaultInlineStyleRangesShouldNotBeFound("stopPos.lessThanOrEqual=" + SMALLER_STOP_POS);
    }

    @Test
    @Transactional
    void getAllInlineStyleRangesByStopPosIsLessThanSomething() throws Exception {
        // Initialize the database
        inlineStyleRangesRepository.saveAndFlush(inlineStyleRanges);

        // Get all the inlineStyleRangesList where stopPos is less than DEFAULT_STOP_POS
        defaultInlineStyleRangesShouldNotBeFound("stopPos.lessThan=" + DEFAULT_STOP_POS);

        // Get all the inlineStyleRangesList where stopPos is less than UPDATED_STOP_POS
        defaultInlineStyleRangesShouldBeFound("stopPos.lessThan=" + UPDATED_STOP_POS);
    }

    @Test
    @Transactional
    void getAllInlineStyleRangesByStopPosIsGreaterThanSomething() throws Exception {
        // Initialize the database
        inlineStyleRangesRepository.saveAndFlush(inlineStyleRanges);

        // Get all the inlineStyleRangesList where stopPos is greater than DEFAULT_STOP_POS
        defaultInlineStyleRangesShouldNotBeFound("stopPos.greaterThan=" + DEFAULT_STOP_POS);

        // Get all the inlineStyleRangesList where stopPos is greater than SMALLER_STOP_POS
        defaultInlineStyleRangesShouldBeFound("stopPos.greaterThan=" + SMALLER_STOP_POS);
    }

    @Test
    @Transactional
    void getAllInlineStyleRangesByTextBlockIsEqualToSomething() throws Exception {
        // Initialize the database
        inlineStyleRangesRepository.saveAndFlush(inlineStyleRanges);
        TextBlock textBlock;
        if (TestUtil.findAll(em, TextBlock.class).isEmpty()) {
            textBlock = TextBlockResourceIT.createEntity(em);
            em.persist(textBlock);
            em.flush();
        } else {
            textBlock = TestUtil.findAll(em, TextBlock.class).get(0);
        }
        em.persist(textBlock);
        em.flush();
        inlineStyleRanges.setTextBlock(textBlock);
        inlineStyleRangesRepository.saveAndFlush(inlineStyleRanges);
        Long textBlockId = textBlock.getId();

        // Get all the inlineStyleRangesList where textBlock equals to textBlockId
        defaultInlineStyleRangesShouldBeFound("textBlockId.equals=" + textBlockId);

        // Get all the inlineStyleRangesList where textBlock equals to (textBlockId + 1)
        defaultInlineStyleRangesShouldNotBeFound("textBlockId.equals=" + (textBlockId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultInlineStyleRangesShouldBeFound(String filter) throws Exception {
        restInlineStyleRangesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inlineStyleRanges.getId().intValue())))
            .andExpect(jsonPath("$.[*].startPos").value(hasItem(DEFAULT_START_POS)))
            .andExpect(jsonPath("$.[*].stopPos").value(hasItem(DEFAULT_STOP_POS)));

        // Check, that the count call also returns 1
        restInlineStyleRangesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultInlineStyleRangesShouldNotBeFound(String filter) throws Exception {
        restInlineStyleRangesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restInlineStyleRangesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingInlineStyleRanges() throws Exception {
        // Get the inlineStyleRanges
        restInlineStyleRangesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewInlineStyleRanges() throws Exception {
        // Initialize the database
        inlineStyleRangesRepository.saveAndFlush(inlineStyleRanges);

        int databaseSizeBeforeUpdate = inlineStyleRangesRepository.findAll().size();

        // Update the inlineStyleRanges
        InlineStyleRanges updatedInlineStyleRanges = inlineStyleRangesRepository.findById(inlineStyleRanges.getId()).get();
        // Disconnect from session so that the updates on updatedInlineStyleRanges are not directly saved in db
        em.detach(updatedInlineStyleRanges);
        updatedInlineStyleRanges.startPos(UPDATED_START_POS).stopPos(UPDATED_STOP_POS);
        InlineStyleRangesDTO inlineStyleRangesDTO = inlineStyleRangesMapper.toDto(updatedInlineStyleRanges);

        restInlineStyleRangesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, inlineStyleRangesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(inlineStyleRangesDTO))
            )
            .andExpect(status().isOk());

        // Validate the InlineStyleRanges in the database
        List<InlineStyleRanges> inlineStyleRangesList = inlineStyleRangesRepository.findAll();
        assertThat(inlineStyleRangesList).hasSize(databaseSizeBeforeUpdate);
        InlineStyleRanges testInlineStyleRanges = inlineStyleRangesList.get(inlineStyleRangesList.size() - 1);
        assertThat(testInlineStyleRanges.getStartPos()).isEqualTo(UPDATED_START_POS);
        assertThat(testInlineStyleRanges.getStopPos()).isEqualTo(UPDATED_STOP_POS);
    }

    @Test
    @Transactional
    void putNonExistingInlineStyleRanges() throws Exception {
        int databaseSizeBeforeUpdate = inlineStyleRangesRepository.findAll().size();
        inlineStyleRanges.setId(count.incrementAndGet());

        // Create the InlineStyleRanges
        InlineStyleRangesDTO inlineStyleRangesDTO = inlineStyleRangesMapper.toDto(inlineStyleRanges);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInlineStyleRangesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, inlineStyleRangesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(inlineStyleRangesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the InlineStyleRanges in the database
        List<InlineStyleRanges> inlineStyleRangesList = inlineStyleRangesRepository.findAll();
        assertThat(inlineStyleRangesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchInlineStyleRanges() throws Exception {
        int databaseSizeBeforeUpdate = inlineStyleRangesRepository.findAll().size();
        inlineStyleRanges.setId(count.incrementAndGet());

        // Create the InlineStyleRanges
        InlineStyleRangesDTO inlineStyleRangesDTO = inlineStyleRangesMapper.toDto(inlineStyleRanges);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInlineStyleRangesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(inlineStyleRangesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the InlineStyleRanges in the database
        List<InlineStyleRanges> inlineStyleRangesList = inlineStyleRangesRepository.findAll();
        assertThat(inlineStyleRangesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamInlineStyleRanges() throws Exception {
        int databaseSizeBeforeUpdate = inlineStyleRangesRepository.findAll().size();
        inlineStyleRanges.setId(count.incrementAndGet());

        // Create the InlineStyleRanges
        InlineStyleRangesDTO inlineStyleRangesDTO = inlineStyleRangesMapper.toDto(inlineStyleRanges);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInlineStyleRangesMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(inlineStyleRangesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the InlineStyleRanges in the database
        List<InlineStyleRanges> inlineStyleRangesList = inlineStyleRangesRepository.findAll();
        assertThat(inlineStyleRangesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateInlineStyleRangesWithPatch() throws Exception {
        // Initialize the database
        inlineStyleRangesRepository.saveAndFlush(inlineStyleRanges);

        int databaseSizeBeforeUpdate = inlineStyleRangesRepository.findAll().size();

        // Update the inlineStyleRanges using partial update
        InlineStyleRanges partialUpdatedInlineStyleRanges = new InlineStyleRanges();
        partialUpdatedInlineStyleRanges.setId(inlineStyleRanges.getId());

        partialUpdatedInlineStyleRanges.startPos(UPDATED_START_POS);

        restInlineStyleRangesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInlineStyleRanges.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInlineStyleRanges))
            )
            .andExpect(status().isOk());

        // Validate the InlineStyleRanges in the database
        List<InlineStyleRanges> inlineStyleRangesList = inlineStyleRangesRepository.findAll();
        assertThat(inlineStyleRangesList).hasSize(databaseSizeBeforeUpdate);
        InlineStyleRanges testInlineStyleRanges = inlineStyleRangesList.get(inlineStyleRangesList.size() - 1);
        assertThat(testInlineStyleRanges.getStartPos()).isEqualTo(UPDATED_START_POS);
        assertThat(testInlineStyleRanges.getStopPos()).isEqualTo(DEFAULT_STOP_POS);
    }

    @Test
    @Transactional
    void fullUpdateInlineStyleRangesWithPatch() throws Exception {
        // Initialize the database
        inlineStyleRangesRepository.saveAndFlush(inlineStyleRanges);

        int databaseSizeBeforeUpdate = inlineStyleRangesRepository.findAll().size();

        // Update the inlineStyleRanges using partial update
        InlineStyleRanges partialUpdatedInlineStyleRanges = new InlineStyleRanges();
        partialUpdatedInlineStyleRanges.setId(inlineStyleRanges.getId());

        partialUpdatedInlineStyleRanges.startPos(UPDATED_START_POS).stopPos(UPDATED_STOP_POS);

        restInlineStyleRangesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInlineStyleRanges.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInlineStyleRanges))
            )
            .andExpect(status().isOk());

        // Validate the InlineStyleRanges in the database
        List<InlineStyleRanges> inlineStyleRangesList = inlineStyleRangesRepository.findAll();
        assertThat(inlineStyleRangesList).hasSize(databaseSizeBeforeUpdate);
        InlineStyleRanges testInlineStyleRanges = inlineStyleRangesList.get(inlineStyleRangesList.size() - 1);
        assertThat(testInlineStyleRanges.getStartPos()).isEqualTo(UPDATED_START_POS);
        assertThat(testInlineStyleRanges.getStopPos()).isEqualTo(UPDATED_STOP_POS);
    }

    @Test
    @Transactional
    void patchNonExistingInlineStyleRanges() throws Exception {
        int databaseSizeBeforeUpdate = inlineStyleRangesRepository.findAll().size();
        inlineStyleRanges.setId(count.incrementAndGet());

        // Create the InlineStyleRanges
        InlineStyleRangesDTO inlineStyleRangesDTO = inlineStyleRangesMapper.toDto(inlineStyleRanges);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInlineStyleRangesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, inlineStyleRangesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(inlineStyleRangesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the InlineStyleRanges in the database
        List<InlineStyleRanges> inlineStyleRangesList = inlineStyleRangesRepository.findAll();
        assertThat(inlineStyleRangesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchInlineStyleRanges() throws Exception {
        int databaseSizeBeforeUpdate = inlineStyleRangesRepository.findAll().size();
        inlineStyleRanges.setId(count.incrementAndGet());

        // Create the InlineStyleRanges
        InlineStyleRangesDTO inlineStyleRangesDTO = inlineStyleRangesMapper.toDto(inlineStyleRanges);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInlineStyleRangesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(inlineStyleRangesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the InlineStyleRanges in the database
        List<InlineStyleRanges> inlineStyleRangesList = inlineStyleRangesRepository.findAll();
        assertThat(inlineStyleRangesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamInlineStyleRanges() throws Exception {
        int databaseSizeBeforeUpdate = inlineStyleRangesRepository.findAll().size();
        inlineStyleRanges.setId(count.incrementAndGet());

        // Create the InlineStyleRanges
        InlineStyleRangesDTO inlineStyleRangesDTO = inlineStyleRangesMapper.toDto(inlineStyleRanges);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInlineStyleRangesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(inlineStyleRangesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the InlineStyleRanges in the database
        List<InlineStyleRanges> inlineStyleRangesList = inlineStyleRangesRepository.findAll();
        assertThat(inlineStyleRangesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteInlineStyleRanges() throws Exception {
        // Initialize the database
        inlineStyleRangesRepository.saveAndFlush(inlineStyleRanges);

        int databaseSizeBeforeDelete = inlineStyleRangesRepository.findAll().size();

        // Delete the inlineStyleRanges
        restInlineStyleRangesMockMvc
            .perform(delete(ENTITY_API_URL_ID, inlineStyleRanges.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InlineStyleRanges> inlineStyleRangesList = inlineStyleRangesRepository.findAll();
        assertThat(inlineStyleRangesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
