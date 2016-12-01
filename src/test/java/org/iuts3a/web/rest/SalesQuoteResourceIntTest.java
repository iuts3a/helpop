package org.iuts3a.web.rest;

import org.iuts3a.HelpopApp;

import org.iuts3a.domain.SalesQuote;
import org.iuts3a.repository.SalesQuoteRepository;
import org.iuts3a.service.SalesQuoteService;
import org.iuts3a.service.dto.SalesQuoteDTO;
import org.iuts3a.service.mapper.SalesQuoteMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static org.iuts3a.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SalesQuoteResource REST controller.
 *
 * @see SalesQuoteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HelpopApp.class)
public class SalesQuoteResourceIntTest {

    private static final ZonedDateTime DEFAULT_QUOTED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_QUOTED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Inject
    private SalesQuoteRepository salesQuoteRepository;

    @Inject
    private SalesQuoteMapper salesQuoteMapper;

    @Inject
    private SalesQuoteService salesQuoteService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restSalesQuoteMockMvc;

    private SalesQuote salesQuote;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SalesQuoteResource salesQuoteResource = new SalesQuoteResource();
        ReflectionTestUtils.setField(salesQuoteResource, "salesQuoteService", salesQuoteService);
        this.restSalesQuoteMockMvc = MockMvcBuilders.standaloneSetup(salesQuoteResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SalesQuote createEntity(EntityManager em) {
        SalesQuote salesQuote = new SalesQuote()
                .quotedAt(DEFAULT_QUOTED_AT);
        return salesQuote;
    }

    @Before
    public void initTest() {
        salesQuote = createEntity(em);
    }

    @Test
    @Transactional
    public void createSalesQuote() throws Exception {
        int databaseSizeBeforeCreate = salesQuoteRepository.findAll().size();

        // Create the SalesQuote
        SalesQuoteDTO salesQuoteDTO = salesQuoteMapper.salesQuoteToSalesQuoteDTO(salesQuote);

        restSalesQuoteMockMvc.perform(post("/api/sales-quotes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salesQuoteDTO)))
            .andExpect(status().isCreated());

        // Validate the SalesQuote in the database
        List<SalesQuote> salesQuotes = salesQuoteRepository.findAll();
        assertThat(salesQuotes).hasSize(databaseSizeBeforeCreate + 1);
        SalesQuote testSalesQuote = salesQuotes.get(salesQuotes.size() - 1);
        assertThat(testSalesQuote.getQuotedAt()).isEqualTo(DEFAULT_QUOTED_AT);
    }

    @Test
    @Transactional
    public void checkQuotedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = salesQuoteRepository.findAll().size();
        // set the field null
        salesQuote.setQuotedAt(null);

        // Create the SalesQuote, which fails.
        SalesQuoteDTO salesQuoteDTO = salesQuoteMapper.salesQuoteToSalesQuoteDTO(salesQuote);

        restSalesQuoteMockMvc.perform(post("/api/sales-quotes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salesQuoteDTO)))
            .andExpect(status().isBadRequest());

        List<SalesQuote> salesQuotes = salesQuoteRepository.findAll();
        assertThat(salesQuotes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSalesQuotes() throws Exception {
        // Initialize the database
        salesQuoteRepository.saveAndFlush(salesQuote);

        // Get all the salesQuotes
        restSalesQuoteMockMvc.perform(get("/api/sales-quotes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(salesQuote.getId().intValue())))
            .andExpect(jsonPath("$.[*].quotedAt").value(hasItem(sameInstant(DEFAULT_QUOTED_AT))));
    }

    @Test
    @Transactional
    public void getSalesQuote() throws Exception {
        // Initialize the database
        salesQuoteRepository.saveAndFlush(salesQuote);

        // Get the salesQuote
        restSalesQuoteMockMvc.perform(get("/api/sales-quotes/{id}", salesQuote.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(salesQuote.getId().intValue()))
            .andExpect(jsonPath("$.quotedAt").value(sameInstant(DEFAULT_QUOTED_AT)));
    }

    @Test
    @Transactional
    public void getNonExistingSalesQuote() throws Exception {
        // Get the salesQuote
        restSalesQuoteMockMvc.perform(get("/api/sales-quotes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSalesQuote() throws Exception {
        // Initialize the database
        salesQuoteRepository.saveAndFlush(salesQuote);
        int databaseSizeBeforeUpdate = salesQuoteRepository.findAll().size();

        // Update the salesQuote
        SalesQuote updatedSalesQuote = salesQuoteRepository.findOne(salesQuote.getId());
        updatedSalesQuote
                .quotedAt(UPDATED_QUOTED_AT);
        SalesQuoteDTO salesQuoteDTO = salesQuoteMapper.salesQuoteToSalesQuoteDTO(updatedSalesQuote);

        restSalesQuoteMockMvc.perform(put("/api/sales-quotes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salesQuoteDTO)))
            .andExpect(status().isOk());

        // Validate the SalesQuote in the database
        List<SalesQuote> salesQuotes = salesQuoteRepository.findAll();
        assertThat(salesQuotes).hasSize(databaseSizeBeforeUpdate);
        SalesQuote testSalesQuote = salesQuotes.get(salesQuotes.size() - 1);
        assertThat(testSalesQuote.getQuotedAt()).isEqualTo(UPDATED_QUOTED_AT);
    }

    @Test
    @Transactional
    public void deleteSalesQuote() throws Exception {
        // Initialize the database
        salesQuoteRepository.saveAndFlush(salesQuote);
        int databaseSizeBeforeDelete = salesQuoteRepository.findAll().size();

        // Get the salesQuote
        restSalesQuoteMockMvc.perform(delete("/api/sales-quotes/{id}", salesQuote.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SalesQuote> salesQuotes = salesQuoteRepository.findAll();
        assertThat(salesQuotes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
