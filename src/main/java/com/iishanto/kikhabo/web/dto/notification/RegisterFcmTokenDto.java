package com.iishanto.kikhabo.web.dto.notification;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterFcmTokenDto {

    @NotBlank
    private String fcmToken;

    private String platform;
}
