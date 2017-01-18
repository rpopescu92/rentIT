package com.rentIT.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rentIT.domain.model.Address;
import com.rentIT.domain.model.City;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PropertyDto implements Serializable{

    private String username;
    private String title;
    private String longDescription;
    private Address address;

    private float averageRating;
    private float price;
    private int constructionYear;
    private int roomsNumber;
    private boolean isFurnished;
    @JsonProperty("images")
    private String[] images;
}
