package com.rentIT.resource;

import com.rentIT.domain.model.City;
import com.rentIT.service.CitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CityResource {

    @Autowired
    private CitiesService citiesService;

    @RequestMapping(value = "/cities", method = RequestMethod.GET)
    public ResponseEntity<List<City>> getCities() {
        List<City> cities = citiesService.getAllCities();

        return new ResponseEntity(cities, HttpStatus.OK);
    }

    @RequestMapping(value = "/cities/regions", method = RequestMethod.GET)
    public ResponseEntity<List<String>> getRegions() {
        List<String> uniqueRegion = citiesService.getRegions();

        return new ResponseEntity(uniqueRegion, HttpStatus.OK);
    }
}
