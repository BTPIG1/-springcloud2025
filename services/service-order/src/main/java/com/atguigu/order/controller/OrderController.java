package com.atguigu.order.controller;

import com.atguigu.order.bean.Order;
import com.atguigu.order.properties.OrderProperties;
import com.atguigu.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//@RefreshScope     因为使用的是注入注解
@RestController
public class OrderController {

    @Autowired
    OrderService orderService;

//    @Value("${order.timeout}")
//    String timeout;
//    @Value("${order.auto-confirm}")
//    String autoConfirm;
    @Autowired
    OrderProperties orderProperties;

    @GetMapping("/config")
    public String config(){
        return orderProperties.getTimeout() + orderProperties.getAutoConfirm() + orderProperties.getDbUrl();
    }

    @GetMapping("/create")
    public Order createOrder(@RequestParam("productId") Long productId,
                             @RequestParam("userId") Long userId){
        Order order = orderService.createOrder(productId,userId);
        return order;
    }

    @GetMapping("/createByOF")
    public Order createOrderByOF(@RequestParam("productId") Long productId,
                             @RequestParam("userId") Long userId){
        Order order = orderService.createOrderByFeign(productId,userId);
        return order;
    }

}
