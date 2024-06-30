package com.iishanto.kikhabo.infrastructure.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iishanto.kikhabo.common.exception.user.UserRegistrationFailureException;
import com.iishanto.kikhabo.domain.datasource.UserDataSource;
import com.iishanto.kikhabo.domain.entities.people.User;
import com.iishanto.kikhabo.infrastructure.persistence.entities.JpaUser;
import com.iishanto.kikhabo.infrastructure.persistence.jpa.user.UserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.SQLIntegrityConstraintViolationException;

@Component
@AllArgsConstructor
public class UserDataSourceImpl implements UserDataSource {
    UserRepository userRepository;
    ObjectMapper objectMapper;
    Logger logger;
    PasswordEncoder passwordEncoder;
    @Override
    public User register(User user) throws UserRegistrationFailureException {
        try{
            String encryptedPassword=passwordEncoder.encode(user.getPassword());
            JpaUser jpaUser =objectMapper.convertValue(user, JpaUser.class);
            if(userRepository.existsByEmail(user.getEmail())) throw new RuntimeException("User already exists!");
            JpaUser savedUser = userRepository.save(jpaUser);
            return objectMapper.convertValue(savedUser, User.class);
        }catch (Throwable e){
            throw new UserRegistrationFailureException(e.getLocalizedMessage());
        }
    }
}
