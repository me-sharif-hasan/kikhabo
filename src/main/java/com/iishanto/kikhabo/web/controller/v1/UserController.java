package com.iishanto.kikhabo.web.controller.v1;

import com.iishanto.kikhabo.web.dto.UserDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User", description = "Create, Read, Update, Delete user")
@RestController
@RequestMapping("api/v1/user")
public class UserController {
    @PostMapping
    public void createNewUser(@Valid @RequestBody UserDto user){

    }
}
