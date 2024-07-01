package com.iishanto.kikhabo.infrastructure.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iishanto.kikhabo.common.exception.global.GlobalServerException;
import com.iishanto.kikhabo.common.exception.user.UserLoginFailureException;
import com.iishanto.kikhabo.common.exception.user.UserRegistrationFailureException;
import com.iishanto.kikhabo.domain.datasource.UserDataSource;
import com.iishanto.kikhabo.domain.entities.people.Credentials;
import com.iishanto.kikhabo.domain.entities.people.User;
import com.iishanto.kikhabo.infrastructure.model.JpaUser;
import com.iishanto.kikhabo.infrastructure.repositories.database.UserRepository;
import com.iishanto.kikhabo.infrastructure.services.security.JwtService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class UserDataSourceImpl implements UserDataSource {
    UserRepository userRepository;
    ObjectMapper objectMapper;
    PasswordEncoder passwordEncoder;
    AuthenticationManager authenticationManager;
    JwtService jwtService;
    Logger logger;
    @Override
    public User register(User user) throws UserRegistrationFailureException {
        try{
            String encryptedPassword=passwordEncoder.encode(user.getPassword());
            JpaUser jpaUser =objectMapper.convertValue(user, JpaUser.class);
            jpaUser.setPassword(encryptedPassword);
            if(userRepository.existsByEmail(user.getEmail())) throw new RuntimeException("User already exists!");
            JpaUser savedUser = userRepository.save(jpaUser);
            return objectMapper.convertValue(savedUser, User.class);
        }catch (Throwable e){
            throw new UserRegistrationFailureException(e.getLocalizedMessage());
        }
    }

    @Override
    public Credentials login(Credentials credentials) throws UserLoginFailureException, GlobalServerException {
        try{
            JpaUser jpaUser=userRepository.findByEmail(credentials.getEmail());
            if(jpaUser==null) throw new UserLoginFailureException("User with email %s not found".formatted(credentials.getEmail()));
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(credentials.getEmail(),credentials.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return Credentials.builder().
                    token(jwtService.generateToken(authentication)).
                    user(objectMapper.convertValue(jpaUser,User.class)).
                    build();
        }catch (UserLoginFailureException e){
            throw new UserLoginFailureException("Login failure. User does not exists.");
        }catch (BadCredentialsException e){
            throw new UserLoginFailureException("Username or password is wrong.");
        }catch (Throwable e){
            throw new GlobalServerException("Something Went Wrong During Login Process");
        }
    }

}
