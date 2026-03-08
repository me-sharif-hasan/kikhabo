package com.iishanto.kikhabo.infrastructure.services.scheduler;

import com.iishanto.kikhabo.domain.datasource.FcmTokenDataSource;
import com.iishanto.kikhabo.domain.datasource.NotificationContentDataSource;
import com.iishanto.kikhabo.domain.datasource.NotificationDataSource;
import com.iishanto.kikhabo.domain.entities.notification.FcmNotification;
import com.iishanto.kikhabo.domain.entities.notification.NotificationVariant;
import com.iishanto.kikhabo.domain.entities.notification.NotificationType;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@AllArgsConstructor
public class DailyNotificationScheduler {

    private final NotificationContentDataSource notificationContentDataSource;
    private final FcmTokenDataSource fcmTokenDataSource;
    private final NotificationDataSource notificationDataSource;
    private final Logger logger;

    @Scheduled(cron = "0 0 5 * * *", zone = "UTC")
    public void sendDailyEngagementNotifications() {
        logger.info("[DailyNotification] Scheduler triggered — generating engagement notification variants");

        List<NotificationVariant> variants = notificationContentDataSource.generateDailyVariants();
        if (variants.isEmpty()) {
            logger.warn("[DailyNotification] No variants generated, aborting broadcast");
            return;
        }

        int groupCount = variants.size();
        List<Long> allUserIds = new ArrayList<>(fcmTokenDataSource.getAllUserIdsWithTokens());
        if (allUserIds.isEmpty()) {
            logger.warn("[DailyNotification] No users with registered tokens, aborting broadcast");
            return;
        }

        Collections.shuffle(allUserIds);
        logger.info("[DailyNotification] {} user(s) will be split into {} group(s)", allUserIds.size(), groupCount);

        // Split users into groups by index modulo groupCount
        Map<Integer, List<Long>> groups = IntStream.range(0, allUserIds.size())
                .boxed()
                .collect(Collectors.groupingBy(
                        i -> i % groupCount,
                        Collectors.mapping(allUserIds::get, Collectors.toList())
                ));

        for (int i = 0; i < groupCount; i++) {
            NotificationVariant variant = variants.get(i);
            List<Long> group = groups.getOrDefault(i, List.of());
            if (group.isEmpty()) {
                continue;
            }

            FcmNotification notification = FcmNotification.builder()
                    .type(NotificationType.STREAK_REMINDER)
                    .title(variant.getTitle())
                    .body(variant.getBody())
                    .extra(variant.getExtra())
                    .build();

            logger.info("[DailyNotification] Sending variant '{}' (audience: {}) to {} user(s)",
                    variant.getTitle(), variant.getAudience(), group.size());

            for (Long userId : group) {
                notificationDataSource.sendToUser(userId, notification);
            }
        }

        logger.info("[DailyNotification] Daily engagement broadcast complete");
    }
}
