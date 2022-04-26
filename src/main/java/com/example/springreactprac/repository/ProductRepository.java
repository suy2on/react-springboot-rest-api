package com.example.springreactprac.repository;

import com.example.springreactprac.model.Category;
import com.example.springreactprac.model.Product;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.swing.text.html.Option;

public interface ProductRepository {

    List<Product> findAll();

    Product insert(Product product);

    Product update(Product product);

    Optional<Product> findById(UUID productId);

    Optional<Product> findByName(String productName);

    List<Product> findByCategory(Category category);

    void deleteAll();



}
