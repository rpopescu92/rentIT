package com.rentIT.resource;

import com.rentIT.domain.model.Property;
import com.rentIT.dto.PropertyDto;
import com.rentIT.exception.InvalidPropertyException;
import com.rentIT.exception.UserNotAuthenticatedException;
import com.rentIT.service.PropertyService;
import com.rentIT.util.AuthenticatedUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
//@RequestMapping("/api")
@Slf4j
public class PropertyResource {

    @Autowired
    private PropertyService propertyService;

    @RequestMapping(value = "/property", method = RequestMethod.POST)
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

    @RequestMapping(value = "/property", method = RequestMethod.GET)
    public ResponseEntity<Page<Property>> getAllProperties(@RequestParam("page") Integer page,
                                                           @RequestParam("limit") Integer limit,
                                                           @RequestParam("order") String order) {
        return new ResponseEntity<Page<Property>>(propertyService.getAllProperties(page,limit, order), HttpStatus.OK);

    }

}
