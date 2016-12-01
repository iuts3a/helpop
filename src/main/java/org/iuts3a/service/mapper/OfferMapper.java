package org.iuts3a.service.mapper;

import org.iuts3a.domain.*;
import org.iuts3a.service.dto.OfferDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Offer and its DTO OfferDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OfferMapper {

    @Mapping(source = "owner.id", target = "ownerId")
    OfferDTO offerToOfferDTO(Offer offer);

    List<OfferDTO> offersToOfferDTOs(List<Offer> offers);

    @Mapping(source = "ownerId", target = "owner")
    Offer offerDTOToOffer(OfferDTO offerDTO);

    List<Offer> offerDTOsToOffers(List<OfferDTO> offerDTOs);

    default Customer customerFromId(Long id) {
        if (id == null) {
            return null;
        }
        Customer customer = new Customer();
        customer.setId(id);
        return customer;
    }
}
