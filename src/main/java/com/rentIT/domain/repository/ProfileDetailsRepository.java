package com.rentIT.domain.repository;


import com.rentIT.domain.model.User;
import com.rentIT.domain.model.ProfileDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileDetailsRepository extends JpaRepository<ProfileDetails, Long>{
    ProfileDetails findByUser(User user);
}
