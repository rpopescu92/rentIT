package com.rentIT.service;

import com.rentIT.domain.model.User;
import com.rentIT.domain.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Optional;

@Service
@Slf4j
public class AuthenticationService implements UserDetailsService{

    @Inject
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Authenticating user with username {}", username);

        Optional<User> user = userRepository.findByUsername(username.toLowerCase(Locale.ENGLISH));
        if(user.isPresent()) {
            return new org.springframework.security.core.userdetails.User(user.get().getUsername(), user.get().getPassword(), new ArrayList<>());
        }else {
            throw new UsernameNotFoundException("Invalid username or password");
        }
    }
}
