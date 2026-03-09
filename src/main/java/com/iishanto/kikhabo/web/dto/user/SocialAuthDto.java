package com.iishanto.kikhabo.web.dto.user;

import com.iishanto.kikhabo.domain.entities.people.AuthProvider;
import com.iishanto.kikhabo.domain.entities.people.SocialAuthRequest;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SocialAuthDto {

    /** Firebase ID token obtained by the client after signing in with any provider */
    @NotEmpty
    private String idToken;

    /**
     * Which provider was used.
     * Currently supported: GOOGLE
     * Future: FACEBOOK, PHONE_OTP
     */
    @NotNull
    private AuthProvider provider;

    public SocialAuthRequest toDomain() {
        return SocialAuthRequest.builder()
                .idToken(idToken)
                .provider(provider)
                .build();
    }
}
