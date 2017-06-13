package com.rentIT.domain.repository;

import com.rentIT.domain.model.HistoryRenting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HistoryRentingRepository extends JpaRepository<HistoryRenting, Long> {

    @Query("select h from HistoryRenting h where h.property.address.city.sector in ?1 and h.status='RENTED' " +
            "and h.dateRented <= ?2 and h.dateRented >= ?3")
    List<HistoryRenting> findPropertiesBetweenRentedDates(List<String> sectors, String begin, String last);

}
