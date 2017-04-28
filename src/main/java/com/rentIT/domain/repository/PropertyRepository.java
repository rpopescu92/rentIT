package com.rentIT.domain.repository;

import com.rentIT.domain.model.Property;
import com.rentIT.domain.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Long> {

    @Query("select p from Property p where p.owner=?1")
    Page<Property> findPropertyByUserOwner(User owner, Pageable pageable);

    @Query("select p from Property p where p.isRented=false")
    Page<Property> findAllProperties(Pageable pageable);

    @Modifying
    @Transactional
    @Query("update Property p set p.isRented=?2 where p.id=?1")
    void isRented(long id, boolean isRented);

    @Query("select p from Property p where p.owner.username=?1")
    List<Property> findByOwner(String username);
}