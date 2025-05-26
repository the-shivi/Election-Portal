package com.example.votingSystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "user_details")
public class UserDetails {

    @Id
    @Column(name = "id", length = 20)
    private String id;

    @Column(name = "name", length = 30)
    private String name;

    @Column(name = "age")
    private Integer age;

    @Column(name="mobile_Number")
    @NotNull(message = "MobileNumber is required")
    private String mobileNumber;

    @Column(name = "category", length = 30)
    private String category;

    @Column(name = "categoryId", length = 10)
    @NotNull(message = "CategoryId is required")
    private String categoryId;

    @Column(name = "Status", length = 2)
    private String status;

    @Column(name = "created_on")
    private LocalDateTime createdOn;
}