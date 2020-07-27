package org.jhipster.dich.web.rest;

import org.jhipster.dich.DiChApp;
import org.jhipster.dich.domain.Collections;
import org.jhipster.dich.repository.CollectionsRepository;
import org.jhipster.dich.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static org.jhipster.dich.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CollectionsResource} REST controller.
 */
@SpringBootTest(classes = DiChApp.class)
public class CollectionsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private CollectionsRepository collectionsRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restCollectionsMockMvc;

    private Collections collections;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CollectionsResource collectionsResource = new CollectionsResource(collectionsRepository);
        this.restCollectionsMockMvc = MockMvcBuilders.standaloneSetup(collectionsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Collections createEntity(EntityManager em) {
        Collections collections = new Collections()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return collections;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Collections createUpdatedEntity(EntityManager em) {
        Collections collections = new Collections()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return collections;
    }

    @BeforeEach
    public void initTest() {
        collections = createEntity(em);
    }

    @Test
    @Transactional
    public void createCollections() throws Exception {
        int databaseSizeBeforeCreate = collectionsRepository.findAll().size();

        // Create the Collections
        restCollectionsMockMvc.perform(post("/api/collections")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(collections)))
            .andExpect(status().isCreated());

        // Validate the Collections in the database
        List<Collections> collectionsList = collectionsRepository.findAll();
        assertThat(collectionsList).hasSize(databaseSizeBeforeCreate + 1);
        Collections testCollections = collectionsList.get(collectionsList.size() - 1);
        assertThat(testCollections.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCollections.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createCollectionsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = collectionsRepository.findAll().size();

        // Create the Collections with an existing ID
        collections.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCollectionsMockMvc.perform(post("/api/collections")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(collections)))
            .andExpect(status().isBadRequest());

        // Validate the Collections in the database
        List<Collections> collectionsList = collectionsRepository.findAll();
        assertThat(collectionsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = collectionsRepository.findAll().size();
        // set the field null
        collections.setName(null);

        // Create the Collections, which fails.

        restCollectionsMockMvc.perform(post("/api/collections")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(collections)))
            .andExpect(status().isBadRequest());

        List<Collections> collectionsList = collectionsRepository.findAll();
        assertThat(collectionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCollections() throws Exception {
        // Initialize the database
        collectionsRepository.saveAndFlush(collections);

        // Get all the collectionsList
        restCollectionsMockMvc.perform(get("/api/collections?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(collections.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getCollections() throws Exception {
        // Initialize the database
        collectionsRepository.saveAndFlush(collections);

        // Get the collections
        restCollectionsMockMvc.perform(get("/api/collections/{id}", collections.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(collections.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    public void getNonExistingCollections() throws Exception {
        // Get the collections
        restCollectionsMockMvc.perform(get("/api/collections/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCollections() throws Exception {
        // Initialize the database
        collectionsRepository.saveAndFlush(collections);

        int databaseSizeBeforeUpdate = collectionsRepository.findAll().size();

        // Update the collections
        Collections updatedCollections = collectionsRepository.findById(collections.getId()).get();
        // Disconnect from session so that the updates on updatedCollections are not directly saved in db
        em.detach(updatedCollections);
        updatedCollections
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restCollectionsMockMvc.perform(put("/api/collections")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCollections)))
            .andExpect(status().isOk());

        // Validate the Collections in the database
        List<Collections> collectionsList = collectionsRepository.findAll();
        assertThat(collectionsList).hasSize(databaseSizeBeforeUpdate);
        Collections testCollections = collectionsList.get(collectionsList.size() - 1);
        assertThat(testCollections.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCollections.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingCollections() throws Exception {
        int databaseSizeBeforeUpdate = collectionsRepository.findAll().size();

        // Create the Collections

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCollectionsMockMvc.perform(put("/api/collections")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(collections)))
            .andExpect(status().isBadRequest());

        // Validate the Collections in the database
        List<Collections> collectionsList = collectionsRepository.findAll();
        assertThat(collectionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCollections() throws Exception {
        // Initialize the database
        collectionsRepository.saveAndFlush(collections);

        int databaseSizeBeforeDelete = collectionsRepository.findAll().size();

        // Delete the collections
        restCollectionsMockMvc.perform(delete("/api/collections/{id}", collections.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Collections> collectionsList = collectionsRepository.findAll();
        assertThat(collectionsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
