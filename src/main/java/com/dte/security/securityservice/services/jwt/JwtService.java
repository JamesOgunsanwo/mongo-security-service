package com.dte.security.securityservice.services.jwt;

import com.dte.security.securityservice.services.jwt.models.TokenSubjectRequest;
import com.dte.security.securityservice.services.jwt.models.TokenSubjectResponse;
import com.dte.security.securityservice.services.jwt.models.UserCredentials;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    public static final String REMOVE_BEARER = "Bearer ";
    private static Logger logger = Logger.getLogger(JwtService.class);
    @Value("${jwt.secret.key}")
    private String SECRET_KEY;
    @Value("${jwt.time.duration}")
    private int DURATION;

    public static String stripBearerTag(String token) {
        return token.substring(7);
    }

    private TokenSubjectResponse exactSubject(String token) throws JsonProcessingException {
        String subject = extractClaim(token, Claims::getSubject);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(subject, TokenSubjectResponse.class);
    }

    public String extractUsername(String token) throws JsonProcessingException {
        token = token.contains(REMOVE_BEARER) ? stripBearerTag(token) : token;
        return exactSubject(token).getUsername();
    }

    public String extractUserID(String token) throws JsonProcessingException {
        token = token.contains(REMOVE_BEARER) ? stripBearerTag(token) : token;
        return exactSubject(token).getId();
    }

    public String extractRole(String token) throws JsonProcessingException {
        token = token.contains(REMOVE_BEARER) ? stripBearerTag(token) : token;
        return exactSubject(token).getRole();
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(UserCredentials userCredentials) throws JsonProcessingException {
        Map<String, Object> claims = new HashMap<>();
        TokenSubjectRequest tokenSubjectRequest = new TokenSubjectRequest(userCredentials.getId(), userCredentials.getUsername(), userCredentials.getRole());
        ObjectMapper Obj = new ObjectMapper();
        String jsonStr = Obj.writeValueAsString(tokenSubjectRequest);
        return createToken(claims, jsonStr);
    }

    //change subject in the future
    private String createToken(Map<String, Object> claims, String subject) {
        Date currentDate = new Date();
        Date newDate = new Date(currentDate.getTime() + 3600 * DURATION);

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(currentDate)
                .setExpiration(newDate)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    /* Not in use */
    private String createLifeTimeToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date("July 1, 2099")) // valid until 2099
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) throws JsonProcessingException {
        token = token.contains(JwtService.REMOVE_BEARER) ? JwtService.stripBearerTag(token) : token;
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}
