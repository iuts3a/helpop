package org.iuts3a.service;

import org.iuts3a.service.dto.SalesQuoteDTO;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing SalesQuote.
 */
public interface SalesQuoteService {

    /**
     * Save a salesQuote.
     *
     * @param salesQuoteDTO the entity to save
     * @return the persisted entity
     */
    SalesQuoteDTO save(SalesQuoteDTO salesQuoteDTO);

    /**
     *  Get all the salesQuotes.
     *  
     *  @return the list of entities
     */
    List<SalesQuoteDTO> findAll();

    /**
     *  Get the "id" salesQuote.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    SalesQuoteDTO findOne(Long id);

    /**
     *  Delete the "id" salesQuote.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
