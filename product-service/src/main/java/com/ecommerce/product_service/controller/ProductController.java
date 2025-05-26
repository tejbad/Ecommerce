package com.ecommerce.product_service.controller;


import com.ecommerce.product_service.model.Product;
import com.ecommerce.product_service.repository.ProductRepository;
import com.ecommerce.product_service.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    @Autowired
    private final ProductService productService;

    @Operation(summary = "Get all products")
    @GetMapping
    public List<Product> getAll() {

        return productService.getAllProducts();
    }

    @Operation(summary = "Create product")
    @PostMapping
    public Product create(@RequestBody Product product) {

        return productService.createProduct(product);
    }

//    @Operation(summary = "Get product by id")
//    @GetMapping("/{id}")
//    public Product get(@PathVariable("id") Long id) {
//        return productService.getProductById(id).orElseThrow();
//    }

    @GetMapping("/{id}/availability")
    public ResponseEntity<?> checkAvailability(@PathVariable("id") Long id, @RequestParam("quantity") Integer quantity) {
        boolean available = productService.isProductAvailable(id,quantity);
        return ResponseEntity.ok().body(available);
    }

    @Operation(summary = "Get product by id")
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long id) {
        Optional<Product> product = productService.getProductById(id);
        return product.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}