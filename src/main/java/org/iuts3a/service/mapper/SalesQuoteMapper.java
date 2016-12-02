package org.iuts3a.service.mapper;

import org.iuts3a.domain.*;
import org.iuts3a.service.dto.SalesQuoteDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity SalesQuote and its DTO SalesQuoteDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SalesQuoteMapper {

    @Mapping(source = "items.id", target = "itemsId")
    SalesQuoteDTO salesQuoteToSalesQuoteDTO(SalesQuote salesQuote);

    List<SalesQuoteDTO> salesQuotesToSalesQuoteDTOs(List<SalesQuote> salesQuotes);

    @Mapping(source = "itemsId", target = "items")
    SalesQuote salesQuoteDTOToSalesQuote(SalesQuoteDTO salesQuoteDTO);

    List<SalesQuote> salesQuoteDTOsToSalesQuotes(List<SalesQuoteDTO> salesQuoteDTOs);

    default SalesQuoteItem salesQuoteItemFromId(Long id) {
        if (id == null) {
            return null;
        }
        SalesQuoteItem salesQuoteItem = new SalesQuoteItem();
        salesQuoteItem.setId(id);
        return salesQuoteItem;
    }
}
