package com.ecommerce.notification_service.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderNotificationKafkaListener {

    @KafkaListener(topics = "order-events", groupId = "notification-group")
    public void consumeOrderEvent(String message) {
        // Simulating sending notification for now
        // Will complete later
        System.out.println("NOTIFICATION: " + message);
    }
}