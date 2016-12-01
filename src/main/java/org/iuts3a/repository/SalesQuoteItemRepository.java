package org.iuts3a.repository;

import org.iuts3a.domain.SalesQuoteItem;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the SalesQuoteItem entity.
 */
@SuppressWarnings("unused")
public interface SalesQuoteItemRepository extends JpaRepository<SalesQuoteItem,Long> {

}
