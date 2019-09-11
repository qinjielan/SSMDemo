package com.qjl.controller;


import com.qjl.domain.Order;
import com.qjl.domain.Product;
import com.qjl.service.OrderService;
import com.qjl.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Resource
    OrderService orderService;
    @Resource
    ProductService productService;

    @RequestMapping("/findAll")
    public ModelAndView findAll(){
        //查询所有订单数据
        List<Order> orderList = orderService.findAll();
        //创建ModelAndView对象
        ModelAndView modelAndView = new ModelAndView();
        //添加数据
        modelAndView.addObject("orderList",orderList);
        //指定页面
        modelAndView.setViewName("order-list");
        return modelAndView;
    }

    @RequestMapping("/addUI")
    public ModelAndView addUI(){
        List<Product> productList = productService.findAll();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("productList",productList);
        modelAndView.setViewName("order-add");
        return modelAndView;
    }

    @RequestMapping("/save")
    public String save(Order order){
        orderService.save(order);
        return "redirect:/order/findAll";
    }
}
