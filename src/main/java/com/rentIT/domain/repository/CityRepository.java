package com.rentIT.domain.repository;

import com.rentIT.domain.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface CityRepository extends JpaRepository<City, Long> {

    List<City> findByRegion(String region);

    City findByCity(String city);

    City findByCityAndRegion(String city, String region);

    @Query("select distinct region  from City")
    List<String> findDistinctRegions();

}
