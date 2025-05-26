package com.example.votingSystem.controller;

import com.example.votingSystem.config.JwtUtil;
import com.example.votingSystem.entity.UserAuthentication;
import com.example.votingSystem.entity.UserDetails;
import com.example.votingSystem.enums.ServiceCodes;
import com.example.votingSystem.enums.Status;
import com.example.votingSystem.model.*;
import com.example.votingSystem.repository.UserAuthenticationRepository;
import com.example.votingSystem.service.RoleValidationService;
import com.example.votingSystem.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
public class AuthController {

    private final UserAuthenticationRepository authRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(UserAuthenticationRepository authRepository,
                          BCryptPasswordEncoder passwordEncoder,
                          JwtUtil jwtUtil) {
        this.authRepository = authRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Autowired
    UserService userService;

    @Autowired
    RoleValidationService roleValidationService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
            LoginResponse loginResponse = userService.loginService(request);
            return ResponseEntity.status(HttpStatus.OK).body(loginResponse);
    }

    @PostMapping("/change-credential")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordRequest request){
        roleValidationService.hasAccess(ServiceCodes.RESET_PIN.toString());
        CreationSuccessResponse creationSuccessResponse = userService.changePassword(request);
        return ResponseEntity.ok(creationSuccessResponse);
    }
}

