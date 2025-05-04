package com.example.votingSystem.repository;

import com.example.votingSystem.entity.CandidateDetails;
import com.example.votingSystem.entity.UserElectionId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateDetailsRepository extends JpaRepository<CandidateDetails, UserElectionId> {

    long countByIdUserIdAndIdElectionId(String userId, String electionId);

    long countByIdElectionId(String electionId);


}
