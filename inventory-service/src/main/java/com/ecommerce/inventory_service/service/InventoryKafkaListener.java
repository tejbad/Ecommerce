package com.ecommerce.inventory_service.service;

import com.ecommerce.inventory_service.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryKafkaListener {
    private final InventoryRepository repo;

    @KafkaListener(topics = "inventory-events", groupId = "inventory-group")
    public void handleInventoryEvent(String message) {
        // Message like "productId:qty"
        String[] parts = message.split(":");
        Long productId = Long.valueOf(parts[0]);
        Integer qty = Integer.valueOf(parts[1]);
        repo.findById(productId).ifPresent(inv -> {
            inv.setStock(inv.getStock() - qty);
            repo.save(inv);
        });
    }
}