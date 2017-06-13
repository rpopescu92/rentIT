package com.rentIT.service;

import com.rentIT.domain.model.ChartResponse;
import com.rentIT.domain.model.HistoryRenting;
import com.rentIT.domain.model.Property;
import com.rentIT.domain.repository.CityRepository;
import com.rentIT.domain.repository.HistoryRentingRepository;
import com.rentIT.domain.repository.PropertyRepository;
import com.rentIT.util.DateTimeFactory;
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
    private final HistoryRentingRepository historyRentingRepository;
    private final List<String> sectors = new ArrayList<>();

    @Autowired
    public MarketAnalysisService(PropertyRepository propertyRepository,
                                 CityRepository cityRepository,
                                 HistoryRentingRepository historyRentingRepository) {
        this.propertyRepository = propertyRepository;
        this.cityRepository = cityRepository;
        this.historyRentingRepository = historyRentingRepository;
        sectors.add("Sector 1");
        sectors.add("Sector 2");
        sectors.add("Sector 3");
        sectors.add("Sector 4");
        sectors.add("Sector 5");
        sectors.add("Sector 6");
    }

    public Collection<Integer> getAnalysisForBucharest() {
        List<Property> properties = propertyRepository.findPropertiesForBucharest(sectors);
        Map<String, Integer> results = mapCityOrSectorWithNumberAppearances(properties, sectors);
        return results.values();
    }

    private Map<String, Integer> mapCityOrSectorWithNumberAppearances(List<Property> properties,
                                                                      List<String> citiesOrSectors) {
        Map<String, Integer> r = new TreeMap<>();
        citiesOrSectors.forEach(c -> r.put(c, 0));

        properties.forEach(p -> {
            String city = p.getAddress().getCity().getCityName();
            if (r.get(city) == 0) {
                r.put(city, 1);
            } else {
                int n = r.get(city);
                r.put(city, n + 1);
            }
        });

        return r;
    }

    public ChartResponse getAnalysisForEachCity() {
        ChartResponse response = new ChartResponse();
        List<Property> properties = propertyRepository.findPropertiesForEachCity();
        List<String> cities = cityRepository.findByCityName();

        Map<String, Integer> r = mapCityOrSectorWithNumberAppearances(properties, cities);

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

    public ChartResponse getAnalysisForBucharestForTheLastMonths() {
        String now = DateTimeFactory.createNowDate();
        String lastDate = DateTimeFactory.createDateMinusMonths(3);
        List<HistoryRenting> historicalRentings = historyRentingRepository.findPropertiesBetweenRentedDates(sectors, now, lastDate);
        List<String> months = DateTimeFactory.getMonthNames(3);
        List<String> monthValues = DateTimeFactory.getMonthValues(3);

        log.info("{}", months);
        log.info("{}", monthValues);

        Map<String, Map<String, Integer>> res = new LinkedHashMap<>();

        monthValues.forEach(m -> {
            Map<String, Integer> r = new TreeMap<>();
            historicalRentings.forEach(h -> {
                if (h.getDateRented().endsWith(m)) {
                    String sector = h.getProperty().getAddress().getCity().getSector();
                    if (r.get(sector) == null) {
                        r.put(sector, 1);
                    } else {
                        int n = r.get(sector);
                        r.put(sector, n + 1);
                    }
                }
            });
            sectors.forEach(s -> r.putIfAbsent(s, 0));
            res.put(m, r);
        });

        List<List<Integer>> ints = new ArrayList<>();

        res.keySet().forEach(m -> {
            ints.add(new ArrayList<>(res.get(m).values()));
        });


        //res looks like this -> 5-2017 (MAY) and then the list of sectors
        log.info("{}", res);
        log.info("{}", ints);

        ChartResponse response = new ChartResponse();

        response.setSeries(months);
        response.setLabels(sectors);
        response.setComplexData(ints);

        return response;
    }
}
