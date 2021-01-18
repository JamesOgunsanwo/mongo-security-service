package com.dte.security.securityservice.services.user;

import com.dte.security.securityservice.models.requests.AuthenticateUserRequest;
import com.dte.security.securityservice.models.requests.CreateUserRequest;
import com.dte.security.securityservice.models.responses.TokenResponse;
import com.dte.security.securityservice.repositories.UserRepository;
import com.dte.security.securityservice.services.jwt.JwtService;
import com.dte.security.securityservice.services.jwt.models.UserCredentials;
import com.dte.security.securityservice.services.user.models.UserDao;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserDataService implements UserDetailsService {

    private static Logger logger = Logger.getLogger(UserDataService.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserCredentials loadUserByUsername(String username) {
        UserDao user = userRepository.findByUsername(username).orElse(null);
        if (user != null) {
            UserCredentials userCredentials = new UserCredentials();
            BeanUtils.copyProperties(user, userCredentials);
            return userCredentials;
        }
        return null;
    }

    public UserCredentials loadUserById(String id) {
        UserDao user = userRepository.findById(id).orElse(null);
        if (user != null) {
            UserCredentials userCredentials = new UserCredentials();
            BeanUtils.copyProperties(user, userCredentials);
            return userCredentials;
        }
        return null;
    }

    public UserCredentials loadUserByEmail(String email) {
        UserDao user = userRepository.findByEmail(email).orElse(null);
        if (user != null) {
            UserCredentials userCredentials = new UserCredentials();
            BeanUtils.copyProperties(user, userCredentials);
            return userCredentials;
        }
        return null;
    }

    public TokenResponse authenticateUser(AuthenticateUserRequest request) throws Exception {
        TokenResponse tokenResponse;

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        } catch (BadCredentialsException bce) {
            logger.error("Incorrect username or password.");
            throw new BadCredentialsException("Incorrect username or password.", bce);
        } catch (AccountExpiredException aee) {
            logger.error("Account is expired. Please email support.");
            throw new BadCredentialsException("Account is expired. Please email support.");
        } catch (LockedException ale) {
            logger.error("Account is locked. Please email support.");
            throw new BadCredentialsException("Account is locked. Please email support.");
        } catch (HttpClientErrorException.NotFound e) {
            logger.error("Account not found. Please email support.");
            throw new BadCredentialsException("Account not found. Please email support.");
        } catch (Exception e) {
            logger.error("Exception has been thrown");
            throw new Exception("Unknown exception ");
        }

        final Optional<UserDao> user = userRepository.findByEmail(request.getEmail());

        if (user.isPresent()) {
            // Update Last signed in
            user.get().getLastSignedIn().add(LocalDateTime.now());
            userRepository.save(user.get());

            // Create Token
            String token = jwtService.generateToken(populateUserCredentials(user.get()));

            // Get privilege
            tokenResponse = new TokenResponse(token, user.get().getId(), user.get().getFirstname(), returnPrivilege(user.get().getRole()));
            return tokenResponse;
        } else {
            throw new Exception("unable to find user");
        }
    }

    public TokenResponse createUser(CreateUserRequest createUserRequest) throws Exception {
        TokenResponse tokenResponse;
        UserDao user;

        checkUnique(createUserRequest);

        try {
            user = populateUserDao(createUserRequest);
            user = userRepository.save(user);
        } catch (Exception e) {
            throw new Exception(String.format("Unable to save user with email %s", createUserRequest.getEmail()));
        }

        String token = jwtService.generateToken(populateUserCredentials(user));

        tokenResponse = new TokenResponse(token, user.getId(), user.getFirstname(), returnPrivilege(user.getRole()));
        return tokenResponse;
    }

    public Boolean verifyPrivilege(String token, String privilege) throws JsonProcessingException {
        String userID = jwtService.extractUserID(token);
        UserCredentials userCredentials = loadUserById(userID);
        if(Objects.isNull(userCredentials)){
            return false;
        }
        return returnPrivilege(userCredentials.getRole()).equals(privilege);
    }

    private UserDao populateUserDao(CreateUserRequest createUserRequest) {
        UserDao user = new UserDao();
        BeanUtils.copyProperties(createUserRequest, user);

        // username for security
        user.setUsername(user.getEmail());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Time
        LocalDateTime now = LocalDateTime.now();
        user.setCreatedDate(now);
        user.setLastSignedIn(Collections.singletonList(now));
        user.setLastUpdated(Collections.singletonList(now));

        // Role
        user.setRole("member-standard");

        // Account status
        user.setIsAccountNonExpired(true);
        user.setIsCredentialsNonExpired(true);
        user.setIsEnabled(true);
        user.setIsAccountNonLocked(true);
        return user;
    }

    private UserCredentials populateUserCredentials(UserDao userDao) {
        UserCredentials userCredentials = new UserCredentials();
        BeanUtils.copyProperties(userDao, userCredentials);
        return userCredentials;
    }

    private void checkUnique(CreateUserRequest createUserRequest) throws Exception {
        if (userRepository.findByEmail(createUserRequest.getEmail()).isPresent()) {
            throw new DuplicateKeyException(String.format("User with email %s already exists", createUserRequest.getEmail()));
        }
    }

    private String returnPrivilege(String role){
        switch(role) {
            case "member-standard":
                return "01";
            case "member-plus":
                return "02";
            case "member-premium":
                return "03";
            case "admin":
                return "04";
            default:
                return null;
        }
    }
}
