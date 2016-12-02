package org.iuts3a.web.rest;

import org.iuts3a.HelpopApp;

import org.iuts3a.domain.Offer;
import org.iuts3a.repository.OfferRepository;
import org.iuts3a.service.OfferService;
import org.iuts3a.service.dto.OfferDTO;
import org.iuts3a.service.mapper.OfferMapper;

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

import org.iuts3a.domain.enumeration.OfferTimeType;
import org.iuts3a.domain.enumeration.OfferType;
/**
 * Test class for the OfferResource REST controller.
 *
 * @see OfferResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HelpopApp.class)
public class OfferResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_PRICE_USD = 1D;
    private static final Double UPDATED_PRICE_USD = 2D;

    private static final Double DEFAULT_DISCOUNT_PERCENT = 1D;
    private static final Double UPDATED_DISCOUNT_PERCENT = 2D;

    private static final ZonedDateTime DEFAULT_CREATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_LIMIT_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LIMIT_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Float DEFAULT_GRADE = 1F;
    private static final Float UPDATED_GRADE = 2F;

    private static final OfferTimeType DEFAULT_TIME_TYPE = OfferTimeType.TEMPORARY;
    private static final OfferTimeType UPDATED_TIME_TYPE = OfferTimeType.NOTIMELIMIT;

    private static final OfferType DEFAULT_OFFER_TYPE = OfferType.SERVICE;
    private static final OfferType UPDATED_OFFER_TYPE = OfferType.PRODUCT;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Inject
    private OfferRepository offerRepository;

    @Inject
    private OfferMapper offerMapper;

    @Inject
    private OfferService offerService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restOfferMockMvc;

    private Offer offer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        OfferResource offerResource = new OfferResource();
        ReflectionTestUtils.setField(offerResource, "offerService", offerService);
        this.restOfferMockMvc = MockMvcBuilders.standaloneSetup(offerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Offer createEntity(EntityManager em) {
        Offer offer = new Offer()
                .name(DEFAULT_NAME)
                .priceUSD(DEFAULT_PRICE_USD)
                .discountPercent(DEFAULT_DISCOUNT_PERCENT)
                .createdAt(DEFAULT_CREATED_AT)
                .limitAt(DEFAULT_LIMIT_AT)
                .grade(DEFAULT_GRADE)
                .timeType(DEFAULT_TIME_TYPE)
                .offerType(DEFAULT_OFFER_TYPE)
                .description(DEFAULT_DESCRIPTION);
        return offer;
    }

    @Before
    public void initTest() {
        offer = createEntity(em);
    }

    @Test
    @Transactional
    public void createOffer() throws Exception {
        int databaseSizeBeforeCreate = offerRepository.findAll().size();

        // Create the Offer
        OfferDTO offerDTO = offerMapper.offerToOfferDTO(offer);

        restOfferMockMvc.perform(post("/api/offers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(offerDTO)))
            .andExpect(status().isCreated());

        // Validate the Offer in the database
        List<Offer> offers = offerRepository.findAll();
        assertThat(offers).hasSize(databaseSizeBeforeCreate + 1);
        Offer testOffer = offers.get(offers.size() - 1);
        assertThat(testOffer.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOffer.getPriceUSD()).isEqualTo(DEFAULT_PRICE_USD);
        assertThat(testOffer.getDiscountPercent()).isEqualTo(DEFAULT_DISCOUNT_PERCENT);
        assertThat(testOffer.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testOffer.getLimitAt()).isEqualTo(DEFAULT_LIMIT_AT);
        assertThat(testOffer.getGrade()).isEqualTo(DEFAULT_GRADE);
        assertThat(testOffer.getTimeType()).isEqualTo(DEFAULT_TIME_TYPE);
        assertThat(testOffer.getOfferType()).isEqualTo(DEFAULT_OFFER_TYPE);
        assertThat(testOffer.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = offerRepository.findAll().size();
        // set the field null
        offer.setName(null);

        // Create the Offer, which fails.
        OfferDTO offerDTO = offerMapper.offerToOfferDTO(offer);

        restOfferMockMvc.perform(post("/api/offers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(offerDTO)))
            .andExpect(status().isBadRequest());

        List<Offer> offers = offerRepository.findAll();
        assertThat(offers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriceUSDIsRequired() throws Exception {
        int databaseSizeBeforeTest = offerRepository.findAll().size();
        // set the field null
        offer.setPriceUSD(null);

        // Create the Offer, which fails.
        OfferDTO offerDTO = offerMapper.offerToOfferDTO(offer);

        restOfferMockMvc.perform(post("/api/offers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(offerDTO)))
            .andExpect(status().isBadRequest());

        List<Offer> offers = offerRepository.findAll();
        assertThat(offers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = offerRepository.findAll().size();
        // set the field null
        offer.setCreatedAt(null);

        // Create the Offer, which fails.
        OfferDTO offerDTO = offerMapper.offerToOfferDTO(offer);

        restOfferMockMvc.perform(post("/api/offers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(offerDTO)))
            .andExpect(status().isBadRequest());

        List<Offer> offers = offerRepository.findAll();
        assertThat(offers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTimeTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = offerRepository.findAll().size();
        // set the field null
        offer.setTimeType(null);

        // Create the Offer, which fails.
        OfferDTO offerDTO = offerMapper.offerToOfferDTO(offer);

        restOfferMockMvc.perform(post("/api/offers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(offerDTO)))
            .andExpect(status().isBadRequest());

        List<Offer> offers = offerRepository.findAll();
        assertThat(offers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOfferTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = offerRepository.findAll().size();
        // set the field null
        offer.setOfferType(null);

        // Create the Offer, which fails.
        OfferDTO offerDTO = offerMapper.offerToOfferDTO(offer);

        restOfferMockMvc.perform(post("/api/offers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(offerDTO)))
            .andExpect(status().isBadRequest());

        List<Offer> offers = offerRepository.findAll();
        assertThat(offers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOffers() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offers
        restOfferMockMvc.perform(get("/api/offers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(offer.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].priceUSD").value(hasItem(DEFAULT_PRICE_USD.doubleValue())))
            .andExpect(jsonPath("$.[*].discountPercent").value(hasItem(DEFAULT_DISCOUNT_PERCENT.doubleValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))))
            .andExpect(jsonPath("$.[*].limitAt").value(hasItem(sameInstant(DEFAULT_LIMIT_AT))))
            .andExpect(jsonPath("$.[*].grade").value(hasItem(DEFAULT_GRADE.doubleValue())))
            .andExpect(jsonPath("$.[*].timeType").value(hasItem(DEFAULT_TIME_TYPE.toString())))
            .andExpect(jsonPath("$.[*].offerType").value(hasItem(DEFAULT_OFFER_TYPE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getOffer() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get the offer
        restOfferMockMvc.perform(get("/api/offers/{id}", offer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(offer.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.priceUSD").value(DEFAULT_PRICE_USD.doubleValue()))
            .andExpect(jsonPath("$.discountPercent").value(DEFAULT_DISCOUNT_PERCENT.doubleValue()))
            .andExpect(jsonPath("$.createdAt").value(sameInstant(DEFAULT_CREATED_AT)))
            .andExpect(jsonPath("$.limitAt").value(sameInstant(DEFAULT_LIMIT_AT)))
            .andExpect(jsonPath("$.grade").value(DEFAULT_GRADE.doubleValue()))
            .andExpect(jsonPath("$.timeType").value(DEFAULT_TIME_TYPE.toString()))
            .andExpect(jsonPath("$.offerType").value(DEFAULT_OFFER_TYPE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOffer() throws Exception {
        // Get the offer
        restOfferMockMvc.perform(get("/api/offers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOffer() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);
        int databaseSizeBeforeUpdate = offerRepository.findAll().size();

        // Update the offer
        Offer updatedOffer = offerRepository.findOne(offer.getId());
        updatedOffer
                .name(UPDATED_NAME)
                .priceUSD(UPDATED_PRICE_USD)
                .discountPercent(UPDATED_DISCOUNT_PERCENT)
                .createdAt(UPDATED_CREATED_AT)
                .limitAt(UPDATED_LIMIT_AT)
                .grade(UPDATED_GRADE)
                .timeType(UPDATED_TIME_TYPE)
                .offerType(UPDATED_OFFER_TYPE)
                .description(UPDATED_DESCRIPTION);
        OfferDTO offerDTO = offerMapper.offerToOfferDTO(updatedOffer);

        restOfferMockMvc.perform(put("/api/offers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(offerDTO)))
            .andExpect(status().isOk());

        // Validate the Offer in the database
        List<Offer> offers = offerRepository.findAll();
        assertThat(offers).hasSize(databaseSizeBeforeUpdate);
        Offer testOffer = offers.get(offers.size() - 1);
        assertThat(testOffer.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOffer.getPriceUSD()).isEqualTo(UPDATED_PRICE_USD);
        assertThat(testOffer.getDiscountPercent()).isEqualTo(UPDATED_DISCOUNT_PERCENT);
        assertThat(testOffer.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testOffer.getLimitAt()).isEqualTo(UPDATED_LIMIT_AT);
        assertThat(testOffer.getGrade()).isEqualTo(UPDATED_GRADE);
        assertThat(testOffer.getTimeType()).isEqualTo(UPDATED_TIME_TYPE);
        assertThat(testOffer.getOfferType()).isEqualTo(UPDATED_OFFER_TYPE);
        assertThat(testOffer.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteOffer() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);
        int databaseSizeBeforeDelete = offerRepository.findAll().size();

        // Get the offer
        restOfferMockMvc.perform(delete("/api/offers/{id}", offer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Offer> offers = offerRepository.findAll();
        assertThat(offers).hasSize(databaseSizeBeforeDelete - 1);
    }
}
