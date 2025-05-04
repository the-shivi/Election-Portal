package com.example.votingSystem.repository;

import com.example.votingSystem.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,String> {

    Optional<Category> findByIdAndStatus(String id, String status);

}
