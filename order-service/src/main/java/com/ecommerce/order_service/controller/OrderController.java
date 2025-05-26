package com.ecommerce.order_service.controller;

import com.ecommerce.order_service.model.Order;
import com.ecommerce.order_service.service.OrderService;
import com.ecommerce.order_service.dto.OrderRequest;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "Place a new order")
    @PostMapping
    public Order placeOrder(@RequestBody OrderRequest request) {
        return orderService.placeOrder(request);
    }

    @Operation(summary = "Get all orders")
    @GetMapping
    public List<Order> getAll() {
        return orderService.getAll();
    }
}
