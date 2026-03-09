package com.iishanto.kikhabo.domain.entities.people;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SocialAuthRequest {
    private String idToken;
    private AuthProvider provider;
}
