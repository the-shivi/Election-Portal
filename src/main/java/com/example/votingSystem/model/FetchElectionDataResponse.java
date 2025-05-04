package com.example.votingSystem.model;

import com.example.votingSystem.entity.ElectionDetails;
import com.example.votingSystem.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FetchElectionDataResponse {
    private Status status;
    private Integer totalElections;
    private List<ElectionDetails> electionDetailsList;
    private LocalDateTime date;
}
