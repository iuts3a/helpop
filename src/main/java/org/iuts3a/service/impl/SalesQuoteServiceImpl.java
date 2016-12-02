package org.iuts3a.service.impl;

import org.iuts3a.service.SalesQuoteService;
import org.iuts3a.domain.SalesQuote;
import org.iuts3a.repository.SalesQuoteRepository;
import org.iuts3a.service.dto.SalesQuoteDTO;
import org.iuts3a.service.mapper.SalesQuoteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing SalesQuote.
 */
@Service
@Transactional
public class SalesQuoteServiceImpl implements SalesQuoteService{

    private final Logger log = LoggerFactory.getLogger(SalesQuoteServiceImpl.class);
    
    @Inject
    private SalesQuoteRepository salesQuoteRepository;

    @Inject
    private SalesQuoteMapper salesQuoteMapper;

    /**
     * Save a salesQuote.
     *
     * @param salesQuoteDTO the entity to save
     * @return the persisted entity
     */
    public SalesQuoteDTO save(SalesQuoteDTO salesQuoteDTO) {
        log.debug("Request to save SalesQuote : {}", salesQuoteDTO);
        SalesQuote salesQuote = salesQuoteMapper.salesQuoteDTOToSalesQuote(salesQuoteDTO);
        salesQuote = salesQuoteRepository.save(salesQuote);
        SalesQuoteDTO result = salesQuoteMapper.salesQuoteToSalesQuoteDTO(salesQuote);
        return result;
    }

    /**
     *  Get all the salesQuotes.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<SalesQuoteDTO> findAll() {
        log.debug("Request to get all SalesQuotes");
        List<SalesQuoteDTO> result = salesQuoteRepository.findAll().stream()
            .map(salesQuoteMapper::salesQuoteToSalesQuoteDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one salesQuote by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public SalesQuoteDTO findOne(Long id) {
        log.debug("Request to get SalesQuote : {}", id);
        SalesQuote salesQuote = salesQuoteRepository.findOne(id);
        SalesQuoteDTO salesQuoteDTO = salesQuoteMapper.salesQuoteToSalesQuoteDTO(salesQuote);
        return salesQuoteDTO;
    }

    /**
     *  Delete the  salesQuote by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete SalesQuote : {}", id);
        salesQuoteRepository.delete(id);
    }
}
