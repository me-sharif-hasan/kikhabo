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
    public Float weightInKg;
    public Float heightInFt;
    public String religion;
    public User toDomain(){
        return User.builder()
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .country(country)
                .gender(gender)
                .dateOfBirth(dateOfBirth)
                .weightInKg(weightInKg)
                .heightInFt(heightInFt)
                .religion(religion)
                .build();
    }
}
