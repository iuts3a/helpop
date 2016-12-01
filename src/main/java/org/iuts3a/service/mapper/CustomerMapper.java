package org.iuts3a.service.mapper;

import org.iuts3a.domain.*;
import org.iuts3a.service.dto.CustomerDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Customer and its DTO CustomerDTO.
 */
@Mapper(componentModel = "spring", uses = {SalesOrderMapper.class, })
public interface CustomerMapper {

    @Mapping(source = "salesQuote.id", target = "salesQuoteId")
    @Mapping(source = "address.id", target = "addressId")
    CustomerDTO customerToCustomerDTO(Customer customer);

    List<CustomerDTO> customersToCustomerDTOs(List<Customer> customers);

    @Mapping(source = "salesQuoteId", target = "salesQuote")
    @Mapping(source = "addressId", target = "address")
    Customer customerDTOToCustomer(CustomerDTO customerDTO);

    List<Customer> customerDTOsToCustomers(List<CustomerDTO> customerDTOs);

    default SalesQuote salesQuoteFromId(Long id) {
        if (id == null) {
            return null;
        }
        SalesQuote salesQuote = new SalesQuote();
        salesQuote.setId(id);
        return salesQuote;
    }

    default Address addressFromId(Long id) {
        if (id == null) {
            return null;
        }
        Address address = new Address();
        address.setId(id);
        return address;
    }

    default SalesOrder salesOrderFromId(Long id) {
        if (id == null) {
            return null;
        }
        SalesOrder salesOrder = new SalesOrder();
        salesOrder.setId(id);
        return salesOrder;
    }
}
