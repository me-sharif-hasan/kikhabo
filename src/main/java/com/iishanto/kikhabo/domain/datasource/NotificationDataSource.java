package com.iishanto.kikhabo.domain.datasource;

import com.iishanto.kikhabo.domain.entities.notification.FcmNotification;

public interface NotificationDataSource {

    void sendToUser(Long userId, FcmNotification notification);

    void broadcastToAll(FcmNotification notification);
}
