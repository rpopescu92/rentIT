package com.rentIT.service;

import com.rentIT.domain.model.Photo;
import com.rentIT.domain.repository.PhotoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PhotoService {

    @Autowired
    private PhotoRepository photoRepository;

    public void savePhotos(List<Photo> photos) {
        photoRepository.save(photos);
    }
}
