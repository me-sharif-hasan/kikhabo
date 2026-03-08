package com.iishanto.kikhabo.infrastructure.services.notification.implementations;

import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.AndroidNotification;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.MessagingErrorCode;
import com.google.firebase.messaging.Notification;
import com.iishanto.kikhabo.domain.entities.notification.FcmNotification;
import com.iishanto.kikhabo.infrastructure.services.notification.FcmApiService;
import com.iishanto.kikhabo.infrastructure.services.notification.FcmSendResult;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class FcmApiServiceImpl implements FcmApiService {

    FirebaseMessaging firebaseMessaging;
    Logger logger;

    @Override
    public FcmSendResult send(String fcmToken, FcmNotification notification) {
        try {
            Notification.Builder notificationBuilder = Notification.builder()
                    .setTitle(notification.getTitle())
                    .setBody(notification.getBody());
            if (notification.getImageUrl() != null) {
                notificationBuilder.setImage(notification.getImageUrl());
            }

            AndroidConfig.Builder androidConfigBuilder = AndroidConfig.builder()
                    .setPriority(notification.getType().isHighPriority()
                            ? AndroidConfig.Priority.HIGH
                            : AndroidConfig.Priority.NORMAL);

            AndroidNotification.Builder androidNotificationBuilder = AndroidNotification.builder()
                    .setChannelId(notification.getChannelId());
            if (notification.getImageUrl() != null) {
                androidNotificationBuilder.setImage(notification.getImageUrl());
            }
            androidConfigBuilder.setNotification(androidNotificationBuilder.build());

            Message message = Message.builder()
                    .setToken(fcmToken)
                    .setNotification(notificationBuilder.build())
                    .setAndroidConfig(androidConfigBuilder.build())
                    .putData("type", notification.getType().getValue())
                    .putData("route", notification.getEffectiveRoute())
                    .putData("extra", notification.getEffectiveExtra())
                    .build();

            String messageId = firebaseMessaging.send(message);
            String tokenTail = fcmToken.length() >= 8
                    ? fcmToken.substring(fcmToken.length() - 8)
                    : fcmToken;
            logger.info("[FCM] Message sent successfully — token tail: ...{}, messageId: {}", tokenTail, messageId);
            return FcmSendResult.SUCCESS;

        } catch (FirebaseMessagingException e) {
            MessagingErrorCode errorCode = e.getMessagingErrorCode();
            if (errorCode == MessagingErrorCode.UNREGISTERED
                    || errorCode == MessagingErrorCode.INVALID_ARGUMENT) {
                logger.warn("[FCM] Invalid token detected — errorCode: {}", errorCode);
                return FcmSendResult.INVALID_TOKEN;
            }
            logger.error("[FCM] Transient error sending message — errorCode: {}", errorCode);
            return FcmSendResult.TRANSIENT_ERROR;
        }
    }
}
