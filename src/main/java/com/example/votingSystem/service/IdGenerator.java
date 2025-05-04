package com.example.votingSystem.service;

import com.example.votingSystem.enums.IdType;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Component
public class IdGenerator {

    public String generateId(IdType type) {
        String prefix;
        switch(type) {
            case USERID:
                prefix = "UI_";
                break;
            case ELECTIONID:
                prefix = "ES_";
                break;
            default:
                prefix= "XX_";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");
        String timestamp = LocalDateTime.now().format(formatter);
        return prefix + timestamp;
    }
}
