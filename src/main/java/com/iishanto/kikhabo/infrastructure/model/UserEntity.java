package com.iishanto.kikhabo.infrastructure.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
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
import org.springframework.context.annotation.Lazy;

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

    private String religion;

    private String dateOfBirth;
    private Float weightInKg;
    private Float heightInFt;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "user",fetch = FetchType.LAZY)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonManagedReference
    @JsonIgnore
    private List<MealHistoryEntity> mealHistories;

    @JoinTable(
            name = "user_family_members",
            joinColumns = @JoinColumn(name = "user_entity_id"),
            inverseJoinColumns = @JoinColumn(name = "family_members_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"user_entity_id", "family_members_id"})}
    )
    @ManyToMany(cascade = CascadeType.DETACH,fetch = FetchType.LAZY)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonIgnore
    private List<UserEntity> familyMembers;

    @OneToOne
    private PreferenceEntity preference;

    public User toDomain() {
        User userDomain=new User();
        userDomain.setId(id);
        userDomain.setEmail(email);
        userDomain.setPassword(password);
        userDomain.setFirstName(firstName);
        userDomain.setLastName(lastName);
        userDomain.setGender(gender);
        userDomain.setCountry(country);
        userDomain.setDateOfBirth(dateOfBirth);
        userDomain.setWeightInKg(weightInKg);
        userDomain.setHeightInFt(heightInFt);
        userDomain.setReligion(religion);
        if(preference!=null) userDomain.setPreference(preference.toDomain());
        return userDomain;
    }

    public void fill(User user) {
        if(user.getEmail()!=null) email=user.getEmail();
        if(user.getPassword()!=null) password=user.getPassword();
        if(user.getFirstName()!=null) firstName=user.getFirstName();
        if(user.getLastName()!=null) lastName=user.getLastName();
        if(user.getGender()!=null) gender=user.getGender();
        if(user.getCountry()!=null) country=user.getCountry();
        if(user.getDateOfBirth()!=null) dateOfBirth=user.getDateOfBirth();
        if(user.getWeightInKg()!=null) weightInKg=user.getWeightInKg();
        if(user.getHeightInFt()!=null) heightInFt=user.getHeightInFt();
        if(user.getReligion()!=null) religion=user.getReligion();
    }
}
