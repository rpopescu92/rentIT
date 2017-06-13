package com.rentIT.resource;

import com.rentIT.domain.model.Property;
import com.rentIT.domain.model.Status;
import com.rentIT.dto.PropertyDto;
import com.rentIT.exception.InvalidPropertyException;
import com.rentIT.exception.UserNotAuthenticatedException;
import com.rentIT.resource.model.SearchOptions;
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
@RequestMapping("/api")
@Slf4j
public class PropertyResource {

    private final PropertyService propertyService;

    @Autowired
    public PropertyResource(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @RequestMapping(value = "/properties", method = RequestMethod.POST)
    public ResponseEntity addProperty(@Valid @RequestBody PropertyDto propertyDto) {
        if (StringUtils.isEmpty(propertyDto.getUsername())) {
            propertyDto.setUsername(AuthenticatedUser.getAuthenticatedUsername());
        }

        try {
            propertyService.saveProperty(propertyDto);
            return new ResponseEntity(HttpStatus.OK);
        } catch (InvalidPropertyException | UserNotAuthenticatedException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @RequestMapping(value = "/properties/advanced", method = RequestMethod.POST)
    public ResponseEntity<Page<Property>> getAllProperties(@RequestParam("page") Integer page,
                                                           @RequestParam("limit") Integer limit,
                                                           @RequestParam("order") String order,
                                                           @RequestBody(required = false) SearchOptions searchOptions) {
        return new ResponseEntity<>(propertyService.getAllProperties(page, limit, order, searchOptions), HttpStatus.OK);
    }

    @RequestMapping(value = "/properties/{username}", method = RequestMethod.GET)
    public ResponseEntity<Page<Property>> getAllPropertiesByUser(@PathVariable("username") String username,
                                                                 @RequestParam("page") String page,
                                                                 @RequestParam("limit") String limit,
                                                                 @RequestParam("order") String order) {
        return new ResponseEntity<>(propertyService.getPropertiesByOwner(username, page, limit, order), HttpStatus.OK);
    }

    @RequestMapping(value = "/properties/{id}", method = RequestMethod.POST)
    public ResponseEntity<Property> updateProperty(@PathVariable("id") long id, @RequestBody Property property) {
        Property updatedProperty = propertyService.updateProperty(property);
        return new ResponseEntity<>(updatedProperty, HttpStatus.OK);
    }

    @RequestMapping(value = "/properties/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteProperty(@PathVariable("id") long id) {
        propertyService.deleteProperty(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/properties/{id}", method = RequestMethod.PATCH)
    public ResponseEntity rentProperty(@PathVariable("id") long id, @RequestBody Status status) {
        propertyService.rentProperty(id, status);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/properties/{id}/value", method = RequestMethod.GET)
    public ResponseEntity<Property> getProperty(@PathVariable("id") long id) {
        return new ResponseEntity<>(propertyService.getPropertyById(id), HttpStatus.OK);
    }

}
