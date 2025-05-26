package com.example.votingSystem.service;

import com.example.votingSystem.entity.CandidateDetails;
import com.example.votingSystem.entity.ElectionDetails;
import com.example.votingSystem.entity.UserDetails;
import com.example.votingSystem.enums.ErrorCodes;
import com.example.votingSystem.exception.ElectionException;
import com.example.votingSystem.repository.CandidateDetailsRepository;
import com.example.votingSystem.repository.ElectionDetailsRepository;
import com.example.votingSystem.repository.UserDetailsRepository;
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
            throw new ElectionException(ErrorCodes.INVALID_PAYLOAD,"Mobile Number and Election Id are Mandatory");
        }
        Optional<UserDetails> userDetails = userDetailsRepository.findByMobileNumber(msisdn);
        if(userDetails.isEmpty()) {
            throw new ElectionException(ErrorCodes.USER_NOT_FOUND, "User Not Found");
        }
        candidateDetails.getId().setUserId(userDetails.get().getId());
        candidateDetails.setName(userDetails.get().getName());
        Optional<ElectionDetails> electionDetails = electionDetailsRepository.findById(candidateDetails.getId().getElectionId());
        if(electionDetails.isEmpty()){
            throw new ElectionException(ErrorCodes.ELECTION_NOT_FOUND ,"Election Not Found");
        }
        if(electionDetails.get().getClosingDate().isBefore(LocalDateTime.now())){
            throw new ElectionException(ErrorCodes.DATE_EXPIRED, "Election Date Expired");
        }
        if(candidateDetailsRepository.countByIdUserIdAndIdElectionId(candidateDetails.getId().getUserId(),candidateDetails.getId().getElectionId())>0){
            throw new ElectionException(ErrorCodes.ALREADY_ENROLLED, "Candidate is already enrolled in this election");
        }
        if(electionDetails.get().getMaximumCandidate() <= electionDetails.get().getEnrolledCandidate()) {
            throw new ElectionException(ErrorCodes.NO_SEAT_AVAILABLE, "No More candidates allowed for this election");
        }
        candidateDetails.setElectionName(electionDetails.get().getName());
        candidateDetails.setVoteCount(0);
        electionDetails.get().setEnrolledCandidate(electionDetails.get().getEnrolledCandidate()+1);
        electionDetails.get().setModifiedOn(LocalDateTime.now());
        electionDetailsRepository.save(electionDetails.get());
    }
}
