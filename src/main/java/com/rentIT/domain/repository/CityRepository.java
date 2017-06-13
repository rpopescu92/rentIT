package com.rentIT.domain.repository;

import com.rentIT.domain.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface CityRepository extends JpaRepository<City, Long> {

    List<City> findByRegion(String region);

    City findByCityName(String cityName);

    City findByCityNameAndRegion(String cityName, String region);

    @Query("select distinct region from City")
    List<String> findDistinctRegions();

    //ignore case ?
    @Query("select c from City c where c.cityName like %:name% or c.sector like %:name%")
    List<City> findByCityNameContainsOrSectorContainsAllIgnoreCase(@Param("name") String cityNameOrSector);
}
