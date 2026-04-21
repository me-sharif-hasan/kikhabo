package com.iishanto.kikhabo.infrastructure.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iishanto.kikhabo.common.exception.global.GlobalServerException;
import com.iishanto.kikhabo.common.exception.user.EmailVerificationException;
import com.iishanto.kikhabo.common.exception.user.UserLoginFailureException;
import com.iishanto.kikhabo.common.exception.user.UserRegistrationFailureException;
import com.iishanto.kikhabo.domain.datasource.UserDataSource;
import com.iishanto.kikhabo.domain.entities.people.Credentials;
import com.iishanto.kikhabo.domain.entities.people.SocialAuthRequest;
import com.iishanto.kikhabo.domain.entities.people.User;
import com.iishanto.kikhabo.infrastructure.model.UserEntity;
import com.iishanto.kikhabo.infrastructure.repositories.database.UserRepository;
import com.iishanto.kikhabo.infrastructure.services.notification.EmailService;
import com.iishanto.kikhabo.infrastructure.services.security.FirebaseTokenVerificationService;
import com.iishanto.kikhabo.infrastructure.services.security.JwtService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;


@Component
@AllArgsConstructor
public class UserDataSourceImpl implements UserDataSource {

    UserRepository userRepository;
    ObjectMapper objectMapper;
    PasswordEncoder passwordEncoder;
    AuthenticationManager authenticationManager;
    JwtService jwtService;
    FirebaseTokenVerificationService firebaseTokenVerificationService;
    EmailService emailService;
    Logger logger;

    private static final int OTP_EXPIRY_MINUTES = 10;
    private static final int OTP_RESEND_COOLDOWN_MINUTES = 2;

    // -------------------------------------------------------------------------
    // Registration
    // -------------------------------------------------------------------------

    @Override
    public User register(User user) throws UserRegistrationFailureException {
        try {
            if (userRepository.existsByEmail(user.getEmail())) {
                throw new RuntimeException("User already exists!");
            }
            String encryptedPassword = passwordEncoder.encode(user.getPassword());
            UserEntity userEntity = objectMapper.convertValue(user, UserEntity.class);
            userEntity.setPassword(encryptedPassword);
            // Email/password users start unverified
            userEntity.setVerified(false);
            UserEntity savedUser = userRepository.save(userEntity);

            // Generate and send OTP asynchronously-ish (any mail error is swallowed
            // so registration itself does not fail — user can use resend-otp later)
            try {
                String otp = generateOtp();
                savedUser.setVerificationCode(otp);
                savedUser.setVerificationCodeExpiry(LocalDateTime.now().plusMinutes(OTP_EXPIRY_MINUTES));
                userRepository.save(savedUser);
                emailService.sendOtpEmail(savedUser.getEmail(), otp);
                logger.info("Verification OTP sent to {} after registration", savedUser.getEmail());
            } catch (Exception mailEx) {
                logger.error("Failed to send verification OTP after registration to {}: {}", savedUser.getEmail(), mailEx.getMessage());
            }

            return objectMapper.convertValue(savedUser, User.class);
        } catch (Throwable e) {
            throw new UserRegistrationFailureException(e.getLocalizedMessage());
        }
    }

    // -------------------------------------------------------------------------
    // Login
    // -------------------------------------------------------------------------

    @Override
    public Credentials login(Credentials credentials) throws UserLoginFailureException, GlobalServerException {
        try {
            UserEntity userEntity = userRepository.findByEmail(credentials.getEmail());
            if (userEntity == null) {
                throw new UserLoginFailureException("User with email %s not found".formatted(credentials.getEmail()));
            }

            // Block unverified email/password users and send a fresh OTP
            if (!userEntity.isVerified()) {
                try {
                    sendVerificationOtp(credentials.getEmail());
                } catch (EmailVerificationException cooldownEx) {
                    // Cooldown active — still block login but don't send another OTP
                    logger.info("Cooldown active for {} during login, no new OTP sent", credentials.getEmail());
                }
                throw new UserLoginFailureException(
                        "Your email is not verified. A verification OTP has been sent to " + credentials.getEmail() + ". Please verify and try again."
                );
            }

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return Credentials.builder()
                    .token(jwtService.generateToken(authentication))
                    .user(objectMapper.convertValue(userEntity, User.class))
                    .build();

        } catch (UserLoginFailureException e) {
            throw e;
        } catch (BadCredentialsException e) {
            throw new UserLoginFailureException("Username or password is wrong.");
        } catch (Throwable e) {
            throw new GlobalServerException("Something Went Wrong During Login Process");
        }
    }

    // -------------------------------------------------------------------------
    // Social login
    // -------------------------------------------------------------------------

    @Override
    public Credentials socialLogin(SocialAuthRequest request) throws Exception {
        FirebaseTokenVerificationService.VerifiedUser verified = firebaseTokenVerificationService.verify(request.getIdToken());

        String email = verified.getEmail();
        if (email == null || email.isBlank()) {
            throw new UserLoginFailureException("No email associated with this social account.");
        }

        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null) {
            String fullName = verified.getName() != null ? verified.getName() : "";
            String[] parts = fullName.split(" ", 2);
            String firstName = parts.length > 0 ? parts[0] : "";
            String lastName = parts.length > 1 ? parts[1] : "";

            userEntity = new UserEntity();
            userEntity.setEmail(email);
            userEntity.setFirstName(firstName);
            userEntity.setLastName(lastName);
            userEntity.setProfileImageUrl(verified.getPicture());
            userEntity.setAuthProvider(request.getProvider().name());
            userEntity.setExternalId(verified.getUid());
            // Social login users are auto-verified
            userEntity.setVerified(true);
            userEntity = userRepository.save(userEntity);
            logger.info("Social login: new user auto-registered via {} for email={}", request.getProvider(), email);
        } else {
            if (verified.getPicture() != null && !verified.getPicture().equals(userEntity.getProfileImageUrl())) {
                userEntity.setProfileImageUrl(verified.getPicture());
                userEntity = userRepository.save(userEntity);
            }
            logger.info("Social login: existing user signed in via {} for email={}", request.getProvider(), email);
        }

        String jwt = jwtService.generateTokenForEmail(email);
        return Credentials.builder()
                .token(jwt)
                .user(userEntity.toDomain())
                .build();
    }

    // -------------------------------------------------------------------------
    // OTP operations
    // -------------------------------------------------------------------------

    @Override
    public void sendVerificationOtp(String email) throws EmailVerificationException {
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null) {
            throw new EmailVerificationException("No account found with email: " + email);
        }
        if (userEntity.isVerified()) {
            throw new EmailVerificationException("This account is already verified.");
        }

        // Enforce 2-minute cooldown
        if (userEntity.getVerificationCodeExpiry() != null) {
            LocalDateTime cooldownBoundary = LocalDateTime.now().minusMinutes(OTP_EXPIRY_MINUTES - OTP_RESEND_COOLDOWN_MINUTES);
            if (userEntity.getVerificationCodeExpiry().isAfter(cooldownBoundary)) {
                throw new EmailVerificationException(
                        "Please wait " + OTP_RESEND_COOLDOWN_MINUTES + " minutes before requesting a new OTP."
                );
            }
        }

        String otp = generateOtp();
        userEntity.setVerificationCode(otp);
        userEntity.setVerificationCodeExpiry(LocalDateTime.now().plusMinutes(OTP_EXPIRY_MINUTES));
        userRepository.save(userEntity);

        emailService.sendOtpEmail(email, otp);
        logger.info("Verification OTP sent to {}", email);
    }

    @Override
    public boolean verifyOtp(String email, String otp) throws EmailVerificationException {
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null) {
            throw new EmailVerificationException("No account found with email: " + email);
        }
        if (userEntity.isVerified()) {
            throw new EmailVerificationException("This account is already verified.");
        }
        if (userEntity.getVerificationCode() == null || userEntity.getVerificationCodeExpiry() == null) {
            throw new EmailVerificationException("No pending OTP found. Please request a new one.");
        }
        if (LocalDateTime.now().isAfter(userEntity.getVerificationCodeExpiry())) {
            throw new EmailVerificationException("OTP has expired. Please request a new one.");
        }
        if (!userEntity.getVerificationCode().equals(otp)) {
            throw new EmailVerificationException("Invalid OTP. Please check and try again.");
        }

        userEntity.setVerified(true);
        userEntity.setVerificationCode(null);
        userEntity.setVerificationCodeExpiry(null);
        userRepository.save(userEntity);
        logger.info("Email verified successfully for {}", email);
        return true;
    }

    // -------------------------------------------------------------------------
    // User management helpers
    // -------------------------------------------------------------------------

    @Override
    public User getAuthenticatedUser() {
        UserEntity user = userRepository.findByEmail(getAuthUserEmail());
        return user.toDomain();
    }

    @Override
    public String getAuthUserEmail() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUsername();
    }

    @Override
    public User updateAuthenticatedUser(User user) {
        User authenticatedUser = getAuthenticatedUser();
        UserEntity userEntity = userRepository.findByEmail(authenticatedUser.getEmail());
        userEntity.fill(user);
        userRepository.save(userEntity);
        return userEntity.toDomain();
    }

    @Override
    public List<User> searchUser(String keyword) {
        List<UserEntity> users = userRepository.searchUserByKeyword(keyword, 10);
        return users.stream().map(UserEntity::toDomain).toList();
    }

    @Override
    public List<String> getDistinctCountriesForUsers(List<Long> userIds) {
        if (userIds == null || userIds.isEmpty()) return List.of();
        return userRepository.findDistinctCountriesByIdIn(userIds);
    }

    @Override
    public List<Long> getUserIdsByCountry(String country, List<Long> userIds) {
        if (userIds == null || userIds.isEmpty()) return List.of();
        return userRepository.findIdsByCountryAndIdIn(country, userIds);
    }

    // -------------------------------------------------------------------------
    // Private helpers
    // -------------------------------------------------------------------------

    /** Generates a cryptographically-random 6-digit OTP string (zero-padded). */
    private String generateOtp() {
        SecureRandom random = new SecureRandom();
        int otp = random.nextInt(900000) + 100000; // 100000–999999
        return String.valueOf(otp);
    }
}
