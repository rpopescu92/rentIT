package com.rentIT.service;

import com.rentIT.domain.model.*;
import com.rentIT.domain.repository.*;
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
import java.util.List;
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
    @Autowired
    private PhotoRepository photoRepository;

    private Logger logger = LoggerFactory.getLogger(PropertyService.class);

    public void saveProperty(PropertyDto propertyDto) {
        logger.debug("Save new property for user {}", propertyDto.getUsername());
        Optional<User> owner = userRepository.findByUsername(propertyDto.getUsername());
        if (!owner.isPresent()) {
            throw new UserNotAuthenticatedException();
        }
        if (StringUtils.isEmpty(propertyDto.getAddress().getStreetName()) || StringUtils.isEmpty(propertyDto.getAddress().getStreetNumber())
                || StringUtils.isEmpty(propertyDto.getTitle())
                || StringUtils.isEmpty(propertyDto.getConstructionYear())) {
            throw new InvalidPropertyException("Invalid fields. Address cannot be empty");
        }

        City city = cityRepository.findByCityNameAndRegion(propertyDto.getAddress().getCity().getCityName(), propertyDto.getAddress().getCity().getRegion());
        Address address = Address.builder().streetName(propertyDto.getAddress().getStreetName())
                .streetNumber(propertyDto.getAddress().getStreetNumber())
                .apartmentNumber(propertyDto.getAddress().getApartmentNumber())
                .floorNumber(propertyDto.getAddress().getFloorNumber())
                .city(city).build();
        Address savedAddress = addressRepository.save(address);

        List<Photo> photos = photoRepository.save(propertyDto.getImages());

        Property property = Property.builder()
                .owner(owner.get())
                .address(savedAddress)
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
                .otherInfo(propertyDto.getOtherInfo())
                .dateAdded(new Date())
                .build();
        owner.get().setOwner(true);
        userRepository.save(owner.get());

        propertyRepository.save(property);
    }

    public Page<Property> getPropertiesByOwner(String username, String page, String limit, String option) {
        Optional<User> owner = userRepository.findByUsername(username);
        if (!owner.isPresent()) {
            throw new UserNotAuthenticatedException();
        }
        Sort.Direction direction = option.startsWith("-") ? Sort.Direction.DESC : Sort.Direction.ASC;
        if (option.startsWith("+")) {
            option = option.substring(option.indexOf('+') + 1);
        } else {
            option = option.substring(option.indexOf('-') + 1);
        }
        return propertyRepository.findPropertyByUserOwner(owner.get(), new PageRequest(Integer.parseInt(page) - 1, Integer.parseInt(limit), direction, option));
    }

    public Page<Property> getAllProperties(Integer page, Integer limit, String option) {
        Sort.Direction direction = option.startsWith("-") ? Sort.Direction.DESC : Sort.Direction.ASC;
        if (option.startsWith("+")) {
            option = option.substring(option.indexOf('+') + 1);
        } else {
            option = option.substring(option.indexOf('-') + 1);
        }
        return propertyRepository.findAllProperties(new PageRequest(page - 1, limit, direction, option));
    }

    public Property updateProperty(Property property) {
        Optional<User> owner = userRepository.findByUsername(property.getOwner().getUsername());
        if (!owner.isPresent()) {
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

    public void deleteProperty(long id) {
        propertyRepository.delete(id);
    }

    private void updateAddress(Property property) {
        City city = cityRepository.findByCityNameAndRegion(property.getAddress().getCity().getCityName()
                , property.getAddress().getCity().getRegion());
        property.getAddress().setCity(city);
        Address address = property.getAddress();

        addressRepository.save(address);
    }

    public void rentProperty(long id, Boolean isRented) {
        propertyRepository.isRented(id, isRented);
    }

    public Property getPropertyById(long id) {
        return propertyRepository.findOne(id);
    }
}
