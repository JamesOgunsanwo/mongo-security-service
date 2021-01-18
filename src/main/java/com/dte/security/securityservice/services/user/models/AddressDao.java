package com.dte.security.securityservice.services.user.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AddressDao {

    private String houseNumber;

    private String street;

    private String city;

    private String county;

    private String country;

    private String postCode;

}
