package com.example.votingSystem.service;

import com.example.votingSystem.enums.ErrorCodes;
import com.example.votingSystem.exception.ElectionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.regex.Pattern;

@Component
public class CustomValidator {

    @Value("${mobile.number.regex}")
    private String mobileNumberRegex;

    private Pattern MOBILE_NUMBER_PATTERN;

    public boolean isClosingDateValid(LocalDateTime startingDate, LocalDateTime closingDate, int day) {
        if (startingDate == null || closingDate == null) {
            throw new ElectionException(ErrorCodes.INVALID_PAYLOAD, "Dates can not be null");
        }
        System.out.println("days is "+ day + " " +startingDate + " "+ closingDate);
        long duration =Duration.between(startingDate, closingDate).toDays();
        return  duration < day;
    }

    public void checkMobileNumber(String msisdn){
        MOBILE_NUMBER_PATTERN = Pattern.compile(mobileNumberRegex);
        boolean isValid= MOBILE_NUMBER_PATTERN.matcher(msisdn).matches();
        if(!isValid){
            throw new ElectionException(ErrorCodes.INVALID_MOBILE, "Invalid Mobile Number");
        }
    }

}
