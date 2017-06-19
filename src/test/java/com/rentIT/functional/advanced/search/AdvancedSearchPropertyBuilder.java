package com.rentIT.functional.advanced.search;

import com.rentIT.domain.model.Address;
import com.rentIT.domain.model.City;
import com.rentIT.domain.model.Currency;
import com.rentIT.dto.PropertyDto;
import com.rentIT.resource.model.SearchOptions;

import java.util.ArrayList;
import java.util.List;

public final class AdvancedSearchPropertyBuilder {

    private AdvancedSearchPropertyBuilder() {
        throw new RuntimeException("should not be invoked");
    }

    public static PropertyDto createProperty(String title, float price) {
        Address address = new Address();
        address.setStreetName("test");
        address.setStreetNumber("412");
        address.setCity(City.builder().cityName("Bucharest").build());
        return PropertyDto.builder()
                .address(address)
                .title(title == null || title.isEmpty() ? "simple-property" : title)
                .averageRating(1)
                .constructionYear(1990)
                .currency(Currency.EUR)
                .floorArea(82)
                .images(null)
                .isFurnished(true)
                .price(-10 + price)
                .roomsNumber(7)
                .username("turnball")
                .build();
    }

    public static List<PropertyDto> createProperties(int nrProperties) {
        List<PropertyDto> properties = new ArrayList<>();

        for (int i = 0; i < nrProperties; i++) {
            properties.add(createProperty("simple-property-" + i, -10 * 3 * i));
        }

        return properties;
    }

    public static SearchOptions createSearchOptions(float minPrice, float maxPrice) {
        return SearchOptions.builder()
                .minPrice(minPrice)
                .maxPrice(maxPrice)
                .build();
    }

}