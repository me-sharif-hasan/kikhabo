package com.iishanto.kikhabo.infrastructure.data;

import com.iishanto.kikhabo.domain.datasource.FcmTokenDataSource;
import com.iishanto.kikhabo.domain.datasource.NotificationDataSource;
import com.iishanto.kikhabo.domain.entities.notification.FcmNotification;
import com.iishanto.kikhabo.infrastructure.services.notification.FcmApiService;
import com.iishanto.kikhabo.infrastructure.services.notification.FcmSendResult;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class NotificationDataSourceImpl implements NotificationDataSource {

    FcmApiService fcmApiService;
    FcmTokenDataSource fcmTokenDataSource;
    Logger logger;

    @Override
    public void broadcastToAll(FcmNotification notification) {
        List<Long> userIds = fcmTokenDataSource.getAllUserIdsWithTokens();
        logger.info("[FCM] Broadcasting {} notification to {} user(s)", notification.getType().getValue(), userIds.size());
        for (Long userId : userIds) {
            sendToUser(userId, notification);
        }
        logger.info("[FCM] Broadcast complete");
    }

    @Override
    public void sendToUser(Long userId, FcmNotification notification) {
        List<String> tokens = fcmTokenDataSource.getTokensForUser(userId);
        if (tokens.isEmpty()) {
            logger.warn("[FCM] No FCM tokens found for user {}, skipping notification send.", userId);
            return;
        }
        logger.info("[FCM] Sending {} notification to user {} ({} device(s))",
                notification.getType().getValue(), userId, tokens.size());
        for (String token : tokens) {
            FcmSendResult result = fcmApiService.send(token, notification);
            if (result == FcmSendResult.INVALID_TOKEN) {
                fcmTokenDataSource.removeInvalidToken(token);
            } else if (result == FcmSendResult.TRANSIENT_ERROR) {
                String tokenTail = token.length() >= 8 ? token.substring(token.length() - 8) : token;
                logger.warn("[FCM] Transient error for user {}, token tail ...{}", userId, tokenTail);
            }
        }
    }
}
