package com.example.votingSystem.service;

import com.example.votingSystem.entity.ElectionDetails;
import com.example.votingSystem.enums.IdType;
import com.example.votingSystem.enums.Status;
import com.example.votingSystem.exception.AdminElectionException;
import com.example.votingSystem.model.FetchElectionDataResponse;
import com.example.votingSystem.repository.ElectionDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.votingSystem.enums.IdType.ELECTIONID;

@ConfigurationProperties
@Service
public class AdminVotingService {

    @Value("${min_election_active_days}")
    private int minElectionActiveDays;

    @Autowired
    ElectionDetailsRepository electionDetailsRepository;

    @Autowired
    Validator validator;

    @Autowired
    IdGenerator idGenerator;


    public ElectionDetails addNewElection(ElectionDetails electionDetails) {

        String generatedElectionIdId = idGenerator.generateId(ELECTIONID);
        electionDetails.setId(generatedElectionIdId);
        if(electionDetails.getMaximumCandidate() == null) {
            electionDetails.setMaximumCandidate(5);
        }

        if (validator.isClosingDateValid(electionDetails.getStartingDate(),electionDetails.getClosingDate(),minElectionActiveDays)) {
            throw new AdminElectionException("Active duration must be more than "+ minElectionActiveDays +" days");
        }
        electionDetails.setCreatedOn(LocalDateTime.now());
        electionDetails.setEnrolledCandidate(0);
        electionDetails.setModifiedOn(LocalDateTime.now());
        return electionDetailsRepository.save(electionDetails);
    }

    public FetchElectionDataResponse getAllElection() {
        List<ElectionDetails> electionDetailsList = electionDetailsRepository.findAll();
        if(electionDetailsList.isEmpty()){
            throw new AdminElectionException("No Election Data is Present");
        }
        return new FetchElectionDataResponse(Status.SUCCESS,electionDetailsList.size(),electionDetailsList,LocalDateTime.now());
    }


}