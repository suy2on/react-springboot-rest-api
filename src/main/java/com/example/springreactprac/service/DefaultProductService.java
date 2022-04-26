package com.example.springreactprac.service;

import com.example.springreactprac.model.Category;
import com.example.springreactprac.model.Product;
import com.example.springreactprac.repository.ProductRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class DefaultProductService implements
    ProductService {

    private final ProductRepository productRepository;

    public DefaultProductService(
        ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Override
    public List<Product> getProductsByCategory(Category category) {
        return productRepository.findByCategory(category);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product createProduct(String productName, Category category, long price) {
        Product product = new Product(UUID.randomUUID(), productName, category, price);
        return productRepository.insert(product);
    }

    @Override
    public Product createProduct(String productName, Category category, long price,
        String description) {
        Product product = new Product(UUID.randomUUID(), productName, category, price, description, LocalDateTime
            .now(), LocalDateTime.now());
        return productRepository.insert(product);
    }
}