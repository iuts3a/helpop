package org.iuts3a.web.rest;

import org.iuts3a.HelpopApp;

import org.iuts3a.domain.SalesQuoteItem;
import org.iuts3a.repository.SalesQuoteItemRepository;
import org.iuts3a.service.SalesQuoteItemService;
import org.iuts3a.service.dto.SalesQuoteItemDTO;
import org.iuts3a.service.mapper.SalesQuoteItemMapper;

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
 * Test class for the SalesQuoteItemResource REST controller.
 *
 * @see SalesQuoteItemResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HelpopApp.class)
public class SalesQuoteItemResourceIntTest {

    private static final ZonedDateTime DEFAULT_CREATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Inject
    private SalesQuoteItemRepository salesQuoteItemRepository;

    @Inject
    private SalesQuoteItemMapper salesQuoteItemMapper;

    @Inject
    private SalesQuoteItemService salesQuoteItemService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restSalesQuoteItemMockMvc;

    private SalesQuoteItem salesQuoteItem;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SalesQuoteItemResource salesQuoteItemResource = new SalesQuoteItemResource();
        ReflectionTestUtils.setField(salesQuoteItemResource, "salesQuoteItemService", salesQuoteItemService);
        this.restSalesQuoteItemMockMvc = MockMvcBuilders.standaloneSetup(salesQuoteItemResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SalesQuoteItem createEntity(EntityManager em) {
        SalesQuoteItem salesQuoteItem = new SalesQuoteItem()
                .createdAt(DEFAULT_CREATED_AT)
                .updatedAt(DEFAULT_UPDATED_AT);
        return salesQuoteItem;
    }

    @Before
    public void initTest() {
        salesQuoteItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createSalesQuoteItem() throws Exception {
        int databaseSizeBeforeCreate = salesQuoteItemRepository.findAll().size();

        // Create the SalesQuoteItem
        SalesQuoteItemDTO salesQuoteItemDTO = salesQuoteItemMapper.salesQuoteItemToSalesQuoteItemDTO(salesQuoteItem);

        restSalesQuoteItemMockMvc.perform(post("/api/sales-quote-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salesQuoteItemDTO)))
            .andExpect(status().isCreated());

        // Validate the SalesQuoteItem in the database
        List<SalesQuoteItem> salesQuoteItems = salesQuoteItemRepository.findAll();
        assertThat(salesQuoteItems).hasSize(databaseSizeBeforeCreate + 1);
        SalesQuoteItem testSalesQuoteItem = salesQuoteItems.get(salesQuoteItems.size() - 1);
        assertThat(testSalesQuoteItem.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testSalesQuoteItem.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    public void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = salesQuoteItemRepository.findAll().size();
        // set the field null
        salesQuoteItem.setCreatedAt(null);

        // Create the SalesQuoteItem, which fails.
        SalesQuoteItemDTO salesQuoteItemDTO = salesQuoteItemMapper.salesQuoteItemToSalesQuoteItemDTO(salesQuoteItem);

        restSalesQuoteItemMockMvc.perform(post("/api/sales-quote-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salesQuoteItemDTO)))
            .andExpect(status().isBadRequest());

        List<SalesQuoteItem> salesQuoteItems = salesQuoteItemRepository.findAll();
        assertThat(salesQuoteItems).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSalesQuoteItems() throws Exception {
        // Initialize the database
        salesQuoteItemRepository.saveAndFlush(salesQuoteItem);

        // Get all the salesQuoteItems
        restSalesQuoteItemMockMvc.perform(get("/api/sales-quote-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(salesQuoteItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(sameInstant(DEFAULT_UPDATED_AT))));
    }

    @Test
    @Transactional
    public void getSalesQuoteItem() throws Exception {
        // Initialize the database
        salesQuoteItemRepository.saveAndFlush(salesQuoteItem);

        // Get the salesQuoteItem
        restSalesQuoteItemMockMvc.perform(get("/api/sales-quote-items/{id}", salesQuoteItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(salesQuoteItem.getId().intValue()))
            .andExpect(jsonPath("$.createdAt").value(sameInstant(DEFAULT_CREATED_AT)))
            .andExpect(jsonPath("$.updatedAt").value(sameInstant(DEFAULT_UPDATED_AT)));
    }

    @Test
    @Transactional
    public void getNonExistingSalesQuoteItem() throws Exception {
        // Get the salesQuoteItem
        restSalesQuoteItemMockMvc.perform(get("/api/sales-quote-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSalesQuoteItem() throws Exception {
        // Initialize the database
        salesQuoteItemRepository.saveAndFlush(salesQuoteItem);
        int databaseSizeBeforeUpdate = salesQuoteItemRepository.findAll().size();

        // Update the salesQuoteItem
        SalesQuoteItem updatedSalesQuoteItem = salesQuoteItemRepository.findOne(salesQuoteItem.getId());
        updatedSalesQuoteItem
                .createdAt(UPDATED_CREATED_AT)
                .updatedAt(UPDATED_UPDATED_AT);
        SalesQuoteItemDTO salesQuoteItemDTO = salesQuoteItemMapper.salesQuoteItemToSalesQuoteItemDTO(updatedSalesQuoteItem);

        restSalesQuoteItemMockMvc.perform(put("/api/sales-quote-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salesQuoteItemDTO)))
            .andExpect(status().isOk());

        // Validate the SalesQuoteItem in the database
        List<SalesQuoteItem> salesQuoteItems = salesQuoteItemRepository.findAll();
        assertThat(salesQuoteItems).hasSize(databaseSizeBeforeUpdate);
        SalesQuoteItem testSalesQuoteItem = salesQuoteItems.get(salesQuoteItems.size() - 1);
        assertThat(testSalesQuoteItem.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testSalesQuoteItem.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void deleteSalesQuoteItem() throws Exception {
        // Initialize the database
        salesQuoteItemRepository.saveAndFlush(salesQuoteItem);
        int databaseSizeBeforeDelete = salesQuoteItemRepository.findAll().size();

        // Get the salesQuoteItem
        restSalesQuoteItemMockMvc.perform(delete("/api/sales-quote-items/{id}", salesQuoteItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SalesQuoteItem> salesQuoteItems = salesQuoteItemRepository.findAll();
        assertThat(salesQuoteItems).hasSize(databaseSizeBeforeDelete - 1);
    }
}
