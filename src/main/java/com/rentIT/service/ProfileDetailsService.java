package com.rentIT.service;

import com.rentIT.domain.model.*;
import com.rentIT.domain.repository.*;
import com.rentIT.dto.UserDetailsDto;
import com.rentIT.exception.UserNotAuthenticatedException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class ProfileDetailsService {

    @Autowired
    private ProfileDetailsRepository profileDetailsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private PhotoRepository photoRepository;

    private Logger logger = LoggerFactory.getLogger(ProfileDetailsService.class);


    public ProfileDetails updateDetails(ProfileDetails profileDetails) {
        logger.debug("Update user details");
        Optional<User> optional = userRepository.findByUsername(profileDetails.getUser().getUsername());
        if(!optional.isPresent()) {
            throw new UserNotAuthenticatedException();
        }
        ProfileDetails existingProfileDetails = profileDetailsRepository.findByUser(optional.get());

        if(existingProfileDetails != null){
            existingProfileDetails.setAddress(buildAddress(profileDetails));
            existingProfileDetails.setEmailAddress(profileDetails.getEmailAddress());
            existingProfileDetails.setPhoneNumber(profileDetails.getPhoneNumber());

            profileDetailsRepository.save(existingProfileDetails);
        }

        return existingProfileDetails;
    }

    public ProfileDetails getProfileDetails(String username){
        Optional<User> optional = userRepository.findByUsername(username);
        if(!optional.isPresent()) {
            throw new UserNotAuthenticatedException();
        }
        return profileDetailsRepository.findByUser(optional.get());
    }

    private Address buildAddress(ProfileDetails profileDetails) {
        Address address = addressRepository.findOne(profileDetails.getAddress().getId());
        address.setApartmentNumber(profileDetails.getAddress().getApartmentNumber());
        City city = cityRepository.findByCityName(profileDetails.getAddress().getCity().getCityName());
        if(city != null) {
            address.setCity(city);
        }
        address.setStreetName(profileDetails.getAddress().getStreetName());
        address.setStreetNumber(profileDetails.getAddress().getStreetNumber());
        address.setFloorNumber(profileDetails.getAddress().getFloorNumber());

        addressRepository.save(address);
        return address;
    }

    public void uploadPhoto(String username, Photo photo) {
        Optional<User> user = userRepository.findByUsername(username);
        if(!user.isPresent()) {
            throw new UserNotAuthenticatedException();
        }

        ProfileDetails profileDetails = profileDetailsRepository.findByUser(user.get());
        if(profileDetails.getPhoto() != null) {
            Photo savedPhoto = photoRepository.findOne(profileDetails.getPhoto().getId());
            savedPhoto.setContent(photo.getContent());
            savedPhoto.setName(photo.getName());
            photoRepository.save(savedPhoto);
            profileDetails.setPhoto(savedPhoto);
        } else {
            photo = photoRepository.save(photo);
            profileDetails.setPhoto(photo);
        }
        profileDetailsRepository.save(profileDetails);
    }
}
