package com.example.votingSystem.model;

import com.example.votingSystem.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class CreationSuccessResponse {
    private Status status;
    private String id;
    private LocalDateTime date;

}

