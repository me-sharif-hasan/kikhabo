package com.iishanto.kikhabo.web.dto.user;

import com.iishanto.kikhabo.domain.entities.people.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class UserDto {
    private Long id;
    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    @Length(min = 8,max = 128)
    private String password;
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @NotEmpty
    private String country;
    @NotEmpty
    private String gender;
    private String religion;

    @NotEmpty
    private String dateOfBirth;
    private Float weightInKg;
    private Float heightInFt;


    public User toDomain() {
        return User.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password(password)
                .country(country)
                .gender(gender)
                .dateOfBirth(dateOfBirth)
                .weightInKg(weightInKg)
                .heightInFt(heightInFt)
                .religion(religion)
                .build();
    }
}
