package com.iishanto.kikhabo.infrastructure.repositories.database;

import com.iishanto.kikhabo.infrastructure.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository <UserEntity, Long>{
    boolean existsByEmail(String email);
    UserEntity findByEmail(String email);
    @Query(value = "SELECT * FROM user WHERE (first_name LIKE %:keyword% OR last_name LIKE %:keyword% OR email LIKE %:keyword%) LIMIT :limit", nativeQuery = true)
    List<UserEntity> searchUserByKeyword(@Param("keyword") String keyword, @Param("limit") int limit);

    @Query("SELECT DISTINCT u.country FROM UserEntity u WHERE u.id IN :ids AND u.country IS NOT NULL AND u.country <> ''")
    List<String> findDistinctCountriesByIdIn(@Param("ids") List<Long> ids);

    @Query("SELECT u.id FROM UserEntity u WHERE u.country = :country AND u.id IN :ids")
    List<Long> findIdsByCountryAndIdIn(@Param("country") String country, @Param("ids") List<Long> ids);
}
