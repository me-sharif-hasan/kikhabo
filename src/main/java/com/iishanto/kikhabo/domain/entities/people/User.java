package com.iishanto.kikhabo.domain.entities.people;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iishanto.kikhabo.domain.entities.meal.MealHistory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
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
    private List<MealHistory> mealHistories;
    private Preference preference;
    @JsonIgnore
    public String getPassword() {
        return password;
    }
}
