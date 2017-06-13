package com.rentIT.service;

import com.rentIT.domain.model.ChartResponse;
import com.rentIT.domain.model.Property;
import com.rentIT.domain.repository.CityRepository;
import com.rentIT.domain.repository.PropertyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MarketAnalysisService {

    private final PropertyRepository propertyRepository;
    private final CityRepository cityRepository;
    private final List<String> sectors = new ArrayList<>();

    @Autowired
    public MarketAnalysisService(PropertyRepository propertyRepository,
                                 CityRepository cityRepository) {
        this.propertyRepository = propertyRepository;
        this.cityRepository = cityRepository;
        sectors.add("Sector 1");
        sectors.add("Sector 2");
        sectors.add("Sector 3");
        sectors.add("Sector 4");
        sectors.add("Sector 5");
        sectors.add("Sector 6");
    }

    public Collection<Integer> getAnalysisForBucharest() {
        List<Property> properties = propertyRepository.findPropertiesForBucharest(sectors);
        Map<String, Integer> results = new TreeMap<>();

        sectors.forEach(s -> results.put(s, 0));

        for (Property p : properties) {
            String sector = p.getAddress().getCity().getSector();
            if (results.get(sector) == 0) {
                results.put(sector, 1);
            } else {
                int n = results.get(sector);
                results.put(sector, n + 1);
            }
        }
        return results.values();
    }

    public ChartResponse getAnalysisForEachCity() {
        ChartResponse response = new ChartResponse();
        List<Property> properties = propertyRepository.findPropertiesForEachCity();
        List<String> cities = cityRepository.findByCityName();

        Map<String, Integer> r = new HashMap<>();
        cities.forEach(c -> r.put(c, 0));

        // duplicate code (see above)
        properties.forEach(p -> {
            String city = p.getAddress().getCity().getCityName();
            if (r.get(city) == 0) {
                r.put(city, 1);
            } else {
                int n = r.get(city);
                r.put(city, n + 1);
            }
        });

        //for bucharest - to be refactored
        int b_count = r.keySet()
                .stream()
                .filter(c -> c.startsWith("Sector"))
                .map(r::get)
                .reduce((x, y) -> x + y)
                .orElseThrow(RuntimeException::new);

        r.put("Bucuresti", b_count);
        List<String> labels = r.keySet().stream().filter(c -> !c.startsWith("Sector")).sorted().collect(Collectors.toList());
        List<Integer> data = r.keySet().stream().filter(c -> !c.startsWith("Sector")).sorted().map(r::get).collect(Collectors.toList());
        response.setData(data);
        response.setLabels(labels);
        return response;
    }
}
