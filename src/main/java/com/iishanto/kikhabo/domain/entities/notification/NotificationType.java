package com.iishanto.kikhabo.domain.entities.notification;

import lombok.Getter;

@Getter
public enum NotificationType {

    MEAL_LIST("meal_list", "kikhabo_reminders", false, "/dashboard/meals"),
    STREAK_REMINDER("streak_reminder", "kikhabo_reminders", false, "/dashboard/home"),
    HEALTHY_MEAL("healthy_meal", "kikhabo_reminders", false, "/dashboard/recipes"),
    ACHIEVEMENT("achievement", "kikhabo_gamification", true, "/dashboard/profile"),
    POINTS_EARNED("points_earned", "kikhabo_gamification", true, "/dashboard/statistics"),
    CHALLENGE("challenge", "kikhabo_gamification", true, "/dashboard/statistics"),
    FAMILY_ACTIVITY("family_activity", "kikhabo_reminders", false, "/dashboard/family");

    private final String value;
    private final String channelId;
    private final boolean highPriority;
    private final String defaultRoute;

    NotificationType(String value, String channelId, boolean highPriority, String defaultRoute) {
        this.value = value;
        this.channelId = channelId;
        this.highPriority = highPriority;
        this.defaultRoute = defaultRoute;
    }

}
