package com.example.votingSystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthentication {
    @Id
    private String authenticationId;
    private String userId;
    private String mobileNumber;
    private String password;
    private LocalDateTime createdOn;
    private LocalDateTime modifiedOn;
    private  String isdefaultPassword;
}
