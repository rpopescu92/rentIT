package com.rentIT.service;

import com.rentIT.domain.model.*;
import com.rentIT.domain.repository.*;
import com.rentIT.dto.PropertyDto;
import com.rentIT.exception.UserNotAuthenticatedException;
import com.rentIT.factory.AddressFactory;
import com.rentIT.factory.PropertyFactory;
import com.rentIT.resource.model.SearchOptions;
import com.rentIT.util.DateTimeFactory;
import com.rentIT.validation.PropertyValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
    @Autowired
    private PropertyValidator propertyValidator;
    @Autowired
    private PropertyAdvancedSearchRepository propertyAdvancedSearchRepository;

    public void saveProperty(PropertyDto propertyDto) {
        log.debug("Save new property for user {}", propertyDto.getUsername());
        Optional<User> owner = userRepository.findByUsername(propertyDto.getUsername());
        if (!owner.isPresent()) {
            throw new UserNotAuthenticatedException();
        }
        User ownerUser = owner.get();
        propertyValidator.isValid(propertyDto);

        City city = cityRepository.findByCityNameAndRegion(propertyDto.getAddress().getCity().getCityName(), propertyDto.getAddress().getCity().getRegion());
        Address address = AddressFactory.createAddress(propertyDto, city);
        Address savedAddress = addressRepository.save(address);

        List<Photo> photos = photoRepository.save(propertyDto.getImages());

        Property property = PropertyFactory.createProperty(ownerUser, propertyDto, savedAddress, photos);
        owner.get().setOwner(true);
        userRepository.save(ownerUser);

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

    public Page<Property> getAllProperties(Integer page, Integer limit, String option, SearchOptions searchOptions) {
        if (searchOptions == null) {
            Sort.Direction direction = option.startsWith("-") ? Sort.Direction.DESC : Sort.Direction.ASC;
            if (option.startsWith("+")) {
                option = option.substring(option.indexOf('+') + 1);
            } else {
                option = option.substring(option.indexOf('-') + 1);
            }
            return propertyRepository.findAllProperties(new PageRequest(page - 1, limit, direction, option));
        } else {
            return new PageImpl<>(propertyAdvancedSearchRepository.getAllPropertiesBySearchOptions(page, limit, option, searchOptions), new PageRequest(page - 1, limit), 0);
        }
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
        updatedProperty.setStatus(property.getStatus());
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

    public void rentProperty(long id, Rent status) {
        if (status.getStatus() == Status.NOT_RENTED) {
            propertyRepository.rent(id, status.getStatus(), "");
        } else {
            propertyRepository.rent(id, status.getStatus(), DateTimeFactory.createNowDate());
        }
    }

    public Property getPropertyById(long id) {
        return propertyRepository.findOne(id);
    }
}
