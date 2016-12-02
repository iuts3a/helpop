package org.iuts3a.repository;

import org.iuts3a.domain.Customer;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Customer entity.
 */
@SuppressWarnings("unused")
public interface CustomerRepository extends JpaRepository<Customer,Long> {

    @Query("select distinct customer from Customer customer left join fetch customer.orders")
    List<Customer> findAllWithEagerRelationships();

    @Query("select customer from Customer customer left join fetch customer.orders where customer.id =:id")
    Customer findOneWithEagerRelationships(@Param("id") Long id);

}
