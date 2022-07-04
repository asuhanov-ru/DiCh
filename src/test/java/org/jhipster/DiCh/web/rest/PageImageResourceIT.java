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
import org.jhipster.dich.repository.PageImageRepository;
import org.jhipster.dich.service.criteria.PageImageCriteria;
import org.jhipster.dich.service.dto.PageImageDTO;
import org.jhipster.dich.service.mapper.PageImageMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link PageImageResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PageImageResourceIT {

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/page-images";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PageImageRepository pageImageRepository;

    @Autowired
    private PageImageMapper pageImageMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPageImageMockMvc;

    private PageImage pageImage;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PageImage createEntity(EntityManager em) {
        PageImage pageImage = new PageImage().image(DEFAULT_IMAGE).imageContentType(DEFAULT_IMAGE_CONTENT_TYPE);
        return pageImage;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PageImage createUpdatedEntity(EntityManager em) {
        PageImage pageImage = new PageImage().image(UPDATED_IMAGE).imageContentType(UPDATED_IMAGE_CONTENT_TYPE);
        return pageImage;
    }

    @BeforeEach
    public void initTest() {
        pageImage = createEntity(em);
    }

    @Test
    @Transactional
    void createPageImage() throws Exception {
        int databaseSizeBeforeCreate = pageImageRepository.findAll().size();
        // Create the PageImage
        PageImageDTO pageImageDTO = pageImageMapper.toDto(pageImage);
        restPageImageMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pageImageDTO)))
            .andExpect(status().isCreated());

        // Validate the PageImage in the database
        List<PageImage> pageImageList = pageImageRepository.findAll();
        assertThat(pageImageList).hasSize(databaseSizeBeforeCreate + 1);
        PageImage testPageImage = pageImageList.get(pageImageList.size() - 1);
        assertThat(testPageImage.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testPageImage.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createPageImageWithExistingId() throws Exception {
        // Create the PageImage with an existing ID
        pageImage.setId(1L);
        PageImageDTO pageImageDTO = pageImageMapper.toDto(pageImage);

        int databaseSizeBeforeCreate = pageImageRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPageImageMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pageImageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PageImage in the database
        List<PageImage> pageImageList = pageImageRepository.findAll();
        assertThat(pageImageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPageImages() throws Exception {
        // Initialize the database
        pageImageRepository.saveAndFlush(pageImage);

        // Get all the pageImageList
        restPageImageMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pageImage.getId().intValue())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))));
    }

    @Test
    @Transactional
    void getPageImage() throws Exception {
        // Initialize the database
        pageImageRepository.saveAndFlush(pageImage);

        // Get the pageImage
        restPageImageMockMvc
            .perform(get(ENTITY_API_URL_ID, pageImage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pageImage.getId().intValue()))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)));
    }

    @Test
    @Transactional
    void getPageImagesByIdFiltering() throws Exception {
        // Initialize the database
        pageImageRepository.saveAndFlush(pageImage);

        Long id = pageImage.getId();

        defaultPageImageShouldBeFound("id.equals=" + id);
        defaultPageImageShouldNotBeFound("id.notEquals=" + id);

        defaultPageImageShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPageImageShouldNotBeFound("id.greaterThan=" + id);

        defaultPageImageShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPageImageShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPageImagesByPageWordIsEqualToSomething() throws Exception {
        // Initialize the database
        pageImageRepository.saveAndFlush(pageImage);
        PageWord pageWord;
        if (TestUtil.findAll(em, PageWord.class).isEmpty()) {
            pageWord = PageWordResourceIT.createEntity(em);
            em.persist(pageWord);
            em.flush();
        } else {
            pageWord = TestUtil.findAll(em, PageWord.class).get(0);
        }
        em.persist(pageWord);
        em.flush();
        pageImage.addPageWord(pageWord);
        pageImageRepository.saveAndFlush(pageImage);
        Long pageWordId = pageWord.getId();

        // Get all the pageImageList where pageWord equals to pageWordId
        defaultPageImageShouldBeFound("pageWordId.equals=" + pageWordId);

        // Get all the pageImageList where pageWord equals to (pageWordId + 1)
        defaultPageImageShouldNotBeFound("pageWordId.equals=" + (pageWordId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPageImageShouldBeFound(String filter) throws Exception {
        restPageImageMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pageImage.getId().intValue())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))));

        // Check, that the count call also returns 1
        restPageImageMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPageImageShouldNotBeFound(String filter) throws Exception {
        restPageImageMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPageImageMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPageImage() throws Exception {
        // Get the pageImage
        restPageImageMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPageImage() throws Exception {
        // Initialize the database
        pageImageRepository.saveAndFlush(pageImage);

        int databaseSizeBeforeUpdate = pageImageRepository.findAll().size();

        // Update the pageImage
        PageImage updatedPageImage = pageImageRepository.findById(pageImage.getId()).get();
        // Disconnect from session so that the updates on updatedPageImage are not directly saved in db
        em.detach(updatedPageImage);
        updatedPageImage.image(UPDATED_IMAGE).imageContentType(UPDATED_IMAGE_CONTENT_TYPE);
        PageImageDTO pageImageDTO = pageImageMapper.toDto(updatedPageImage);

        restPageImageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pageImageDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pageImageDTO))
            )
            .andExpect(status().isOk());

        // Validate the PageImage in the database
        List<PageImage> pageImageList = pageImageRepository.findAll();
        assertThat(pageImageList).hasSize(databaseSizeBeforeUpdate);
        PageImage testPageImage = pageImageList.get(pageImageList.size() - 1);
        assertThat(testPageImage.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testPageImage.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingPageImage() throws Exception {
        int databaseSizeBeforeUpdate = pageImageRepository.findAll().size();
        pageImage.setId(count.incrementAndGet());

        // Create the PageImage
        PageImageDTO pageImageDTO = pageImageMapper.toDto(pageImage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPageImageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pageImageDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pageImageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PageImage in the database
        List<PageImage> pageImageList = pageImageRepository.findAll();
        assertThat(pageImageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPageImage() throws Exception {
        int databaseSizeBeforeUpdate = pageImageRepository.findAll().size();
        pageImage.setId(count.incrementAndGet());

        // Create the PageImage
        PageImageDTO pageImageDTO = pageImageMapper.toDto(pageImage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPageImageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pageImageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PageImage in the database
        List<PageImage> pageImageList = pageImageRepository.findAll();
        assertThat(pageImageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPageImage() throws Exception {
        int databaseSizeBeforeUpdate = pageImageRepository.findAll().size();
        pageImage.setId(count.incrementAndGet());

        // Create the PageImage
        PageImageDTO pageImageDTO = pageImageMapper.toDto(pageImage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPageImageMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pageImageDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PageImage in the database
        List<PageImage> pageImageList = pageImageRepository.findAll();
        assertThat(pageImageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePageImageWithPatch() throws Exception {
        // Initialize the database
        pageImageRepository.saveAndFlush(pageImage);

        int databaseSizeBeforeUpdate = pageImageRepository.findAll().size();

        // Update the pageImage using partial update
        PageImage partialUpdatedPageImage = new PageImage();
        partialUpdatedPageImage.setId(pageImage.getId());

        partialUpdatedPageImage.image(UPDATED_IMAGE).imageContentType(UPDATED_IMAGE_CONTENT_TYPE);

        restPageImageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPageImage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPageImage))
            )
            .andExpect(status().isOk());

        // Validate the PageImage in the database
        List<PageImage> pageImageList = pageImageRepository.findAll();
        assertThat(pageImageList).hasSize(databaseSizeBeforeUpdate);
        PageImage testPageImage = pageImageList.get(pageImageList.size() - 1);
        assertThat(testPageImage.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testPageImage.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdatePageImageWithPatch() throws Exception {
        // Initialize the database
        pageImageRepository.saveAndFlush(pageImage);

        int databaseSizeBeforeUpdate = pageImageRepository.findAll().size();

        // Update the pageImage using partial update
        PageImage partialUpdatedPageImage = new PageImage();
        partialUpdatedPageImage.setId(pageImage.getId());

        partialUpdatedPageImage.image(UPDATED_IMAGE).imageContentType(UPDATED_IMAGE_CONTENT_TYPE);

        restPageImageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPageImage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPageImage))
            )
            .andExpect(status().isOk());

        // Validate the PageImage in the database
        List<PageImage> pageImageList = pageImageRepository.findAll();
        assertThat(pageImageList).hasSize(databaseSizeBeforeUpdate);
        PageImage testPageImage = pageImageList.get(pageImageList.size() - 1);
        assertThat(testPageImage.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testPageImage.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingPageImage() throws Exception {
        int databaseSizeBeforeUpdate = pageImageRepository.findAll().size();
        pageImage.setId(count.incrementAndGet());

        // Create the PageImage
        PageImageDTO pageImageDTO = pageImageMapper.toDto(pageImage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPageImageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pageImageDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pageImageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PageImage in the database
        List<PageImage> pageImageList = pageImageRepository.findAll();
        assertThat(pageImageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPageImage() throws Exception {
        int databaseSizeBeforeUpdate = pageImageRepository.findAll().size();
        pageImage.setId(count.incrementAndGet());

        // Create the PageImage
        PageImageDTO pageImageDTO = pageImageMapper.toDto(pageImage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPageImageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pageImageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PageImage in the database
        List<PageImage> pageImageList = pageImageRepository.findAll();
        assertThat(pageImageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPageImage() throws Exception {
        int databaseSizeBeforeUpdate = pageImageRepository.findAll().size();
        pageImage.setId(count.incrementAndGet());

        // Create the PageImage
        PageImageDTO pageImageDTO = pageImageMapper.toDto(pageImage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPageImageMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(pageImageDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PageImage in the database
        List<PageImage> pageImageList = pageImageRepository.findAll();
        assertThat(pageImageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePageImage() throws Exception {
        // Initialize the database
        pageImageRepository.saveAndFlush(pageImage);

        int databaseSizeBeforeDelete = pageImageRepository.findAll().size();

        // Delete the pageImage
        restPageImageMockMvc
            .perform(delete(ENTITY_API_URL_ID, pageImage.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PageImage> pageImageList = pageImageRepository.findAll();
        assertThat(pageImageList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
