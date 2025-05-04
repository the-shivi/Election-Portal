package com.example.votingSystem.service;

import com.example.votingSystem.exception.AdminElectionException;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
@Component
public class Validator {

    public boolean isClosingDateValid(LocalDateTime startingDate, LocalDateTime closingDate, int day) {
        if (startingDate == null || closingDate == null) {
            throw new AdminElectionException("Dates can not be null");
        }
        System.out.println("days is "+ day + " " +startingDate + " "+ closingDate);
        long duration =Duration.between(startingDate, closingDate).toDays();
        return  duration < day;
    }

}
