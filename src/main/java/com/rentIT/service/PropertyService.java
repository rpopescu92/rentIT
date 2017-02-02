package com.rentIT.service;

import com.rentIT.domain.model.*;
import com.rentIT.domain.repository.AddressRepository;
import com.rentIT.domain.repository.CityRepository;
import com.rentIT.domain.repository.PropertyRepository;
import com.rentIT.domain.repository.UserRepository;
import com.rentIT.dto.PropertyDto;
import com.rentIT.exception.InvalidPropertyException;
import com.rentIT.exception.UserNotAuthenticatedException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

@Service
@Slf4j
public class PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private CityRepository cityRepository;

    private Logger logger = LoggerFactory.getLogger(PropertyService.class);

    public void saveProperty(PropertyDto propertyDto) {
        logger.debug("Save new property for user {}", propertyDto.getUsername());
        Optional<User> owner = userRepository.findByUsername(propertyDto.getUsername());
        if(!owner.isPresent()) {
            throw new UserNotAuthenticatedException();
        }
        if(StringUtils.isEmpty(propertyDto.getAddress().getStreetName()) || StringUtils.isEmpty(propertyDto.getAddress().getStreetNumber())
                || StringUtils.isEmpty(propertyDto.getTitle())
                || StringUtils.isEmpty(propertyDto.getConstructionYear())) {
            throw new InvalidPropertyException("Invalid fields. Address cannot be empty");
        }

        City city = cityRepository.findByCityNameAndRegion(propertyDto.getAddress().getCity().getCityName(), propertyDto.getAddress().getCity().getRegion());
        Address address = Address.builder().streetName(propertyDto.getAddress().getStreetName())
                                    .streetNumber(propertyDto.getAddress().getStreetNumber())
                                    .apartmentNumber(propertyDto.getAddress().getApartmentNumber())
                                    .floorNumber(propertyDto.getAddress().getFloorNumber())
                                    .otherDirections(propertyDto.getAddress().getOtherDirections())
                                    .city(city).build();
        Address savedAddress = addressRepository.save(address);

        Property property = Property.builder()
                            .owner((User)owner.get())
                            .address(address)
                            .averageRating(0)
                            .constructionYear(propertyDto.getConstructionYear())
                            .images(Arrays.asList(propertyDto.getImages()))
                            .isFurnished(propertyDto.isFurnished())
                            .longDescription(propertyDto.getLongDescription())
                            .title(propertyDto.getTitle())
                            .price(propertyDto.getPrice())
                            .currency(propertyDto.getCurrency())
                            .roomsNumber(propertyDto.getRoomsNumber())
                            .dateAdded(new Date())
                            .build();

        propertyRepository.save(property);
    }

    public Page<Property> getPropertiesByOwner(String username, String page, String limit, String option) {
        Optional<User> owner = userRepository.findByUsername(username);
        if(!owner.isPresent()) {
            throw new UserNotAuthenticatedException();
        }
        Sort.Direction direction = option.startsWith("-") ? Sort.Direction.DESC: Sort.Direction.ASC;
        if(option.startsWith("+")) {
            option = option.substring(option.indexOf('+') +1);
        } else {
            option = option.substring(option.indexOf('-') +1);
        }
        Page<Property> properties = propertyRepository.findPropertyByUserOwner(owner.get(),new PageRequest(Integer.parseInt(page) - 1, Integer.parseInt(limit), direction, option));
        return properties;
    }

    public Page<Property> getAllProperties(Integer page, Integer limit, String option) {
        Sort.Direction direction = option.startsWith("-") ? Sort.Direction.DESC: Sort.Direction.ASC;
        if(option.startsWith("+")) {
            option = option.substring(option.indexOf('+') +1);
        } else {
            option = option.substring(option.indexOf('-') +1);
        }
        return propertyRepository.findAllProperties(new PageRequest(page - 1, limit, direction, option));
    }

    public Property updateProperty(Property property) {
        Optional<User> owner = userRepository.findByUsername(property.getOwner().getUsername());
        if(!owner.isPresent()) {
            throw new UserNotAuthenticatedException();
        }
        Property updatedProperty = propertyRepository.findOne(property.getId());
        updateAddress(property);
        updatedProperty.setAverageRating(property.getAverageRating());
        updatedProperty.setAddress(property.getAddress());
        updatedProperty.setConstructionYear(property.getConstructionYear());
        updatedProperty.setAddress(property.getAddress());
        updatedProperty.setFurnished(property.isFurnished());
        updatedProperty.setImages(property.getImages());
        updatedProperty.setLongDescription(property.getLongDescription());
        updatedProperty.setTitle(property.getTitle());
        updatedProperty.setPrice(property.getPrice());
        updatedProperty.setRented(property.isRented());
        updatedProperty.setRoomsNumber(property.getRoomsNumber());
        updatedProperty.setTenant(property.getTenant());

        propertyRepository.save(updatedProperty);

        return updatedProperty;
    }

    private void updateAddress(Property property) {
        City city = cityRepository.findByCityNameAndRegion(property.getAddress().getCity().getCityName()
                , property.getAddress().getCity().getRegion());
        property.getAddress().setCity(city);
        Address address = addressRepository.findOne(property.getAddress().getId());
        address = property.getAddress();

        addressRepository.save(address);
    }
}
