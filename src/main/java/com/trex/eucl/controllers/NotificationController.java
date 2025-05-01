package com.trex.eucl.controllers;

import com.trex.eucl.services.impl.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/send-expiry-warnings")
    public ResponseEntity<String> sendTokenExpiryNotifications() {
        notificationService.generateExpiryNotifications();
        return ResponseEntity.ok("Notifications processed and emails sent");
    }

}
