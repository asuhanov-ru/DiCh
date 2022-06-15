package org.jhipster.dich.web.rest;

import org.jhipster.dich.DiChApp;
import org.jhipster.dich.domain.MediaStructure;
import org.jhipster.dich.repository.MediaStructureRepository;
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
 * Integration tests for the {@link MediaStructureResource} REST controller.
 */
@SpringBootTest(classes = DiChApp.class)
public class MediaStructureResourceIT {

    private static final String DEFAULT_OBJ_NAME = "AAAAAAAAAA";
    private static final String UPDATED_OBJ_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_OBJ_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_OBJ_TYPE = "BBBBBBBBBB";

    private static final Long DEFAULT_PARENT_ID = 1L;
    private static final Long UPDATED_PARENT_ID = 2L;

    private static final String DEFAULT_TAG = "AAAAAAAAAA";
    private static final String UPDATED_TAG = "BBBBBBBBBB";

    @Autowired
    private MediaStructureRepository mediaStructureRepository;

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

    private MockMvc restMediaStructureMockMvc;

    private MediaStructure mediaStructure;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MediaStructureResource mediaStructureResource = new MediaStructureResource(mediaStructureRepository);
        this.restMediaStructureMockMvc = MockMvcBuilders.standaloneSetup(mediaStructureResource)
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
    public void createMediaStructure() throws Exception {
        int databaseSizeBeforeCreate = mediaStructureRepository.findAll().size();

        // Create the MediaStructure
        restMediaStructureMockMvc.perform(post("/api/media-structures")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mediaStructure)))
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
    public void createMediaStructureWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mediaStructureRepository.findAll().size();

        // Create the MediaStructure with an existing ID
        mediaStructure.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMediaStructureMockMvc.perform(post("/api/media-structures")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mediaStructure)))
            .andExpect(status().isBadRequest());

        // Validate the MediaStructure in the database
        List<MediaStructure> mediaStructureList = mediaStructureRepository.findAll();
        assertThat(mediaStructureList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMediaStructures() throws Exception {
        // Initialize the database
        mediaStructureRepository.saveAndFlush(mediaStructure);

        // Get all the mediaStructureList
        restMediaStructureMockMvc.perform(get("/api/media-structures?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mediaStructure.getId().intValue())))
            .andExpect(jsonPath("$.[*].objName").value(hasItem(DEFAULT_OBJ_NAME)))
            .andExpect(jsonPath("$.[*].objType").value(hasItem(DEFAULT_OBJ_TYPE)))
            .andExpect(jsonPath("$.[*].parentId").value(hasItem(DEFAULT_PARENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].tag").value(hasItem(DEFAULT_TAG)));
    }
    
    @Test
    @Transactional
    public void getMediaStructure() throws Exception {
        // Initialize the database
        mediaStructureRepository.saveAndFlush(mediaStructure);

        // Get the mediaStructure
        restMediaStructureMockMvc.perform(get("/api/media-structures/{id}", mediaStructure.getId()))
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
    public void getNonExistingMediaStructure() throws Exception {
        // Get the mediaStructure
        restMediaStructureMockMvc.perform(get("/api/media-structures/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMediaStructure() throws Exception {
        // Initialize the database
        mediaStructureRepository.saveAndFlush(mediaStructure);

        int databaseSizeBeforeUpdate = mediaStructureRepository.findAll().size();

        // Update the mediaStructure
        MediaStructure updatedMediaStructure = mediaStructureRepository.findById(mediaStructure.getId()).get();
        // Disconnect from session so that the updates on updatedMediaStructure are not directly saved in db
        em.detach(updatedMediaStructure);
        updatedMediaStructure
            .objName(UPDATED_OBJ_NAME)
            .objType(UPDATED_OBJ_TYPE)
            .parentId(UPDATED_PARENT_ID)
            .tag(UPDATED_TAG);

        restMediaStructureMockMvc.perform(put("/api/media-structures")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedMediaStructure)))
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
    public void updateNonExistingMediaStructure() throws Exception {
        int databaseSizeBeforeUpdate = mediaStructureRepository.findAll().size();

        // Create the MediaStructure

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMediaStructureMockMvc.perform(put("/api/media-structures")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mediaStructure)))
            .andExpect(status().isBadRequest());

        // Validate the MediaStructure in the database
        List<MediaStructure> mediaStructureList = mediaStructureRepository.findAll();
        assertThat(mediaStructureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMediaStructure() throws Exception {
        // Initialize the database
        mediaStructureRepository.saveAndFlush(mediaStructure);

        int databaseSizeBeforeDelete = mediaStructureRepository.findAll().size();

        // Delete the mediaStructure
        restMediaStructureMockMvc.perform(delete("/api/media-structures/{id}", mediaStructure.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MediaStructure> mediaStructureList = mediaStructureRepository.findAll();
        assertThat(mediaStructureList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
