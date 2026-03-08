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
public class BroadcastNotifyDto {
    @NotNull
    private NotificationType type;
    @NotBlank
    private String title;
    @NotBlank
    private String body;
    private String imageUrl;
    private String route;
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
