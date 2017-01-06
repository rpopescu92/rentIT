package com.rentIT.domain.repository;


import com.rentIT.domain.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
