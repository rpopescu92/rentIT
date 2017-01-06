package com.rentIT.resource;

import com.rentIT.dto.PropertyDto;
import com.rentIT.exception.InvalidPropertyException;
import com.rentIT.exception.UserNotAuthenticatedException;
import com.rentIT.service.PropertyService;
import com.rentIT.util.AuthenticatedUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
//@RequestMapping("/api")
@Slf4j
public class PropertyResource {

    @Autowired
    private PropertyService propertyService;

    @RequestMapping(value = "property", method = RequestMethod.POST)
    public ResponseEntity addProperty(@Valid @RequestBody PropertyDto propertyDto) {
        if(StringUtils.isEmpty(propertyDto.getUsername())) {
            propertyDto.setUsername(AuthenticatedUser.getAuthenticatedUsername());
        }

        try{
            propertyService.saveProperty(propertyDto);
            return new ResponseEntity(HttpStatus.OK);
        } catch (InvalidPropertyException | UserNotAuthenticatedException ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }
}
