package com.example.springreactprac.controller.api;

import com.example.springreactprac.model.Email;
import com.example.springreactprac.model.Order;
import com.example.springreactprac.service.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderRestController {

    private final OrderService orderService;

    public OrderRestController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/api/v1/orders")
    public Order createOrder(@RequestBody CreateOrderRequest orderRequest){
        return orderService.createOrder(
            new Email(orderRequest.email()), orderRequest.address(),
            orderRequest.postcode(), orderRequest.orderItems());
    }

}
