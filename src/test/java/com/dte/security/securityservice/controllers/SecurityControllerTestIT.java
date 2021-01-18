package com.dte.security.securityservice.controllers;

import com.dte.security.securityservice.services.SecurityService;
import com.dte.security.securityservice.utils.TestUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

// TODO
@DisplayName("Security Service integration test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = SecurityService.class)
@AutoConfigureMockMvc
class SecurityControllerTestIT {

    @Autowired
    private TestUtils testUtils;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Value("${server.port}")
    private int port;
    private MockMvc mockMvc;
    private HttpHeaders headers;
    private HttpHeaders authenticatedHeaders;

    @BeforeEach
    void setUp() {
        headers = testUtils.createHeaders();
        authenticatedHeaders = testUtils.createHeadersWithAuthorization();
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(SecurityMockMvcConfigurers.springSecurity()).build();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void healthCheck() {
    }

    @Test
    void advancedHealthCheck() {
    }

    @Test
    void authenticateUser() {
    }

    @Test
    void createUser() {
    }

    @Test
    void verifySession() {
    }
}