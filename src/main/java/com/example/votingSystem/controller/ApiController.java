package com.example.votingSystem.controller;


import com.example.votingSystem.entity.CandidateDetails;
import com.example.votingSystem.entity.ElectionDetails;
import com.example.votingSystem.entity.UserDetails;
import com.example.votingSystem.enums.Status;
import com.example.votingSystem.model.CreationSuccessResponse;
import com.example.votingSystem.model.ErrorResponse;
import com.example.votingSystem.model.FetchElectionDataResponse;
import com.example.votingSystem.service.AdminVotingService;
import com.example.votingSystem.service.CandidateService;
import com.example.votingSystem.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    //create Election
    @PostMapping("/election/add")
    public ResponseEntity<Object> addElection(@Valid  @RequestBody ElectionDetails electionDetails){
        try {
            ElectionDetails saved = adminVotingService.addNewElection(electionDetails);

            return ResponseEntity.status(HttpStatus.CREATED).body(new CreationSuccessResponse(Status.SUCCESS, electionDetails.getId(), LocalDateTime.now()));
        }
        catch (Exception e) {
            // If an error occurs, return the error response
            ErrorResponse errorResponse = new ErrorResponse("ELECTION_CREATION_ERROR", e.getMessage(),Status.FAILED);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    //View all election
    @GetMapping("/election/getAll")
    public ResponseEntity<Object> GetAllElection(){
        try {
            FetchElectionDataResponse fetchElectionDataResponse = adminVotingService.getAllElection();
            return ResponseEntity.status(HttpStatus.CREATED).body(fetchElectionDataResponse);
        }
        catch (Exception e) {
            // If an error occurs, return the error response
            ErrorResponse errorResponse = new ErrorResponse("FETCH_ELECTION_ERROR", e.getMessage(),Status.FAILED);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    //Add user
    @PostMapping("/user/add")
    public ResponseEntity<Object> addUser(@RequestBody UserDetails userDetails){
        try {
            UserDetails saved = userService.addUser(userDetails);

            return ResponseEntity.status(HttpStatus.CREATED).body(new CreationSuccessResponse(Status.SUCCESS, userDetails.getId(), LocalDateTime.now()));
        }
        catch (Exception e) {
            // If an error occurs, return the error response
            ErrorResponse errorResponse = new ErrorResponse("USER_CREATION_ERROR", e.getMessage(),Status.FAILED);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    //Add candidate to a election
    @PostMapping("/election/addCandidate")
    public ResponseEntity<Object> addCandidate(@RequestBody CandidateDetails candidateDetails){
        try {
            CandidateDetails saved = candidateService.addCandidate(candidateDetails);

            return ResponseEntity.status(HttpStatus.CREATED).body(new CreationSuccessResponse(Status.SUCCESS, candidateDetails.getId().getElectionId()+ " "+candidateDetails.getId().getUserId(), LocalDateTime.now()));
        }
        catch (Exception e) {
            // If an error occurs, return the error response
            ErrorResponse errorResponse = new ErrorResponse("CANDIDATE_CREATION_ERROR", e.getMessage(),Status.FAILED);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

}
