package com.iishanto.kikhabo.domain.usercase.notification;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterFcmTokenCommand {

    private String fcmToken;
    private String platform;
}
