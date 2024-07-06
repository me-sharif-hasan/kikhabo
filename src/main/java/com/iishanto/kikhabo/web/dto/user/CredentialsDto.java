package com.iishanto.kikhabo.web.dto.user;

import com.iishanto.kikhabo.domain.entities.people.Credentials;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
@Data
@AllArgsConstructor
public class CredentialsDto {
    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    @Length(min = 8,max = 128)
    private String password;

    public Credentials toDomain(){
        return new Credentials(email,password,null,null);
    }
}
