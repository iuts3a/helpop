package org.iuts3a.service;

import org.iuts3a.service.dto.SalesOrderItemDTO;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing SalesOrderItem.
 */
public interface SalesOrderItemService {

    /**
     * Save a salesOrderItem.
     *
     * @param salesOrderItemDTO the entity to save
     * @return the persisted entity
     */
    SalesOrderItemDTO save(SalesOrderItemDTO salesOrderItemDTO);

    /**
     *  Get all the salesOrderItems.
     *  
     *  @return the list of entities
     */
    List<SalesOrderItemDTO> findAll();

    /**
     *  Get the "id" salesOrderItem.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    SalesOrderItemDTO findOne(Long id);

    /**
     *  Delete the "id" salesOrderItem.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
