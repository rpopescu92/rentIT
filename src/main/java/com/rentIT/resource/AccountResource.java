package com.rentIT.resource;

import com.rentIT.service.UserService;
import jdk.nashorn.internal.objects.annotations.Getter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Slf4j
public class AccountResource {

    @Autowired
    private UserService userService;

    private Logger log = LoggerFactory.getLogger(ProfileDetailsResource.class);

    @RequestMapping(value = "/account", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAccount() {
        log.debug("Get Account");
        return userService.getAuthenticatedUser()
                .map(user -> new ResponseEntity(user, HttpStatus.OK))
                .orElse(new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR));
    }
}
