package org.iuts3a.repository;

import org.iuts3a.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Address entity.
 */
@SuppressWarnings("unused")
public interface AddressRepository extends JpaRepository<Address,Long> {

}
