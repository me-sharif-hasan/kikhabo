package com.iishanto.kikhabo.web.controller.v1;

import com.iishanto.kikhabo.common.exception.user.UserRegistrationFailureException;
import com.iishanto.kikhabo.domain.entities.people.User;
import com.iishanto.kikhabo.domain.usercase.user.UserRegistrationUseCase;
import com.iishanto.kikhabo.web.dto.UserDto;
import com.iishanto.kikhabo.web.response.ErrorResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@AllArgsConstructor
@Tag(name = "User", description = "Create, Read, Update, Delete user")
@RestController
@RequestMapping("api/v1/user")
public class UserController {
    UserRegistrationUseCase userRegistrationUseCase;
    Logger logger;

    @PostMapping("register")
    public ResponseEntity<User> registration(@Valid @RequestBody UserDto user) throws UserRegistrationFailureException {
        logger.info(user.toString());
        return new ResponseEntity<>(userRegistrationUseCase.execute(user.toDomain()), HttpStatus.CREATED);
    }
}
