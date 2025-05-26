package com.example.votingSystem.repository;

import com.example.votingSystem.entity.UserAuthentication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAuthenticationRepository extends JpaRepository<UserAuthentication,String> {

    Optional<UserAuthentication>findByMobileNumber(String mobileNumber);
    Optional<UserAuthentication>findByUserId(String userId);
}
