package com.ecommerce.product_service.service;

import com.ecommerce.product_service.model.Product;
import com.ecommerce.product_service.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public boolean isProductAvailable(Long productId) {
        return productRepository.findById(productId)
                .map(product -> product.getStock() != null && product.getStock() > 0)
                .orElse(false);
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

}