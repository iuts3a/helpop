package org.iuts3a.web.rest;

import org.iuts3a.HelpopApp;

import org.iuts3a.domain.SalesOrderItem;
import org.iuts3a.repository.SalesOrderItemRepository;
import org.iuts3a.service.SalesOrderItemService;
import org.iuts3a.service.dto.SalesOrderItemDTO;
import org.iuts3a.service.mapper.SalesOrderItemMapper;

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
 * Test class for the SalesOrderItemResource REST controller.
 *
 * @see SalesOrderItemResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HelpopApp.class)
public class SalesOrderItemResourceIntTest {

    private static final ZonedDateTime DEFAULT_CREATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Inject
    private SalesOrderItemRepository salesOrderItemRepository;

    @Inject
    private SalesOrderItemMapper salesOrderItemMapper;

    @Inject
    private SalesOrderItemService salesOrderItemService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restSalesOrderItemMockMvc;

    private SalesOrderItem salesOrderItem;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SalesOrderItemResource salesOrderItemResource = new SalesOrderItemResource();
        ReflectionTestUtils.setField(salesOrderItemResource, "salesOrderItemService", salesOrderItemService);
        this.restSalesOrderItemMockMvc = MockMvcBuilders.standaloneSetup(salesOrderItemResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SalesOrderItem createEntity(EntityManager em) {
        SalesOrderItem salesOrderItem = new SalesOrderItem()
                .createdAt(DEFAULT_CREATED_AT)
                .updatedAt(DEFAULT_UPDATED_AT);
        return salesOrderItem;
    }

    @Before
    public void initTest() {
        salesOrderItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createSalesOrderItem() throws Exception {
        int databaseSizeBeforeCreate = salesOrderItemRepository.findAll().size();

        // Create the SalesOrderItem
        SalesOrderItemDTO salesOrderItemDTO = salesOrderItemMapper.salesOrderItemToSalesOrderItemDTO(salesOrderItem);

        restSalesOrderItemMockMvc.perform(post("/api/sales-order-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salesOrderItemDTO)))
            .andExpect(status().isCreated());

        // Validate the SalesOrderItem in the database
        List<SalesOrderItem> salesOrderItems = salesOrderItemRepository.findAll();
        assertThat(salesOrderItems).hasSize(databaseSizeBeforeCreate + 1);
        SalesOrderItem testSalesOrderItem = salesOrderItems.get(salesOrderItems.size() - 1);
        assertThat(testSalesOrderItem.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testSalesOrderItem.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    public void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = salesOrderItemRepository.findAll().size();
        // set the field null
        salesOrderItem.setCreatedAt(null);

        // Create the SalesOrderItem, which fails.
        SalesOrderItemDTO salesOrderItemDTO = salesOrderItemMapper.salesOrderItemToSalesOrderItemDTO(salesOrderItem);

        restSalesOrderItemMockMvc.perform(post("/api/sales-order-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salesOrderItemDTO)))
            .andExpect(status().isBadRequest());

        List<SalesOrderItem> salesOrderItems = salesOrderItemRepository.findAll();
        assertThat(salesOrderItems).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSalesOrderItems() throws Exception {
        // Initialize the database
        salesOrderItemRepository.saveAndFlush(salesOrderItem);

        // Get all the salesOrderItems
        restSalesOrderItemMockMvc.perform(get("/api/sales-order-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(salesOrderItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(sameInstant(DEFAULT_UPDATED_AT))));
    }

    @Test
    @Transactional
    public void getSalesOrderItem() throws Exception {
        // Initialize the database
        salesOrderItemRepository.saveAndFlush(salesOrderItem);

        // Get the salesOrderItem
        restSalesOrderItemMockMvc.perform(get("/api/sales-order-items/{id}", salesOrderItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(salesOrderItem.getId().intValue()))
            .andExpect(jsonPath("$.createdAt").value(sameInstant(DEFAULT_CREATED_AT)))
            .andExpect(jsonPath("$.updatedAt").value(sameInstant(DEFAULT_UPDATED_AT)));
    }

    @Test
    @Transactional
    public void getNonExistingSalesOrderItem() throws Exception {
        // Get the salesOrderItem
        restSalesOrderItemMockMvc.perform(get("/api/sales-order-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSalesOrderItem() throws Exception {
        // Initialize the database
        salesOrderItemRepository.saveAndFlush(salesOrderItem);
        int databaseSizeBeforeUpdate = salesOrderItemRepository.findAll().size();

        // Update the salesOrderItem
        SalesOrderItem updatedSalesOrderItem = salesOrderItemRepository.findOne(salesOrderItem.getId());
        updatedSalesOrderItem
                .createdAt(UPDATED_CREATED_AT)
                .updatedAt(UPDATED_UPDATED_AT);
        SalesOrderItemDTO salesOrderItemDTO = salesOrderItemMapper.salesOrderItemToSalesOrderItemDTO(updatedSalesOrderItem);

        restSalesOrderItemMockMvc.perform(put("/api/sales-order-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salesOrderItemDTO)))
            .andExpect(status().isOk());

        // Validate the SalesOrderItem in the database
        List<SalesOrderItem> salesOrderItems = salesOrderItemRepository.findAll();
        assertThat(salesOrderItems).hasSize(databaseSizeBeforeUpdate);
        SalesOrderItem testSalesOrderItem = salesOrderItems.get(salesOrderItems.size() - 1);
        assertThat(testSalesOrderItem.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testSalesOrderItem.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void deleteSalesOrderItem() throws Exception {
        // Initialize the database
        salesOrderItemRepository.saveAndFlush(salesOrderItem);
        int databaseSizeBeforeDelete = salesOrderItemRepository.findAll().size();

        // Get the salesOrderItem
        restSalesOrderItemMockMvc.perform(delete("/api/sales-order-items/{id}", salesOrderItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SalesOrderItem> salesOrderItems = salesOrderItemRepository.findAll();
        assertThat(salesOrderItems).hasSize(databaseSizeBeforeDelete - 1);
    }
}
