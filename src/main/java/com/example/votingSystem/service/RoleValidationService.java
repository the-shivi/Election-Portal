package com.example.votingSystem.service;

import com.example.votingSystem.enums.ErrorCodes;
import com.example.votingSystem.exception.UnAuthorisedException;
import com.example.votingSystem.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RoleValidationService {

    @Autowired
    private RoleRepository roleRepository;

    public void hasAccess(String serviceCode) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // Extract category (stored in details)
        String category = null;
        Object details = authentication.getDetails();
        if (details instanceof Map<?, ?> detailMap) {
            Object catObj = detailMap.get("category");
            if (catObj instanceof String) {
                category = (String) catObj;
            }
        }
        System.out.println("Category: " + category);
        if(!roleRepository.existsByCategoryAndServiceCode(category, serviceCode)) {
            throw new UnAuthorisedException(ErrorCodes.NOT_AUTHORISED,"User has not permitted for this service");
        }
    }
}