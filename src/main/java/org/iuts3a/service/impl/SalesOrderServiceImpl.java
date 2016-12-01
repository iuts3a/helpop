package org.iuts3a.service.impl;

import org.iuts3a.service.SalesOrderService;
import org.iuts3a.domain.SalesOrder;
import org.iuts3a.repository.SalesOrderRepository;
import org.iuts3a.service.dto.SalesOrderDTO;
import org.iuts3a.service.mapper.SalesOrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing SalesOrder.
 */
@Service
@Transactional
public class SalesOrderServiceImpl implements SalesOrderService{

    private final Logger log = LoggerFactory.getLogger(SalesOrderServiceImpl.class);
    
    @Inject
    private SalesOrderRepository salesOrderRepository;

    @Inject
    private SalesOrderMapper salesOrderMapper;

    /**
     * Save a salesOrder.
     *
     * @param salesOrderDTO the entity to save
     * @return the persisted entity
     */
    public SalesOrderDTO save(SalesOrderDTO salesOrderDTO) {
        log.debug("Request to save SalesOrder : {}", salesOrderDTO);
        SalesOrder salesOrder = salesOrderMapper.salesOrderDTOToSalesOrder(salesOrderDTO);
        salesOrder = salesOrderRepository.save(salesOrder);
        SalesOrderDTO result = salesOrderMapper.salesOrderToSalesOrderDTO(salesOrder);
        return result;
    }

    /**
     *  Get all the salesOrders.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<SalesOrderDTO> findAll() {
        log.debug("Request to get all SalesOrders");
        List<SalesOrderDTO> result = salesOrderRepository.findAll().stream()
            .map(salesOrderMapper::salesOrderToSalesOrderDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one salesOrder by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public SalesOrderDTO findOne(Long id) {
        log.debug("Request to get SalesOrder : {}", id);
        SalesOrder salesOrder = salesOrderRepository.findOne(id);
        SalesOrderDTO salesOrderDTO = salesOrderMapper.salesOrderToSalesOrderDTO(salesOrder);
        return salesOrderDTO;
    }

    /**
     *  Delete the  salesOrder by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete SalesOrder : {}", id);
        salesOrderRepository.delete(id);
    }
}
