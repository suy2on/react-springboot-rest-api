package com.example.springreactprac.service;

import com.example.springreactprac.model.Email;
import com.example.springreactprac.model.Order;
import com.example.springreactprac.model.OrderItem;
import java.util.List;

public interface OrderService {

    Order createOrder(Email email, String address, String postcode, List<OrderItem> orderItems);

}
