package com.example.votingSystem.controller;


import com.example.votingSystem.entity.CandidateDetails;
import com.example.votingSystem.entity.ElectionDetails;
import com.example.votingSystem.entity.UserDetails;
import com.example.votingSystem.enums.ServiceCodes;
import com.example.votingSystem.enums.Status;
import com.example.votingSystem.model.CreationSuccessResponse;
import com.example.votingSystem.model.ErrorResponse;
import com.example.votingSystem.model.FetchElectionDataResponse;
import com.example.votingSystem.service.AdminVotingService;
import com.example.votingSystem.service.CandidateService;
import com.example.votingSystem.service.RoleValidationService;
import com.example.votingSystem.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class ApiController {

    @Autowired
    AdminVotingService adminVotingService;

    @Autowired
    UserService userService;

    @Autowired
    CandidateService candidateService;

    @Autowired
    RoleValidationService roleValidationService;

    //create Election
    @PostMapping("/election/add")
    public ResponseEntity<Object> addElection(@Valid  @RequestBody ElectionDetails electionDetails ){
        roleValidationService.hasAccess(ServiceCodes.ADD_ELECTION.toString());
        ElectionDetails saved = adminVotingService.addNewElection(electionDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CreationSuccessResponse(Status.SUCCESS, electionDetails.getId(), LocalDateTime.now(),ServiceCodes.ADD_ELECTION.toString()));

    }

    //View all election
    @GetMapping("/election/getAll")
    public ResponseEntity<Object> GetAllElection(){
        roleValidationService.hasAccess(ServiceCodes.GET_ELECTION_LIST.toString());
        FetchElectionDataResponse fetchElectionDataResponse = adminVotingService.getAllElection();
        return ResponseEntity.status(HttpStatus.CREATED).body(fetchElectionDataResponse);

    }

    //Add user
    @PostMapping("/user/add")
    public ResponseEntity<Object> addUser( @Valid@RequestBody UserDetails userDetails){
        roleValidationService.hasAccess(ServiceCodes.ADD_USER.toString());
        UserDetails saved = userService.addUser(userDetails);
        return ResponseEntity.ok(new CreationSuccessResponse(Status.SUCCESS, userDetails.getId(), LocalDateTime.now(), ServiceCodes.ADD_USER.toString()));
    }

    //Add candidate to a election
    @PostMapping("/election/addCandidate")
    public ResponseEntity<Object> addCandidate( @Valid @RequestBody CandidateDetails candidateDetails){
        roleValidationService.hasAccess(ServiceCodes.ADD_CANDIDATE.toString());
        CandidateDetails saved = candidateService.addCandidate(candidateDetails);
        return ResponseEntity.status(HttpStatus.OK).body(new CreationSuccessResponse(Status.SUCCESS, candidateDetails.getId().getElectionId()+ " "+candidateDetails.getId().getUserId(), LocalDateTime.now(), ServiceCodes.ADD_CANDIDATE.toString()));
    }

}
