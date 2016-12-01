package org.iuts3a.repository;

import org.iuts3a.domain.SalesOrderItem;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the SalesOrderItem entity.
 */
@SuppressWarnings("unused")
public interface SalesOrderItemRepository extends JpaRepository<SalesOrderItem,Long> {

}
