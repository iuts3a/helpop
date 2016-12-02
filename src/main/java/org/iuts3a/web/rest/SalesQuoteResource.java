package org.iuts3a.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.iuts3a.service.SalesQuoteService;
import org.iuts3a.web.rest.util.HeaderUtil;
import org.iuts3a.service.dto.SalesQuoteDTO;
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
 * REST controller for managing SalesQuote.
 */
@RestController
@RequestMapping("/api")
public class SalesQuoteResource {

    private final Logger log = LoggerFactory.getLogger(SalesQuoteResource.class);
        
    @Inject
    private SalesQuoteService salesQuoteService;

    /**
     * POST  /sales-quotes : Create a new salesQuote.
     *
     * @param salesQuoteDTO the salesQuoteDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new salesQuoteDTO, or with status 400 (Bad Request) if the salesQuote has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sales-quotes")
    @Timed
    public ResponseEntity<SalesQuoteDTO> createSalesQuote(@Valid @RequestBody SalesQuoteDTO salesQuoteDTO) throws URISyntaxException {
        log.debug("REST request to save SalesQuote : {}", salesQuoteDTO);
        if (salesQuoteDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("salesQuote", "idexists", "A new salesQuote cannot already have an ID")).body(null);
        }
        SalesQuoteDTO result = salesQuoteService.save(salesQuoteDTO);
        return ResponseEntity.created(new URI("/api/sales-quotes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("salesQuote", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sales-quotes : Updates an existing salesQuote.
     *
     * @param salesQuoteDTO the salesQuoteDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated salesQuoteDTO,
     * or with status 400 (Bad Request) if the salesQuoteDTO is not valid,
     * or with status 500 (Internal Server Error) if the salesQuoteDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sales-quotes")
    @Timed
    public ResponseEntity<SalesQuoteDTO> updateSalesQuote(@Valid @RequestBody SalesQuoteDTO salesQuoteDTO) throws URISyntaxException {
        log.debug("REST request to update SalesQuote : {}", salesQuoteDTO);
        if (salesQuoteDTO.getId() == null) {
            return createSalesQuote(salesQuoteDTO);
        }
        SalesQuoteDTO result = salesQuoteService.save(salesQuoteDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("salesQuote", salesQuoteDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sales-quotes : get all the salesQuotes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of salesQuotes in body
     */
    @GetMapping("/sales-quotes")
    @Timed
    public List<SalesQuoteDTO> getAllSalesQuotes() {
        log.debug("REST request to get all SalesQuotes");
        return salesQuoteService.findAll();
    }

    /**
     * GET  /sales-quotes/:id : get the "id" salesQuote.
     *
     * @param id the id of the salesQuoteDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the salesQuoteDTO, or with status 404 (Not Found)
     */
    @GetMapping("/sales-quotes/{id}")
    @Timed
    public ResponseEntity<SalesQuoteDTO> getSalesQuote(@PathVariable Long id) {
        log.debug("REST request to get SalesQuote : {}", id);
        SalesQuoteDTO salesQuoteDTO = salesQuoteService.findOne(id);
        return Optional.ofNullable(salesQuoteDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /sales-quotes/:id : delete the "id" salesQuote.
     *
     * @param id the id of the salesQuoteDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sales-quotes/{id}")
    @Timed
    public ResponseEntity<Void> deleteSalesQuote(@PathVariable Long id) {
        log.debug("REST request to delete SalesQuote : {}", id);
        salesQuoteService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("salesQuote", id.toString())).build();
    }

}
