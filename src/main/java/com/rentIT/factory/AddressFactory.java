package com.rentIT.factory;

import com.rentIT.domain.model.Address;
import com.rentIT.domain.model.City;
import com.rentIT.dto.PropertyDto;

public final class AddressFactory {

    private AddressFactory() {
        throw new UnsupportedOperationException("This should not be accessed as it's a utility class");
    }

    public static Address createAddress(PropertyDto propertyDto, City city) {
        return Address.builder().streetName(propertyDto.getAddress().getStreetName())
                .streetNumber(propertyDto.getAddress().getStreetNumber())
                .apartmentNumber(propertyDto.getAddress().getApartmentNumber())
                .floorNumber(propertyDto.getAddress().getFloorNumber())
                .city(city).build();
    }

}
