package com.example.votingSystem.repository;

import com.example.votingSystem.entity.Category;
import com.example.votingSystem.entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDetailsRepository extends JpaRepository<UserDetails,String> {

    Optional<UserDetails> findByMobileNumber(String msisdn);
}
