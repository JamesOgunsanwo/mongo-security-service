package com.dte.security.securityservice.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class TestUtils {

    public HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    public HttpHeaders createHeadersWithAuthorization() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String JWT = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ7XCJpZFwiOlwiMTIzNDVcIixcInVzZXJuYW1lXCI6XCJvZ3Vuc2Fud28wOEBnbWFpbC5jb21cIixcInJvbGVcIjpcIkFETUlOXCJ9IiwiZXhwIjo0MDg2NTQzNjAwLCJpYXQiOjE1Nzk5ODcyMjl9.sP2OWSSElHdOv-ZDWsqWE0HoMW6BoJUf5bb4TQDU8sE";
        headers.add("Authorization", JWT);
        return headers;
    }
}
