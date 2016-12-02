package org.iuts3a.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.iuts3a.service.SalesOrderService;
import org.iuts3a.web.rest.util.HeaderUtil;
import org.iuts3a.service.dto.SalesOrderDTO;
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
 * REST controller for managing SalesOrder.
 */
@RestController
@RequestMapping("/api")
public class SalesOrderResource {

    private final Logger log = LoggerFactory.getLogger(SalesOrderResource.class);
        
    @Inject
    private SalesOrderService salesOrderService;

    /**
     * POST  /sales-orders : Create a new salesOrder.
     *
     * @param salesOrderDTO the salesOrderDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new salesOrderDTO, or with status 400 (Bad Request) if the salesOrder has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sales-orders")
    @Timed
    public ResponseEntity<SalesOrderDTO> createSalesOrder(@Valid @RequestBody SalesOrderDTO salesOrderDTO) throws URISyntaxException {
        log.debug("REST request to save SalesOrder : {}", salesOrderDTO);
        if (salesOrderDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("salesOrder", "idexists", "A new salesOrder cannot already have an ID")).body(null);
        }
        SalesOrderDTO result = salesOrderService.save(salesOrderDTO);
        return ResponseEntity.created(new URI("/api/sales-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("salesOrder", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sales-orders : Updates an existing salesOrder.
     *
     * @param salesOrderDTO the salesOrderDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated salesOrderDTO,
     * or with status 400 (Bad Request) if the salesOrderDTO is not valid,
     * or with status 500 (Internal Server Error) if the salesOrderDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sales-orders")
    @Timed
    public ResponseEntity<SalesOrderDTO> updateSalesOrder(@Valid @RequestBody SalesOrderDTO salesOrderDTO) throws URISyntaxException {
        log.debug("REST request to update SalesOrder : {}", salesOrderDTO);
        if (salesOrderDTO.getId() == null) {
            return createSalesOrder(salesOrderDTO);
        }
        SalesOrderDTO result = salesOrderService.save(salesOrderDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("salesOrder", salesOrderDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sales-orders : get all the salesOrders.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of salesOrders in body
     */
    @GetMapping("/sales-orders")
    @Timed
    public List<SalesOrderDTO> getAllSalesOrders() {
        log.debug("REST request to get all SalesOrders");
        return salesOrderService.findAll();
    }

    /**
     * GET  /sales-orders/:id : get the "id" salesOrder.
     *
     * @param id the id of the salesOrderDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the salesOrderDTO, or with status 404 (Not Found)
     */
    @GetMapping("/sales-orders/{id}")
    @Timed
    public ResponseEntity<SalesOrderDTO> getSalesOrder(@PathVariable Long id) {
        log.debug("REST request to get SalesOrder : {}", id);
        SalesOrderDTO salesOrderDTO = salesOrderService.findOne(id);
        return Optional.ofNullable(salesOrderDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /sales-orders/:id : delete the "id" salesOrder.
     *
     * @param id the id of the salesOrderDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sales-orders/{id}")
    @Timed
    public ResponseEntity<Void> deleteSalesOrder(@PathVariable Long id) {
        log.debug("REST request to delete SalesOrder : {}", id);
        salesOrderService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("salesOrder", id.toString())).build();
    }

}
