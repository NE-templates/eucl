package com.trex.eucl.controllers;

import com.trex.eucl.services.impl.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Notifications", description = "Endpoints related to email notifications")
@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @Operation(
            summary = "Send token expiry warning emails",
            description = "Triggers email notifications for users whose tokens are about to expire"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Notifications processed and emails sent successfully"
    )
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/send-expiry-warnings")
    public ResponseEntity<String> sendTokenExpiryNotifications() {
        notificationService.generateExpiryNotifications();
        return ResponseEntity.ok("Notifications processed and emails sent");
    }

}
