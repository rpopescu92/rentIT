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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(profileDetails, HttpStatus.OK);
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResponseEntity<Photo> uploadProfilePhoto(@RequestParam("file") MultipartFile file,
                                                    @RequestParam("username") String username ) throws IOException{

        if (!file.isEmpty()) {
            Photo photo = Photo.builder().content(file.getBytes())
                                .name(file.getName()).build();
            profileDetailsService.uploadPhoto(username,photo);
            logger.debug("file upload has bytes");
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
