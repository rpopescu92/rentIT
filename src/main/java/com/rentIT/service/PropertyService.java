package com.rentIT.service;

import com.rentIT.domain.model.*;
import com.rentIT.domain.repository.AddressRepository;
import com.rentIT.domain.repository.PropertyRepository;
import com.rentIT.domain.repository.UserRepository;
import com.rentIT.dto.PropertyDto;
import com.rentIT.exception.InvalidPropertyException;
import com.rentIT.exception.UserNotAuthenticatedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
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

    public void saveProperty(PropertyDto propertyDto) {

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
}
