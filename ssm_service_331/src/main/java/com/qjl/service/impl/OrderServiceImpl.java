package com.qjl.service.impl;

import com.qjl.dao.OrderDao;
import com.qjl.domain.Order;
import com.qjl.service.OrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    OrderDao orderDao;

    @Override
    public List<Order> findAll() {
        return orderDao.findAll();
    }

    @Override
    public void save(Order order) {
        orderDao.save(order);
    }
}
