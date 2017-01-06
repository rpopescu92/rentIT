package com.rentIT.dto;

import com.rentIT.domain.model.Address;
import com.rentIT.domain.model.Image;
import com.rentIT.domain.model.User;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PropertyDto {

    private String username;
    private String shortDescription;
    private String longDescription;
    private Address address;

    private float averageRating;
    private float price;
    private int constructionYear;
    private int roomsNumber;
    private boolean isFurnished;
    private List<Image> images;
}
