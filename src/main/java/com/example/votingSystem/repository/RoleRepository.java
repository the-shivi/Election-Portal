package com.example.votingSystem.repository;

import com.example.votingSystem.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Roles, String> {

    boolean existsByCategoryAndServiceCode(String category, String serviceCode);
}
