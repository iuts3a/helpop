package org.iuts3a.service.mapper;

import org.iuts3a.domain.*;
import org.iuts3a.service.dto.SalesOrderDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity SalesOrder and its DTO SalesOrderDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SalesOrderMapper {

    @Mapping(source = "items.id", target = "itemsId")
    SalesOrderDTO salesOrderToSalesOrderDTO(SalesOrder salesOrder);

    List<SalesOrderDTO> salesOrdersToSalesOrderDTOs(List<SalesOrder> salesOrders);

    @Mapping(source = "itemsId", target = "items")
    SalesOrder salesOrderDTOToSalesOrder(SalesOrderDTO salesOrderDTO);

    List<SalesOrder> salesOrderDTOsToSalesOrders(List<SalesOrderDTO> salesOrderDTOs);

    default SalesOrderItem salesOrderItemFromId(Long id) {
        if (id == null) {
            return null;
        }
        SalesOrderItem salesOrderItem = new SalesOrderItem();
        salesOrderItem.setId(id);
        return salesOrderItem;
    }
}
