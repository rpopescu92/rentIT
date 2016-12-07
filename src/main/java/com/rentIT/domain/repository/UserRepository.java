package com.rentIT.domain.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.rentIT.domain.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
