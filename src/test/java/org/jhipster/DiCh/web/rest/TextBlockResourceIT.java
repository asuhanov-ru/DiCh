package org.jhipster.dich.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.jhipster.dich.IntegrationTest;
import org.jhipster.dich.domain.Media;
import org.jhipster.dich.domain.TextBlock;
import org.jhipster.dich.repository.TextBlockRepository;
import org.jhipster.dich.service.TextBlockService;
import org.jhipster.dich.service.criteria.TextBlockCriteria;
import org.jhipster.dich.service.dto.TextBlockDTO;
import org.jhipster.dich.service.mapper.TextBlockMapper;
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

/**
 * Integration tests for the {@link TextBlockResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class TextBlockResourceIT {

    private static final Integer DEFAULT_PAGE_NUMBER = 1;
    private static final Integer UPDATED_PAGE_NUMBER = 2;
    private static final Integer SMALLER_PAGE_NUMBER = 1 - 1;

    private static final Integer DEFAULT_BLOCK_INDEX = 1;
    private static final Integer UPDATED_BLOCK_INDEX = 2;
    private static final Integer SMALLER_BLOCK_INDEX = 1 - 1;

    private static final UUID DEFAULT_BLOCK_UUID = UUID.randomUUID();
    private static final UUID UPDATED_BLOCK_UUID = UUID.randomUUID();

    private static final String ENTITY_API_URL = "/api/text-blocks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TextBlockRepository textBlockRepository;

    @Mock
    private TextBlockRepository textBlockRepositoryMock;

    @Autowired
    private TextBlockMapper textBlockMapper;

    @Mock
    private TextBlockService textBlockServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTextBlockMockMvc;

    private TextBlock textBlock;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TextBlock createEntity(EntityManager em) {
        TextBlock textBlock = new TextBlock().pageNumber(DEFAULT_PAGE_NUMBER).blockIndex(DEFAULT_BLOCK_INDEX).blockUUID(DEFAULT_BLOCK_UUID);
        return textBlock;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TextBlock createUpdatedEntity(EntityManager em) {
        TextBlock textBlock = new TextBlock().pageNumber(UPDATED_PAGE_NUMBER).blockIndex(UPDATED_BLOCK_INDEX).blockUUID(UPDATED_BLOCK_UUID);
        return textBlock;
    }

    @BeforeEach
    public void initTest() {
        textBlock = createEntity(em);
    }

    @Test
    @Transactional
    void createTextBlock() throws Exception {
        int databaseSizeBeforeCreate = textBlockRepository.findAll().size();
        // Create the TextBlock
        TextBlockDTO textBlockDTO = textBlockMapper.toDto(textBlock);
        restTextBlockMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(textBlockDTO)))
            .andExpect(status().isCreated());

        // Validate the TextBlock in the database
        List<TextBlock> textBlockList = textBlockRepository.findAll();
        assertThat(textBlockList).hasSize(databaseSizeBeforeCreate + 1);
        TextBlock testTextBlock = textBlockList.get(textBlockList.size() - 1);
        assertThat(testTextBlock.getPageNumber()).isEqualTo(DEFAULT_PAGE_NUMBER);
        assertThat(testTextBlock.getBlockIndex()).isEqualTo(DEFAULT_BLOCK_INDEX);
        assertThat(testTextBlock.getBlockUUID()).isEqualTo(DEFAULT_BLOCK_UUID);
    }

    @Test
    @Transactional
    void createTextBlockWithExistingId() throws Exception {
        // Create the TextBlock with an existing ID
        textBlock.setId(1L);
        TextBlockDTO textBlockDTO = textBlockMapper.toDto(textBlock);

        int databaseSizeBeforeCreate = textBlockRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTextBlockMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(textBlockDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TextBlock in the database
        List<TextBlock> textBlockList = textBlockRepository.findAll();
        assertThat(textBlockList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTextBlocks() throws Exception {
        // Initialize the database
        textBlockRepository.saveAndFlush(textBlock);

        // Get all the textBlockList
        restTextBlockMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(textBlock.getId().intValue())))
            .andExpect(jsonPath("$.[*].pageNumber").value(hasItem(DEFAULT_PAGE_NUMBER)))
            .andExpect(jsonPath("$.[*].blockIndex").value(hasItem(DEFAULT_BLOCK_INDEX)))
            .andExpect(jsonPath("$.[*].blockUUID").value(hasItem(DEFAULT_BLOCK_UUID.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTextBlocksWithEagerRelationshipsIsEnabled() throws Exception {
        when(textBlockServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTextBlockMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(textBlockServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTextBlocksWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(textBlockServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTextBlockMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(textBlockServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getTextBlock() throws Exception {
        // Initialize the database
        textBlockRepository.saveAndFlush(textBlock);

        // Get the textBlock
        restTextBlockMockMvc
            .perform(get(ENTITY_API_URL_ID, textBlock.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(textBlock.getId().intValue()))
            .andExpect(jsonPath("$.pageNumber").value(DEFAULT_PAGE_NUMBER))
            .andExpect(jsonPath("$.blockIndex").value(DEFAULT_BLOCK_INDEX))
            .andExpect(jsonPath("$.blockUUID").value(DEFAULT_BLOCK_UUID.toString()));
    }

    @Test
    @Transactional
    void getTextBlocksByIdFiltering() throws Exception {
        // Initialize the database
        textBlockRepository.saveAndFlush(textBlock);

        Long id = textBlock.getId();

        defaultTextBlockShouldBeFound("id.equals=" + id);
        defaultTextBlockShouldNotBeFound("id.notEquals=" + id);

        defaultTextBlockShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTextBlockShouldNotBeFound("id.greaterThan=" + id);

        defaultTextBlockShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTextBlockShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTextBlocksByPageNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        textBlockRepository.saveAndFlush(textBlock);

        // Get all the textBlockList where pageNumber equals to DEFAULT_PAGE_NUMBER
        defaultTextBlockShouldBeFound("pageNumber.equals=" + DEFAULT_PAGE_NUMBER);

        // Get all the textBlockList where pageNumber equals to UPDATED_PAGE_NUMBER
        defaultTextBlockShouldNotBeFound("pageNumber.equals=" + UPDATED_PAGE_NUMBER);
    }

    @Test
    @Transactional
    void getAllTextBlocksByPageNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        textBlockRepository.saveAndFlush(textBlock);

        // Get all the textBlockList where pageNumber not equals to DEFAULT_PAGE_NUMBER
        defaultTextBlockShouldNotBeFound("pageNumber.notEquals=" + DEFAULT_PAGE_NUMBER);

        // Get all the textBlockList where pageNumber not equals to UPDATED_PAGE_NUMBER
        defaultTextBlockShouldBeFound("pageNumber.notEquals=" + UPDATED_PAGE_NUMBER);
    }

    @Test
    @Transactional
    void getAllTextBlocksByPageNumberIsInShouldWork() throws Exception {
        // Initialize the database
        textBlockRepository.saveAndFlush(textBlock);

        // Get all the textBlockList where pageNumber in DEFAULT_PAGE_NUMBER or UPDATED_PAGE_NUMBER
        defaultTextBlockShouldBeFound("pageNumber.in=" + DEFAULT_PAGE_NUMBER + "," + UPDATED_PAGE_NUMBER);

        // Get all the textBlockList where pageNumber equals to UPDATED_PAGE_NUMBER
        defaultTextBlockShouldNotBeFound("pageNumber.in=" + UPDATED_PAGE_NUMBER);
    }

    @Test
    @Transactional
    void getAllTextBlocksByPageNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        textBlockRepository.saveAndFlush(textBlock);

        // Get all the textBlockList where pageNumber is not null
        defaultTextBlockShouldBeFound("pageNumber.specified=true");

        // Get all the textBlockList where pageNumber is null
        defaultTextBlockShouldNotBeFound("pageNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllTextBlocksByPageNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        textBlockRepository.saveAndFlush(textBlock);

        // Get all the textBlockList where pageNumber is greater than or equal to DEFAULT_PAGE_NUMBER
        defaultTextBlockShouldBeFound("pageNumber.greaterThanOrEqual=" + DEFAULT_PAGE_NUMBER);

        // Get all the textBlockList where pageNumber is greater than or equal to UPDATED_PAGE_NUMBER
        defaultTextBlockShouldNotBeFound("pageNumber.greaterThanOrEqual=" + UPDATED_PAGE_NUMBER);
    }

    @Test
    @Transactional
    void getAllTextBlocksByPageNumberIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        textBlockRepository.saveAndFlush(textBlock);

        // Get all the textBlockList where pageNumber is less than or equal to DEFAULT_PAGE_NUMBER
        defaultTextBlockShouldBeFound("pageNumber.lessThanOrEqual=" + DEFAULT_PAGE_NUMBER);

        // Get all the textBlockList where pageNumber is less than or equal to SMALLER_PAGE_NUMBER
        defaultTextBlockShouldNotBeFound("pageNumber.lessThanOrEqual=" + SMALLER_PAGE_NUMBER);
    }

    @Test
    @Transactional
    void getAllTextBlocksByPageNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        textBlockRepository.saveAndFlush(textBlock);

        // Get all the textBlockList where pageNumber is less than DEFAULT_PAGE_NUMBER
        defaultTextBlockShouldNotBeFound("pageNumber.lessThan=" + DEFAULT_PAGE_NUMBER);

        // Get all the textBlockList where pageNumber is less than UPDATED_PAGE_NUMBER
        defaultTextBlockShouldBeFound("pageNumber.lessThan=" + UPDATED_PAGE_NUMBER);
    }

    @Test
    @Transactional
    void getAllTextBlocksByPageNumberIsGreaterThanSomething() throws Exception {
        // Initialize the database
        textBlockRepository.saveAndFlush(textBlock);

        // Get all the textBlockList where pageNumber is greater than DEFAULT_PAGE_NUMBER
        defaultTextBlockShouldNotBeFound("pageNumber.greaterThan=" + DEFAULT_PAGE_NUMBER);

        // Get all the textBlockList where pageNumber is greater than SMALLER_PAGE_NUMBER
        defaultTextBlockShouldBeFound("pageNumber.greaterThan=" + SMALLER_PAGE_NUMBER);
    }

    @Test
    @Transactional
    void getAllTextBlocksByBlockIndexIsEqualToSomething() throws Exception {
        // Initialize the database
        textBlockRepository.saveAndFlush(textBlock);

        // Get all the textBlockList where blockIndex equals to DEFAULT_BLOCK_INDEX
        defaultTextBlockShouldBeFound("blockIndex.equals=" + DEFAULT_BLOCK_INDEX);

        // Get all the textBlockList where blockIndex equals to UPDATED_BLOCK_INDEX
        defaultTextBlockShouldNotBeFound("blockIndex.equals=" + UPDATED_BLOCK_INDEX);
    }

    @Test
    @Transactional
    void getAllTextBlocksByBlockIndexIsNotEqualToSomething() throws Exception {
        // Initialize the database
        textBlockRepository.saveAndFlush(textBlock);

        // Get all the textBlockList where blockIndex not equals to DEFAULT_BLOCK_INDEX
        defaultTextBlockShouldNotBeFound("blockIndex.notEquals=" + DEFAULT_BLOCK_INDEX);

        // Get all the textBlockList where blockIndex not equals to UPDATED_BLOCK_INDEX
        defaultTextBlockShouldBeFound("blockIndex.notEquals=" + UPDATED_BLOCK_INDEX);
    }

    @Test
    @Transactional
    void getAllTextBlocksByBlockIndexIsInShouldWork() throws Exception {
        // Initialize the database
        textBlockRepository.saveAndFlush(textBlock);

        // Get all the textBlockList where blockIndex in DEFAULT_BLOCK_INDEX or UPDATED_BLOCK_INDEX
        defaultTextBlockShouldBeFound("blockIndex.in=" + DEFAULT_BLOCK_INDEX + "," + UPDATED_BLOCK_INDEX);

        // Get all the textBlockList where blockIndex equals to UPDATED_BLOCK_INDEX
        defaultTextBlockShouldNotBeFound("blockIndex.in=" + UPDATED_BLOCK_INDEX);
    }

    @Test
    @Transactional
    void getAllTextBlocksByBlockIndexIsNullOrNotNull() throws Exception {
        // Initialize the database
        textBlockRepository.saveAndFlush(textBlock);

        // Get all the textBlockList where blockIndex is not null
        defaultTextBlockShouldBeFound("blockIndex.specified=true");

        // Get all the textBlockList where blockIndex is null
        defaultTextBlockShouldNotBeFound("blockIndex.specified=false");
    }

    @Test
    @Transactional
    void getAllTextBlocksByBlockIndexIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        textBlockRepository.saveAndFlush(textBlock);

        // Get all the textBlockList where blockIndex is greater than or equal to DEFAULT_BLOCK_INDEX
        defaultTextBlockShouldBeFound("blockIndex.greaterThanOrEqual=" + DEFAULT_BLOCK_INDEX);

        // Get all the textBlockList where blockIndex is greater than or equal to UPDATED_BLOCK_INDEX
        defaultTextBlockShouldNotBeFound("blockIndex.greaterThanOrEqual=" + UPDATED_BLOCK_INDEX);
    }

    @Test
    @Transactional
    void getAllTextBlocksByBlockIndexIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        textBlockRepository.saveAndFlush(textBlock);

        // Get all the textBlockList where blockIndex is less than or equal to DEFAULT_BLOCK_INDEX
        defaultTextBlockShouldBeFound("blockIndex.lessThanOrEqual=" + DEFAULT_BLOCK_INDEX);

        // Get all the textBlockList where blockIndex is less than or equal to SMALLER_BLOCK_INDEX
        defaultTextBlockShouldNotBeFound("blockIndex.lessThanOrEqual=" + SMALLER_BLOCK_INDEX);
    }

    @Test
    @Transactional
    void getAllTextBlocksByBlockIndexIsLessThanSomething() throws Exception {
        // Initialize the database
        textBlockRepository.saveAndFlush(textBlock);

        // Get all the textBlockList where blockIndex is less than DEFAULT_BLOCK_INDEX
        defaultTextBlockShouldNotBeFound("blockIndex.lessThan=" + DEFAULT_BLOCK_INDEX);

        // Get all the textBlockList where blockIndex is less than UPDATED_BLOCK_INDEX
        defaultTextBlockShouldBeFound("blockIndex.lessThan=" + UPDATED_BLOCK_INDEX);
    }

    @Test
    @Transactional
    void getAllTextBlocksByBlockIndexIsGreaterThanSomething() throws Exception {
        // Initialize the database
        textBlockRepository.saveAndFlush(textBlock);

        // Get all the textBlockList where blockIndex is greater than DEFAULT_BLOCK_INDEX
        defaultTextBlockShouldNotBeFound("blockIndex.greaterThan=" + DEFAULT_BLOCK_INDEX);

        // Get all the textBlockList where blockIndex is greater than SMALLER_BLOCK_INDEX
        defaultTextBlockShouldBeFound("blockIndex.greaterThan=" + SMALLER_BLOCK_INDEX);
    }

    @Test
    @Transactional
    void getAllTextBlocksByBlockUUIDIsEqualToSomething() throws Exception {
        // Initialize the database
        textBlockRepository.saveAndFlush(textBlock);

        // Get all the textBlockList where blockUUID equals to DEFAULT_BLOCK_UUID
        defaultTextBlockShouldBeFound("blockUUID.equals=" + DEFAULT_BLOCK_UUID);

        // Get all the textBlockList where blockUUID equals to UPDATED_BLOCK_UUID
        defaultTextBlockShouldNotBeFound("blockUUID.equals=" + UPDATED_BLOCK_UUID);
    }

    @Test
    @Transactional
    void getAllTextBlocksByBlockUUIDIsNotEqualToSomething() throws Exception {
        // Initialize the database
        textBlockRepository.saveAndFlush(textBlock);

        // Get all the textBlockList where blockUUID not equals to DEFAULT_BLOCK_UUID
        defaultTextBlockShouldNotBeFound("blockUUID.notEquals=" + DEFAULT_BLOCK_UUID);

        // Get all the textBlockList where blockUUID not equals to UPDATED_BLOCK_UUID
        defaultTextBlockShouldBeFound("blockUUID.notEquals=" + UPDATED_BLOCK_UUID);
    }

    @Test
    @Transactional
    void getAllTextBlocksByBlockUUIDIsInShouldWork() throws Exception {
        // Initialize the database
        textBlockRepository.saveAndFlush(textBlock);

        // Get all the textBlockList where blockUUID in DEFAULT_BLOCK_UUID or UPDATED_BLOCK_UUID
        defaultTextBlockShouldBeFound("blockUUID.in=" + DEFAULT_BLOCK_UUID + "," + UPDATED_BLOCK_UUID);

        // Get all the textBlockList where blockUUID equals to UPDATED_BLOCK_UUID
        defaultTextBlockShouldNotBeFound("blockUUID.in=" + UPDATED_BLOCK_UUID);
    }

    @Test
    @Transactional
    void getAllTextBlocksByBlockUUIDIsNullOrNotNull() throws Exception {
        // Initialize the database
        textBlockRepository.saveAndFlush(textBlock);

        // Get all the textBlockList where blockUUID is not null
        defaultTextBlockShouldBeFound("blockUUID.specified=true");

        // Get all the textBlockList where blockUUID is null
        defaultTextBlockShouldNotBeFound("blockUUID.specified=false");
    }

    @Test
    @Transactional
    void getAllTextBlocksByMediaIsEqualToSomething() throws Exception {
        // Initialize the database
        textBlockRepository.saveAndFlush(textBlock);
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
        textBlock.setMedia(media);
        textBlockRepository.saveAndFlush(textBlock);
        Long mediaId = media.getId();

        // Get all the textBlockList where media equals to mediaId
        defaultTextBlockShouldBeFound("mediaId.equals=" + mediaId);

        // Get all the textBlockList where media equals to (mediaId + 1)
        defaultTextBlockShouldNotBeFound("mediaId.equals=" + (mediaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTextBlockShouldBeFound(String filter) throws Exception {
        restTextBlockMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(textBlock.getId().intValue())))
            .andExpect(jsonPath("$.[*].pageNumber").value(hasItem(DEFAULT_PAGE_NUMBER)))
            .andExpect(jsonPath("$.[*].blockIndex").value(hasItem(DEFAULT_BLOCK_INDEX)))
            .andExpect(jsonPath("$.[*].blockUUID").value(hasItem(DEFAULT_BLOCK_UUID.toString())));

        // Check, that the count call also returns 1
        restTextBlockMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTextBlockShouldNotBeFound(String filter) throws Exception {
        restTextBlockMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTextBlockMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTextBlock() throws Exception {
        // Get the textBlock
        restTextBlockMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTextBlock() throws Exception {
        // Initialize the database
        textBlockRepository.saveAndFlush(textBlock);

        int databaseSizeBeforeUpdate = textBlockRepository.findAll().size();

        // Update the textBlock
        TextBlock updatedTextBlock = textBlockRepository.findById(textBlock.getId()).get();
        // Disconnect from session so that the updates on updatedTextBlock are not directly saved in db
        em.detach(updatedTextBlock);
        updatedTextBlock.pageNumber(UPDATED_PAGE_NUMBER).blockIndex(UPDATED_BLOCK_INDEX).blockUUID(UPDATED_BLOCK_UUID);
        TextBlockDTO textBlockDTO = textBlockMapper.toDto(updatedTextBlock);

        restTextBlockMockMvc
            .perform(
                put(ENTITY_API_URL_ID, textBlockDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(textBlockDTO))
            )
            .andExpect(status().isOk());

        // Validate the TextBlock in the database
        List<TextBlock> textBlockList = textBlockRepository.findAll();
        assertThat(textBlockList).hasSize(databaseSizeBeforeUpdate);
        TextBlock testTextBlock = textBlockList.get(textBlockList.size() - 1);
        assertThat(testTextBlock.getPageNumber()).isEqualTo(UPDATED_PAGE_NUMBER);
        assertThat(testTextBlock.getBlockIndex()).isEqualTo(UPDATED_BLOCK_INDEX);
        assertThat(testTextBlock.getBlockUUID()).isEqualTo(UPDATED_BLOCK_UUID);
    }

    @Test
    @Transactional
    void putNonExistingTextBlock() throws Exception {
        int databaseSizeBeforeUpdate = textBlockRepository.findAll().size();
        textBlock.setId(count.incrementAndGet());

        // Create the TextBlock
        TextBlockDTO textBlockDTO = textBlockMapper.toDto(textBlock);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTextBlockMockMvc
            .perform(
                put(ENTITY_API_URL_ID, textBlockDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(textBlockDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TextBlock in the database
        List<TextBlock> textBlockList = textBlockRepository.findAll();
        assertThat(textBlockList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTextBlock() throws Exception {
        int databaseSizeBeforeUpdate = textBlockRepository.findAll().size();
        textBlock.setId(count.incrementAndGet());

        // Create the TextBlock
        TextBlockDTO textBlockDTO = textBlockMapper.toDto(textBlock);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTextBlockMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(textBlockDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TextBlock in the database
        List<TextBlock> textBlockList = textBlockRepository.findAll();
        assertThat(textBlockList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTextBlock() throws Exception {
        int databaseSizeBeforeUpdate = textBlockRepository.findAll().size();
        textBlock.setId(count.incrementAndGet());

        // Create the TextBlock
        TextBlockDTO textBlockDTO = textBlockMapper.toDto(textBlock);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTextBlockMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(textBlockDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TextBlock in the database
        List<TextBlock> textBlockList = textBlockRepository.findAll();
        assertThat(textBlockList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTextBlockWithPatch() throws Exception {
        // Initialize the database
        textBlockRepository.saveAndFlush(textBlock);

        int databaseSizeBeforeUpdate = textBlockRepository.findAll().size();

        // Update the textBlock using partial update
        TextBlock partialUpdatedTextBlock = new TextBlock();
        partialUpdatedTextBlock.setId(textBlock.getId());

        partialUpdatedTextBlock.pageNumber(UPDATED_PAGE_NUMBER).blockIndex(UPDATED_BLOCK_INDEX);

        restTextBlockMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTextBlock.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTextBlock))
            )
            .andExpect(status().isOk());

        // Validate the TextBlock in the database
        List<TextBlock> textBlockList = textBlockRepository.findAll();
        assertThat(textBlockList).hasSize(databaseSizeBeforeUpdate);
        TextBlock testTextBlock = textBlockList.get(textBlockList.size() - 1);
        assertThat(testTextBlock.getPageNumber()).isEqualTo(UPDATED_PAGE_NUMBER);
        assertThat(testTextBlock.getBlockIndex()).isEqualTo(UPDATED_BLOCK_INDEX);
        assertThat(testTextBlock.getBlockUUID()).isEqualTo(DEFAULT_BLOCK_UUID);
    }

    @Test
    @Transactional
    void fullUpdateTextBlockWithPatch() throws Exception {
        // Initialize the database
        textBlockRepository.saveAndFlush(textBlock);

        int databaseSizeBeforeUpdate = textBlockRepository.findAll().size();

        // Update the textBlock using partial update
        TextBlock partialUpdatedTextBlock = new TextBlock();
        partialUpdatedTextBlock.setId(textBlock.getId());

        partialUpdatedTextBlock.pageNumber(UPDATED_PAGE_NUMBER).blockIndex(UPDATED_BLOCK_INDEX).blockUUID(UPDATED_BLOCK_UUID);

        restTextBlockMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTextBlock.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTextBlock))
            )
            .andExpect(status().isOk());

        // Validate the TextBlock in the database
        List<TextBlock> textBlockList = textBlockRepository.findAll();
        assertThat(textBlockList).hasSize(databaseSizeBeforeUpdate);
        TextBlock testTextBlock = textBlockList.get(textBlockList.size() - 1);
        assertThat(testTextBlock.getPageNumber()).isEqualTo(UPDATED_PAGE_NUMBER);
        assertThat(testTextBlock.getBlockIndex()).isEqualTo(UPDATED_BLOCK_INDEX);
        assertThat(testTextBlock.getBlockUUID()).isEqualTo(UPDATED_BLOCK_UUID);
    }

    @Test
    @Transactional
    void patchNonExistingTextBlock() throws Exception {
        int databaseSizeBeforeUpdate = textBlockRepository.findAll().size();
        textBlock.setId(count.incrementAndGet());

        // Create the TextBlock
        TextBlockDTO textBlockDTO = textBlockMapper.toDto(textBlock);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTextBlockMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, textBlockDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(textBlockDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TextBlock in the database
        List<TextBlock> textBlockList = textBlockRepository.findAll();
        assertThat(textBlockList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTextBlock() throws Exception {
        int databaseSizeBeforeUpdate = textBlockRepository.findAll().size();
        textBlock.setId(count.incrementAndGet());

        // Create the TextBlock
        TextBlockDTO textBlockDTO = textBlockMapper.toDto(textBlock);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTextBlockMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(textBlockDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TextBlock in the database
        List<TextBlock> textBlockList = textBlockRepository.findAll();
        assertThat(textBlockList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTextBlock() throws Exception {
        int databaseSizeBeforeUpdate = textBlockRepository.findAll().size();
        textBlock.setId(count.incrementAndGet());

        // Create the TextBlock
        TextBlockDTO textBlockDTO = textBlockMapper.toDto(textBlock);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTextBlockMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(textBlockDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TextBlock in the database
        List<TextBlock> textBlockList = textBlockRepository.findAll();
        assertThat(textBlockList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTextBlock() throws Exception {
        // Initialize the database
        textBlockRepository.saveAndFlush(textBlock);

        int databaseSizeBeforeDelete = textBlockRepository.findAll().size();

        // Delete the textBlock
        restTextBlockMockMvc
            .perform(delete(ENTITY_API_URL_ID, textBlock.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TextBlock> textBlockList = textBlockRepository.findAll();
        assertThat(textBlockList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
