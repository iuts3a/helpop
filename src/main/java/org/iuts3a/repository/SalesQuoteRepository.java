package org.iuts3a.repository;

import org.iuts3a.domain.SalesQuote;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the SalesQuote entity.
 */
@SuppressWarnings("unused")
public interface SalesQuoteRepository extends JpaRepository<SalesQuote,Long> {

}
