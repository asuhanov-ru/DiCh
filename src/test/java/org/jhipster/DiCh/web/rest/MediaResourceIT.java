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
import org.jhipster.dich.domain.Media;
import org.jhipster.dich.repository.MediaRepository;
import org.jhipster.dich.service.MediaService;
import org.jhipster.dich.service.dto.MediaDTO;
import org.jhipster.dich.service.mapper.MediaMapper;
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
 * Integration tests for the {@link MediaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class MediaResourceIT {

    private static final String DEFAULT_FILE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FILE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FILE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_FILE_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_FILE_DESC = "AAAAAAAAAA";
    private static final String UPDATED_FILE_DESC = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/media";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MediaRepository mediaRepository;

    @Mock
    private MediaRepository mediaRepositoryMock;

    @Autowired
    private MediaMapper mediaMapper;

    @Mock
    private MediaService mediaServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMediaMockMvc;

    private Media media;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Media createEntity(EntityManager em) {
        Media media = new Media().fileName(DEFAULT_FILE_NAME).fileType(DEFAULT_FILE_TYPE).fileDesc(DEFAULT_FILE_DESC);
        return media;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Media createUpdatedEntity(EntityManager em) {
        Media media = new Media().fileName(UPDATED_FILE_NAME).fileType(UPDATED_FILE_TYPE).fileDesc(UPDATED_FILE_DESC);
        return media;
    }

    @BeforeEach
    public void initTest() {
        media = createEntity(em);
    }

    @Test
    @Transactional
    void createMedia() throws Exception {
        int databaseSizeBeforeCreate = mediaRepository.findAll().size();
        // Create the Media
        MediaDTO mediaDTO = mediaMapper.toDto(media);
        restMediaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mediaDTO)))
            .andExpect(status().isCreated());

        // Validate the Media in the database
        List<Media> mediaList = mediaRepository.findAll();
        assertThat(mediaList).hasSize(databaseSizeBeforeCreate + 1);
        Media testMedia = mediaList.get(mediaList.size() - 1);
        assertThat(testMedia.getFileName()).isEqualTo(DEFAULT_FILE_NAME);
        assertThat(testMedia.getFileType()).isEqualTo(DEFAULT_FILE_TYPE);
        assertThat(testMedia.getFileDesc()).isEqualTo(DEFAULT_FILE_DESC);
    }

    @Test
    @Transactional
    void createMediaWithExistingId() throws Exception {
        // Create the Media with an existing ID
        media.setId(1L);
        MediaDTO mediaDTO = mediaMapper.toDto(media);

        int databaseSizeBeforeCreate = mediaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMediaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mediaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Media in the database
        List<Media> mediaList = mediaRepository.findAll();
        assertThat(mediaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFileNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = mediaRepository.findAll().size();
        // set the field null
        media.setFileName(null);

        // Create the Media, which fails.
        MediaDTO mediaDTO = mediaMapper.toDto(media);

        restMediaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mediaDTO)))
            .andExpect(status().isBadRequest());

        List<Media> mediaList = mediaRepository.findAll();
        assertThat(mediaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFileTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = mediaRepository.findAll().size();
        // set the field null
        media.setFileType(null);

        // Create the Media, which fails.
        MediaDTO mediaDTO = mediaMapper.toDto(media);

        restMediaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mediaDTO)))
            .andExpect(status().isBadRequest());

        List<Media> mediaList = mediaRepository.findAll();
        assertThat(mediaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMedia() throws Exception {
        // Initialize the database
        mediaRepository.saveAndFlush(media);

        // Get all the mediaList
        restMediaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(media.getId().intValue())))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME)))
            .andExpect(jsonPath("$.[*].fileType").value(hasItem(DEFAULT_FILE_TYPE)))
            .andExpect(jsonPath("$.[*].fileDesc").value(hasItem(DEFAULT_FILE_DESC)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllMediaWithEagerRelationshipsIsEnabled() throws Exception {
        when(mediaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restMediaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(mediaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllMediaWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(mediaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restMediaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(mediaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getMedia() throws Exception {
        // Initialize the database
        mediaRepository.saveAndFlush(media);

        // Get the media
        restMediaMockMvc
            .perform(get(ENTITY_API_URL_ID, media.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(media.getId().intValue()))
            .andExpect(jsonPath("$.fileName").value(DEFAULT_FILE_NAME))
            .andExpect(jsonPath("$.fileType").value(DEFAULT_FILE_TYPE))
            .andExpect(jsonPath("$.fileDesc").value(DEFAULT_FILE_DESC));
    }

    @Test
    @Transactional
    void getNonExistingMedia() throws Exception {
        // Get the media
        restMediaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMedia() throws Exception {
        // Initialize the database
        mediaRepository.saveAndFlush(media);

        int databaseSizeBeforeUpdate = mediaRepository.findAll().size();

        // Update the media
        Media updatedMedia = mediaRepository.findById(media.getId()).get();
        // Disconnect from session so that the updates on updatedMedia are not directly saved in db
        em.detach(updatedMedia);
        updatedMedia.fileName(UPDATED_FILE_NAME).fileType(UPDATED_FILE_TYPE).fileDesc(UPDATED_FILE_DESC);
        MediaDTO mediaDTO = mediaMapper.toDto(updatedMedia);

        restMediaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, mediaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mediaDTO))
            )
            .andExpect(status().isOk());

        // Validate the Media in the database
        List<Media> mediaList = mediaRepository.findAll();
        assertThat(mediaList).hasSize(databaseSizeBeforeUpdate);
        Media testMedia = mediaList.get(mediaList.size() - 1);
        assertThat(testMedia.getFileName()).isEqualTo(UPDATED_FILE_NAME);
        assertThat(testMedia.getFileType()).isEqualTo(UPDATED_FILE_TYPE);
        assertThat(testMedia.getFileDesc()).isEqualTo(UPDATED_FILE_DESC);
    }

    @Test
    @Transactional
    void putNonExistingMedia() throws Exception {
        int databaseSizeBeforeUpdate = mediaRepository.findAll().size();
        media.setId(count.incrementAndGet());

        // Create the Media
        MediaDTO mediaDTO = mediaMapper.toDto(media);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMediaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, mediaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mediaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Media in the database
        List<Media> mediaList = mediaRepository.findAll();
        assertThat(mediaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMedia() throws Exception {
        int databaseSizeBeforeUpdate = mediaRepository.findAll().size();
        media.setId(count.incrementAndGet());

        // Create the Media
        MediaDTO mediaDTO = mediaMapper.toDto(media);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMediaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mediaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Media in the database
        List<Media> mediaList = mediaRepository.findAll();
        assertThat(mediaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMedia() throws Exception {
        int databaseSizeBeforeUpdate = mediaRepository.findAll().size();
        media.setId(count.incrementAndGet());

        // Create the Media
        MediaDTO mediaDTO = mediaMapper.toDto(media);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMediaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mediaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Media in the database
        List<Media> mediaList = mediaRepository.findAll();
        assertThat(mediaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMediaWithPatch() throws Exception {
        // Initialize the database
        mediaRepository.saveAndFlush(media);

        int databaseSizeBeforeUpdate = mediaRepository.findAll().size();

        // Update the media using partial update
        Media partialUpdatedMedia = new Media();
        partialUpdatedMedia.setId(media.getId());

        partialUpdatedMedia.fileName(UPDATED_FILE_NAME).fileDesc(UPDATED_FILE_DESC);

        restMediaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMedia.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMedia))
            )
            .andExpect(status().isOk());

        // Validate the Media in the database
        List<Media> mediaList = mediaRepository.findAll();
        assertThat(mediaList).hasSize(databaseSizeBeforeUpdate);
        Media testMedia = mediaList.get(mediaList.size() - 1);
        assertThat(testMedia.getFileName()).isEqualTo(UPDATED_FILE_NAME);
        assertThat(testMedia.getFileType()).isEqualTo(DEFAULT_FILE_TYPE);
        assertThat(testMedia.getFileDesc()).isEqualTo(UPDATED_FILE_DESC);
    }

    @Test
    @Transactional
    void fullUpdateMediaWithPatch() throws Exception {
        // Initialize the database
        mediaRepository.saveAndFlush(media);

        int databaseSizeBeforeUpdate = mediaRepository.findAll().size();

        // Update the media using partial update
        Media partialUpdatedMedia = new Media();
        partialUpdatedMedia.setId(media.getId());

        partialUpdatedMedia.fileName(UPDATED_FILE_NAME).fileType(UPDATED_FILE_TYPE).fileDesc(UPDATED_FILE_DESC);

        restMediaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMedia.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMedia))
            )
            .andExpect(status().isOk());

        // Validate the Media in the database
        List<Media> mediaList = mediaRepository.findAll();
        assertThat(mediaList).hasSize(databaseSizeBeforeUpdate);
        Media testMedia = mediaList.get(mediaList.size() - 1);
        assertThat(testMedia.getFileName()).isEqualTo(UPDATED_FILE_NAME);
        assertThat(testMedia.getFileType()).isEqualTo(UPDATED_FILE_TYPE);
        assertThat(testMedia.getFileDesc()).isEqualTo(UPDATED_FILE_DESC);
    }

    @Test
    @Transactional
    void patchNonExistingMedia() throws Exception {
        int databaseSizeBeforeUpdate = mediaRepository.findAll().size();
        media.setId(count.incrementAndGet());

        // Create the Media
        MediaDTO mediaDTO = mediaMapper.toDto(media);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMediaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, mediaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mediaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Media in the database
        List<Media> mediaList = mediaRepository.findAll();
        assertThat(mediaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMedia() throws Exception {
        int databaseSizeBeforeUpdate = mediaRepository.findAll().size();
        media.setId(count.incrementAndGet());

        // Create the Media
        MediaDTO mediaDTO = mediaMapper.toDto(media);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMediaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mediaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Media in the database
        List<Media> mediaList = mediaRepository.findAll();
        assertThat(mediaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMedia() throws Exception {
        int databaseSizeBeforeUpdate = mediaRepository.findAll().size();
        media.setId(count.incrementAndGet());

        // Create the Media
        MediaDTO mediaDTO = mediaMapper.toDto(media);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMediaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(mediaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Media in the database
        List<Media> mediaList = mediaRepository.findAll();
        assertThat(mediaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMedia() throws Exception {
        // Initialize the database
        mediaRepository.saveAndFlush(media);

        int databaseSizeBeforeDelete = mediaRepository.findAll().size();

        // Delete the media
        restMediaMockMvc
            .perform(delete(ENTITY_API_URL_ID, media.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Media> mediaList = mediaRepository.findAll();
        assertThat(mediaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
