package com.dte.security.securityservice.models.requests;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Validated
public class CreateUserRequest {


    @NotNull(message = "firstname cannot be null")
    @Length(min = 2, message = "firstname must be at least two characters long")
    private String firstname;

    @NotNull(message = "surname cannot be null")
    @Length(min = 2, message = "surname must be at least two characters long")
    private String surname;

    @NotNull(message = "email cannot be null")
    private String email;

    private String username;

    @NotNull(message = "password cannot be null")
    private String password;

}
