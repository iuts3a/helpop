package org.iuts3a.service.impl;

import org.iuts3a.service.SalesOrderItemService;
import org.iuts3a.domain.SalesOrderItem;
import org.iuts3a.repository.SalesOrderItemRepository;
import org.iuts3a.service.dto.SalesOrderItemDTO;
import org.iuts3a.service.mapper.SalesOrderItemMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing SalesOrderItem.
 */
@Service
@Transactional
public class SalesOrderItemServiceImpl implements SalesOrderItemService{

    private final Logger log = LoggerFactory.getLogger(SalesOrderItemServiceImpl.class);
    
    @Inject
    private SalesOrderItemRepository salesOrderItemRepository;

    @Inject
    private SalesOrderItemMapper salesOrderItemMapper;

    /**
     * Save a salesOrderItem.
     *
     * @param salesOrderItemDTO the entity to save
     * @return the persisted entity
     */
    public SalesOrderItemDTO save(SalesOrderItemDTO salesOrderItemDTO) {
        log.debug("Request to save SalesOrderItem : {}", salesOrderItemDTO);
        SalesOrderItem salesOrderItem = salesOrderItemMapper.salesOrderItemDTOToSalesOrderItem(salesOrderItemDTO);
        salesOrderItem = salesOrderItemRepository.save(salesOrderItem);
        SalesOrderItemDTO result = salesOrderItemMapper.salesOrderItemToSalesOrderItemDTO(salesOrderItem);
        return result;
    }

    /**
     *  Get all the salesOrderItems.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<SalesOrderItemDTO> findAll() {
        log.debug("Request to get all SalesOrderItems");
        List<SalesOrderItemDTO> result = salesOrderItemRepository.findAll().stream()
            .map(salesOrderItemMapper::salesOrderItemToSalesOrderItemDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one salesOrderItem by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public SalesOrderItemDTO findOne(Long id) {
        log.debug("Request to get SalesOrderItem : {}", id);
        SalesOrderItem salesOrderItem = salesOrderItemRepository.findOne(id);
        SalesOrderItemDTO salesOrderItemDTO = salesOrderItemMapper.salesOrderItemToSalesOrderItemDTO(salesOrderItem);
        return salesOrderItemDTO;
    }

    /**
     *  Delete the  salesOrderItem by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete SalesOrderItem : {}", id);
        salesOrderItemRepository.delete(id);
    }
}
