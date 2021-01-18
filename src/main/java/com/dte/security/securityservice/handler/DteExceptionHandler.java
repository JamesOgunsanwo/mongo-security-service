package com.dte.security.securityservice.handler;

import com.dte.security.securityservice.models.GenericResponseModel;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.apache.log4j.Logger;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

@RestControllerAdvice
public class DteExceptionHandler {

    private static Logger logger = Logger.getLogger(DteExceptionHandler.class);

    /* Http Client ErrorExceptions */
    @ExceptionHandler({HttpClientErrorException.BadRequest.class})
    public final ResponseEntity<?> handleBadRequestExceptionError(Exception e) {
        logger.error("Bad Request [Exception]: " + e.getCause());
        return ResponseEntity.status(400).body(new GenericResponseModel("Missing request body or request parameter"));
    }

    @ExceptionHandler({HttpClientErrorException.Forbidden.class})
    public final ResponseEntity<?> handleForbiddenError(Exception e) {
        logger.error("Forbidden error [HttpClientErrorException]:" + e.getCause());
        return ResponseEntity.status(401).body(new GenericResponseModel("The path you have searched is forbidden. Please authenticate"));
    }

    @ExceptionHandler({HttpClientErrorException.NotFound.class})
    public final ResponseEntity<?> NotFoundException(Exception e) {
        logger.error("Not found error [Exception]: " + e.getCause());
        return ResponseEntity.status(404).body(new GenericResponseModel("Internal server error. Please try request again"));
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public final ResponseEntity<?> handleHttpMethodd(Exception e) {
        logger.error("Internal server error. Incorrect HTTP Method [HttpRequestMethodNotSupportedException]: " + e.getCause());
        return ResponseEntity.status(415).body(new GenericResponseModel("Internal server error. Incorrect HTTP Method"));
    }

    @ExceptionHandler({HttpServerErrorException.InternalServerError.class})
    public final ResponseEntity<?> handleInternalServerError(Exception e) {
        logger.error("Internal server error [HttpServerErrorException]:" + e.getCause());
        return ResponseEntity.status(500).body(new GenericResponseModel("Internal server error. Please try request again"));
    }

    /* Data Exceptions */
    @ExceptionHandler(BadCredentialsException.class)
    public final ResponseEntity<?> handleBadCredentialsException(Exception e) {
        logger.error("Bad Credentials Exception [BadCredentialsException]:" + e.getCause());
        return ResponseEntity.status(401).body(new GenericResponseModel("Unauthorised"));
    }

    @ExceptionHandler({MalformedJwtException.class, SignatureException.class, JwtException.class})
    public final ResponseEntity<?> handleMalformedJwtException(Exception e) {
        logger.error("Malformed Jwt Exception [MalformedJwtException]:" + e.getCause());
        return ResponseEntity.status(401).body(new GenericResponseModel("Malformed Jwt"));
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public final ResponseEntity<?> handleExpiredJwtException(Exception e) {
        logger.error("Expired Jwt Exception [ExpiredJwtException]:" + e.getCause());
        return ResponseEntity.status(401).body(new GenericResponseModel("Token expired"));
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public final ResponseEntity<?> handleDuplicateKeyException(Exception e) {
        logger.error("Duplicate Key Exception [DuplicateKeyException]:" + e.getCause());
        return ResponseEntity.status(401).body(new GenericResponseModel(e.getMessage()));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public final ResponseEntity<?> handleUsernameNotFoundException(Exception e) {
        logger.error("User not Found [UsernameNotFoundException]:" + e.getCause());
        return ResponseEntity.status(401).body(new GenericResponseModel(e.getMessage()));
    }

    @ExceptionHandler({MissingServletRequestParameterException.class, MissingRequestHeaderException.class})
    public final ResponseEntity<?> handleMissingRequestParamHeaderException(Exception e) {
        logger.error("Missing Param or Header:" + e.getCause());
        return ResponseEntity.status(400).body(new GenericResponseModel(e.getMessage()));
    }

    /* Generic Exceptions */
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<?> handleGenericError(Exception e) {
        logger.error("Internal server error [GenericError]:" + e.getCause());
        return ResponseEntity.status(500).body(new GenericResponseModel("Internal server error. Please try request again"));
    }

}
