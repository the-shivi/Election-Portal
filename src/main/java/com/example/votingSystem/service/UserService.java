package com.example.votingSystem.service;

import com.example.votingSystem.entity.Category;
import com.example.votingSystem.entity.ElectionDetails;
import com.example.votingSystem.entity.UserDetails;
import com.example.votingSystem.enums.IdType;
import com.example.votingSystem.exception.AdminElectionException;
import com.example.votingSystem.repository.CategoryRepository;
import com.example.votingSystem.repository.ElectionDetailsRepository;
import com.example.votingSystem.repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserDetailsRepository userDetailsRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    Validator validator;

    @Autowired
    IdGenerator idGenerator;

    public UserDetails addUser(@org.jetbrains.annotations.NotNull UserDetails userDetails)  {

        if(userDetails.getCategoryId() == null){
            throw new AdminElectionException("Category Id is mandatory");
        }
        validateCategoryIdAndAddCategoryName(userDetails);
        validatemobileNumber(userDetails.getMobileNumber());
        userDetails.setCreatedOn(LocalDateTime.now());
        userDetails.setStatus("Y");
        String generatedElectionIdId = idGenerator.generateId(IdType.USERID);
        userDetails.setId(generatedElectionIdId);
        return userDetailsRepository.save(userDetails);
    }

    public void validateCategoryIdAndAddCategoryName(UserDetails userDetails) {
        Optional<Category> category= categoryRepository.findByIdAndStatus(userDetails.getCategoryId(),"Y");
        if (category.isEmpty())
        {
            throw new AdminElectionException("Invalid Category Id");
        }
        if(category.get().getName() != null){
            userDetails.setCategory(category.get().getName());
        }
    }

    public void validatemobileNumber(String msisdn) {
        if(msisdn== null){
            throw new AdminElectionException("Mobile Number is mandatory");
        }
        if(userDetailsRepository.findByMobileNumber(msisdn).isPresent()) {
            throw new AdminElectionException("Mobile Number Already Registered");
        }
    }
}
