package com.rentIT.service;

import com.rentIT.domain.model.*;
import com.rentIT.domain.repository.AddressRepository;
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
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
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

    private Logger logger = LoggerFactory.getLogger(PropertyService.class);

    public void saveProperty(PropertyDto propertyDto) {
        logger.debug("Save new property for user {}", propertyDto.getUsername());
        Optional<User> owner = userRepository.findByUsername(propertyDto.getUsername());
        if(!owner.isPresent()) {
            throw new UserNotAuthenticatedException();
        }
        if(StringUtils.isEmpty(propertyDto.getAddress().getStreetName()) || StringUtils.isEmpty(propertyDto.getAddress().getStreetNumber())
                || StringUtils.isEmpty(propertyDto.getShortDescription())
                || StringUtils.isEmpty(propertyDto.getConstructionYear())) {
            throw new InvalidPropertyException("Invalid fields. Address cannot be empty");
        }

        Address address = addressRepository.save(propertyDto.getAddress());
        Property property = Property.builder()
                            .owner((User)owner.get())
                            .address(address)
                            .averageRating(0)
                            .constructionYear(propertyDto.getConstructionYear())
                            //.historyRatings(new ArrayList<HistoryRating>())
                            //.images(new ArrayList<Image>())
                            .isFurnished(propertyDto.isFurnished())
                            .longDescription(propertyDto.getLongDescription())
                            .shortDescription(propertyDto.getShortDescription())
                            .price(propertyDto.getPrice())
                            .roomsNumber(propertyDto.getRoomsNumber())
                            .build();
        propertyRepository.save(property);
    }

    public List<Property> getPropertiesByOwner(String username) {
        Optional<User> owner = userRepository.findByUsername(username);
        if(!owner.isPresent()) {
            throw new UserNotAuthenticatedException();
        }

        List<Property> properties = propertyRepository.findPropertyByUserOwner(owner.get());
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
}
