package com.iishanto.kikhabo.domain.entities.people;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Credentials {
    private String email;
    private String password;
    private String token;
    private User user;
}
