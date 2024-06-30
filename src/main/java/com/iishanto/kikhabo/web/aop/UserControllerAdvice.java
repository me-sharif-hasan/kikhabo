package com.iishanto.kikhabo.web.aop;

import com.iishanto.kikhabo.common.exception.user.UserRegistrationFailureException;
import com.iishanto.kikhabo.web.response.ErrorCodes;
import com.iishanto.kikhabo.web.response.ErrorResponse;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
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
        logger.debug("USER REGISTRATION EXCEPTION {}",e.getLocalizedMessage());
        return new ResponseEntity<>(err,HttpStatus.BAD_REQUEST);
    }
}
