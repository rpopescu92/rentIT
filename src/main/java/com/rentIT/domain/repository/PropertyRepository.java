package com.rentIT.domain.repository;

import com.rentIT.domain.model.Property;
import com.rentIT.domain.model.Status;
import com.rentIT.domain.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Long> {

    @Query("select p from Property p where p.owner=?1 order by p.dateAdded DESC")
    Page<Property> findPropertyByUserOwner(User owner, Pageable pageable);

    @Query("select p from Property p where p.status='NOT_RENTED' order by p.dateAdded DESC")
    Page<Property> findAllProperties(Pageable pageable);

    @Modifying
    @Transactional
    @Query("update Property p set p.status=?2, p.dateRented=?3 where p.id=?1")
    void isRented(long id, Status status, ZonedDateTime dateRented);

    @Query("select p from Property p where p.owner.username=?1")
    List<Property> findByOwner(String username);

    @Query("select p from Property p where p.address.city.sector in ?1 and p.status='RENTED' ")
    List<Property> findPropertiesForBucharest(List<String> sectors);

    @Query("select p from Property p where p.status = 'RENTED' ")
    List<Property> findPropertiesForEachCity();
}