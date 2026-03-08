package com.iishanto.kikhabo.infrastructure.repositories.database;

import com.iishanto.kikhabo.infrastructure.model.FcmTokenEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FcmTokenRepository extends JpaRepository<FcmTokenEntity, Long> {

    boolean existsByUser_IdAndToken(Long userId, String token);

    List<FcmTokenEntity> findByUser_Id(Long userId);

    @Query("SELECT DISTINCT t.user.id FROM FcmTokenEntity t")
    List<Long> findDistinctUserIds();

    @Transactional
    void deleteByUser_IdAndToken(Long userId, String token);

    @Transactional
    void deleteByToken(String token);
}
