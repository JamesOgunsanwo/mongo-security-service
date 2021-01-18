package com.dte.security.securityservice.services;

import com.dte.security.securityservice.models.requests.AuthenticateUserRequest;
import com.dte.security.securityservice.models.requests.CreateUserRequest;
import com.dte.security.securityservice.models.responses.TokenResponse;
import com.dte.security.securityservice.services.jwt.JwtService;
import com.dte.security.securityservice.services.user.UserDataService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import static com.dte.security.securityservice.services.jwt.JwtService.stripBearerTag;

@Service
public class SecurityService {

    private static Logger logger = Logger.getLogger(SecurityService.class);

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDataService userDataService;

    public ResponseEntity<?> createUser(CreateUserRequest createUserRequest) throws Exception {
        TokenResponse tokenResponse;
        logger.info("Start of SecurityService.createUser()");
        tokenResponse = userDataService.createUser(createUserRequest);
        logger.info("End of SecurityService.createUser()");
        return ResponseEntity.ok(tokenResponse);
    }

    public ResponseEntity<?> authenticateUser(AuthenticateUserRequest authenticateUserRequest) throws Exception {
        TokenResponse tokenResponse;
        logger.info("Start of SecurityService.authenticateUser()");
        tokenResponse = userDataService.authenticateUser(authenticateUserRequest);
        logger.info("End of SecurityService.authenticateUser()");
        return ResponseEntity.ok(tokenResponse);
    }

    public ResponseEntity<?> verifySession(String jwt, String id) throws Exception {
        logger.info("Start of SecurityService.verifySession()");
        String userId = jwtService.extractUserID(jwt.contains(JwtService.REMOVE_BEARER) ? stripBearerTag(jwt) : jwt);

        if (!userId.equals(id)) {
            throw new BadCredentialsException(String.format("Id mismatch %s", id));
        }

        logger.info("End of SecurityService.verifySession()");
        return ResponseEntity.ok(true);
    }

    public ResponseEntity<?> verifyPrivilege(String token, String privilege) throws JsonProcessingException {
        logger.info("Start of SecurityService.verifyPrivilege()");

        Boolean verifyPrivilege = userDataService.verifyPrivilege(token, privilege);

        logger.info("End of SecurityService.verifyPrivilege()");
        return ResponseEntity.ok(verifyPrivilege);
    }
}
