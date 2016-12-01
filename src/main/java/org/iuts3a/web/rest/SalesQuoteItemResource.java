package org.iuts3a.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.iuts3a.service.SalesQuoteItemService;
import org.iuts3a.web.rest.util.HeaderUtil;
import org.iuts3a.service.dto.SalesQuoteItemDTO;
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
 * REST controller for managing SalesQuoteItem.
 */
@RestController
@RequestMapping("/api")
public class SalesQuoteItemResource {

    private final Logger log = LoggerFactory.getLogger(SalesQuoteItemResource.class);
        
    @Inject
    private SalesQuoteItemService salesQuoteItemService;

    /**
     * POST  /sales-quote-items : Create a new salesQuoteItem.
     *
     * @param salesQuoteItemDTO the salesQuoteItemDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new salesQuoteItemDTO, or with status 400 (Bad Request) if the salesQuoteItem has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sales-quote-items")
    @Timed
    public ResponseEntity<SalesQuoteItemDTO> createSalesQuoteItem(@Valid @RequestBody SalesQuoteItemDTO salesQuoteItemDTO) throws URISyntaxException {
        log.debug("REST request to save SalesQuoteItem : {}", salesQuoteItemDTO);
        if (salesQuoteItemDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("salesQuoteItem", "idexists", "A new salesQuoteItem cannot already have an ID")).body(null);
        }
        SalesQuoteItemDTO result = salesQuoteItemService.save(salesQuoteItemDTO);
        return ResponseEntity.created(new URI("/api/sales-quote-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("salesQuoteItem", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sales-quote-items : Updates an existing salesQuoteItem.
     *
     * @param salesQuoteItemDTO the salesQuoteItemDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated salesQuoteItemDTO,
     * or with status 400 (Bad Request) if the salesQuoteItemDTO is not valid,
     * or with status 500 (Internal Server Error) if the salesQuoteItemDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sales-quote-items")
    @Timed
    public ResponseEntity<SalesQuoteItemDTO> updateSalesQuoteItem(@Valid @RequestBody SalesQuoteItemDTO salesQuoteItemDTO) throws URISyntaxException {
        log.debug("REST request to update SalesQuoteItem : {}", salesQuoteItemDTO);
        if (salesQuoteItemDTO.getId() == null) {
            return createSalesQuoteItem(salesQuoteItemDTO);
        }
        SalesQuoteItemDTO result = salesQuoteItemService.save(salesQuoteItemDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("salesQuoteItem", salesQuoteItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sales-quote-items : get all the salesQuoteItems.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of salesQuoteItems in body
     */
    @GetMapping("/sales-quote-items")
    @Timed
    public List<SalesQuoteItemDTO> getAllSalesQuoteItems() {
        log.debug("REST request to get all SalesQuoteItems");
        return salesQuoteItemService.findAll();
    }

    /**
     * GET  /sales-quote-items/:id : get the "id" salesQuoteItem.
     *
     * @param id the id of the salesQuoteItemDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the salesQuoteItemDTO, or with status 404 (Not Found)
     */
    @GetMapping("/sales-quote-items/{id}")
    @Timed
    public ResponseEntity<SalesQuoteItemDTO> getSalesQuoteItem(@PathVariable Long id) {
        log.debug("REST request to get SalesQuoteItem : {}", id);
        SalesQuoteItemDTO salesQuoteItemDTO = salesQuoteItemService.findOne(id);
        return Optional.ofNullable(salesQuoteItemDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /sales-quote-items/:id : delete the "id" salesQuoteItem.
     *
     * @param id the id of the salesQuoteItemDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sales-quote-items/{id}")
    @Timed
    public ResponseEntity<Void> deleteSalesQuoteItem(@PathVariable Long id) {
        log.debug("REST request to delete SalesQuoteItem : {}", id);
        salesQuoteItemService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("salesQuoteItem", id.toString())).build();
    }

}
