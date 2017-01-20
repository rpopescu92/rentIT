package com.rentIT.domain.repository;

import com.rentIT.domain.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository  extends JpaRepository<Photo,Long>{
}
