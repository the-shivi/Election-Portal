package com.example.votingSystem.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="CATEGORY")
public class Category {
    @Id
    @Column(name = "id", length = 20)
    private String id;

    @Column(name = "name", length = 30)
    private String name;

    @Column(name = "status")
    private String status;
}
