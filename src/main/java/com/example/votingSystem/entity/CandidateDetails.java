package com.example.votingSystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "candidate_details")
public class CandidateDetails {

    @EmbeddedId
    private UserElectionId id;

    @Column(name = "name", length = 30)
    private String name;

    @Column(name="mobile_Number")
    private String mobileNumber;

    @Column(name = "election_name", length = 30)
    private String electionName;

    @Column(name = "vote_count")
    private int voteCount;

    @Column(name = "isWinner")
    private boolean isWinner;

}
