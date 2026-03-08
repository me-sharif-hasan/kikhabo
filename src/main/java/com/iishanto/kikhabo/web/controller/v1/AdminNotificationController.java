package com.iishanto.kikhabo.web.controller.v1;

import com.iishanto.kikhabo.domain.datasource.NotificationDataSource;
import com.iishanto.kikhabo.infrastructure.services.storage.S3Service;
import com.iishanto.kikhabo.web.dto.notification.AdminNotifyDto;
import com.iishanto.kikhabo.web.response.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import com.iishanto.kikhabo.web.dto.notification.BroadcastNotifyDto;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/admin")
@Tag(name = "Admin", description = "Internal admin endpoints — requires X-Api-Key header.")
public class AdminNotificationController {

    private final NotificationDataSource notificationDataSource;
    private final S3Service s3Service;

    @PostMapping("/notify")
    @Operation(
            summary = "Send a push notification (JSON)",
            description = "Sends an FCM push notification. Set imageUrl manually in the request body. " +
                    "Requires the master API key in the X-Api-Key header."
    )
    public ResponseEntity<SuccessResponse<Void>> notify(@Valid @RequestBody AdminNotifyDto dto) {
        notificationDataSource.sendToUser(dto.getUserId(), dto.toNotification());
        return new ResponseEntity<>(new SuccessResponse<>("success", "Notification dispatched"), HttpStatus.OK);
    }

    @PostMapping("/broadcast")
    @Operation(
            summary = "Broadcast a push notification to ALL users",
            description = "Sends an FCM notification to every user who has a registered device token. " +
                    "Requires the master API key in the X-Api-Key header."
    )
    public ResponseEntity<SuccessResponse<Void>> broadcast(@Valid @RequestBody BroadcastNotifyDto dto) {
        notificationDataSource.broadcastToAll(dto.toNotification());
        return new ResponseEntity<>(new SuccessResponse<>("success", "Broadcast dispatched"), HttpStatus.OK);
    }

    @PostMapping(value = "/notify", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Send a push notification with image upload (multipart)",
            description = "Uploads the image to S3 and uses the public URL as the notification imageUrl. " +
                    "All other fields are the same as the JSON endpoint. " +
                    "Requires the master API key in the X-Api-Key header."
    )
    public ResponseEntity<SuccessResponse<Void>> notifyWithImage(
            @Valid @ModelAttribute AdminNotifyDto dto,
            @RequestPart(value = "image", required = false) MultipartFile image) throws Exception {
        if (image != null && !image.isEmpty()) {
            String imageUrl = s3Service.uploadFile(image, "notification-images");
            dto.setImageUrl(imageUrl);
        }
        notificationDataSource.sendToUser(dto.getUserId(), dto.toNotification());
        return new ResponseEntity<>(new SuccessResponse<>("success", "Notification dispatched"), HttpStatus.OK);
    }
}
