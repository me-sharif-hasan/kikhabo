package com.iishanto.kikhabo.web.dto.user;

import com.iishanto.kikhabo.domain.entities.people.Credentials;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LoginResponseDto {
    private String status;
    private String token;
    public static LoginResponseDto fromCredential(Credentials credentials){
        return new LoginResponseDto("success",credentials.getToken());
    }
}
