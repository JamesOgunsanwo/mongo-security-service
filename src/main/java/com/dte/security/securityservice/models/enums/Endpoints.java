package com.dte.security.securityservice.models.enums;

public interface Endpoints {

    /* Path variables */
    String ALL = "/**";
    String DEFAULT = "/"; //default path
    String API = "/api"; // API path
    String DTE = "/dte"; // DTE path
    String HEALTH = "/health"; // Health path
    String ADVANCED_HEALTH = "/advanced/health"; // Advanced health path
    String CREATE_USER = "/create-user"; // Creates user
    String VERIFY_USER = "/verify-user"; // Verify user
    String VERIFY_PRIVILEGE = "/verify-privilege"; // Verify privilege
    String AUTHENTICATE_USER = "/authenticate_user"; // Authenticate User

    /* PATHS */
    String BASE_PATH = API + DTE + "/security";
    String HEALTH_PATH = BASE_PATH + HEALTH;
    String ADVANCED_HEALTH_PATH = BASE_PATH + ADVANCED_HEALTH;
    String CREATE_USER_PATH = BASE_PATH + CREATE_USER;
    String VERIFY_USER_PATH = BASE_PATH + VERIFY_USER;
    String AUTHENTICATE_USER_PATH = BASE_PATH + AUTHENTICATE_USER;
    String VERIFY_PRIVILEGE_PATH = BASE_PATH + VERIFY_PRIVILEGE;

}
