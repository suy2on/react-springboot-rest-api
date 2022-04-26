package com.example.springreactprac.controller;

import com.example.springreactprac.model.Category;

public record CreateProductRequest(String productName, Category category, long price, String description) {

}
