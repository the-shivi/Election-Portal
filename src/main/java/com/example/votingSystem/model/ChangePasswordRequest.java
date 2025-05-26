package com.example.votingSystem.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordRequest {
    @NotNull(message = "IdentifierType is required")
    private String identifierType;
    @NotNull(message = "Identifier Value is required")
    private String identifierValue;
    @NotNull(message = "Old Password is required")
    private String oldPassword;
    @NotNull(message = "New Password is required")
    private String newPassword;
}
