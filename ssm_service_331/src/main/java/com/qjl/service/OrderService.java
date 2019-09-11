package com.qjl.service;

import com.qjl.domain.Order;

import java.util.List;

public interface OrderService {
    List<Order> findAll();

    void save(Order order);
}
