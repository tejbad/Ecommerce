package com.ecommerce.product_service.model;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "products")
@Getter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private Double price;
    private Integer stock;


}