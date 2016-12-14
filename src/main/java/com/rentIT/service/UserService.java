package com.rentIT.service;

import com.rentIT.domain.model.User;
import com.rentIT.domain.repository.UserRepository;
import com.rentIT.exception.UserExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null) {
            return null;
        }

        Optional<User> optional = userRepository.findByUsername(authentication.getName());
        return optional;
    }

    public void registerUser(User user) {
        Optional<User> optional = userRepository.findByUsername(user.getUsername());
        if(optional.isPresent()) {
            throw new UserExistsException("Username already exists.");
        }
        User savedUser = userRepository.save(user);
    }
}
