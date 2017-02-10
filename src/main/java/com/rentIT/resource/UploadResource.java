package com.rentIT.resource;

import com.rentIT.domain.model.Photo;
import com.rentIT.service.ProfileDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/upload")
@Slf4j
public class UploadResource {

    @Autowired
    private ProfileDetailsService profileDetailsService;

    private Logger logger = LoggerFactory.getLogger(ProfileDetailsResource.class);

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Photo> uploadProfilePhoto(@RequestParam("file") MultipartFile file,
                                                    @RequestParam("username") String username ) throws IOException {
        byte[] bytes;
        logger.debug("file upload");
        if (!file.isEmpty()) {
            bytes = file.getBytes();
            //store file in storage
            logger.debug("file upload has bytes");
            return new ResponseEntity<Photo>(HttpStatus.OK);
        }
        return new ResponseEntity<Photo>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
