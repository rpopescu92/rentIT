package com.rentIT.service;

import com.rentIT.domain.model.Address;
import com.rentIT.domain.model.User;
import com.rentIT.domain.model.ProfileDetails;
import com.rentIT.domain.repository.AddressRepository;
import com.rentIT.domain.repository.ProfileDetailsRepository;
import com.rentIT.domain.repository.UserRepository;
import com.rentIT.dto.UserDetailsDto;
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

    private Logger logger = LoggerFactory.getLogger(ProfileDetailsService.class);


    public ProfileDetails updateDetails(ProfileDetails profileDetails) {
        logger.debug("Update user details");
        Optional<User> optional = userRepository.findByUsername(profileDetails.getUser().getUsername());
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
            return null;
        }
        return profileDetailsRepository.findByUser(optional.get());
    }

    private Address buildAddress(ProfileDetails profileDetails) {
        Address address = addressRepository.findOne(profileDetails.getAddress().getId());
        address.setApartmentNumber(profileDetails.getAddress().getApartmentNumber());
        address.setCity(profileDetails.getAddress().getCity());
        address.setStreetName(profileDetails.getAddress().getStreetName());
        address.setStreetNumber(profileDetails.getAddress().getStreetNumber());
        address.setFloorNumber(profileDetails.getAddress().getFloorNumber());
        address.setOtherDirections(profileDetails.getAddress().getOtherDirections());

        addressRepository.save(address);
        return address;
    }
}
