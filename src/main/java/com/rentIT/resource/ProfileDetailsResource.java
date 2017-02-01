package com.rentIT.resource;

import com.rentIT.domain.model.Photo;
import com.rentIT.domain.model.ProfileDetails;
import com.rentIT.dto.UserDetailsDto;
import com.rentIT.service.ProfileDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Slf4j
public class ProfileDetailsResource {

    @Autowired
    private ProfileDetailsService profileDetailsService;

    private Logger logger = LoggerFactory.getLogger(ProfileDetailsResource.class);

    @RequestMapping(value = "/profile/{username}", method = RequestMethod.POST)
    public ResponseEntity registerDetails(@PathVariable("username")String username, @RequestBody ProfileDetails profileDetails) {
        try {
           ProfileDetails updatedProfileDetails = profileDetailsService.updateDetails(profileDetails);
            return new ResponseEntity(updatedProfileDetails, HttpStatus.OK);
        }catch (Exception ex) {
            return new ResponseEntity(ex.getStackTrace(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/profile/{username}", method = RequestMethod.GET)
    public ResponseEntity<ProfileDetails> getProfileDetails(@PathVariable("username") String username) {
        ProfileDetails profileDetails = profileDetailsService.getProfileDetails(username);
        if(profileDetails == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(profileDetails, HttpStatus.OK);
    }

    @RequestMapping(value = "/profile/{username}/photo", method = RequestMethod.POST)
    public ResponseEntity<Photo> uploadProfilePhoto(@PathVariable("username") String username, @RequestBody Photo photo){
        profileDetailsService.uploadPhoto(username, photo);
        logger.debug("Photo for {} username uploaded",username);
        return new ResponseEntity( HttpStatus.OK);
    }
}
