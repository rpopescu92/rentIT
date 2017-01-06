package com.rentIT.service;

import com.rentIT.domain.model.Address;
import com.rentIT.domain.model.User;
import com.rentIT.domain.model.ProfileDetails;
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
    private Logger logger = LoggerFactory.getLogger(ProfileDetailsService.class);

    public ProfileDetails saveDetails(UserDetailsDto userDetailsDto) {
        logger.debug("Save user details {}",userDetailsDto);

        Address address = buildAddress(userDetailsDto);
        ProfileDetails profileDetails = ProfileDetails.builder().address(address)
                .emailAddress(userDetailsDto.getEmailAddress())
                .phoneNumber(userDetailsDto.getPhoneNumber())
                .build();

        User user = userRepository.findByUsername(userDetailsDto.getUsername()).get();
        profileDetails.setUser(user);

        return profileDetailsRepository.save(profileDetails);
    }

    public ProfileDetails updateDetails(UserDetailsDto userDetailsDto) {
        logger.debug("Update user details");
        Optional<User> optional = userRepository.findByUsername(userDetailsDto.getUsername());
        ProfileDetails profileDetails = profileDetailsRepository.findByUser(optional.get());

        if(profileDetails != null){
            profileDetails.setAddress(buildAddress(userDetailsDto));
            profileDetails.setEmailAddress(userDetailsDto.getEmailAddress());
            profileDetails.setPhoneNumber(userDetailsDto.getPhoneNumber());

            profileDetailsRepository.save(profileDetails);
        }

        return profileDetails;
    }

    private Address buildAddress(UserDetailsDto userDetailsDto) {
        Address address = Address.builder().city(userDetailsDto.getCity())
                .streetName(userDetailsDto.getStreetName())
                .streetNumber(userDetailsDto.getStreetNumber())
                .build();
        return address;
    }
}
