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
import org.jhipster.dich.domain.Author;
import org.jhipster.dich.domain.Book;
import org.jhipster.dich.repository.AuthorRepository;
import org.jhipster.dich.service.criteria.AuthorCriteria;
import org.jhipster.dich.service.dto.AuthorDTO;
import org.jhipster.dich.service.mapper.AuthorMapper;
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
 * Integration tests for the {@link AuthorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AuthorResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CALLSIGN = "AAAAAAAAAA";
    private static final String UPDATED_CALLSIGN = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/authors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorMapper authorMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAuthorMockMvc;

    private Author author;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Author createEntity(EntityManager em) {
        Author author = new Author()
            .name(DEFAULT_NAME)
            .callsign(DEFAULT_CALLSIGN)
            .description(DEFAULT_DESCRIPTION)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE);
        return author;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Author createUpdatedEntity(EntityManager em) {
        Author author = new Author()
            .name(UPDATED_NAME)
            .callsign(UPDATED_CALLSIGN)
            .description(UPDATED_DESCRIPTION)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);
        return author;
    }

    @BeforeEach
    public void initTest() {
        author = createEntity(em);
    }

    @Test
    @Transactional
    void createAuthor() throws Exception {
        int databaseSizeBeforeCreate = authorRepository.findAll().size();
        // Create the Author
        AuthorDTO authorDTO = authorMapper.toDto(author);
        restAuthorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(authorDTO)))
            .andExpect(status().isCreated());

        // Validate the Author in the database
        List<Author> authorList = authorRepository.findAll();
        assertThat(authorList).hasSize(databaseSizeBeforeCreate + 1);
        Author testAuthor = authorList.get(authorList.size() - 1);
        assertThat(testAuthor.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAuthor.getCallsign()).isEqualTo(DEFAULT_CALLSIGN);
        assertThat(testAuthor.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAuthor.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testAuthor.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createAuthorWithExistingId() throws Exception {
        // Create the Author with an existing ID
        author.setId(1L);
        AuthorDTO authorDTO = authorMapper.toDto(author);

        int databaseSizeBeforeCreate = authorRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAuthorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(authorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Author in the database
        List<Author> authorList = authorRepository.findAll();
        assertThat(authorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAuthors() throws Exception {
        // Initialize the database
        authorRepository.saveAndFlush(author);

        // Get all the authorList
        restAuthorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(author.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].callsign").value(hasItem(DEFAULT_CALLSIGN)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))));
    }

    @Test
    @Transactional
    void getAuthor() throws Exception {
        // Initialize the database
        authorRepository.saveAndFlush(author);

        // Get the author
        restAuthorMockMvc
            .perform(get(ENTITY_API_URL_ID, author.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(author.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.callsign").value(DEFAULT_CALLSIGN))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)));
    }

    @Test
    @Transactional
    void getAuthorsByIdFiltering() throws Exception {
        // Initialize the database
        authorRepository.saveAndFlush(author);

        Long id = author.getId();

        defaultAuthorShouldBeFound("id.equals=" + id);
        defaultAuthorShouldNotBeFound("id.notEquals=" + id);

        defaultAuthorShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAuthorShouldNotBeFound("id.greaterThan=" + id);

        defaultAuthorShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAuthorShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAuthorsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        authorRepository.saveAndFlush(author);

        // Get all the authorList where name equals to DEFAULT_NAME
        defaultAuthorShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the authorList where name equals to UPDATED_NAME
        defaultAuthorShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllAuthorsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        authorRepository.saveAndFlush(author);

        // Get all the authorList where name not equals to DEFAULT_NAME
        defaultAuthorShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the authorList where name not equals to UPDATED_NAME
        defaultAuthorShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllAuthorsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        authorRepository.saveAndFlush(author);

        // Get all the authorList where name in DEFAULT_NAME or UPDATED_NAME
        defaultAuthorShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the authorList where name equals to UPDATED_NAME
        defaultAuthorShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllAuthorsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        authorRepository.saveAndFlush(author);

        // Get all the authorList where name is not null
        defaultAuthorShouldBeFound("name.specified=true");

        // Get all the authorList where name is null
        defaultAuthorShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllAuthorsByNameContainsSomething() throws Exception {
        // Initialize the database
        authorRepository.saveAndFlush(author);

        // Get all the authorList where name contains DEFAULT_NAME
        defaultAuthorShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the authorList where name contains UPDATED_NAME
        defaultAuthorShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllAuthorsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        authorRepository.saveAndFlush(author);

        // Get all the authorList where name does not contain DEFAULT_NAME
        defaultAuthorShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the authorList where name does not contain UPDATED_NAME
        defaultAuthorShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllAuthorsByCallsignIsEqualToSomething() throws Exception {
        // Initialize the database
        authorRepository.saveAndFlush(author);

        // Get all the authorList where callsign equals to DEFAULT_CALLSIGN
        defaultAuthorShouldBeFound("callsign.equals=" + DEFAULT_CALLSIGN);

        // Get all the authorList where callsign equals to UPDATED_CALLSIGN
        defaultAuthorShouldNotBeFound("callsign.equals=" + UPDATED_CALLSIGN);
    }

    @Test
    @Transactional
    void getAllAuthorsByCallsignIsNotEqualToSomething() throws Exception {
        // Initialize the database
        authorRepository.saveAndFlush(author);

        // Get all the authorList where callsign not equals to DEFAULT_CALLSIGN
        defaultAuthorShouldNotBeFound("callsign.notEquals=" + DEFAULT_CALLSIGN);

        // Get all the authorList where callsign not equals to UPDATED_CALLSIGN
        defaultAuthorShouldBeFound("callsign.notEquals=" + UPDATED_CALLSIGN);
    }

    @Test
    @Transactional
    void getAllAuthorsByCallsignIsInShouldWork() throws Exception {
        // Initialize the database
        authorRepository.saveAndFlush(author);

        // Get all the authorList where callsign in DEFAULT_CALLSIGN or UPDATED_CALLSIGN
        defaultAuthorShouldBeFound("callsign.in=" + DEFAULT_CALLSIGN + "," + UPDATED_CALLSIGN);

        // Get all the authorList where callsign equals to UPDATED_CALLSIGN
        defaultAuthorShouldNotBeFound("callsign.in=" + UPDATED_CALLSIGN);
    }

    @Test
    @Transactional
    void getAllAuthorsByCallsignIsNullOrNotNull() throws Exception {
        // Initialize the database
        authorRepository.saveAndFlush(author);

        // Get all the authorList where callsign is not null
        defaultAuthorShouldBeFound("callsign.specified=true");

        // Get all the authorList where callsign is null
        defaultAuthorShouldNotBeFound("callsign.specified=false");
    }

    @Test
    @Transactional
    void getAllAuthorsByCallsignContainsSomething() throws Exception {
        // Initialize the database
        authorRepository.saveAndFlush(author);

        // Get all the authorList where callsign contains DEFAULT_CALLSIGN
        defaultAuthorShouldBeFound("callsign.contains=" + DEFAULT_CALLSIGN);

        // Get all the authorList where callsign contains UPDATED_CALLSIGN
        defaultAuthorShouldNotBeFound("callsign.contains=" + UPDATED_CALLSIGN);
    }

    @Test
    @Transactional
    void getAllAuthorsByCallsignNotContainsSomething() throws Exception {
        // Initialize the database
        authorRepository.saveAndFlush(author);

        // Get all the authorList where callsign does not contain DEFAULT_CALLSIGN
        defaultAuthorShouldNotBeFound("callsign.doesNotContain=" + DEFAULT_CALLSIGN);

        // Get all the authorList where callsign does not contain UPDATED_CALLSIGN
        defaultAuthorShouldBeFound("callsign.doesNotContain=" + UPDATED_CALLSIGN);
    }

    @Test
    @Transactional
    void getAllAuthorsByBookIsEqualToSomething() throws Exception {
        // Initialize the database
        authorRepository.saveAndFlush(author);
        Book book;
        if (TestUtil.findAll(em, Book.class).isEmpty()) {
            book = BookResourceIT.createEntity(em);
            em.persist(book);
            em.flush();
        } else {
            book = TestUtil.findAll(em, Book.class).get(0);
        }
        em.persist(book);
        em.flush();
        author.addBook(book);
        authorRepository.saveAndFlush(author);
        Long bookId = book.getId();

        // Get all the authorList where book equals to bookId
        defaultAuthorShouldBeFound("bookId.equals=" + bookId);

        // Get all the authorList where book equals to (bookId + 1)
        defaultAuthorShouldNotBeFound("bookId.equals=" + (bookId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAuthorShouldBeFound(String filter) throws Exception {
        restAuthorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(author.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].callsign").value(hasItem(DEFAULT_CALLSIGN)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))));

        // Check, that the count call also returns 1
        restAuthorMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAuthorShouldNotBeFound(String filter) throws Exception {
        restAuthorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAuthorMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAuthor() throws Exception {
        // Get the author
        restAuthorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAuthor() throws Exception {
        // Initialize the database
        authorRepository.saveAndFlush(author);

        int databaseSizeBeforeUpdate = authorRepository.findAll().size();

        // Update the author
        Author updatedAuthor = authorRepository.findById(author.getId()).get();
        // Disconnect from session so that the updates on updatedAuthor are not directly saved in db
        em.detach(updatedAuthor);
        updatedAuthor
            .name(UPDATED_NAME)
            .callsign(UPDATED_CALLSIGN)
            .description(UPDATED_DESCRIPTION)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);
        AuthorDTO authorDTO = authorMapper.toDto(updatedAuthor);

        restAuthorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, authorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(authorDTO))
            )
            .andExpect(status().isOk());

        // Validate the Author in the database
        List<Author> authorList = authorRepository.findAll();
        assertThat(authorList).hasSize(databaseSizeBeforeUpdate);
        Author testAuthor = authorList.get(authorList.size() - 1);
        assertThat(testAuthor.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAuthor.getCallsign()).isEqualTo(UPDATED_CALLSIGN);
        assertThat(testAuthor.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAuthor.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testAuthor.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingAuthor() throws Exception {
        int databaseSizeBeforeUpdate = authorRepository.findAll().size();
        author.setId(count.incrementAndGet());

        // Create the Author
        AuthorDTO authorDTO = authorMapper.toDto(author);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAuthorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, authorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(authorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Author in the database
        List<Author> authorList = authorRepository.findAll();
        assertThat(authorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAuthor() throws Exception {
        int databaseSizeBeforeUpdate = authorRepository.findAll().size();
        author.setId(count.incrementAndGet());

        // Create the Author
        AuthorDTO authorDTO = authorMapper.toDto(author);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuthorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(authorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Author in the database
        List<Author> authorList = authorRepository.findAll();
        assertThat(authorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAuthor() throws Exception {
        int databaseSizeBeforeUpdate = authorRepository.findAll().size();
        author.setId(count.incrementAndGet());

        // Create the Author
        AuthorDTO authorDTO = authorMapper.toDto(author);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuthorMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(authorDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Author in the database
        List<Author> authorList = authorRepository.findAll();
        assertThat(authorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAuthorWithPatch() throws Exception {
        // Initialize the database
        authorRepository.saveAndFlush(author);

        int databaseSizeBeforeUpdate = authorRepository.findAll().size();

        // Update the author using partial update
        Author partialUpdatedAuthor = new Author();
        partialUpdatedAuthor.setId(author.getId());

        partialUpdatedAuthor.callsign(UPDATED_CALLSIGN);

        restAuthorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAuthor.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAuthor))
            )
            .andExpect(status().isOk());

        // Validate the Author in the database
        List<Author> authorList = authorRepository.findAll();
        assertThat(authorList).hasSize(databaseSizeBeforeUpdate);
        Author testAuthor = authorList.get(authorList.size() - 1);
        assertThat(testAuthor.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAuthor.getCallsign()).isEqualTo(UPDATED_CALLSIGN);
        assertThat(testAuthor.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAuthor.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testAuthor.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateAuthorWithPatch() throws Exception {
        // Initialize the database
        authorRepository.saveAndFlush(author);

        int databaseSizeBeforeUpdate = authorRepository.findAll().size();

        // Update the author using partial update
        Author partialUpdatedAuthor = new Author();
        partialUpdatedAuthor.setId(author.getId());

        partialUpdatedAuthor
            .name(UPDATED_NAME)
            .callsign(UPDATED_CALLSIGN)
            .description(UPDATED_DESCRIPTION)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);

        restAuthorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAuthor.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAuthor))
            )
            .andExpect(status().isOk());

        // Validate the Author in the database
        List<Author> authorList = authorRepository.findAll();
        assertThat(authorList).hasSize(databaseSizeBeforeUpdate);
        Author testAuthor = authorList.get(authorList.size() - 1);
        assertThat(testAuthor.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAuthor.getCallsign()).isEqualTo(UPDATED_CALLSIGN);
        assertThat(testAuthor.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAuthor.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testAuthor.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingAuthor() throws Exception {
        int databaseSizeBeforeUpdate = authorRepository.findAll().size();
        author.setId(count.incrementAndGet());

        // Create the Author
        AuthorDTO authorDTO = authorMapper.toDto(author);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAuthorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, authorDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(authorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Author in the database
        List<Author> authorList = authorRepository.findAll();
        assertThat(authorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAuthor() throws Exception {
        int databaseSizeBeforeUpdate = authorRepository.findAll().size();
        author.setId(count.incrementAndGet());

        // Create the Author
        AuthorDTO authorDTO = authorMapper.toDto(author);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuthorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(authorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Author in the database
        List<Author> authorList = authorRepository.findAll();
        assertThat(authorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAuthor() throws Exception {
        int databaseSizeBeforeUpdate = authorRepository.findAll().size();
        author.setId(count.incrementAndGet());

        // Create the Author
        AuthorDTO authorDTO = authorMapper.toDto(author);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuthorMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(authorDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Author in the database
        List<Author> authorList = authorRepository.findAll();
        assertThat(authorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAuthor() throws Exception {
        // Initialize the database
        authorRepository.saveAndFlush(author);

        int databaseSizeBeforeDelete = authorRepository.findAll().size();

        // Delete the author
        restAuthorMockMvc
            .perform(delete(ENTITY_API_URL_ID, author.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Author> authorList = authorRepository.findAll();
        assertThat(authorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
