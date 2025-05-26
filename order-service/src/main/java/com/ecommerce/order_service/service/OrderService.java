package com.ecommerce.order_service.service;

import com.ecommerce.order_service.model.Order;
import com.ecommerce.order_service.dto.OrderRequest;
import com.ecommerce.order_service.respository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepo;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final WebClient.Builder webClientBuilder;

    @Value("${product.service.url:http://localhost:8083}")
    private String productServiceUrl;

    public Order placeOrder(OrderRequest request) {
        // Check stock from inventory-service via WebClient
        Boolean inStock = webClientBuilder.build()
                .get()
                .uri(productServiceUrl+"/products/{productId}/availability?quantity={qty}",
                        Map.of("productId", request.getProductId(), "qty", request.getQuantity()))
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();

        Order order = new Order();
        order.setProductId(request.getProductId());
        order.setQuantity(request.getQuantity());

        if (inStock != null && inStock) {
            order.setStatus(Order.Status.PLACED);
            order.setTotalPrice(fetchProductPrice(request.getProductId()) * request.getQuantity());
            orderRepo.save(order);
            kafkaTemplate.send("order-events", "Order placed: " + order.getId());
            // Notify inventory to decrement
            kafkaTemplate.send("inventory-events", order.getProductId() + ":" + order.getQuantity());
        } else {
            order.setStatus(Order.Status.REJECTED);
            order.setTotalPrice(0.0);
            orderRepo.save(order);
        }
        return order;
    }

    public List<Order> getAll() {
        return orderRepo.findAll();
    }

    private Double fetchProductPrice(Long productId) {
        // Call product-service for price
        Map res = webClientBuilder.build()
                .get()
                .uri("http://localhost:8081/products/{id}", productId)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
        return Double.valueOf(String.valueOf(res.get("price")));
    }
}