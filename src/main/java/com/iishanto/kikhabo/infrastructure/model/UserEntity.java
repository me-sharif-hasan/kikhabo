package com.iishanto.kikhabo.infrastructure.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iishanto.kikhabo.domain.entities.people.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.boot.SpringApplication;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user",indexes = {
        @Index(name = "unk_email",columnList = "email")
})
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    @Email
    @Column(unique = true)
    private String email;
    @NotEmpty
    @Length(min = 8)
    private String password;
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @NotEmpty
    private String gender;
    @NotEmpty
    private String country;

    private String dateOfBirth;
    private Float weightInKg;
    private Float heightInFt;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "user")
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonManagedReference
    private List<MealHistoryEntity> mealHistories;

    public User toDomain() {
        ObjectMapper objectMapper=new ObjectMapper();
        User user=objectMapper.convertValue(this,User.class);
//        return User.builder()
//                .id(id)
//                .country(country)
//                .email(email)
//                .firstName(firstName)
//                .lastName(lastName)
//                .weightInKg(weightInKg)
//                .gender(gender)
//                .dateOfBirth(dateOfBirth)
//                .heightInFt(heightInFt)
//                .build();
        return user;
    }
}
