package com.rentIT.domain.repository;

import com.rentIT.domain.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image,Long>{
}
