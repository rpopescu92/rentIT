package com.rentIT.service;

import com.rentIT.domain.model.City;
import com.rentIT.domain.repository.CityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Set;

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

    @PostConstruct
    public void createListOfCities() {
        City city = cityRepository.getOne(1L);
        if(city == null) {
            City cityToCreate = new City(1, "Alba","Romania", "Aiud");
            City cityToCreate1 = new City(2, "Braila","Romania", "Ianca");
            City cityToCreate2 = new City(3, "Alba","Romania", "Abrdu");
            City cityToCreate3 = new City(4, "Alba","Romania", "Baia de Arias");
            City cityToCreate4 = new City(5, "Alba","Romania", "Cugir");
            City cityToCreate5 = new City(6, "Alba","Romania", "Campeni");
            City cityToCreate6 = new City(7, "Alba","Romania", "Ocna Mures");
            City cityToCreate7 = new City(8, "Alba","Romania", "Sebes");

            cityRepository.save(cityToCreate);
            cityRepository.save(cityToCreate1);
            cityRepository.save(cityToCreate2);
            cityRepository.save(cityToCreate3);
            cityRepository.save(cityToCreate4);
            cityRepository.save(cityToCreate5);
            cityRepository.save(cityToCreate6);
            cityRepository.save(cityToCreate7);
        }

    }
}
