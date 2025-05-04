package com.example.votingSystem.repository;

import com.example.votingSystem.entity.ElectionDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ElectionDetailsRepository extends JpaRepository<ElectionDetails,String> {


}
