package org.iuts3a.service.impl;

import org.iuts3a.service.OfferService;
import org.iuts3a.domain.Offer;
import org.iuts3a.repository.OfferRepository;
import org.iuts3a.service.dto.OfferDTO;
import org.iuts3a.service.mapper.OfferMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Offer.
 */
@Service
@Transactional
public class OfferServiceImpl implements OfferService{

    private final Logger log = LoggerFactory.getLogger(OfferServiceImpl.class);
    
    @Inject
    private OfferRepository offerRepository;

    @Inject
    private OfferMapper offerMapper;

    /**
     * Save a offer.
     *
     * @param offerDTO the entity to save
     * @return the persisted entity
     */
    public OfferDTO save(OfferDTO offerDTO) {
        log.debug("Request to save Offer : {}", offerDTO);
        Offer offer = offerMapper.offerDTOToOffer(offerDTO);
        offer = offerRepository.save(offer);
        OfferDTO result = offerMapper.offerToOfferDTO(offer);
        return result;
    }

    /**
     *  Get all the offers.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<OfferDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Offers");
        Page<Offer> result = offerRepository.findAll(pageable);
        return result.map(offer -> offerMapper.offerToOfferDTO(offer));
    }

    /**
     *  Get one offer by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public OfferDTO findOne(Long id) {
        log.debug("Request to get Offer : {}", id);
        Offer offer = offerRepository.findOne(id);
        OfferDTO offerDTO = offerMapper.offerToOfferDTO(offer);
        return offerDTO;
    }

    /**
     *  Delete the  offer by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Offer : {}", id);
        offerRepository.delete(id);
    }
}
