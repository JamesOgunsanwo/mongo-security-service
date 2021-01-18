package com.dte.security.securityservice.services.user.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Document(collection = "User")
public class UserDao {

    @Id
    private String id;

    private String firstname;

    private String surname;

    private String email;

    private String username;

    private String password;

    private LocalDateTime createdDate;

    private List<LocalDateTime> lastSignedIn;

    private List<LocalDateTime> lastUpdated;

    private String role;

    private AddressDao address;

    private Boolean isAccountNonExpired;

    private Boolean isAccountNonLocked;

    private Boolean isCredentialsNonExpired;

    private Boolean isEnabled;

}
