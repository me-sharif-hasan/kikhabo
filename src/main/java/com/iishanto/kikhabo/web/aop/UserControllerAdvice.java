package com.iishanto.kikhabo.web.aop;

import com.iishanto.kikhabo.common.exception.global.GlobalServerException;
import com.iishanto.kikhabo.common.exception.user.UserLoginFailureException;
import com.iishanto.kikhabo.common.exception.user.UserRegistrationFailureException;
import com.iishanto.kikhabo.web.response.ErrorCodes;
import com.iishanto.kikhabo.web.response.ErrorResponse;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;


@RestControllerAdvice
@AllArgsConstructor
public class UserControllerAdvice {
    Logger logger;
    @ExceptionHandler(UserRegistrationFailureException.class)
    public ResponseEntity<ErrorResponse> handle(UserRegistrationFailureException e){
        ErrorResponse err=ErrorResponse.of(List.of(e.getLocalizedMessage()),ErrorCodes.USER_ALREADY_EXISTS);
        logger.debug("USER REGISTRATION EXCEPTION {}",e.getLocalizedMessage());
        return new ResponseEntity<>(err, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException e){
        List <String> errors=e.getFieldErrors().stream().map(fieldError -> "input %s %s".formatted(fieldError.getField(),fieldError.getDefaultMessage())).toList();
        ErrorResponse err=ErrorResponse.of(errors,ErrorCodes.INVALID_ARGUMENTS);
        logger.debug("INVALID DATA EXCEPTION {}",e.getLocalizedMessage());
        return new ResponseEntity<>(err,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserLoginFailureException.class)
    public ResponseEntity<ErrorResponse> handleUserLoginFailureException(UserLoginFailureException e){
        ErrorResponse err=ErrorResponse.of(List.of(e.getLocalizedMessage()),ErrorCodes.INVALID_CREDENTIALS);
        logger.debug("USER LOGIN EXCEPTION {}",e.getLocalizedMessage());
        return new ResponseEntity<>(err,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> httpMessageNotReadable(HttpMessageNotReadableException e){
        ErrorResponse err=ErrorResponse.of(List.of(e.getLocalizedMessage()),ErrorCodes.BAD_JSON);
        logger.debug("HTTP MESSAGE EXCEPTION {}",e.getLocalizedMessage());
        return new ResponseEntity<>(err,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(GlobalServerException.class)
    public ResponseEntity<ErrorResponse> handleUndeterminedException(GlobalServerException e){
        ErrorResponse err=ErrorResponse.of(List.of(e.getLocalizedMessage()),ErrorCodes.SERVER_ERROR);
        logger.debug("UNRECOGNIZED SERVER EXCEPTION {}",e.getLocalizedMessage());
        return new ResponseEntity<>(err,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedAccessException(AuthenticationException e){
        logger.debug("UNAUTHORIZED ACCESS EXCEPTION {}",e.getLocalizedMessage());
        ErrorResponse err=ErrorResponse.of(List.of(e.getLocalizedMessage()),ErrorCodes.UNAUTHORIZED_ACCESS);
        return new ResponseEntity<>(err,HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllException(Exception e){
        logger.debug("UNKNOWN EXCEPTION {}",e.getLocalizedMessage());
        ErrorResponse err=ErrorResponse.of(List.of(e.getLocalizedMessage()),ErrorCodes.UNKNOWN_ERROR);
        return new ResponseEntity<>(err,HttpStatus.SERVICE_UNAVAILABLE);
    }

}
