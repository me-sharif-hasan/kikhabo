package com.iishanto.kikhabo.domain.usercase.user.command.out;

import com.iishanto.kikhabo.domain.entities.meal.MealHistory;
import com.iishanto.kikhabo.domain.entities.people.Preference;
import com.iishanto.kikhabo.domain.entities.people.User;
import lombok.Builder;
import lombok.Data;

import java.util.List;
@Builder
@Data
public class GetUserResponse {
    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String gender;
    private String country;
    private String dateOfBirth;
    private Float weightInKg;
    private Float heightInFt;
    private String religion;
    private Preference preference;

    public static GetUserResponse fromDomain(User user){
        return GetUserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .gender(user.getGender())
                .country(user.getCountry())
                .dateOfBirth(user.getDateOfBirth())
                .weightInKg(user.getWeightInKg())
                .heightInFt(user.getHeightInFt())
                .religion(user.getReligion())
                .preference(user.getPreference())
                .build();
    }
}
