package com.rentIT.service;

import com.rentIT.domain.model.User;
import com.rentIT.domain.model.UserDetails;
import com.rentIT.domain.repository.UserDetailsRepository;
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
    private UserDetailsRepository userDetailsRepository;
    @Autowired
    private UserRepository userRepository;
    private Logger logger = LoggerFactory.getLogger(ProfileDetailsService.class);

    public UserDetails saveDetails(UserDetailsDto userDetailsDto) {
        logger.debug("Save user details {}",userDetailsDto);
        UserDetails userDetails = UserDetails.builder().city(userDetailsDto.getCity())
                .emailAddress(userDetailsDto.getEmailAddress())
                .streetName(userDetailsDto.getStreetName())
                .streetNumber(userDetailsDto.getStreetNumber())
                .phoneNumber(userDetailsDto.getPhoneNumber())
                .build();
        User user = userRepository.findByUsername(userDetailsDto.getUsername()).get();
        userDetails.setUser(user);

        return userDetailsRepository.save(userDetails);
    }

    public UserDetails updateDetails(UserDetailsDto userDetailsDto) {
        logger.debug("Update user details");
        Optional<User> optional = userRepository.findByUsername(userDetailsDto.getUsername());
        UserDetails userDetails = userDetailsRepository.findByUser(optional.get());

        if(userDetails!= null){
            userDetails.setCity(userDetails.getCity());
            userDetails.setEmailAddress(userDetailsDto.getEmailAddress());
            userDetails.setPhoneNumber(userDetailsDto.getPhoneNumber());
            userDetails.setStreetName(userDetailsDto.getStreetName());
            userDetails.setStreetNumber(userDetails.getStreetNumber());

            userDetailsRepository.save(userDetails);
        }

        return userDetails;
    }
}
