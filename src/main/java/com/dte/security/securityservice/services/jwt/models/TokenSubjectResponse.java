package com.dte.security.securityservice.services.jwt.models;

import lombok.Data;

@Data
public class TokenSubjectResponse {

    private String id;

    private String username;

    private String Role;

}
