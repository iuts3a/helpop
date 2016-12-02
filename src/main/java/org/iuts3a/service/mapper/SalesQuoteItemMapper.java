package org.iuts3a.service.mapper;

import org.iuts3a.domain.*;
import org.iuts3a.service.dto.SalesQuoteItemDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity SalesQuoteItem and its DTO SalesQuoteItemDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SalesQuoteItemMapper {

    SalesQuoteItemDTO salesQuoteItemToSalesQuoteItemDTO(SalesQuoteItem salesQuoteItem);

    List<SalesQuoteItemDTO> salesQuoteItemsToSalesQuoteItemDTOs(List<SalesQuoteItem> salesQuoteItems);

    SalesQuoteItem salesQuoteItemDTOToSalesQuoteItem(SalesQuoteItemDTO salesQuoteItemDTO);

    List<SalesQuoteItem> salesQuoteItemDTOsToSalesQuoteItems(List<SalesQuoteItemDTO> salesQuoteItemDTOs);
}
