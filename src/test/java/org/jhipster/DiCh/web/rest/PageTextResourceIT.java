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
import org.jhipster.dich.domain.PageText;
import org.jhipster.dich.repository.PageTextRepository;
import org.jhipster.dich.service.criteria.PageTextCriteria;
import org.jhipster.dich.service.dto.PageTextDTO;
import org.jhipster.dich.service.mapper.PageTextMapper;
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
 * Integration tests for the {@link PageTextResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PageTextResourceIT {

    private static final String DEFAULT_PAGE_ID = "AAAAAAAAAA";
    private static final String UPDATED_PAGE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/page-texts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PageTextRepository pageTextRepository;

    @Autowired
    private PageTextMapper pageTextMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPageTextMockMvc;

    private PageText pageText;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PageText createEntity(EntityManager em) {
        PageText pageText = new PageText().page_id(DEFAULT_PAGE_ID).text(DEFAULT_TEXT);
        return pageText;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PageText createUpdatedEntity(EntityManager em) {
        PageText pageText = new PageText().page_id(UPDATED_PAGE_ID).text(UPDATED_TEXT);
        return pageText;
    }

    @BeforeEach
    public void initTest() {
        pageText = createEntity(em);
    }

    @Test
    @Transactional
    void createPageText() throws Exception {
        int databaseSizeBeforeCreate = pageTextRepository.findAll().size();
        // Create the PageText
        PageTextDTO pageTextDTO = pageTextMapper.toDto(pageText);
        restPageTextMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pageTextDTO)))
            .andExpect(status().isCreated());

        // Validate the PageText in the database
        List<PageText> pageTextList = pageTextRepository.findAll();
        assertThat(pageTextList).hasSize(databaseSizeBeforeCreate + 1);
        PageText testPageText = pageTextList.get(pageTextList.size() - 1);
        assertThat(testPageText.getPage_id()).isEqualTo(DEFAULT_PAGE_ID);
        assertThat(testPageText.getText()).isEqualTo(DEFAULT_TEXT);
    }

    @Test
    @Transactional
    void createPageTextWithExistingId() throws Exception {
        // Create the PageText with an existing ID
        pageText.setId(1L);
        PageTextDTO pageTextDTO = pageTextMapper.toDto(pageText);

        int databaseSizeBeforeCreate = pageTextRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPageTextMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pageTextDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PageText in the database
        List<PageText> pageTextList = pageTextRepository.findAll();
        assertThat(pageTextList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPageTexts() throws Exception {
        // Initialize the database
        pageTextRepository.saveAndFlush(pageText);

        // Get all the pageTextList
        restPageTextMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pageText.getId().intValue())))
            .andExpect(jsonPath("$.[*].page_id").value(hasItem(DEFAULT_PAGE_ID)))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT.toString())));
    }

    @Test
    @Transactional
    void getPageText() throws Exception {
        // Initialize the database
        pageTextRepository.saveAndFlush(pageText);

        // Get the pageText
        restPageTextMockMvc
            .perform(get(ENTITY_API_URL_ID, pageText.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pageText.getId().intValue()))
            .andExpect(jsonPath("$.page_id").value(DEFAULT_PAGE_ID))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT.toString()));
    }

    @Test
    @Transactional
    void getPageTextsByIdFiltering() throws Exception {
        // Initialize the database
        pageTextRepository.saveAndFlush(pageText);

        Long id = pageText.getId();

        defaultPageTextShouldBeFound("id.equals=" + id);
        defaultPageTextShouldNotBeFound("id.notEquals=" + id);

        defaultPageTextShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPageTextShouldNotBeFound("id.greaterThan=" + id);

        defaultPageTextShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPageTextShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPageTextsByPage_idIsEqualToSomething() throws Exception {
        // Initialize the database
        pageTextRepository.saveAndFlush(pageText);

        // Get all the pageTextList where page_id equals to DEFAULT_PAGE_ID
        defaultPageTextShouldBeFound("page_id.equals=" + DEFAULT_PAGE_ID);

        // Get all the pageTextList where page_id equals to UPDATED_PAGE_ID
        defaultPageTextShouldNotBeFound("page_id.equals=" + UPDATED_PAGE_ID);
    }

    @Test
    @Transactional
    void getAllPageTextsByPage_idIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pageTextRepository.saveAndFlush(pageText);

        // Get all the pageTextList where page_id not equals to DEFAULT_PAGE_ID
        defaultPageTextShouldNotBeFound("page_id.notEquals=" + DEFAULT_PAGE_ID);

        // Get all the pageTextList where page_id not equals to UPDATED_PAGE_ID
        defaultPageTextShouldBeFound("page_id.notEquals=" + UPDATED_PAGE_ID);
    }

    @Test
    @Transactional
    void getAllPageTextsByPage_idIsInShouldWork() throws Exception {
        // Initialize the database
        pageTextRepository.saveAndFlush(pageText);

        // Get all the pageTextList where page_id in DEFAULT_PAGE_ID or UPDATED_PAGE_ID
        defaultPageTextShouldBeFound("page_id.in=" + DEFAULT_PAGE_ID + "," + UPDATED_PAGE_ID);

        // Get all the pageTextList where page_id equals to UPDATED_PAGE_ID
        defaultPageTextShouldNotBeFound("page_id.in=" + UPDATED_PAGE_ID);
    }

    @Test
    @Transactional
    void getAllPageTextsByPage_idIsNullOrNotNull() throws Exception {
        // Initialize the database
        pageTextRepository.saveAndFlush(pageText);

        // Get all the pageTextList where page_id is not null
        defaultPageTextShouldBeFound("page_id.specified=true");

        // Get all the pageTextList where page_id is null
        defaultPageTextShouldNotBeFound("page_id.specified=false");
    }

    @Test
    @Transactional
    void getAllPageTextsByPage_idContainsSomething() throws Exception {
        // Initialize the database
        pageTextRepository.saveAndFlush(pageText);

        // Get all the pageTextList where page_id contains DEFAULT_PAGE_ID
        defaultPageTextShouldBeFound("page_id.contains=" + DEFAULT_PAGE_ID);

        // Get all the pageTextList where page_id contains UPDATED_PAGE_ID
        defaultPageTextShouldNotBeFound("page_id.contains=" + UPDATED_PAGE_ID);
    }

    @Test
    @Transactional
    void getAllPageTextsByPage_idNotContainsSomething() throws Exception {
        // Initialize the database
        pageTextRepository.saveAndFlush(pageText);

        // Get all the pageTextList where page_id does not contain DEFAULT_PAGE_ID
        defaultPageTextShouldNotBeFound("page_id.doesNotContain=" + DEFAULT_PAGE_ID);

        // Get all the pageTextList where page_id does not contain UPDATED_PAGE_ID
        defaultPageTextShouldBeFound("page_id.doesNotContain=" + UPDATED_PAGE_ID);
    }

    @Test
    @Transactional
    void getAllPageTextsByPageImageIsEqualToSomething() throws Exception {
        // Initialize the database
        pageTextRepository.saveAndFlush(pageText);
        PageImage pageImage;
        if (TestUtil.findAll(em, PageImage.class).isEmpty()) {
            pageImage = PageImageResourceIT.createEntity(em);
            em.persist(pageImage);
            em.flush();
        } else {
            pageImage = TestUtil.findAll(em, PageImage.class).get(0);
        }
        em.persist(pageImage);
        em.flush();
        pageText.setPageImage(pageImage);
        pageTextRepository.saveAndFlush(pageText);
        Long pageImageId = pageImage.getId();

        // Get all the pageTextList where pageImage equals to pageImageId
        defaultPageTextShouldBeFound("pageImageId.equals=" + pageImageId);

        // Get all the pageTextList where pageImage equals to (pageImageId + 1)
        defaultPageTextShouldNotBeFound("pageImageId.equals=" + (pageImageId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPageTextShouldBeFound(String filter) throws Exception {
        restPageTextMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pageText.getId().intValue())))
            .andExpect(jsonPath("$.[*].page_id").value(hasItem(DEFAULT_PAGE_ID)))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT.toString())));

        // Check, that the count call also returns 1
        restPageTextMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPageTextShouldNotBeFound(String filter) throws Exception {
        restPageTextMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPageTextMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPageText() throws Exception {
        // Get the pageText
        restPageTextMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPageText() throws Exception {
        // Initialize the database
        pageTextRepository.saveAndFlush(pageText);

        int databaseSizeBeforeUpdate = pageTextRepository.findAll().size();

        // Update the pageText
        PageText updatedPageText = pageTextRepository.findById(pageText.getId()).get();
        // Disconnect from session so that the updates on updatedPageText are not directly saved in db
        em.detach(updatedPageText);
        updatedPageText.page_id(UPDATED_PAGE_ID).text(UPDATED_TEXT);
        PageTextDTO pageTextDTO = pageTextMapper.toDto(updatedPageText);

        restPageTextMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pageTextDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pageTextDTO))
            )
            .andExpect(status().isOk());

        // Validate the PageText in the database
        List<PageText> pageTextList = pageTextRepository.findAll();
        assertThat(pageTextList).hasSize(databaseSizeBeforeUpdate);
        PageText testPageText = pageTextList.get(pageTextList.size() - 1);
        assertThat(testPageText.getPage_id()).isEqualTo(UPDATED_PAGE_ID);
        assertThat(testPageText.getText()).isEqualTo(UPDATED_TEXT);
    }

    @Test
    @Transactional
    void putNonExistingPageText() throws Exception {
        int databaseSizeBeforeUpdate = pageTextRepository.findAll().size();
        pageText.setId(count.incrementAndGet());

        // Create the PageText
        PageTextDTO pageTextDTO = pageTextMapper.toDto(pageText);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPageTextMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pageTextDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pageTextDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PageText in the database
        List<PageText> pageTextList = pageTextRepository.findAll();
        assertThat(pageTextList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPageText() throws Exception {
        int databaseSizeBeforeUpdate = pageTextRepository.findAll().size();
        pageText.setId(count.incrementAndGet());

        // Create the PageText
        PageTextDTO pageTextDTO = pageTextMapper.toDto(pageText);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPageTextMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pageTextDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PageText in the database
        List<PageText> pageTextList = pageTextRepository.findAll();
        assertThat(pageTextList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPageText() throws Exception {
        int databaseSizeBeforeUpdate = pageTextRepository.findAll().size();
        pageText.setId(count.incrementAndGet());

        // Create the PageText
        PageTextDTO pageTextDTO = pageTextMapper.toDto(pageText);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPageTextMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pageTextDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PageText in the database
        List<PageText> pageTextList = pageTextRepository.findAll();
        assertThat(pageTextList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePageTextWithPatch() throws Exception {
        // Initialize the database
        pageTextRepository.saveAndFlush(pageText);

        int databaseSizeBeforeUpdate = pageTextRepository.findAll().size();

        // Update the pageText using partial update
        PageText partialUpdatedPageText = new PageText();
        partialUpdatedPageText.setId(pageText.getId());

        partialUpdatedPageText.text(UPDATED_TEXT);

        restPageTextMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPageText.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPageText))
            )
            .andExpect(status().isOk());

        // Validate the PageText in the database
        List<PageText> pageTextList = pageTextRepository.findAll();
        assertThat(pageTextList).hasSize(databaseSizeBeforeUpdate);
        PageText testPageText = pageTextList.get(pageTextList.size() - 1);
        assertThat(testPageText.getPage_id()).isEqualTo(DEFAULT_PAGE_ID);
        assertThat(testPageText.getText()).isEqualTo(UPDATED_TEXT);
    }

    @Test
    @Transactional
    void fullUpdatePageTextWithPatch() throws Exception {
        // Initialize the database
        pageTextRepository.saveAndFlush(pageText);

        int databaseSizeBeforeUpdate = pageTextRepository.findAll().size();

        // Update the pageText using partial update
        PageText partialUpdatedPageText = new PageText();
        partialUpdatedPageText.setId(pageText.getId());

        partialUpdatedPageText.page_id(UPDATED_PAGE_ID).text(UPDATED_TEXT);

        restPageTextMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPageText.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPageText))
            )
            .andExpect(status().isOk());

        // Validate the PageText in the database
        List<PageText> pageTextList = pageTextRepository.findAll();
        assertThat(pageTextList).hasSize(databaseSizeBeforeUpdate);
        PageText testPageText = pageTextList.get(pageTextList.size() - 1);
        assertThat(testPageText.getPage_id()).isEqualTo(UPDATED_PAGE_ID);
        assertThat(testPageText.getText()).isEqualTo(UPDATED_TEXT);
    }

    @Test
    @Transactional
    void patchNonExistingPageText() throws Exception {
        int databaseSizeBeforeUpdate = pageTextRepository.findAll().size();
        pageText.setId(count.incrementAndGet());

        // Create the PageText
        PageTextDTO pageTextDTO = pageTextMapper.toDto(pageText);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPageTextMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pageTextDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pageTextDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PageText in the database
        List<PageText> pageTextList = pageTextRepository.findAll();
        assertThat(pageTextList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPageText() throws Exception {
        int databaseSizeBeforeUpdate = pageTextRepository.findAll().size();
        pageText.setId(count.incrementAndGet());

        // Create the PageText
        PageTextDTO pageTextDTO = pageTextMapper.toDto(pageText);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPageTextMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pageTextDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PageText in the database
        List<PageText> pageTextList = pageTextRepository.findAll();
        assertThat(pageTextList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPageText() throws Exception {
        int databaseSizeBeforeUpdate = pageTextRepository.findAll().size();
        pageText.setId(count.incrementAndGet());

        // Create the PageText
        PageTextDTO pageTextDTO = pageTextMapper.toDto(pageText);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPageTextMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(pageTextDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PageText in the database
        List<PageText> pageTextList = pageTextRepository.findAll();
        assertThat(pageTextList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePageText() throws Exception {
        // Initialize the database
        pageTextRepository.saveAndFlush(pageText);

        int databaseSizeBeforeDelete = pageTextRepository.findAll().size();

        // Delete the pageText
        restPageTextMockMvc
            .perform(delete(ENTITY_API_URL_ID, pageText.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PageText> pageTextList = pageTextRepository.findAll();
        assertThat(pageTextList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
