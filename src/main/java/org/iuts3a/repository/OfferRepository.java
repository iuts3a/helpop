package org.iuts3a.repository;

import org.iuts3a.domain.Offer;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Offer entity.
 */
@SuppressWarnings("unused")
public interface OfferRepository extends JpaRepository<Offer,Long> {

}
