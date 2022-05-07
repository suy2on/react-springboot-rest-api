package com.example.springreactprac.service;

import com.example.springreactprac.model.Email;
import com.example.springreactprac.model.Order;
import com.example.springreactprac.model.OrderItem;
import com.example.springreactprac.model.OrderStatus;
import com.example.springreactprac.repository.OrderRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;


@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order createOrder(Email email, String address, String postcode,
        List<OrderItem> orderItems) {
        Order order = new Order(UUID.randomUUID(), email, address, postcode, orderItems,
            OrderStatus.ACCEPTED, LocalDateTime.now(), LocalDateTime.now());
        return orderRepository.insert(order);
    }
}
