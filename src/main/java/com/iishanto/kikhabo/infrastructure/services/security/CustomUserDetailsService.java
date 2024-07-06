package com.iishanto.kikhabo.infrastructure.services.security;

import com.iishanto.kikhabo.infrastructure.model.UserEntity;
import com.iishanto.kikhabo.infrastructure.repositories.database.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity =userRepository.findByEmail(username);
        return getUser(userEntity);
    }


    private User getUser(UserEntity userEntity){
        return new User(userEntity.getEmail(), userEntity.getPassword(),new HashSet<>());
    }
}
