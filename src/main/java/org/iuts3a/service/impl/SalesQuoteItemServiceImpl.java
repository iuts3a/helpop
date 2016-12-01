package org.iuts3a.service.impl;

import org.iuts3a.service.SalesQuoteItemService;
import org.iuts3a.domain.SalesQuoteItem;
import org.iuts3a.repository.SalesQuoteItemRepository;
import org.iuts3a.service.dto.SalesQuoteItemDTO;
import org.iuts3a.service.mapper.SalesQuoteItemMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing SalesQuoteItem.
 */
@Service
@Transactional
public class SalesQuoteItemServiceImpl implements SalesQuoteItemService{

    private final Logger log = LoggerFactory.getLogger(SalesQuoteItemServiceImpl.class);
    
    @Inject
    private SalesQuoteItemRepository salesQuoteItemRepository;

    @Inject
    private SalesQuoteItemMapper salesQuoteItemMapper;

    /**
     * Save a salesQuoteItem.
     *
     * @param salesQuoteItemDTO the entity to save
     * @return the persisted entity
     */
    public SalesQuoteItemDTO save(SalesQuoteItemDTO salesQuoteItemDTO) {
        log.debug("Request to save SalesQuoteItem : {}", salesQuoteItemDTO);
        SalesQuoteItem salesQuoteItem = salesQuoteItemMapper.salesQuoteItemDTOToSalesQuoteItem(salesQuoteItemDTO);
        salesQuoteItem = salesQuoteItemRepository.save(salesQuoteItem);
        SalesQuoteItemDTO result = salesQuoteItemMapper.salesQuoteItemToSalesQuoteItemDTO(salesQuoteItem);
        return result;
    }

    /**
     *  Get all the salesQuoteItems.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<SalesQuoteItemDTO> findAll() {
        log.debug("Request to get all SalesQuoteItems");
        List<SalesQuoteItemDTO> result = salesQuoteItemRepository.findAll().stream()
            .map(salesQuoteItemMapper::salesQuoteItemToSalesQuoteItemDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one salesQuoteItem by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public SalesQuoteItemDTO findOne(Long id) {
        log.debug("Request to get SalesQuoteItem : {}", id);
        SalesQuoteItem salesQuoteItem = salesQuoteItemRepository.findOne(id);
        SalesQuoteItemDTO salesQuoteItemDTO = salesQuoteItemMapper.salesQuoteItemToSalesQuoteItemDTO(salesQuoteItem);
        return salesQuoteItemDTO;
    }

    /**
     *  Delete the  salesQuoteItem by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete SalesQuoteItem : {}", id);
        salesQuoteItemRepository.delete(id);
    }
}
