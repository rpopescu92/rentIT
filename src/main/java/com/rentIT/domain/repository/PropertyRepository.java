package com.rentIT.domain.repository;

import com.rentIT.domain.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by roxanap on 06.01.2017.
 */
public interface PropertyRepository extends JpaRepository<Property, Long> {
}
