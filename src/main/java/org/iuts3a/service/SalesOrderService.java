package org.iuts3a.service;

import org.iuts3a.service.dto.SalesOrderDTO;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing SalesOrder.
 */
public interface SalesOrderService {

    /**
     * Save a salesOrder.
     *
     * @param salesOrderDTO the entity to save
     * @return the persisted entity
     */
    SalesOrderDTO save(SalesOrderDTO salesOrderDTO);

    /**
     *  Get all the salesOrders.
     *  
     *  @return the list of entities
     */
    List<SalesOrderDTO> findAll();

    /**
     *  Get the "id" salesOrder.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    SalesOrderDTO findOne(Long id);

    /**
     *  Delete the "id" salesOrder.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
