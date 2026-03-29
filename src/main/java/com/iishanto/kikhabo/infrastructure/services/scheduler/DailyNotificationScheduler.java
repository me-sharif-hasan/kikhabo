package com.iishanto.kikhabo.infrastructure.services.scheduler;

import com.iishanto.kikhabo.domain.datasource.FcmTokenDataSource;
import com.iishanto.kikhabo.domain.datasource.NotificationContentDataSource;
import com.iishanto.kikhabo.domain.datasource.NotificationDataSource;
import com.iishanto.kikhabo.domain.datasource.UserDataSource;
import com.iishanto.kikhabo.domain.entities.notification.FcmNotification;
import com.iishanto.kikhabo.domain.entities.notification.NotificationVariant;
import com.iishanto.kikhabo.domain.entities.notification.NotificationType;
import com.iishanto.kikhabo.infrastructure.services.notification.FestivalMealNotificationService;
import com.iishanto.kikhabo.infrastructure.services.notification.HealthyMealNotificationService;
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
import java.util.concurrent.TimeUnit;
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
    private final HealthyMealNotificationService healthyMealNotificationService;
    private final FestivalMealNotificationService festivalMealNotificationService;
    private final Logger logger;

    /**
     * Fires every hour on the hour (24 times a day).
     * Uses UTC cron — the hour-matching against local country time happens inside.
     */
    // every min
    //@Scheduled(cron = "0 0 * * * *")
    public void sendScheduledNotifications() {
        logger.info("[Scheduler] Hourly notification check triggered");

        List<Long> allUserIds = new ArrayList<>(fcmTokenDataSource.getAllUserIdsWithTokens());
        if (allUserIds.isEmpty()) {
            logger.info("[Scheduler] No users with registered FCM tokens — nothing to send");
            return;
        }

        List<String> countries = userDataSource.getDistinctCountriesForUsers(allUserIds);
        if (countries.isEmpty()) {
            logger.info("[Scheduler] No country data found for token-holding users,");
          //set default country as USA
            countries = List.of("USA");
            //return;
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
            notificationDataSource.sendToUser(userId, buildStreakNotification(variants.getFirst()));
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

        int groupCount = variants.size();
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

            FcmNotification notification = buildStreakNotification(variant);
            logger.info("[Scheduler] Sending variant {} to {} user(s) in {} ({})",
                    i + 1, group.size(), country, timeSlot);
            for (Long userId : group) {
                notificationDataSource.sendToUser(userId, notification);
            }
        }
    }

    // ── Healthy Meal Schedule ─────────────────────────────────────────────────

    /**
     * Fires every hour alongside the main scheduler.
     * Sends a HEALTHY_MEAL notification at 12:00 (lunch) and 19:00 (dinner) in each country's local time.
     * Generates at most 3 recipe variants per country — one per user group.
     */
    @Scheduled(cron = "0 0 * * * *")
    public void sendHealthyMealNotifications() {logger.info("[HealthyMeal] Hourly healthy meal check triggered");

        List<Long> allUserIds = new ArrayList<>(fcmTokenDataSource.getAllUserIdsWithTokens());
        if (allUserIds.isEmpty()) return;

        List<String> countries = userDataSource.getDistinctCountriesForUsers(allUserIds);
        if (countries.isEmpty()) countries = List.of("USA");

        for (String country : countries) {
            ZoneId zoneId = CountryTimeZoneHelper.getZoneId(country);
            int localHour = ZonedDateTime.now(zoneId).getHour();

            String timeSlot = resolveHealthyMealSlot(localHour);
            if (timeSlot == null) continue;

            logger.info("[HealthyMeal] {} → local hour {} → slot '{}', dispatching healthy meal", country, localHour, timeSlot);

            List<Long> countryUserIds = userDataSource.getUserIdsByCountry(country, allUserIds);
            if (countryUserIds.isEmpty()) continue;

            String language = CountryTimeZoneHelper.getLanguage(country);

            int variantCount = countryUserIds.size() < 3 ? 1 : 3;
            List<NotificationVariant> variants =
                    healthyMealNotificationService.generateVariants(country, language, timeSlot, variantCount);

            if (variants.isEmpty()) {
                logger.warn("[HealthyMeal] No variants generated for country={}, skipping", country);
                continue;
            }

            sendHealthyMealGrouped(variants, countryUserIds, country, timeSlot);
            try{
                TimeUnit.MICROSECONDS.sleep(20000);
            }catch(InterruptedException e){
                logger.warn("[HealthyMeal] Interrupted while sleeping");
            }
        }

        logger.info("[HealthyMeal] Hourly healthy meal pass complete");
    }

    // ── Festival Meal Schedule ────────────────────────────────────────────────

    /**
     * Fires every hour. For each country whose local time is 08:00, calls Calendarific
     * to check if today is a public holiday. If yes, Gemini generates a culturally
     * appropriate festival meal, caches it, and sends a push notification to all
     * users of that country.
     */
    @Scheduled(cron = "0 0 * * * *")
    public void sendFestivalNotifications() {
        logger.info("[FestivalMeal] Hourly festival check triggered");

        List<Long> allUserIds = new ArrayList<>(fcmTokenDataSource.getAllUserIdsWithTokens());
        if (allUserIds.isEmpty()) return;

        List<String> countries = userDataSource.getDistinctCountriesForUsers(allUserIds);
        if (countries.isEmpty()) countries = List.of("USA");

        for (String country : countries) {
            ZoneId zoneId = CountryTimeZoneHelper.getZoneId(country);
            int localHour = ZonedDateTime.now(zoneId).getHour();

            if (localHour != 8) continue;

            logger.info("[FestivalMeal] {} is at 08:00 local — checking for holidays", country);

            List<Long> countryUserIds = userDataSource.getUserIdsByCountry(country, allUserIds);
            if (countryUserIds.isEmpty()) continue;

            String language = CountryTimeZoneHelper.getLanguage(country);

            festivalMealNotificationService.generateFestivalVariant(country, language)
                    .ifPresent(variant -> {
                        FcmNotification notification = FcmNotification.builder()
                                .type(NotificationType.FESTIVAL_MEAL)
                                .title(variant.getTitle())
                                .body(variant.getBody())
                                .extra(variant.getExtra())
                                .imageUrl(variant.getImageUrl())
                                .build();
                        logger.info("[FestivalMeal] Sending festival notification to {} user(s) in {}",
                                countryUserIds.size(), country);
                        for (Long userId : countryUserIds) {
                            notificationDataSource.sendToUser(userId, notification);
                        }
                    });
        }

        logger.info("[FestivalMeal] Hourly festival pass complete");
    }

    private void sendHealthyMealGrouped(List<NotificationVariant> variants, List<Long> userIds,
                                        String country, String timeSlot) {
        int groupCount = variants.size();
        List<Long> shuffled = new ArrayList<>(userIds);
        Collections.shuffle(shuffled);

        Map<Integer, List<Long>> groups = IntStream.range(0, shuffled.size())
                .boxed()
                .collect(Collectors.groupingBy(
                        i -> i % groupCount,
                        Collectors.mapping(shuffled::get, Collectors.toList())
                ));

        for (int i = 0; i < groupCount; i++) {
            List<Long> group = groups.getOrDefault(i, List.of());
            if (group.isEmpty()) continue;
            FcmNotification notification = buildHealthyMealNotification(variants.get(i));
            logger.info("[HealthyMeal] Sending variant {} to {} user(s) in {} ({})", i + 1, group.size(), country, timeSlot);
            for (Long userId : group) {
                notificationDataSource.sendToUser(userId, notification);
            }
        }
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    /** Returns the time-slot label if {@code hour} is a trigger hour, otherwise null. */
    private String resolveTimeSlot(int hour) {
        return switch (hour) {
            case 6  -> "breakfast";
            case 11 -> "lunch";
            case 16 -> "afternoon snack";
            default -> null;
        };
    }

    /** Healthy meal fires at breakfast (6), lunch (11) and afternoon snack (16). */
    private String resolveHealthyMealSlot(int hour) {
        return switch (hour) {
            case 6  -> "breakfast";
            case 11 -> "lunch";
            case 16 -> "afternoon snack";
            default -> null;
        };
    }

    private FcmNotification buildStreakNotification(NotificationVariant variant) {
        return FcmNotification.builder()
                .type(NotificationType.STREAK_REMINDER)
                .title(variant.getTitle())
                .body(variant.getBody())
                .extra(variant.getExtra())
                .build();
    }

    private FcmNotification buildHealthyMealNotification(NotificationVariant variant) {
        return FcmNotification.builder()
                .type(NotificationType.HEALTHY_MEAL)
                .title(variant.getTitle())
                .body(variant.getBody())
                .extra(variant.getExtra())
                .imageUrl(variant.getImageUrl())
                .build();
    }
}
