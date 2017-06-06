package com.rentIT.factory;

import com.rentIT.domain.model.Address;
import com.rentIT.domain.model.Photo;
import com.rentIT.domain.model.Property;
import com.rentIT.domain.model.User;
import com.rentIT.dto.PropertyDto;

import java.util.Date;
import java.util.List;

public final class PropertyFactory {

    private PropertyFactory() {
        throw new UnsupportedOperationException("This should not be accessed as it's a utility class");
    }

    public static Property createProperty(User user, PropertyDto propertyDto, Address address, List<Photo> photos) {
        return Property.builder()
                .owner(user)
                .address(address)
                .averageRating(0)
                .constructionYear(propertyDto.getConstructionYear())
                .images(photos)
                .isFurnished(propertyDto.isFurnished())
                .longDescription(propertyDto.getLongDescription())
                .title(propertyDto.getTitle())
                .price(propertyDto.getPrice())
                .currency(propertyDto.getCurrency())
                .roomsNumber(propertyDto.getRoomsNumber())
                .floorArea(propertyDto.getFloorArea())
                .allowsPets(propertyDto.getAllowsPets())
                .otherInfo(propertyDto.getOtherInfo())
                .dateAdded(new Date())
                .build();
    }

}
