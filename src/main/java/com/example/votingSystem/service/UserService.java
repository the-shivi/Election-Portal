package com.example.votingSystem.service;

import com.example.votingSystem.config.JwtUtil;
import com.example.votingSystem.entity.Category;
import com.example.votingSystem.entity.UserAuthentication;
import com.example.votingSystem.entity.UserDetails;
import com.example.votingSystem.enums.ErrorCodes;
import com.example.votingSystem.enums.IdType;
import com.example.votingSystem.enums.ServiceCodes;
import com.example.votingSystem.enums.Status;
import com.example.votingSystem.exception.ElectionException;
import com.example.votingSystem.model.ChangePasswordRequest;
import com.example.votingSystem.model.CreationSuccessResponse;
import com.example.votingSystem.model.LoginRequest;
import com.example.votingSystem.model.LoginResponse;
import com.example.votingSystem.repository.CategoryRepository;
import com.example.votingSystem.repository.UserAuthenticationRepository;
import com.example.votingSystem.repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    CustomValidator validator;

    @Autowired
    IdGenerator idGenerator;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    UserAuthenticationRepository userAuthenticationRepository;
    @Autowired
    UserAuthenticationRepository authRepository;

    @Autowired
    JwtUtil jwtUtil;

    @Value("${default.password}")
    String  defaultPassword;

    public UserDetails addUser(UserDetails userDetails)  {

        validateCategoryIdAndAddCategoryName(userDetails);
        validatemobileNumber(userDetails.getMobileNumber());
        userDetails.setCreatedOn(LocalDateTime.now());
        userDetails.setStatus("Y");
        String generatedUserId = idGenerator.generateId(IdType.USERID);
        userDetails.setId(generatedUserId);
        saveUserAuth(userDetails);
        return userDetailsRepository.save(userDetails);
    }

    private void validateCategoryIdAndAddCategoryName(UserDetails userDetails) {
        Optional<Category> category= categoryRepository.findByIdAndStatus(userDetails.getCategoryId(),"Y");
        if (category.isEmpty())
        {
            throw new ElectionException(ErrorCodes.INVALID_CATEGORY, "Invalid Category Id");
        }
        if(category.get().getName() != null){
            userDetails.setCategory(category.get().getName());
        }
    }

    private void validatemobileNumber(String msisdn) {
        validator.checkMobileNumber(msisdn);
        if(userDetailsRepository.findByMobileNumber(msisdn).isPresent()) {
            throw new ElectionException(ErrorCodes.ALREADY_ENROLLED, "Mobile Number Already Registered");
        }
    }

    private void saveUserAuth(UserDetails userDetails) {
        UserAuthentication userAuthentication = new UserAuthentication();
        String generatedAuthenticationId=idGenerator.generateId(IdType.AUTHENTICATIONID);
        userAuthentication.setAuthenticationId(generatedAuthenticationId);
        userAuthentication.setMobileNumber(userDetails.getMobileNumber());
        userAuthentication.setUserId(userDetails.getId());
        userAuthentication.setCreatedOn(userDetails.getCreatedOn());
        userAuthentication.setModifiedOn(userDetails.getCreatedOn());
        String encryptedPassword = passwordEncoder.encode(defaultPassword);
        userAuthentication.setPassword(encryptedPassword);
        userAuthentication.setIsdefaultPassword("Y");
        userAuthenticationRepository.save(userAuthentication);
    }

    public LoginResponse loginService(LoginRequest request){
        String identifierType = request.getIdentifierType() ;
        String idetifierValue = request.getIdentifierValue();
        if(identifierType == null || idetifierValue == null){
            throw new ElectionException(ErrorCodes.INVALID_PAYLOAD,"Identifier type and value required");
        }
        Optional<UserAuthentication> optionalUser = getUserAuth(identifierType,idetifierValue);
        if (optionalUser.isEmpty()) {
            throw new ElectionException(ErrorCodes.USER_NOT_FOUND,"Active user not found");
        }

        UserAuthentication user = optionalUser.get();

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ElectionException(ErrorCodes.INVALID_CREDENTIAL, "Invalid Credential");
        }
        Optional <UserDetails> userDetails= userDetailsRepository.findById(user.getUserId());
        String categoryId = userDetails.isPresent() ? userDetails.get().getCategoryId() : "CAT02";
        String token = jwtUtil.generateToken(user.getMobileNumber(),categoryId);
        return new LoginResponse(token,user.getUserId(), Status.SUCCESS, ServiceCodes.LOGIN_USER.toString());

    }


    public CreationSuccessResponse changePassword(ChangePasswordRequest request){
        String identifierType = request.getIdentifierType() ;
        String idetifierValue = request.getIdentifierValue();
        if(identifierType == null || idetifierValue == null){
            throw new ElectionException(ErrorCodes.INVALID_PAYLOAD,"Identifier type and value required");
        }
        Optional<UserAuthentication> optionalUser = getUserAuth(identifierType,idetifierValue);
        if (optionalUser.isEmpty()) {
            throw new ElectionException(ErrorCodes.USER_NOT_FOUND,"Active user not found");
        }

        UserAuthentication user = optionalUser.get();

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new ElectionException(ErrorCodes.INVALID_CREDENTIAL, "Invalid Credential");
        }
        String encryptedPassword = passwordEncoder.encode(request.getNewPassword());
        user.setPassword(encryptedPassword);
        user.setIsdefaultPassword("N");
        user.setModifiedOn(LocalDateTime.now());
        userAuthenticationRepository.save(user);
        return new CreationSuccessResponse(Status.SUCCESS,user.getUserId(),LocalDateTime.now(),ServiceCodes.RESET_PIN.name());
    }

    private Optional<UserAuthentication> getUserAuth(String identifierType,String idetifierValue){
        Optional<UserAuthentication> optionalUser;
        if(identifierType.equals("msisdn") || identifierType.equals("mobileNumber") ) {
            optionalUser = authRepository.findByMobileNumber(idetifierValue);
        }
        else if(identifierType.equals("userId")) {
            optionalUser = authRepository.findByUserId(idetifierValue);
        }
        else {
            throw new ElectionException(ErrorCodes.INVALID_AUTHENTICATION_TYPE,"Invalid identifier type");
        }
        return optionalUser;
    }
}
