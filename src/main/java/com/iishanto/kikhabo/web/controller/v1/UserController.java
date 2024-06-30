package com.iishanto.kikhabo.web.controller.v1;

import com.iishanto.kikhabo.domain.entities.people.User;
import com.iishanto.kikhabo.domain.usercase.user.UserRegistrationUseCase;
import com.iishanto.kikhabo.web.dto.UserDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.*;
@AllArgsConstructor
@Tag(name = "User", description = "Create, Read, Update, Delete user")
@RestController
@RequestMapping("api/v1/user")
public class UserController {
    UserRegistrationUseCase userRegistrationUseCase;
    Logger logger;
    @PostMapping("register")
    public User registration(@Valid @RequestBody UserDto user){
        logger.info(user.toString());
        return userRegistrationUseCase.execute(user.toDomain());
    }
}
