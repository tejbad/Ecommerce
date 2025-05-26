package com.ecommerce.inventory_service.controller;

import com.ecommerce.inventory_service.model.Inventory;
import com.ecommerce.inventory_service.repository.InventoryRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryRepository repo;

    @Operation(summary = "Get all inventory")
    @GetMapping
    public List<Inventory> getAll() {
        return repo.findAll();
    }

    @Operation(summary = "Check if product is in stock")
    @GetMapping("/check")
    public boolean checkStock(@RequestParam("productId") Long productId, @RequestParam Integer qty) {
        return repo.findById(productId).map(i -> i.getStock() >= qty).orElse(false);
    }

    @Operation(summary = "Add or update inventory")
    @PostMapping
    public Inventory addOrUpdate(@RequestBody Inventory inventory) {
        return repo.save(inventory);
    }
}