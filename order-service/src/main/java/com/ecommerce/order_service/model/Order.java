package com.ecommerce.order_service.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "`order`")
public class Order {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;
    private Integer quantity;
    private Double totalPrice;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        PLACED, REJECTED
    }
}