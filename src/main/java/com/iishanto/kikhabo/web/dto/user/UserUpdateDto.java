package com.iishanto.kikhabo.web.dto.user;

import com.iishanto.kikhabo.domain.entities.people.User;
import jakarta.validation.constraints.Email;

public class UserUpdateDto{
    @Email
    public String email;
    public String firstName;
    public String lastName;
    public String country;
    public String gender;
    public String dateOfBirth;
    public User toDomain(){
        return User.builder()
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .country(country)
                .gender(gender)
                .dateOfBirth(dateOfBirth).build();
    }
}
