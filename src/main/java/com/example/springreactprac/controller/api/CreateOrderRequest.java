package com.example.springreactprac.controller.api;

import com.example.springreactprac.model.OrderItem;
import java.util.List;

public record CreateOrderRequest(String email, String address, String postcode, List<OrderItem> orderItems) {

}
