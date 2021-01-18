package com.dte.security.securityservice.models.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TokenResponse {

    private String token;
    private String id;
    private String name;
    private String privilege;

}
