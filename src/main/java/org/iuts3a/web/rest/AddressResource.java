package org.iuts3a.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.iuts3a.service.AddressService;
import org.iuts3a.service.dto.AddressDTO;
import org.iuts3a.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Address.
 */
@RestController
@RequestMapping("/api")
public class AddressResource {

    private final Logger log = LoggerFactory.getLogger(AddressResource.class);

    @Inject
    private AddressService addressService;

    /**
     * POST  /addresses : Create a new address.
     *
     * @param addressDTO the addressDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new addressDTO, or with status 400 (Bad Request) if the address has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/addresses")
    @Timed
    public ResponseEntity<AddressDTO> createAddress(@Valid @RequestBody AddressDTO addressDTO) throws URISyntaxException {
        log.debug("REST request to save Address : {}", addressDTO);
        if (addressDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("address", "idexists", "A new address cannot already have an ID")).body(null);
        }
        AddressDTO result = addressService.save(addressDTO);
        return ResponseEntity.created(new URI("/api/addresses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("address", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /addresses : Updates an existing address.
     *
     * @param addressDTO the addressDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated addressDTO,
     * or with status 400 (Bad Request) if the addressDTO is not valid,
     * or with status 500 (Internal Server Error) if the addressDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/addresses")
    @Timed
    public ResponseEntity<AddressDTO> updateAddress(@Valid @RequestBody AddressDTO addressDTO) throws URISyntaxException {
        log.debug("REST request to update Address : {}", addressDTO);
        if (addressDTO.getId() == null) {
            return createAddress(addressDTO);
        }
        AddressDTO result = addressService.save(addressDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("address", addressDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /addresses : get all the addresses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of addresses in body
     */
    @GetMapping("/addresses")
    @Timed
    public List<AddressDTO> getAllAddresses() {
        log.debug("REST request to get all Addresses");
        return addressService.findAll();
    }

    /**
     * GET  /addresses/:id : get the "id" address.
     *
     * @param id the id of the addressDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the addressDTO, or with status 404 (Not Found)
     */
    @GetMapping("/addresses/{id}")
    @Timed
    public ResponseEntity<AddressDTO> getAddress(@PathVariable Long id) {
        log.debug("REST request to get Address : {}", id);
        AddressDTO addressDTO = addressService.findOne(id);
        return Optional.ofNullable(addressDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /addresses/:id : delete the "id" address.
     *
     * @param id the id of the addressDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/addresses/{id}")
    @Timed
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        log.debug("REST request to delete Address : {}", id);
        addressService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("address", id.toString())).build();
    }

}
