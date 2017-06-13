package com.rentIT.resource.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchOptions {

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "numberOfStars")
    private List<Float> numberOfStars;

    @JsonProperty(value = "minPrice")
    private Float minPrice;

    @JsonProperty(value = "maxPrice")
    private Float maxPrice;

    @JsonProperty(value = "cityId")
    private List<Long> cityId;

    @JsonProperty(value = "numberOfRooms")
    private List<Integer> numberOfRooms;

    @JsonProperty(value = "allowsPets")
    private Boolean allowsPets;

    @JsonProperty("searchLocation")
    private String searchLocation;
}
