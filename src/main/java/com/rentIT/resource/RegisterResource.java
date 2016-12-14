package com.rentIT.resource;

import com.rentIT.domain.model.User;
import com.rentIT.exception.UserExistsException;
import com.rentIT.service.UserService;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.Date;

@RestController
@RequestMapping("/register")
@Slf4j
public class RegisterResource {

    @Inject
    private UserService userService;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity register(@RequestBody User user) {
        log.info("Registering user with username {}",user.getUsername());

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setCreateDate(new Date());

        try{
            userService.registerUser(user);
            return new ResponseEntity(HttpStatus.OK);
        }catch (UserExistsException ex) {
            return new ResponseEntity(ex.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
