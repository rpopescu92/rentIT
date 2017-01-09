package com.rentIT.domain.repository;

import com.rentIT.domain.model.Property;
import com.rentIT.domain.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by roxanap on 06.01.2017.
 */
public interface PropertyRepository extends JpaRepository<Property, Long> {

    @Query("select p from Property p where p.owner=?1")
    List<Property> findPropertyByUserOwner(User owner);

    @Query("select p from Property p")
    Page<Property> findAllProperties(Pageable pageable);

}
