package com.rentIT.service;

import com.rentIT.domain.model.Address;
import com.rentIT.domain.model.ProfileDetails;
import com.rentIT.domain.model.User;
import com.rentIT.domain.model.UserRole;
import com.rentIT.domain.repository.AddressRepository;
import com.rentIT.domain.repository.ProfileDetailsRepository;
import com.rentIT.domain.repository.UserRepository;
import com.rentIT.dto.UserDto;
import com.rentIT.exception.UserExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProfileDetailsRepository profileDetailsRepository;
    @Autowired
    private AddressRepository addressRepository;

    public Optional<User> getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null) {
            return null;
        }

        return userRepository.findByUsername(authentication.getName());
    }

    public void registerUser(UserDto userDto) {
        Optional<User> optional = userRepository.findByUsername(userDto.getUsername());
        if(optional.isPresent()) {
            throw new UserExistsException("Username already exists.");
        }
        User user = User.builder()
                        .username(userDto.getUsername())
                        .password(userDto.getPassword())
                        .createDate(new Date())
                        .role(UserRole.USER)
                        .build();

        Address address = new Address();
        address = addressRepository.save(address);
        ProfileDetails profileDetails = ProfileDetails.builder()
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .address(address)
                .build();
        user = userRepository.save(user);
        profileDetails.setUser(user);
        profileDetailsRepository.save(profileDetails);

    }
}
