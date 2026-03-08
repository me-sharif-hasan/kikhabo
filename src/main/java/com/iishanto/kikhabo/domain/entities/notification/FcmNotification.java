package com.iishanto.kikhabo.domain.entities.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FcmNotification {

    private String title;
    private String body;
    private String imageUrl;
    private NotificationType type;
    private String route;
    private String extra;

    public String getEffectiveRoute() {
        if (route != null && !route.isBlank()) {
            return route;
        }
        return type.getDefaultRoute();
    }

    public String getEffectiveExtra() {
        if (extra != null && !extra.isBlank()) {
            return extra;
        }
        return "{}";
    }

    public String getChannelId() {
        return type.getChannelId();
    }
}
