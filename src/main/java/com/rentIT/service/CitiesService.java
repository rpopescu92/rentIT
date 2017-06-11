package com.rentIT.service;

import com.rentIT.domain.model.City;
import com.rentIT.domain.repository.CityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CitiesService {

    @Autowired
    private CityRepository cityRepository;

    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    public List<String> getRegions() {
        return cityRepository.findDistinctRegions();
    }

}
