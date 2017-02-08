package com.rentIT.domain.repository;

import com.rentIT.domain.model.Property;
import com.rentIT.domain.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PropertyRepository extends JpaRepository<Property, Long> {

    @Query("select p from Property p where p.owner=?1")
    Page<Property> findPropertyByUserOwner(User owner, Pageable pageable);

    @Query("select p from Property p where p.isRented=false")
    Page<Property> findAllProperties(Pageable pageable);

}
