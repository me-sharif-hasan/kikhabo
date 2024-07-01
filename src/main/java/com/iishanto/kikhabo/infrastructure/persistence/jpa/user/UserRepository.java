package com.iishanto.kikhabo.infrastructure.persistence.jpa.user;

import com.iishanto.kikhabo.infrastructure.persistence.entities.JpaUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository <JpaUser, UUID>{
    boolean existsByEmail(String email);

    JpaUser findByEmail(String email);
}
