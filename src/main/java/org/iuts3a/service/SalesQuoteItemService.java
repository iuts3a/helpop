package org.iuts3a.service;

import org.iuts3a.service.dto.SalesQuoteItemDTO;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing SalesQuoteItem.
 */
public interface SalesQuoteItemService {

    /**
     * Save a salesQuoteItem.
     *
     * @param salesQuoteItemDTO the entity to save
     * @return the persisted entity
     */
    SalesQuoteItemDTO save(SalesQuoteItemDTO salesQuoteItemDTO);

    /**
     *  Get all the salesQuoteItems.
     *  
     *  @return the list of entities
     */
    List<SalesQuoteItemDTO> findAll();

    /**
     *  Get the "id" salesQuoteItem.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    SalesQuoteItemDTO findOne(Long id);

    /**
     *  Delete the "id" salesQuoteItem.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
