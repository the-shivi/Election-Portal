package com.example.votingSystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ELECTION_DETAILS")
public class ElectionDetails {

    @Id
    @Column(name = "id", length = 20)
    private String id;

    @Column(name = "name", length = 30)
    @NotBlank(message = "Name must not be blank")
    @Size(max = 30, message = "Name cannot exceed 30 characters")
    private String name;

    @Column(name = "starting_date")
    @NotNull(message = "Starting date is required")
    private LocalDateTime startingDate;

    @Column(name = "closing_date")
    @NotNull(message = "Closing date is required")
    private LocalDateTime closingDate;

    @Column(name = "maximum_candidate")
    @Min(value = 2, message = "There must be at least 2 candidate")
    @Max(value = 10, message = "Too many candidates (max 10)")
    private Integer maximumCandidate;

    @Column(name = "enrolled_candidate")
    private Integer enrolledCandidate;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "modified_on")
    private LocalDateTime modifiedOn;

}
