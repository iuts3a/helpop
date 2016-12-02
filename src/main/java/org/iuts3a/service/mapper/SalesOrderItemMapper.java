package org.iuts3a.service.mapper;

import org.iuts3a.domain.*;
import org.iuts3a.service.dto.SalesOrderItemDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity SalesOrderItem and its DTO SalesOrderItemDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SalesOrderItemMapper {

    SalesOrderItemDTO salesOrderItemToSalesOrderItemDTO(SalesOrderItem salesOrderItem);

    List<SalesOrderItemDTO> salesOrderItemsToSalesOrderItemDTOs(List<SalesOrderItem> salesOrderItems);

    SalesOrderItem salesOrderItemDTOToSalesOrderItem(SalesOrderItemDTO salesOrderItemDTO);

    List<SalesOrderItem> salesOrderItemDTOsToSalesOrderItems(List<SalesOrderItemDTO> salesOrderItemDTOs);
}
