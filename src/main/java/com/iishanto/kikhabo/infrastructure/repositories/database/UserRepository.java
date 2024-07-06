package com.iishanto.kikhabo.infrastructure.repositories.database;

import com.iishanto.kikhabo.infrastructure.model.JpaUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository <JpaUser, UUID>{
    boolean existsByEmail(String email);

    JpaUser findByEmail(String email);
}
