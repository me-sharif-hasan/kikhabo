package com.iishanto.kikhabo.web.controller.v1;

import com.iishanto.kikhabo.common.exception.user.UserRegistrationFailureException;
import com.iishanto.kikhabo.domain.datasource.WeatherDataSource;
import com.iishanto.kikhabo.domain.entities.people.Credentials;
import com.iishanto.kikhabo.domain.entities.people.User;
import com.iishanto.kikhabo.domain.usercase.user.*;
import com.iishanto.kikhabo.domain.usercase.user.command.out.GetUserResponse;
import com.iishanto.kikhabo.web.dto.user.CredentialsDto;
import com.iishanto.kikhabo.web.dto.user.LoginResponseDto;
import com.iishanto.kikhabo.web.dto.user.UserDto;
import com.iishanto.kikhabo.web.dto.user.UserUpdateDto;
import com.iishanto.kikhabo.web.response.SuccessResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@Tag(name = "User", description = "Create, Read, Update, Delete user")
@RestController
@RequestMapping("api/v1/user")
public class UserController {
    UserRegistrationUseCase userRegistrationUseCase;
    UserLoginUseCase userLoginUseCase;
    UserUpdateUseCase userUpdateUseCase;
    WeatherDataSource weatherDataSource;
    GetUserUseCase getUserUseCase;
    UserSearchUseCase userSearchUseCase;
    Logger logger;

    @SecurityRequirements
    @PostMapping("register")
    public ResponseEntity<User> registration(@Valid @RequestBody UserDto user) throws UserRegistrationFailureException {
        logger.info(user.toString());
        return new ResponseEntity<>(userRegistrationUseCase.execute(user.toDomain()), HttpStatus.CREATED);
    }

    @GetMapping("search")
    public ResponseEntity<SuccessResponse<List<User>>> search(@RequestParam String query) throws Exception {
        List<User> users=userSearchUseCase.execute(query);
        SuccessResponse <List<User>> successResponse=new SuccessResponse<>();
        successResponse.setData(users);
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @SecurityRequirements
    @PostMapping("login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody CredentialsDto credentialsDto) throws Exception {
        Credentials response=userLoginUseCase.execute(credentialsDto.toDomain());
        return new ResponseEntity<>(LoginResponseDto.fromCredential(response),HttpStatus.OK);
    }

    @PutMapping("update")
    public ResponseEntity<User> update(@Valid @RequestBody UserUpdateDto user) throws Exception {
        User domainUser=user.toDomain();
        return new ResponseEntity<>(userUpdateUseCase.execute(domainUser),HttpStatus.OK);
    }

    @GetMapping("current-user")
    public ResponseEntity<SuccessResponse<GetUserResponse>> getUser() throws Exception {
        SuccessResponse<GetUserResponse> successResponse=new SuccessResponse<>();
        successResponse.setData(GetUserResponse.fromDomain(getUserUseCase.execute(null)));
        return new ResponseEntity<>(successResponse,HttpStatus.OK);
    }

}
