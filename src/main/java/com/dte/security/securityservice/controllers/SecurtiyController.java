package com.dte.security.securityservice.controllers;

import com.dte.security.securityservice.models.GenericResponseModel;
import com.dte.security.securityservice.models.enums.Endpoints;
import com.dte.security.securityservice.models.requests.AuthenticateUserRequest;
import com.dte.security.securityservice.models.requests.CreateUserRequest;
import com.dte.security.securityservice.services.SecurityService;
import com.dte.security.securityservice.utils.DatabaseTestingService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/dte/security")
public class SecurtiyController {

    private static Logger logger = Logger.getLogger(SecurtiyController.class);

    @Autowired
    private SecurityService securityService;

    @Autowired
    private DatabaseTestingService databaseTestingService;

    @GetMapping(Endpoints.HEALTH)
    public ResponseEntity<GenericResponseModel> healthCheck() {
        return ResponseEntity.ok(new GenericResponseModel("OK"));
    }

    @GetMapping(Endpoints.ADVANCED_HEALTH)
    public ResponseEntity<GenericResponseModel> advancedHealthCheck() {
        return ResponseEntity.ok(new GenericResponseModel(databaseTestingService.test()));
    }

    @PostMapping(Endpoints.AUTHENTICATE_USER)
    public ResponseEntity<?> authenticateUser(@Validated @RequestBody AuthenticateUserRequest authenticateUserRequest) throws Exception {
        return securityService.authenticateUser(authenticateUserRequest);
    }

    @PostMapping(Endpoints.CREATE_USER)
    public ResponseEntity<?> createUser(@Validated @RequestBody CreateUserRequest createUserRequest) throws Exception {
        return securityService.createUser(createUserRequest);
    }

    @PostMapping(Endpoints.VERIFY_USER)
    public ResponseEntity<?> verifySession(@RequestHeader("Authorization") String token, @RequestParam String id) throws Exception {
        return securityService.verifySession(token, id);
    }

    @GetMapping(Endpoints.VERIFY_PRIVILEGE)
    public ResponseEntity<?> verifyPrivilege(@RequestHeader("Authorization") String token, @RequestParam String privilege) throws Exception {
        return securityService.verifyPrivilege(token, privilege);
    }
}
