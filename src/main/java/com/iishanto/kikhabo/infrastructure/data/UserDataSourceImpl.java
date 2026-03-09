package com.iishanto.kikhabo.infrastructure.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iishanto.kikhabo.common.exception.global.GlobalServerException;
import com.iishanto.kikhabo.common.exception.user.UserLoginFailureException;
import com.iishanto.kikhabo.common.exception.user.UserRegistrationFailureException;
import com.iishanto.kikhabo.domain.datasource.UserDataSource;
import com.iishanto.kikhabo.domain.entities.people.Credentials;
import com.iishanto.kikhabo.domain.entities.people.SocialAuthRequest;
import com.iishanto.kikhabo.domain.entities.people.User;
import com.iishanto.kikhabo.infrastructure.model.UserEntity;
import com.iishanto.kikhabo.infrastructure.repositories.database.UserRepository;
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
    Logger logger;
    @Override
    public User register(User user) throws UserRegistrationFailureException {
        try{
            String encryptedPassword=passwordEncoder.encode(user.getPassword());
            UserEntity userEntity =objectMapper.convertValue(user, UserEntity.class);
            userEntity.setPassword(encryptedPassword);
            if(userRepository.existsByEmail(user.getEmail())) throw new RuntimeException("User already exists!");
            UserEntity savedUser = userRepository.save(userEntity);
            return objectMapper.convertValue(savedUser, User.class);
        }catch (Throwable e){
            throw new UserRegistrationFailureException(e.getLocalizedMessage());
        }
    }
    @Override
    public Credentials login(Credentials credentials) throws UserLoginFailureException, GlobalServerException {
        try{
            UserEntity userEntity =userRepository.findByEmail(credentials.getEmail());
            if(userEntity ==null) throw new UserLoginFailureException("User with email %s not found".formatted(credentials.getEmail()));
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(credentials.getEmail(),credentials.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return Credentials.builder().
                    token(jwtService.generateToken(authentication)).
                    user(objectMapper.convertValue(userEntity,User.class)).
                    build();
        }catch (UserLoginFailureException e){
            throw new UserLoginFailureException("Login failure. User does not exists.");
        }catch (BadCredentialsException e){
            throw new UserLoginFailureException("Username or password is wrong.");
        }catch (Throwable e){
            throw new GlobalServerException("Something Went Wrong During Login Process");
        }
    }

    @Override
    public Credentials socialLogin(SocialAuthRequest request) throws Exception {
        FirebaseTokenVerificationService.VerifiedUser verified = firebaseTokenVerificationService.verify(request.getIdToken());

        String email = verified.getEmail();
        if (email == null || email.isBlank()) {
            throw new UserLoginFailureException("No email associated with this social account.");
        }

        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null) {
            // Auto-register: extract first/last name from display name
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
            userEntity = userRepository.save(userEntity);
            logger.info("Social login: new user auto-registered via {} for email={}", request.getProvider(), email);
        } else {
            // Update profile picture if changed
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

    @Override
    public User getAuthenticatedUser() {
        UserEntity user=userRepository.findByEmail(getAuthUserEmail());
        return user.toDomain();
    }

    @Override
    public String getAuthUserEmail() {
        UserDetails userDetails= (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUsername();
    }



    @Override
    public User updateAuthenticatedUser(User user) {
        User authenticatedUser = getAuthenticatedUser();
        UserEntity userEntity=userRepository.findByEmail(authenticatedUser.getEmail());
        userEntity.fill(user);
        userRepository.save(userEntity);
        return userEntity.toDomain();
    }

    @Override
    public List<User> searchUser(String keyword) {
        List<UserEntity> users=userRepository.searchUserByKeyword(keyword,10);
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
}
