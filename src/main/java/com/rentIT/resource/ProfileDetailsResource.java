package com.rentIT.resource;

import com.rentIT.domain.model.ProfileDetails;
import com.rentIT.dto.UserDetailsDto;
import com.rentIT.service.ProfileDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("/api")
@Slf4j
public class ProfileDetailsResource {

    @Autowired
    private ProfileDetailsService profileDetailsService;

    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    public ResponseEntity registerDetails(@RequestBody UserDetailsDto userDetailsDto) {
        try {
           ProfileDetails profileDetails = profileDetailsService.saveDetails(userDetailsDto);
            return new ResponseEntity(profileDetails, HttpStatus.OK);
        }catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/profile/edit", method = RequestMethod.POST)
    public ResponseEntity updateDetails (@RequestBody UserDetailsDto userDetailsDto) {
        try {
            ProfileDetails profileDetails = profileDetailsService.updateDetails(userDetailsDto);
            return new ResponseEntity(profileDetails, HttpStatus.OK);
        }catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
