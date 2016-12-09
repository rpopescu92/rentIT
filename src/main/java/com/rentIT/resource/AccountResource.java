package com.rentIT.resource;

import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by roxanap on 09.12.2016.
 */
@RestController
@RequestMapping("/api/account")
public class AccountResource {

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getAccount() {
        return new ResponseEntity(HttpStatus.OK);

    }


}
