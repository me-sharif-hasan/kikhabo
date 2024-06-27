package com.iishanto.kikhabo.web.dto;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {
    private String uuid;
    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    @Length(min = 8)
    private String password;
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @NotEmpty
    private String country;

    private Float dateOfBirth;
    private Float weightInKg;
    private Float heightInFt;


}
