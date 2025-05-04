package com.example.votingSystem.service;

import com.example.votingSystem.entity.CandidateDetails;
import com.example.votingSystem.entity.ElectionDetails;
import com.example.votingSystem.entity.UserDetails;
import com.example.votingSystem.entity.UserElectionId;
import com.example.votingSystem.exception.AdminElectionException;
import com.example.votingSystem.repository.CandidateDetailsRepository;
import com.example.votingSystem.repository.ElectionDetailsRepository;
import com.example.votingSystem.repository.UserDetailsRepository;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CandidateService {

    @Autowired
    CandidateDetailsRepository candidateDetailsRepository;

    @Autowired
    UserDetailsRepository userDetailsRepository;

    @Autowired
    ElectionDetailsRepository electionDetailsRepository;

    public CandidateDetails addCandidate(CandidateDetails candidateDetails) {
        ValidateAndPopulateData(candidateDetails);
        return candidateDetailsRepository.save(candidateDetails);

    }

    public void ValidateAndPopulateData(CandidateDetails candidateDetails){
        String msisdn = candidateDetails.getMobileNumber();
        if(msisdn== null || candidateDetails.getId().getElectionId() == null){
            throw new AdminElectionException("Mobile Number and Election Id are Mandatory");
        }
        Optional<UserDetails> userDetails = userDetailsRepository.findByMobileNumber(msisdn);
        if(userDetails.isEmpty()) {
            throw new AdminElectionException("User Not Found");
        }
        candidateDetails.getId().setUserId(userDetails.get().getId());
        candidateDetails.setName(userDetails.get().getName());
        Optional<ElectionDetails> electionDetails = electionDetailsRepository.findById(candidateDetails.getId().getElectionId());
        if(electionDetails.isEmpty()){
            throw new AdminElectionException("Election Not Found");
        }
        if(electionDetails.get().getClosingDate().isBefore(LocalDateTime.now())){
            throw new AdminElectionException("Election Date Expired");
        }
        if(candidateDetailsRepository.countByIdUserIdAndIdElectionId(candidateDetails.getId().getUserId(),candidateDetails.getId().getElectionId())>0){
            throw new AdminElectionException("Candidate is already enrolled in this election");
        }
        if(electionDetails.get().getMaximumCandidate() <= electionDetails.get().getEnrolledCandidate()) {
            throw new AdminElectionException("No More candidates allowed for this election");
        }
        candidateDetails.setElectionName(electionDetails.get().getName());
        candidateDetails.setVoteCount(0);
        electionDetails.get().setEnrolledCandidate(electionDetails.get().getEnrolledCandidate()+1);
        electionDetails.get().setModifiedOn(LocalDateTime.now());
        electionDetailsRepository.save(electionDetails.get());
    }
}
