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
import org.jhipster.dich.domain.Collections;
import org.jhipster.dich.repository.CollectionsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CollectionsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CollectionsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/collections";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CollectionsRepository collectionsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCollectionsMockMvc;

    private Collections collections;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Collections createEntity(EntityManager em) {
        Collections collections = new Collections().name(DEFAULT_NAME).description(DEFAULT_DESCRIPTION);
        return collections;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Collections createUpdatedEntity(EntityManager em) {
        Collections collections = new Collections().name(UPDATED_NAME).description(UPDATED_DESCRIPTION);
        return collections;
    }

    @BeforeEach
    public void initTest() {
        collections = createEntity(em);
    }

    @Test
    @Transactional
    void createCollections() throws Exception {
        int databaseSizeBeforeCreate = collectionsRepository.findAll().size();
        // Create the Collections
        restCollectionsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(collections)))
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
    void createCollectionsWithExistingId() throws Exception {
        // Create the Collections with an existing ID
        collections.setId(1L);

        int databaseSizeBeforeCreate = collectionsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCollectionsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(collections)))
            .andExpect(status().isBadRequest());

        // Validate the Collections in the database
        List<Collections> collectionsList = collectionsRepository.findAll();
        assertThat(collectionsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = collectionsRepository.findAll().size();
        // set the field null
        collections.setName(null);

        // Create the Collections, which fails.

        restCollectionsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(collections)))
            .andExpect(status().isBadRequest());

        List<Collections> collectionsList = collectionsRepository.findAll();
        assertThat(collectionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCollections() throws Exception {
        // Initialize the database
        collectionsRepository.saveAndFlush(collections);

        // Get all the collectionsList
        restCollectionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(collections.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getCollections() throws Exception {
        // Initialize the database
        collectionsRepository.saveAndFlush(collections);

        // Get the collections
        restCollectionsMockMvc
            .perform(get(ENTITY_API_URL_ID, collections.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(collections.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingCollections() throws Exception {
        // Get the collections
        restCollectionsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCollections() throws Exception {
        // Initialize the database
        collectionsRepository.saveAndFlush(collections);

        int databaseSizeBeforeUpdate = collectionsRepository.findAll().size();

        // Update the collections
        Collections updatedCollections = collectionsRepository.findById(collections.getId()).get();
        // Disconnect from session so that the updates on updatedCollections are not directly saved in db
        em.detach(updatedCollections);
        updatedCollections.name(UPDATED_NAME).description(UPDATED_DESCRIPTION);

        restCollectionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCollections.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCollections))
            )
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
    void putNonExistingCollections() throws Exception {
        int databaseSizeBeforeUpdate = collectionsRepository.findAll().size();
        collections.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCollectionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, collections.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(collections))
            )
            .andExpect(status().isBadRequest());

        // Validate the Collections in the database
        List<Collections> collectionsList = collectionsRepository.findAll();
        assertThat(collectionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCollections() throws Exception {
        int databaseSizeBeforeUpdate = collectionsRepository.findAll().size();
        collections.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCollectionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(collections))
            )
            .andExpect(status().isBadRequest());

        // Validate the Collections in the database
        List<Collections> collectionsList = collectionsRepository.findAll();
        assertThat(collectionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCollections() throws Exception {
        int databaseSizeBeforeUpdate = collectionsRepository.findAll().size();
        collections.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCollectionsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(collections)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Collections in the database
        List<Collections> collectionsList = collectionsRepository.findAll();
        assertThat(collectionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCollectionsWithPatch() throws Exception {
        // Initialize the database
        collectionsRepository.saveAndFlush(collections);

        int databaseSizeBeforeUpdate = collectionsRepository.findAll().size();

        // Update the collections using partial update
        Collections partialUpdatedCollections = new Collections();
        partialUpdatedCollections.setId(collections.getId());

        restCollectionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCollections.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCollections))
            )
            .andExpect(status().isOk());

        // Validate the Collections in the database
        List<Collections> collectionsList = collectionsRepository.findAll();
        assertThat(collectionsList).hasSize(databaseSizeBeforeUpdate);
        Collections testCollections = collectionsList.get(collectionsList.size() - 1);
        assertThat(testCollections.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCollections.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateCollectionsWithPatch() throws Exception {
        // Initialize the database
        collectionsRepository.saveAndFlush(collections);

        int databaseSizeBeforeUpdate = collectionsRepository.findAll().size();

        // Update the collections using partial update
        Collections partialUpdatedCollections = new Collections();
        partialUpdatedCollections.setId(collections.getId());

        partialUpdatedCollections.name(UPDATED_NAME).description(UPDATED_DESCRIPTION);

        restCollectionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCollections.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCollections))
            )
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
    void patchNonExistingCollections() throws Exception {
        int databaseSizeBeforeUpdate = collectionsRepository.findAll().size();
        collections.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCollectionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, collections.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(collections))
            )
            .andExpect(status().isBadRequest());

        // Validate the Collections in the database
        List<Collections> collectionsList = collectionsRepository.findAll();
        assertThat(collectionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCollections() throws Exception {
        int databaseSizeBeforeUpdate = collectionsRepository.findAll().size();
        collections.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCollectionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(collections))
            )
            .andExpect(status().isBadRequest());

        // Validate the Collections in the database
        List<Collections> collectionsList = collectionsRepository.findAll();
        assertThat(collectionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCollections() throws Exception {
        int databaseSizeBeforeUpdate = collectionsRepository.findAll().size();
        collections.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCollectionsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(collections))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Collections in the database
        List<Collections> collectionsList = collectionsRepository.findAll();
        assertThat(collectionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCollections() throws Exception {
        // Initialize the database
        collectionsRepository.saveAndFlush(collections);

        int databaseSizeBeforeDelete = collectionsRepository.findAll().size();

        // Delete the collections
        restCollectionsMockMvc
            .perform(delete(ENTITY_API_URL_ID, collections.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Collections> collectionsList = collectionsRepository.findAll();
        assertThat(collectionsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
