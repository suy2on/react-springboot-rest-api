package com.example.springreactprac.service;

import com.example.springreactprac.model.Category;
import com.example.springreactprac.model.Product;
import java.util.List;

public interface ProductService {
    List<Product> getProductsByCategory(Category category);

    List<Product> getAllProducts();

    Product createProduct(String productName, Category category, long price);

    Product createProduct(String productName, Category category, long price, String description);


}
