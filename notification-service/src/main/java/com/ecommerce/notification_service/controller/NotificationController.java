package com.ecommerce.notification_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController {

    @Operation(summary = "Health check")
    @GetMapping("/notifications/health")
    public String health() {
        return "Notification Service is up";
    }
}