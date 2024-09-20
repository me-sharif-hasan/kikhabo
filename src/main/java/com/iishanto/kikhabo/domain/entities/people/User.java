package com.iishanto.kikhabo.domain.entities.people;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iishanto.kikhabo.domain.entities.meal.MealHistory;
import com.iishanto.kikhabo.infrastructure.services.health.HealthServices;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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

    public String getAge() throws Exception {
        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM, yyyy");
            LocalDate birthDate = LocalDate.parse(dateOfBirth, formatter);
            LocalDate currentDate = LocalDate.now();
            if (birthDate.isAfter(currentDate)) {
                throw new Exception("Date of birth is after current date");
            }
            Period age = Period.between(birthDate, currentDate);
            return String.valueOf(age.getYears());
        }catch (Exception e){
            return "20";
        }
    }


    public double getBmi(HealthServices healthServices) {
        return healthServices.getBMI(this);
    }
}
