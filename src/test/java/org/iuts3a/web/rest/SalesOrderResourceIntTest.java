package org.iuts3a.web.rest;

import org.iuts3a.HelpopApp;

import org.iuts3a.domain.SalesOrder;
import org.iuts3a.repository.SalesOrderRepository;
import org.iuts3a.service.SalesOrderService;
import org.iuts3a.service.dto.SalesOrderDTO;
import org.iuts3a.service.mapper.SalesOrderMapper;

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
 * Test class for the SalesOrderResource REST controller.
 *
 * @see SalesOrderResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HelpopApp.class)
public class SalesOrderResourceIntTest {

    private static final ZonedDateTime DEFAULT_ORDERED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ORDERED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Inject
    private SalesOrderRepository salesOrderRepository;

    @Inject
    private SalesOrderMapper salesOrderMapper;

    @Inject
    private SalesOrderService salesOrderService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restSalesOrderMockMvc;

    private SalesOrder salesOrder;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SalesOrderResource salesOrderResource = new SalesOrderResource();
        ReflectionTestUtils.setField(salesOrderResource, "salesOrderService", salesOrderService);
        this.restSalesOrderMockMvc = MockMvcBuilders.standaloneSetup(salesOrderResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SalesOrder createEntity(EntityManager em) {
        SalesOrder salesOrder = new SalesOrder()
                .orderedAt(DEFAULT_ORDERED_AT);
        return salesOrder;
    }

    @Before
    public void initTest() {
        salesOrder = createEntity(em);
    }

    @Test
    @Transactional
    public void createSalesOrder() throws Exception {
        int databaseSizeBeforeCreate = salesOrderRepository.findAll().size();

        // Create the SalesOrder
        SalesOrderDTO salesOrderDTO = salesOrderMapper.salesOrderToSalesOrderDTO(salesOrder);

        restSalesOrderMockMvc.perform(post("/api/sales-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salesOrderDTO)))
            .andExpect(status().isCreated());

        // Validate the SalesOrder in the database
        List<SalesOrder> salesOrders = salesOrderRepository.findAll();
        assertThat(salesOrders).hasSize(databaseSizeBeforeCreate + 1);
        SalesOrder testSalesOrder = salesOrders.get(salesOrders.size() - 1);
        assertThat(testSalesOrder.getOrderedAt()).isEqualTo(DEFAULT_ORDERED_AT);
    }

    @Test
    @Transactional
    public void checkOrderedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = salesOrderRepository.findAll().size();
        // set the field null
        salesOrder.setOrderedAt(null);

        // Create the SalesOrder, which fails.
        SalesOrderDTO salesOrderDTO = salesOrderMapper.salesOrderToSalesOrderDTO(salesOrder);

        restSalesOrderMockMvc.perform(post("/api/sales-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salesOrderDTO)))
            .andExpect(status().isBadRequest());

        List<SalesOrder> salesOrders = salesOrderRepository.findAll();
        assertThat(salesOrders).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSalesOrders() throws Exception {
        // Initialize the database
        salesOrderRepository.saveAndFlush(salesOrder);

        // Get all the salesOrders
        restSalesOrderMockMvc.perform(get("/api/sales-orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(salesOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].orderedAt").value(hasItem(sameInstant(DEFAULT_ORDERED_AT))));
    }

    @Test
    @Transactional
    public void getSalesOrder() throws Exception {
        // Initialize the database
        salesOrderRepository.saveAndFlush(salesOrder);

        // Get the salesOrder
        restSalesOrderMockMvc.perform(get("/api/sales-orders/{id}", salesOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(salesOrder.getId().intValue()))
            .andExpect(jsonPath("$.orderedAt").value(sameInstant(DEFAULT_ORDERED_AT)));
    }

    @Test
    @Transactional
    public void getNonExistingSalesOrder() throws Exception {
        // Get the salesOrder
        restSalesOrderMockMvc.perform(get("/api/sales-orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSalesOrder() throws Exception {
        // Initialize the database
        salesOrderRepository.saveAndFlush(salesOrder);
        int databaseSizeBeforeUpdate = salesOrderRepository.findAll().size();

        // Update the salesOrder
        SalesOrder updatedSalesOrder = salesOrderRepository.findOne(salesOrder.getId());
        updatedSalesOrder
                .orderedAt(UPDATED_ORDERED_AT);
        SalesOrderDTO salesOrderDTO = salesOrderMapper.salesOrderToSalesOrderDTO(updatedSalesOrder);

        restSalesOrderMockMvc.perform(put("/api/sales-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salesOrderDTO)))
            .andExpect(status().isOk());

        // Validate the SalesOrder in the database
        List<SalesOrder> salesOrders = salesOrderRepository.findAll();
        assertThat(salesOrders).hasSize(databaseSizeBeforeUpdate);
        SalesOrder testSalesOrder = salesOrders.get(salesOrders.size() - 1);
        assertThat(testSalesOrder.getOrderedAt()).isEqualTo(UPDATED_ORDERED_AT);
    }

    @Test
    @Transactional
    public void deleteSalesOrder() throws Exception {
        // Initialize the database
        salesOrderRepository.saveAndFlush(salesOrder);
        int databaseSizeBeforeDelete = salesOrderRepository.findAll().size();

        // Get the salesOrder
        restSalesOrderMockMvc.perform(delete("/api/sales-orders/{id}", salesOrder.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SalesOrder> salesOrders = salesOrderRepository.findAll();
        assertThat(salesOrders).hasSize(databaseSizeBeforeDelete - 1);
    }
}
