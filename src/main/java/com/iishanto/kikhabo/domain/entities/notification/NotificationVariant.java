package com.iishanto.kikhabo.domain.entities.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationVariant {
    private String audience; // "female", "male", "general"
    private String title;
    private String body;
    private String extra;
    private String imageUrl;
}
