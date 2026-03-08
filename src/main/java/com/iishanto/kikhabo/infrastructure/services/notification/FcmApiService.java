package com.iishanto.kikhabo.infrastructure.services.notification;

import com.iishanto.kikhabo.domain.entities.notification.FcmNotification;

public interface FcmApiService {

    FcmSendResult send(String fcmToken, FcmNotification notification);
}
