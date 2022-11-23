package org.jhipster.dich.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.jhipster.dich.IntegrationTest;
import org.jhipster.dich.domain.MediaStructure;
import org.jhipster.dich.repository.MediaStructureRepository;
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
 * Integration tests for the {@link MediaStructureResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class MediaStructureResourceIT {

    private static final String DEFAULT_OBJ_NAME = "AAAAAAAAAA";
    private static final String UPDATED_OBJ_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_OBJ_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_OBJ_TYPE = "BBBBBBBBBB";

    private static final Long DEFAULT_PARENT_ID = 1L;
    private static final Long UPDATED_PARENT_ID = 2L;

    private static final String DEFAULT_TAG = "AAAAAAAAAA";
    private static final String UPDATED_TAG = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/media-structures";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MediaStructureRepository mediaStructureRepository;

    @Mock
    private MediaStructureRepository mediaStructureRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMediaStructureMockMvc;

    private MediaStructure mediaStructure;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MediaStructure createEntity(EntityManager em) {
        MediaStructure mediaStructure = new MediaStructure()
            .objName(DEFAULT_OBJ_NAME)
            .objType(DEFAULT_OBJ_TYPE)
            .parentId(DEFAULT_PARENT_ID)
            .tag(DEFAULT_TAG);
        return mediaStructure;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MediaStructure createUpdatedEntity(EntityManager em) {
        MediaStructure mediaStructure = new MediaStructure()
            .objName(UPDATED_OBJ_NAME)
            .objType(UPDATED_OBJ_TYPE)
            .parentId(UPDATED_PARENT_ID)
            .tag(UPDATED_TAG);
        return mediaStructure;
    }

    @BeforeEach
    public void initTest() {
        mediaStructure = createEntity(em);
    }

    @Test
    @Transactional
    void createMediaStructure() throws Exception {
        int databaseSizeBeforeCreate = mediaStructureRepository.findAll().size();
        // Create the MediaStructure
        restMediaStructureMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mediaStructure))
            )
            .andExpect(status().isCreated());

        // Validate the MediaStructure in the database
        List<MediaStructure> mediaStructureList = mediaStructureRepository.findAll();
        assertThat(mediaStructureList).hasSize(databaseSizeBeforeCreate + 1);
        MediaStructure testMediaStructure = mediaStructureList.get(mediaStructureList.size() - 1);
        assertThat(testMediaStructure.getObjName()).isEqualTo(DEFAULT_OBJ_NAME);
        assertThat(testMediaStructure.getObjType()).isEqualTo(DEFAULT_OBJ_TYPE);
        assertThat(testMediaStructure.getParentId()).isEqualTo(DEFAULT_PARENT_ID);
        assertThat(testMediaStructure.getTag()).isEqualTo(DEFAULT_TAG);
    }

    @Test
    @Transactional
    void createMediaStructureWithExistingId() throws Exception {
        // Create the MediaStructure with an existing ID
        mediaStructure.setId(1L);

        int databaseSizeBeforeCreate = mediaStructureRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMediaStructureMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mediaStructure))
            )
            .andExpect(status().isBadRequest());

        // Validate the MediaStructure in the database
        List<MediaStructure> mediaStructureList = mediaStructureRepository.findAll();
        assertThat(mediaStructureList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMediaStructures() throws Exception {
        // Initialize the database
        mediaStructureRepository.saveAndFlush(mediaStructure);

        // Get all the mediaStructureList
        restMediaStructureMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mediaStructure.getId().intValue())))
            .andExpect(jsonPath("$.[*].objName").value(hasItem(DEFAULT_OBJ_NAME)))
            .andExpect(jsonPath("$.[*].objType").value(hasItem(DEFAULT_OBJ_TYPE)))
            .andExpect(jsonPath("$.[*].parentId").value(hasItem(DEFAULT_PARENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].tag").value(hasItem(DEFAULT_TAG)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllMediaStructuresWithEagerRelationshipsIsEnabled() throws Exception {
        when(mediaStructureRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restMediaStructureMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(mediaStructureRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllMediaStructuresWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(mediaStructureRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restMediaStructureMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(mediaStructureRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getMediaStructure() throws Exception {
        // Initialize the database
        mediaStructureRepository.saveAndFlush(mediaStructure);

        // Get the mediaStructure
        restMediaStructureMockMvc
            .perform(get(ENTITY_API_URL_ID, mediaStructure.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mediaStructure.getId().intValue()))
            .andExpect(jsonPath("$.objName").value(DEFAULT_OBJ_NAME))
            .andExpect(jsonPath("$.objType").value(DEFAULT_OBJ_TYPE))
            .andExpect(jsonPath("$.parentId").value(DEFAULT_PARENT_ID.intValue()))
            .andExpect(jsonPath("$.tag").value(DEFAULT_TAG));
    }

    @Test
    @Transactional
    void getNonExistingMediaStructure() throws Exception {
        // Get the mediaStructure
        restMediaStructureMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMediaStructure() throws Exception {
        // Initialize the database
        mediaStructureRepository.saveAndFlush(mediaStructure);

        int databaseSizeBeforeUpdate = mediaStructureRepository.findAll().size();

        // Update the mediaStructure
        MediaStructure updatedMediaStructure = mediaStructureRepository.findById(mediaStructure.getId()).get();
        // Disconnect from session so that the updates on updatedMediaStructure are not directly saved in db
        em.detach(updatedMediaStructure);
        updatedMediaStructure.objName(UPDATED_OBJ_NAME).objType(UPDATED_OBJ_TYPE).parentId(UPDATED_PARENT_ID).tag(UPDATED_TAG);

        restMediaStructureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMediaStructure.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMediaStructure))
            )
            .andExpect(status().isOk());

        // Validate the MediaStructure in the database
        List<MediaStructure> mediaStructureList = mediaStructureRepository.findAll();
        assertThat(mediaStructureList).hasSize(databaseSizeBeforeUpdate);
        MediaStructure testMediaStructure = mediaStructureList.get(mediaStructureList.size() - 1);
        assertThat(testMediaStructure.getObjName()).isEqualTo(UPDATED_OBJ_NAME);
        assertThat(testMediaStructure.getObjType()).isEqualTo(UPDATED_OBJ_TYPE);
        assertThat(testMediaStructure.getParentId()).isEqualTo(UPDATED_PARENT_ID);
        assertThat(testMediaStructure.getTag()).isEqualTo(UPDATED_TAG);
    }

    @Test
    @Transactional
    void putNonExistingMediaStructure() throws Exception {
        int databaseSizeBeforeUpdate = mediaStructureRepository.findAll().size();
        mediaStructure.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMediaStructureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, mediaStructure.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mediaStructure))
            )
            .andExpect(status().isBadRequest());

        // Validate the MediaStructure in the database
        List<MediaStructure> mediaStructureList = mediaStructureRepository.findAll();
        assertThat(mediaStructureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMediaStructure() throws Exception {
        int databaseSizeBeforeUpdate = mediaStructureRepository.findAll().size();
        mediaStructure.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMediaStructureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mediaStructure))
            )
            .andExpect(status().isBadRequest());

        // Validate the MediaStructure in the database
        List<MediaStructure> mediaStructureList = mediaStructureRepository.findAll();
        assertThat(mediaStructureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMediaStructure() throws Exception {
        int databaseSizeBeforeUpdate = mediaStructureRepository.findAll().size();
        mediaStructure.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMediaStructureMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mediaStructure)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MediaStructure in the database
        List<MediaStructure> mediaStructureList = mediaStructureRepository.findAll();
        assertThat(mediaStructureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMediaStructureWithPatch() throws Exception {
        // Initialize the database
        mediaStructureRepository.saveAndFlush(mediaStructure);

        int databaseSizeBeforeUpdate = mediaStructureRepository.findAll().size();

        // Update the mediaStructure using partial update
        MediaStructure partialUpdatedMediaStructure = new MediaStructure();
        partialUpdatedMediaStructure.setId(mediaStructure.getId());

        partialUpdatedMediaStructure.objName(UPDATED_OBJ_NAME);

        restMediaStructureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMediaStructure.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMediaStructure))
            )
            .andExpect(status().isOk());

        // Validate the MediaStructure in the database
        List<MediaStructure> mediaStructureList = mediaStructureRepository.findAll();
        assertThat(mediaStructureList).hasSize(databaseSizeBeforeUpdate);
        MediaStructure testMediaStructure = mediaStructureList.get(mediaStructureList.size() - 1);
        assertThat(testMediaStructure.getObjName()).isEqualTo(UPDATED_OBJ_NAME);
        assertThat(testMediaStructure.getObjType()).isEqualTo(DEFAULT_OBJ_TYPE);
        assertThat(testMediaStructure.getParentId()).isEqualTo(DEFAULT_PARENT_ID);
        assertThat(testMediaStructure.getTag()).isEqualTo(DEFAULT_TAG);
    }

    @Test
    @Transactional
    void fullUpdateMediaStructureWithPatch() throws Exception {
        // Initialize the database
        mediaStructureRepository.saveAndFlush(mediaStructure);

        int databaseSizeBeforeUpdate = mediaStructureRepository.findAll().size();

        // Update the mediaStructure using partial update
        MediaStructure partialUpdatedMediaStructure = new MediaStructure();
        partialUpdatedMediaStructure.setId(mediaStructure.getId());

        partialUpdatedMediaStructure.objName(UPDATED_OBJ_NAME).objType(UPDATED_OBJ_TYPE).parentId(UPDATED_PARENT_ID).tag(UPDATED_TAG);

        restMediaStructureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMediaStructure.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMediaStructure))
            )
            .andExpect(status().isOk());

        // Validate the MediaStructure in the database
        List<MediaStructure> mediaStructureList = mediaStructureRepository.findAll();
        assertThat(mediaStructureList).hasSize(databaseSizeBeforeUpdate);
        MediaStructure testMediaStructure = mediaStructureList.get(mediaStructureList.size() - 1);
        assertThat(testMediaStructure.getObjName()).isEqualTo(UPDATED_OBJ_NAME);
        assertThat(testMediaStructure.getObjType()).isEqualTo(UPDATED_OBJ_TYPE);
        assertThat(testMediaStructure.getParentId()).isEqualTo(UPDATED_PARENT_ID);
        assertThat(testMediaStructure.getTag()).isEqualTo(UPDATED_TAG);
    }

    @Test
    @Transactional
    void patchNonExistingMediaStructure() throws Exception {
        int databaseSizeBeforeUpdate = mediaStructureRepository.findAll().size();
        mediaStructure.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMediaStructureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, mediaStructure.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mediaStructure))
            )
            .andExpect(status().isBadRequest());

        // Validate the MediaStructure in the database
        List<MediaStructure> mediaStructureList = mediaStructureRepository.findAll();
        assertThat(mediaStructureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMediaStructure() throws Exception {
        int databaseSizeBeforeUpdate = mediaStructureRepository.findAll().size();
        mediaStructure.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMediaStructureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mediaStructure))
            )
            .andExpect(status().isBadRequest());

        // Validate the MediaStructure in the database
        List<MediaStructure> mediaStructureList = mediaStructureRepository.findAll();
        assertThat(mediaStructureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMediaStructure() throws Exception {
        int databaseSizeBeforeUpdate = mediaStructureRepository.findAll().size();
        mediaStructure.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMediaStructureMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(mediaStructure))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MediaStructure in the database
        List<MediaStructure> mediaStructureList = mediaStructureRepository.findAll();
        assertThat(mediaStructureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMediaStructure() throws Exception {
        // Initialize the database
        mediaStructureRepository.saveAndFlush(mediaStructure);

        int databaseSizeBeforeDelete = mediaStructureRepository.findAll().size();

        // Delete the mediaStructure
        restMediaStructureMockMvc
            .perform(delete(ENTITY_API_URL_ID, mediaStructure.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MediaStructure> mediaStructureList = mediaStructureRepository.findAll();
        assertThat(mediaStructureList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
