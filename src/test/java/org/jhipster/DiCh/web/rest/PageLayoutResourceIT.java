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
import org.jhipster.dich.domain.PageLayout;
import org.jhipster.dich.repository.PageLayoutRepository;
import org.jhipster.dich.service.criteria.PageLayoutCriteria;
import org.jhipster.dich.service.dto.PageLayoutDTO;
import org.jhipster.dich.service.mapper.PageLayoutMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PageLayoutResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PageLayoutResourceIT {

    private static final Long DEFAULT_MEDIA_ID = 1L;
    private static final Long UPDATED_MEDIA_ID = 2L;
    private static final Long SMALLER_MEDIA_ID = 1L - 1L;

    private static final Integer DEFAULT_PAGE_NUMBER = 1;
    private static final Integer UPDATED_PAGE_NUMBER = 2;
    private static final Integer SMALLER_PAGE_NUMBER = 1 - 1;

    private static final String ENTITY_API_URL = "/api/page-layouts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PageLayoutRepository pageLayoutRepository;

    @Autowired
    private PageLayoutMapper pageLayoutMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPageLayoutMockMvc;

    private PageLayout pageLayout;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PageLayout createEntity(EntityManager em) {
        PageLayout pageLayout = new PageLayout().mediaId(DEFAULT_MEDIA_ID).pageNumber(DEFAULT_PAGE_NUMBER);
        return pageLayout;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PageLayout createUpdatedEntity(EntityManager em) {
        PageLayout pageLayout = new PageLayout().mediaId(UPDATED_MEDIA_ID).pageNumber(UPDATED_PAGE_NUMBER);
        return pageLayout;
    }

    @BeforeEach
    public void initTest() {
        pageLayout = createEntity(em);
    }

    @Test
    @Transactional
    void createPageLayout() throws Exception {
        int databaseSizeBeforeCreate = pageLayoutRepository.findAll().size();
        // Create the PageLayout
        PageLayoutDTO pageLayoutDTO = pageLayoutMapper.toDto(pageLayout);
        restPageLayoutMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pageLayoutDTO)))
            .andExpect(status().isCreated());

        // Validate the PageLayout in the database
        List<PageLayout> pageLayoutList = pageLayoutRepository.findAll();
        assertThat(pageLayoutList).hasSize(databaseSizeBeforeCreate + 1);
        PageLayout testPageLayout = pageLayoutList.get(pageLayoutList.size() - 1);
        assertThat(testPageLayout.getMediaId()).isEqualTo(DEFAULT_MEDIA_ID);
        assertThat(testPageLayout.getPageNumber()).isEqualTo(DEFAULT_PAGE_NUMBER);
    }

    @Test
    @Transactional
    void createPageLayoutWithExistingId() throws Exception {
        // Create the PageLayout with an existing ID
        pageLayout.setId(1L);
        PageLayoutDTO pageLayoutDTO = pageLayoutMapper.toDto(pageLayout);

        int databaseSizeBeforeCreate = pageLayoutRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPageLayoutMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pageLayoutDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PageLayout in the database
        List<PageLayout> pageLayoutList = pageLayoutRepository.findAll();
        assertThat(pageLayoutList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPageLayouts() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList
        restPageLayoutMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pageLayout.getId().intValue())))
            .andExpect(jsonPath("$.[*].mediaId").value(hasItem(DEFAULT_MEDIA_ID.intValue())))
            .andExpect(jsonPath("$.[*].pageNumber").value(hasItem(DEFAULT_PAGE_NUMBER)));
    }

    @Test
    @Transactional
    void getPageLayout() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get the pageLayout
        restPageLayoutMockMvc
            .perform(get(ENTITY_API_URL_ID, pageLayout.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pageLayout.getId().intValue()))
            .andExpect(jsonPath("$.mediaId").value(DEFAULT_MEDIA_ID.intValue()))
            .andExpect(jsonPath("$.pageNumber").value(DEFAULT_PAGE_NUMBER));
    }

    @Test
    @Transactional
    void getPageLayoutsByIdFiltering() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        Long id = pageLayout.getId();

        defaultPageLayoutShouldBeFound("id.equals=" + id);
        defaultPageLayoutShouldNotBeFound("id.notEquals=" + id);

        defaultPageLayoutShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPageLayoutShouldNotBeFound("id.greaterThan=" + id);

        defaultPageLayoutShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPageLayoutShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPageLayoutsByMediaIdIsEqualToSomething() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where mediaId equals to DEFAULT_MEDIA_ID
        defaultPageLayoutShouldBeFound("mediaId.equals=" + DEFAULT_MEDIA_ID);

        // Get all the pageLayoutList where mediaId equals to UPDATED_MEDIA_ID
        defaultPageLayoutShouldNotBeFound("mediaId.equals=" + UPDATED_MEDIA_ID);
    }

    @Test
    @Transactional
    void getAllPageLayoutsByMediaIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where mediaId not equals to DEFAULT_MEDIA_ID
        defaultPageLayoutShouldNotBeFound("mediaId.notEquals=" + DEFAULT_MEDIA_ID);

        // Get all the pageLayoutList where mediaId not equals to UPDATED_MEDIA_ID
        defaultPageLayoutShouldBeFound("mediaId.notEquals=" + UPDATED_MEDIA_ID);
    }

    @Test
    @Transactional
    void getAllPageLayoutsByMediaIdIsInShouldWork() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where mediaId in DEFAULT_MEDIA_ID or UPDATED_MEDIA_ID
        defaultPageLayoutShouldBeFound("mediaId.in=" + DEFAULT_MEDIA_ID + "," + UPDATED_MEDIA_ID);

        // Get all the pageLayoutList where mediaId equals to UPDATED_MEDIA_ID
        defaultPageLayoutShouldNotBeFound("mediaId.in=" + UPDATED_MEDIA_ID);
    }

    @Test
    @Transactional
    void getAllPageLayoutsByMediaIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where mediaId is not null
        defaultPageLayoutShouldBeFound("mediaId.specified=true");

        // Get all the pageLayoutList where mediaId is null
        defaultPageLayoutShouldNotBeFound("mediaId.specified=false");
    }

    @Test
    @Transactional
    void getAllPageLayoutsByMediaIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where mediaId is greater than or equal to DEFAULT_MEDIA_ID
        defaultPageLayoutShouldBeFound("mediaId.greaterThanOrEqual=" + DEFAULT_MEDIA_ID);

        // Get all the pageLayoutList where mediaId is greater than or equal to UPDATED_MEDIA_ID
        defaultPageLayoutShouldNotBeFound("mediaId.greaterThanOrEqual=" + UPDATED_MEDIA_ID);
    }

    @Test
    @Transactional
    void getAllPageLayoutsByMediaIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where mediaId is less than or equal to DEFAULT_MEDIA_ID
        defaultPageLayoutShouldBeFound("mediaId.lessThanOrEqual=" + DEFAULT_MEDIA_ID);

        // Get all the pageLayoutList where mediaId is less than or equal to SMALLER_MEDIA_ID
        defaultPageLayoutShouldNotBeFound("mediaId.lessThanOrEqual=" + SMALLER_MEDIA_ID);
    }

    @Test
    @Transactional
    void getAllPageLayoutsByMediaIdIsLessThanSomething() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where mediaId is less than DEFAULT_MEDIA_ID
        defaultPageLayoutShouldNotBeFound("mediaId.lessThan=" + DEFAULT_MEDIA_ID);

        // Get all the pageLayoutList where mediaId is less than UPDATED_MEDIA_ID
        defaultPageLayoutShouldBeFound("mediaId.lessThan=" + UPDATED_MEDIA_ID);
    }

    @Test
    @Transactional
    void getAllPageLayoutsByMediaIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where mediaId is greater than DEFAULT_MEDIA_ID
        defaultPageLayoutShouldNotBeFound("mediaId.greaterThan=" + DEFAULT_MEDIA_ID);

        // Get all the pageLayoutList where mediaId is greater than SMALLER_MEDIA_ID
        defaultPageLayoutShouldBeFound("mediaId.greaterThan=" + SMALLER_MEDIA_ID);
    }

    @Test
    @Transactional
    void getAllPageLayoutsByPageNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where pageNumber equals to DEFAULT_PAGE_NUMBER
        defaultPageLayoutShouldBeFound("pageNumber.equals=" + DEFAULT_PAGE_NUMBER);

        // Get all the pageLayoutList where pageNumber equals to UPDATED_PAGE_NUMBER
        defaultPageLayoutShouldNotBeFound("pageNumber.equals=" + UPDATED_PAGE_NUMBER);
    }

    @Test
    @Transactional
    void getAllPageLayoutsByPageNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where pageNumber not equals to DEFAULT_PAGE_NUMBER
        defaultPageLayoutShouldNotBeFound("pageNumber.notEquals=" + DEFAULT_PAGE_NUMBER);

        // Get all the pageLayoutList where pageNumber not equals to UPDATED_PAGE_NUMBER
        defaultPageLayoutShouldBeFound("pageNumber.notEquals=" + UPDATED_PAGE_NUMBER);
    }

    @Test
    @Transactional
    void getAllPageLayoutsByPageNumberIsInShouldWork() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where pageNumber in DEFAULT_PAGE_NUMBER or UPDATED_PAGE_NUMBER
        defaultPageLayoutShouldBeFound("pageNumber.in=" + DEFAULT_PAGE_NUMBER + "," + UPDATED_PAGE_NUMBER);

        // Get all the pageLayoutList where pageNumber equals to UPDATED_PAGE_NUMBER
        defaultPageLayoutShouldNotBeFound("pageNumber.in=" + UPDATED_PAGE_NUMBER);
    }

    @Test
    @Transactional
    void getAllPageLayoutsByPageNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where pageNumber is not null
        defaultPageLayoutShouldBeFound("pageNumber.specified=true");

        // Get all the pageLayoutList where pageNumber is null
        defaultPageLayoutShouldNotBeFound("pageNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllPageLayoutsByPageNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where pageNumber is greater than or equal to DEFAULT_PAGE_NUMBER
        defaultPageLayoutShouldBeFound("pageNumber.greaterThanOrEqual=" + DEFAULT_PAGE_NUMBER);

        // Get all the pageLayoutList where pageNumber is greater than or equal to UPDATED_PAGE_NUMBER
        defaultPageLayoutShouldNotBeFound("pageNumber.greaterThanOrEqual=" + UPDATED_PAGE_NUMBER);
    }

    @Test
    @Transactional
    void getAllPageLayoutsByPageNumberIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where pageNumber is less than or equal to DEFAULT_PAGE_NUMBER
        defaultPageLayoutShouldBeFound("pageNumber.lessThanOrEqual=" + DEFAULT_PAGE_NUMBER);

        // Get all the pageLayoutList where pageNumber is less than or equal to SMALLER_PAGE_NUMBER
        defaultPageLayoutShouldNotBeFound("pageNumber.lessThanOrEqual=" + SMALLER_PAGE_NUMBER);
    }

    @Test
    @Transactional
    void getAllPageLayoutsByPageNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where pageNumber is less than DEFAULT_PAGE_NUMBER
        defaultPageLayoutShouldNotBeFound("pageNumber.lessThan=" + DEFAULT_PAGE_NUMBER);

        // Get all the pageLayoutList where pageNumber is less than UPDATED_PAGE_NUMBER
        defaultPageLayoutShouldBeFound("pageNumber.lessThan=" + UPDATED_PAGE_NUMBER);
    }

    @Test
    @Transactional
    void getAllPageLayoutsByPageNumberIsGreaterThanSomething() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        // Get all the pageLayoutList where pageNumber is greater than DEFAULT_PAGE_NUMBER
        defaultPageLayoutShouldNotBeFound("pageNumber.greaterThan=" + DEFAULT_PAGE_NUMBER);

        // Get all the pageLayoutList where pageNumber is greater than SMALLER_PAGE_NUMBER
        defaultPageLayoutShouldBeFound("pageNumber.greaterThan=" + SMALLER_PAGE_NUMBER);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPageLayoutShouldBeFound(String filter) throws Exception {
        restPageLayoutMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pageLayout.getId().intValue())))
            .andExpect(jsonPath("$.[*].mediaId").value(hasItem(DEFAULT_MEDIA_ID.intValue())))
            .andExpect(jsonPath("$.[*].pageNumber").value(hasItem(DEFAULT_PAGE_NUMBER)));

        // Check, that the count call also returns 1
        restPageLayoutMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPageLayoutShouldNotBeFound(String filter) throws Exception {
        restPageLayoutMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPageLayoutMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPageLayout() throws Exception {
        // Get the pageLayout
        restPageLayoutMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPageLayout() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        int databaseSizeBeforeUpdate = pageLayoutRepository.findAll().size();

        // Update the pageLayout
        PageLayout updatedPageLayout = pageLayoutRepository.findById(pageLayout.getId()).get();
        // Disconnect from session so that the updates on updatedPageLayout are not directly saved in db
        em.detach(updatedPageLayout);
        updatedPageLayout.mediaId(UPDATED_MEDIA_ID).pageNumber(UPDATED_PAGE_NUMBER);
        PageLayoutDTO pageLayoutDTO = pageLayoutMapper.toDto(updatedPageLayout);

        restPageLayoutMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pageLayoutDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pageLayoutDTO))
            )
            .andExpect(status().isOk());

        // Validate the PageLayout in the database
        List<PageLayout> pageLayoutList = pageLayoutRepository.findAll();
        assertThat(pageLayoutList).hasSize(databaseSizeBeforeUpdate);
        PageLayout testPageLayout = pageLayoutList.get(pageLayoutList.size() - 1);
        assertThat(testPageLayout.getMediaId()).isEqualTo(UPDATED_MEDIA_ID);
        assertThat(testPageLayout.getPageNumber()).isEqualTo(UPDATED_PAGE_NUMBER);
    }

    @Test
    @Transactional
    void putNonExistingPageLayout() throws Exception {
        int databaseSizeBeforeUpdate = pageLayoutRepository.findAll().size();
        pageLayout.setId(count.incrementAndGet());

        // Create the PageLayout
        PageLayoutDTO pageLayoutDTO = pageLayoutMapper.toDto(pageLayout);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPageLayoutMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pageLayoutDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pageLayoutDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PageLayout in the database
        List<PageLayout> pageLayoutList = pageLayoutRepository.findAll();
        assertThat(pageLayoutList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPageLayout() throws Exception {
        int databaseSizeBeforeUpdate = pageLayoutRepository.findAll().size();
        pageLayout.setId(count.incrementAndGet());

        // Create the PageLayout
        PageLayoutDTO pageLayoutDTO = pageLayoutMapper.toDto(pageLayout);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPageLayoutMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pageLayoutDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PageLayout in the database
        List<PageLayout> pageLayoutList = pageLayoutRepository.findAll();
        assertThat(pageLayoutList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPageLayout() throws Exception {
        int databaseSizeBeforeUpdate = pageLayoutRepository.findAll().size();
        pageLayout.setId(count.incrementAndGet());

        // Create the PageLayout
        PageLayoutDTO pageLayoutDTO = pageLayoutMapper.toDto(pageLayout);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPageLayoutMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pageLayoutDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PageLayout in the database
        List<PageLayout> pageLayoutList = pageLayoutRepository.findAll();
        assertThat(pageLayoutList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePageLayoutWithPatch() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        int databaseSizeBeforeUpdate = pageLayoutRepository.findAll().size();

        // Update the pageLayout using partial update
        PageLayout partialUpdatedPageLayout = new PageLayout();
        partialUpdatedPageLayout.setId(pageLayout.getId());

        partialUpdatedPageLayout.pageNumber(UPDATED_PAGE_NUMBER);

        restPageLayoutMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPageLayout.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPageLayout))
            )
            .andExpect(status().isOk());

        // Validate the PageLayout in the database
        List<PageLayout> pageLayoutList = pageLayoutRepository.findAll();
        assertThat(pageLayoutList).hasSize(databaseSizeBeforeUpdate);
        PageLayout testPageLayout = pageLayoutList.get(pageLayoutList.size() - 1);
        assertThat(testPageLayout.getMediaId()).isEqualTo(DEFAULT_MEDIA_ID);
        assertThat(testPageLayout.getPageNumber()).isEqualTo(UPDATED_PAGE_NUMBER);
    }

    @Test
    @Transactional
    void fullUpdatePageLayoutWithPatch() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        int databaseSizeBeforeUpdate = pageLayoutRepository.findAll().size();

        // Update the pageLayout using partial update
        PageLayout partialUpdatedPageLayout = new PageLayout();
        partialUpdatedPageLayout.setId(pageLayout.getId());

        partialUpdatedPageLayout.mediaId(UPDATED_MEDIA_ID).pageNumber(UPDATED_PAGE_NUMBER);

        restPageLayoutMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPageLayout.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPageLayout))
            )
            .andExpect(status().isOk());

        // Validate the PageLayout in the database
        List<PageLayout> pageLayoutList = pageLayoutRepository.findAll();
        assertThat(pageLayoutList).hasSize(databaseSizeBeforeUpdate);
        PageLayout testPageLayout = pageLayoutList.get(pageLayoutList.size() - 1);
        assertThat(testPageLayout.getMediaId()).isEqualTo(UPDATED_MEDIA_ID);
        assertThat(testPageLayout.getPageNumber()).isEqualTo(UPDATED_PAGE_NUMBER);
    }

    @Test
    @Transactional
    void patchNonExistingPageLayout() throws Exception {
        int databaseSizeBeforeUpdate = pageLayoutRepository.findAll().size();
        pageLayout.setId(count.incrementAndGet());

        // Create the PageLayout
        PageLayoutDTO pageLayoutDTO = pageLayoutMapper.toDto(pageLayout);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPageLayoutMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pageLayoutDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pageLayoutDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PageLayout in the database
        List<PageLayout> pageLayoutList = pageLayoutRepository.findAll();
        assertThat(pageLayoutList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPageLayout() throws Exception {
        int databaseSizeBeforeUpdate = pageLayoutRepository.findAll().size();
        pageLayout.setId(count.incrementAndGet());

        // Create the PageLayout
        PageLayoutDTO pageLayoutDTO = pageLayoutMapper.toDto(pageLayout);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPageLayoutMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pageLayoutDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PageLayout in the database
        List<PageLayout> pageLayoutList = pageLayoutRepository.findAll();
        assertThat(pageLayoutList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPageLayout() throws Exception {
        int databaseSizeBeforeUpdate = pageLayoutRepository.findAll().size();
        pageLayout.setId(count.incrementAndGet());

        // Create the PageLayout
        PageLayoutDTO pageLayoutDTO = pageLayoutMapper.toDto(pageLayout);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPageLayoutMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(pageLayoutDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PageLayout in the database
        List<PageLayout> pageLayoutList = pageLayoutRepository.findAll();
        assertThat(pageLayoutList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePageLayout() throws Exception {
        // Initialize the database
        pageLayoutRepository.saveAndFlush(pageLayout);

        int databaseSizeBeforeDelete = pageLayoutRepository.findAll().size();

        // Delete the pageLayout
        restPageLayoutMockMvc
            .perform(delete(ENTITY_API_URL_ID, pageLayout.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PageLayout> pageLayoutList = pageLayoutRepository.findAll();
        assertThat(pageLayoutList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
