package com.iishanto.kikhabo.web.controller.v1;

import com.iishanto.kikhabo.domain.usercase.notification.RegisterFcmTokenCommand;
import com.iishanto.kikhabo.domain.usercase.notification.RegisterFcmTokenUseCase;
import com.iishanto.kikhabo.domain.usercase.notification.RemoveFcmTokenUseCase;
import com.iishanto.kikhabo.web.dto.notification.RegisterFcmTokenDto;
import com.iishanto.kikhabo.web.dto.notification.RemoveFcmTokenDto;
import com.iishanto.kikhabo.web.response.SuccessResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@Tag(name = "FCM Token", description = "Register and remove FCM device tokens for push notifications")
@RestController
@RequestMapping("api/v1/user/fcm-token")
public class FcmTokenController {

    RegisterFcmTokenUseCase registerFcmTokenUseCase;
    RemoveFcmTokenUseCase removeFcmTokenUseCase;

    @PostMapping
    public ResponseEntity<SuccessResponse<Void>> register(@Valid @RequestBody RegisterFcmTokenDto dto) throws Exception {
        registerFcmTokenUseCase.execute(new RegisterFcmTokenCommand(dto.getFcmToken(), dto.getPlatform()));
        return new ResponseEntity<>(new SuccessResponse<>("success", "FCM token registered"), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<SuccessResponse<Void>> remove(@Valid @RequestBody RemoveFcmTokenDto dto) throws Exception {
        removeFcmTokenUseCase.execute(dto.getFcmToken());
        return new ResponseEntity<>(new SuccessResponse<>("success", "FCM token removed"), HttpStatus.OK);
    }
}
