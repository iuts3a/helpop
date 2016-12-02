package org.iuts3a.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.iuts3a.service.SalesOrderItemService;
import org.iuts3a.web.rest.util.HeaderUtil;
import org.iuts3a.service.dto.SalesOrderItemDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing SalesOrderItem.
 */
@RestController
@RequestMapping("/api")
public class SalesOrderItemResource {

    private final Logger log = LoggerFactory.getLogger(SalesOrderItemResource.class);
        
    @Inject
    private SalesOrderItemService salesOrderItemService;

    /**
     * POST  /sales-order-items : Create a new salesOrderItem.
     *
     * @param salesOrderItemDTO the salesOrderItemDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new salesOrderItemDTO, or with status 400 (Bad Request) if the salesOrderItem has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sales-order-items")
    @Timed
    public ResponseEntity<SalesOrderItemDTO> createSalesOrderItem(@Valid @RequestBody SalesOrderItemDTO salesOrderItemDTO) throws URISyntaxException {
        log.debug("REST request to save SalesOrderItem : {}", salesOrderItemDTO);
        if (salesOrderItemDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("salesOrderItem", "idexists", "A new salesOrderItem cannot already have an ID")).body(null);
        }
        SalesOrderItemDTO result = salesOrderItemService.save(salesOrderItemDTO);
        return ResponseEntity.created(new URI("/api/sales-order-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("salesOrderItem", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sales-order-items : Updates an existing salesOrderItem.
     *
     * @param salesOrderItemDTO the salesOrderItemDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated salesOrderItemDTO,
     * or with status 400 (Bad Request) if the salesOrderItemDTO is not valid,
     * or with status 500 (Internal Server Error) if the salesOrderItemDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sales-order-items")
    @Timed
    public ResponseEntity<SalesOrderItemDTO> updateSalesOrderItem(@Valid @RequestBody SalesOrderItemDTO salesOrderItemDTO) throws URISyntaxException {
        log.debug("REST request to update SalesOrderItem : {}", salesOrderItemDTO);
        if (salesOrderItemDTO.getId() == null) {
            return createSalesOrderItem(salesOrderItemDTO);
        }
        SalesOrderItemDTO result = salesOrderItemService.save(salesOrderItemDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("salesOrderItem", salesOrderItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sales-order-items : get all the salesOrderItems.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of salesOrderItems in body
     */
    @GetMapping("/sales-order-items")
    @Timed
    public List<SalesOrderItemDTO> getAllSalesOrderItems() {
        log.debug("REST request to get all SalesOrderItems");
        return salesOrderItemService.findAll();
    }

    /**
     * GET  /sales-order-items/:id : get the "id" salesOrderItem.
     *
     * @param id the id of the salesOrderItemDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the salesOrderItemDTO, or with status 404 (Not Found)
     */
    @GetMapping("/sales-order-items/{id}")
    @Timed
    public ResponseEntity<SalesOrderItemDTO> getSalesOrderItem(@PathVariable Long id) {
        log.debug("REST request to get SalesOrderItem : {}", id);
        SalesOrderItemDTO salesOrderItemDTO = salesOrderItemService.findOne(id);
        return Optional.ofNullable(salesOrderItemDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /sales-order-items/:id : delete the "id" salesOrderItem.
     *
     * @param id the id of the salesOrderItemDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sales-order-items/{id}")
    @Timed
    public ResponseEntity<Void> deleteSalesOrderItem(@PathVariable Long id) {
        log.debug("REST request to delete SalesOrderItem : {}", id);
        salesOrderItemService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("salesOrderItem", id.toString())).build();
    }

}
