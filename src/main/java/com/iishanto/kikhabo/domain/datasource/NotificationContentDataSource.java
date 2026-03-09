package com.iishanto.kikhabo.domain.datasource;

import com.iishanto.kikhabo.domain.entities.notification.NotificationVariant;

import java.util.List;

public interface NotificationContentDataSource {

    /**
     * Generates up to {@code count} funny, nutrition-aware push notification variants
     * localised to the given country and language, tuned for the specified time slot.
     *
     * @param country  country name (e.g. "Bangladesh")
     * @param language language to write the notification in (e.g. "Bengali")
     * @param timeSlot one of "breakfast", "lunch", or "afternoon snack"
     * @param count    number of variants to generate (1–3)
     */
    List<NotificationVariant> generateCountryVariants(String country, String language, String timeSlot, int count);
}
