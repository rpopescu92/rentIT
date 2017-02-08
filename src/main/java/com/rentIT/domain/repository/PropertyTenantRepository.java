package com.rentIT.domain.repository;


import com.rentIT.domain.model.PropertyTenant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyTenantRepository extends JpaRepository<PropertyTenant, Long>{
}
