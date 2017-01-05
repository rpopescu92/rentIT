package com.rentIT.domain.repository;


import com.rentIT.domain.model.User;
import com.rentIT.domain.model.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailsRepository extends JpaRepository<UserDetails, Long>{
    UserDetails findByUser(User user);
}
