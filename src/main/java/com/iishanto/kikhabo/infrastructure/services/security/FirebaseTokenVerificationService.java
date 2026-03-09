package com.iishanto.kikhabo.infrastructure.services.security;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Verifies Firebase ID tokens sent from the mobile client.
 * Works for any provider (Google, Facebook, Phone OTP) that Firebase Auth supports,
 * because the client always exchanges the provider credential for a Firebase ID token first.
 *
 * FirebaseApp dependency is declared to guarantee it is initialized before this service.
 */
@Service
@AllArgsConstructor
public class FirebaseTokenVerificationService {

    @SuppressWarnings("unused")
    private final FirebaseApp firebaseApp;

    public VerifiedUser verify(String idToken) throws Exception {
        FirebaseToken token = FirebaseAuth.getInstance().verifyIdToken(idToken);
        return VerifiedUser.builder()
                .uid(token.getUid())
                .email(token.getEmail())
                .name(token.getName())
                .picture(token.getPicture())
                .build();
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class VerifiedUser {
        private String uid;
        private String email;
        private String name;
        private String picture;
    }
}
