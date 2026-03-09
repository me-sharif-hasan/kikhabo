package com.iishanto.kikhabo.web.controller.v1;

import com.iishanto.kikhabo.common.exception.user.UserRegistrationFailureException;
import com.iishanto.kikhabo.domain.datasource.WeatherDataSource;
import com.iishanto.kikhabo.domain.entities.people.Credentials;
import com.iishanto.kikhabo.domain.entities.people.User;
import com.iishanto.kikhabo.domain.usercase.user.*;
import com.iishanto.kikhabo.domain.usercase.user.command.out.GetUserResponse;
import com.iishanto.kikhabo.infrastructure.services.storage.S3Service;
import com.iishanto.kikhabo.web.dto.user.CredentialsDto;
import com.iishanto.kikhabo.web.dto.user.LoginResponseDto;
import com.iishanto.kikhabo.web.dto.user.SocialAuthDto;
import com.iishanto.kikhabo.web.dto.user.UserDto;
import com.iishanto.kikhabo.web.dto.user.UserUpdateDto;
import com.iishanto.kikhabo.web.response.SuccessResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
@Tag(name = "User", description = "Create, Read, Update, Delete user")
@RestController
@RequestMapping("api/v1/user")
public class UserController {
    UserRegistrationUseCase userRegistrationUseCase;
    UserLoginUseCase userLoginUseCase;
    SocialLoginUseCase socialLoginUseCase;
    UserUpdateUseCase userUpdateUseCase;
    WeatherDataSource weatherDataSource;
    GetUserUseCase getUserUseCase;
    UserSearchUseCase userSearchUseCase;
    S3Service s3Service;
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

    @SecurityRequirements
    @PostMapping("social-login")
    public ResponseEntity<LoginResponseDto> socialLogin(@Valid @RequestBody SocialAuthDto socialAuthDto) throws Exception {
        Credentials response = socialLoginUseCase.execute(socialAuthDto.toDomain());
        return new ResponseEntity<>(LoginResponseDto.fromCredential(response), HttpStatus.OK);
    }

    @PutMapping("update")
    public ResponseEntity<User> update(@Valid @RequestBody UserUpdateDto user) throws Exception {
        User domainUser=user.toDomain();
        return new ResponseEntity<>(userUpdateUseCase.execute(domainUser),HttpStatus.OK);
    }

    @PostMapping(value = "profile-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SuccessResponse<String>> uploadProfileImage(
            @RequestParam("file") MultipartFile file) throws Exception {
        if (file.isEmpty()) throw new IllegalArgumentException("File must not be empty");
        if (file.getSize() > 5 * 1024 * 1024) throw new IllegalArgumentException("File size must not exceed 5MB");
        String imageUrl = s3Service.uploadFile(file, "profile-images");
        userUpdateUseCase.execute(User.builder().profileImageUrl(imageUrl).build());
        return new ResponseEntity<>(new SuccessResponse<>("success", "Profile image uploaded", imageUrl), HttpStatus.OK);
    }

    @GetMapping("current-user")
    public ResponseEntity<SuccessResponse<GetUserResponse>> getUser() throws Exception {
        SuccessResponse<GetUserResponse> successResponse=new SuccessResponse<>();
        successResponse.setData(GetUserResponse.fromDomain(getUserUseCase.execute(null)));
        return new ResponseEntity<>(successResponse,HttpStatus.OK);
    }

}
