package com.dte.security.securityservice.controllers;

import com.dte.security.securityservice.models.enums.Endpoints;
import com.dte.security.securityservice.services.SecurityService;
import com.dte.security.securityservice.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertEquals;
// TODO
@DisplayName("Generic controller integration test")
@SpringBootTest
@AutoConfigureMockMvc
class GenericControllerTest {

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

    @Test
    void healthCheck_success_validHeadersAndParameters() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get(Endpoints.HEALTH_PATH)
                .headers(headers)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED));
        MvcResult result = resultActions.andReturn();
        assertEquals(200, result.getResponse().getStatus());
        assertEquals("{\"message\":\"Authentication has past, this is example end point.\"}", result.getResponse().getContentAsString());
    }

    @Test
    void advancedHealth_success_validHeadersAndParameters() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get(Endpoints.ADVANCED_HEALTH_PATH)
                .headers(headers)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED));
        MvcResult result = resultActions.andReturn();
        assertEquals(200, result.getResponse().getStatus());
        assertEquals("{\"message\":\"Authentication has past, this is example end point.\"}", result.getResponse().getContentAsString());
    }

    @Test
    void notFound_invalidPath() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get(Endpoints.BASE_PATH + "/incorrect_path")
                .headers(headers)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED));
        MvcResult result = resultActions.andReturn();
        assertEquals(404, result.getResponse().getStatus());
    }

    @Test
    void unauthorized_invalidJWT() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get(Endpoints.VERIFY_USER_PATH)
                .headers(headers)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED));
        MvcResult result = resultActions.andReturn();
        assertEquals(403, result.getResponse().getStatus());
    }
}
