package com.iishanto.kikhabo.domain.datasource;

import com.iishanto.kikhabo.domain.entities.notification.NotificationVariant;

import java.util.List;

public interface NotificationContentDataSource {
    List<NotificationVariant> generateDailyVariants();
}
