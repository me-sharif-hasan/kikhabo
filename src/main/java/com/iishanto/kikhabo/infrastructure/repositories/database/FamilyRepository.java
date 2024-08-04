package com.iishanto.kikhabo.infrastructure.repositories.database;

import com.iishanto.kikhabo.infrastructure.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


public interface FamilyRepository extends JpaRepository<UserEntity,Long> {
    @Modifying
    @Query(value = "insert into user_family_members (user_entity_id,family_members_id) values (:userId,:memberId)", nativeQuery = true)
    void addFamily(Long userId, Long  memberId);
}
