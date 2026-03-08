package com.iishanto.kikhabo.web.dto.notification;

import com.iishanto.kikhabo.domain.entities.notification.FcmNotification;
import com.iishanto.kikhabo.domain.entities.notification.NotificationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminNotifyDto {
    @NotNull
    private Long userId;
    @NotNull
    private NotificationType type;
    @NotBlank
    private String title;
    @NotBlank
    private String body;
    private String imageUrl;
    /** Override the default route for this notification type. Leave null to use the type default. */
    private String route;
    /** JSON-encoded extra data string, e.g. "{\"streak_days\": 5}". Leave null for empty. */
    private String extra;

    public FcmNotification toNotification() {
        return FcmNotification.builder()
                .type(type)
                .title(title)
                .body(body)
                .imageUrl(imageUrl)
                .route(route)
                .extra(extra)
                .build();
    }
}
