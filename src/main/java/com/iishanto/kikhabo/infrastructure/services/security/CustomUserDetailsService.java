package com.iishanto.kikhabo.infrastructure.services.security;

import com.iishanto.kikhabo.infrastructure.model.JpaUser;
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
        JpaUser jpaUser=userRepository.findByEmail(username);
        return getUser(jpaUser);
    }


    private User getUser(JpaUser jpaUser){
        return new User(jpaUser.getEmail(),jpaUser.getPassword(),new HashSet<>());
    }
}