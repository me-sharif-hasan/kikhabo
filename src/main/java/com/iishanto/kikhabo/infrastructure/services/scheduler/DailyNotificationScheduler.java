package com.iishanto.kikhabo.infrastructure.services.scheduler;

import com.iishanto.kikhabo.domain.datasource.FcmTokenDataSource;
import com.iishanto.kikhabo.domain.datasource.NotificationContentDataSource;
import com.iishanto.kikhabo.domain.datasource.NotificationDataSource;
import com.iishanto.kikhabo.domain.datasource.UserDataSource;
import com.iishanto.kikhabo.domain.entities.notification.FcmNotification;
import com.iishanto.kikhabo.domain.entities.notification.NotificationVariant;
import com.iishanto.kikhabo.domain.entities.notification.NotificationType;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Runs every hour (24 times/day) and dispatches localised, funny, nutrition-aware
 * push notifications at 06:00, 11:00 and 16:00 in each country's primary time zone.
 *
 * <p>Grouping rules per country:
 * <ul>
 *   <li>If a country has fewer than 3 users with tokens → generate one unique
 *       notification per user (personalised AI call per user).</li>
 *   <li>If a country has 3 or more users with tokens → generate exactly 3
 *       notification variants, shuffle users, split into 3 groups, send each
 *       group one shared variant.</li>
 * </ul>
 */
@Component
@AllArgsConstructor
public class DailyNotificationScheduler {



    private final NotificationContentDataSource notificationContentDataSource;
    private final FcmTokenDataSource fcmTokenDataSource;
    private final NotificationDataSource notificationDataSource;
    private final UserDataSource userDataSource;
    private final Logger logger;

    /**
     * Fires every hour on the hour (24 times a day).
     * Uses UTC cron — the hour-matching against local country time happens inside.
     */
    // every min
    @Scheduled(cron = "0 0 * * * *")
    public void sendScheduledNotifications() {
        logger.info("[Scheduler] Hourly notification check triggered");

        List<Long> allUserIds = new ArrayList<>(fcmTokenDataSource.getAllUserIdsWithTokens());
        if (allUserIds.isEmpty()) {
            logger.info("[Scheduler] No users with registered FCM tokens — nothing to send");
            return;
        }

        List<String> countries = userDataSource.getDistinctCountriesForUsers(allUserIds);
        if (countries.isEmpty()) {
            logger.info("[Scheduler] No country data found for token-holding users");
            return;
        }

        logger.info("[Scheduler] Checking {} country(ies) for scheduled send windows", countries.size());

        for (String country : countries) {
            ZoneId zoneId = CountryTimeZoneHelper.getZoneId(country);
            int localHour = ZonedDateTime.now(zoneId).getHour();

            String timeSlot = resolveTimeSlot(localHour);
            if (timeSlot == null) {
                // Not a trigger hour for this country right now
                continue;
            }

            logger.info("[Scheduler] {} → local hour {} → slot '{}', dispatching notifications",
                    country, localHour, timeSlot);

            List<Long> countryUserIds = userDataSource.getUserIdsByCountry(country, allUserIds);
            if (countryUserIds.isEmpty()) continue;

            String language = CountryTimeZoneHelper.getLanguage(country);

            if (countryUserIds.size() < 3) {
                sendUniquePerUser(country, language, timeSlot, countryUserIds);
            } else {
                sendGrouped(country, language, timeSlot, countryUserIds);
            }
        }

        logger.info("[Scheduler] Hourly notification pass complete");
    }

    // ── Private helpers ──────────────────────────────────────────────────────────

    /**
     * Fewer than 3 users in the country → generate a dedicated notification
     * for each user (unique AI call per user).
     */
    private void sendUniquePerUser(String country, String language, String timeSlot, List<Long> userIds) {
        logger.info("[Scheduler] {} — {} user(s) < 3, sending unique notifications", country, userIds.size());
        for (Long userId : userIds) {
            List<NotificationVariant> variants =
                    notificationContentDataSource.generateCountryVariants(country, language, timeSlot, 1);
            if (variants.isEmpty()) {
                logger.warn("[Scheduler] No variant generated for user {} ({}), skipping", userId, country);
                continue;
            }
            notificationDataSource.sendToUser(userId, buildNotification(variants.getFirst()));
            logger.info("[Scheduler] Sent unique '{}' notification to user {} ({})", timeSlot, userId, country);
        }
    }

    /**
     * 3 or more users in the country → generate 3 shared variants, split users
     * into 3 groups (round-robin), send each group one variant.
     */
    private void sendGrouped(String country, String language, String timeSlot, List<Long> userIds) {
        logger.info("[Scheduler] {} — {} user(s) ≥ 3, generating 3 shared variants", country, userIds.size());

        List<NotificationVariant> variants =
                notificationContentDataSource.generateCountryVariants(country, language, timeSlot, 3);
        if (variants.isEmpty()) {
            logger.warn("[Scheduler] No variants generated for {} — skipping broadcast", country);
            return;
        }

        int groupCount = variants.size(); // may be < 3 if AI returned fewer
        List<Long> shuffled = new ArrayList<>(userIds);
        Collections.shuffle(shuffled);

        Map<Integer, List<Long>> groups = IntStream.range(0, shuffled.size())
                .boxed()
                .collect(Collectors.groupingBy(
                        i -> i % groupCount,
                        Collectors.mapping(shuffled::get, Collectors.toList())
                ));

        for (int i = 0; i < groupCount; i++) {
            NotificationVariant variant = variants.get(i);
            List<Long> group = groups.getOrDefault(i, List.of());
            if (group.isEmpty()) continue;

            FcmNotification notification = buildNotification(variant);
            logger.info("[Scheduler] Sending variant {} to {} user(s) in {} ({})",
                    i + 1, group.size(), country, timeSlot);
            for (Long userId : group) {
                notificationDataSource.sendToUser(userId, notification);
            }
        }
    }

    /** Returns the time-slot label if {@code hour} is a trigger hour, otherwise null. */
    private String resolveTimeSlot(int hour) {
        return switch (hour) {
            case 6  -> "breakfast";
            case 11 -> "lunch";
            case 16 -> "afternoon snack";
            default -> null;
        };
    }

    private FcmNotification buildNotification(NotificationVariant variant) {
        return FcmNotification.builder()
                .type(NotificationType.STREAK_REMINDER)
                .title(variant.getTitle())
                .body(variant.getBody())
                .extra(variant.getExtra())
                .build();
    }
}
