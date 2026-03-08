package com.iishanto.kikhabo.infrastructure.data;

import com.iishanto.kikhabo.domain.datasource.FcmTokenDataSource;
import com.iishanto.kikhabo.infrastructure.model.FcmTokenEntity;
import com.iishanto.kikhabo.infrastructure.model.UserEntity;
import com.iishanto.kikhabo.infrastructure.repositories.database.FcmTokenRepository;
import com.iishanto.kikhabo.infrastructure.repositories.database.UserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class FcmTokenDataSourceImpl implements FcmTokenDataSource {

    FcmTokenRepository fcmTokenRepository;
    UserRepository userRepository;
    Logger logger;

    @Override
    public void registerToken(Long userId, String token, String platform) {
        if (fcmTokenRepository.existsByUser_IdAndToken(userId, token)) {
            logger.info("[FcmToken] Token already registered for user {}, skipping.", userId);
            return;
        }
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("User not found with id: " + userId)
        );
        FcmTokenEntity entity = new FcmTokenEntity();
        entity.setUser(userEntity);
        entity.setToken(token);
        entity.setPlatform(platform);
        entity.setCreatedAt(System.currentTimeMillis());
        fcmTokenRepository.save(entity);
        logger.info("[FcmToken] Registered new token for user {}. Platform: {}", userId, platform);
    }

    @Override
    public void removeToken(Long userId, String token) {
        fcmTokenRepository.deleteByUser_IdAndToken(userId, token);
        logger.info("[FcmToken] Removed token for user {}", userId);
    }

    @Override
    public List<String> getTokensForUser(Long userId) {
        return fcmTokenRepository.findByUser_Id(userId)
                .stream()
                .map(FcmTokenEntity::getToken)
                .toList();
    }

    @Override
    public void removeInvalidToken(String token) {
        fcmTokenRepository.deleteByToken(token);
        String tokenTail = token.length() >= 8 ? token.substring(token.length() - 8) : token;
        logger.warn("[FcmToken] Deleted invalid token (tail: ...{})", tokenTail);
    }

    @Override
    public List<Long> getAllUserIdsWithTokens() {
        return fcmTokenRepository.findDistinctUserIds();
    }
}
