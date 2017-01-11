package com.rentIT.resource;

import com.rentIT.domain.model.User;
import com.rentIT.dto.UserDto;
import com.rentIT.exception.UserExistsException;
import com.rentIT.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity register(@RequestBody UserDto userDto) {
        log.info("Register user with username {}",userDto.getUsername());

        BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
        String encodedPassword = bCrypt.encode(userDto.getPassword());
        userDto.setPassword(encodedPassword);

        try{
            userService.registerUser(userDto);
            return new ResponseEntity(HttpStatus.OK);
        }catch (UserExistsException ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
