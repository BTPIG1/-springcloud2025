package com.atguigu.order.service.impl;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;


import com.alibaba.cloud.nacos.annotation.NacosConfig;
import com.atguigu.order.bean.Order;
import com.atguigu.order.feign.ProductFeignClient;
import com.atguigu.order.service.OrderService;
import com.atguigu.product.bean.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    DiscoveryClient discoveryClient;

    @Autowired
    LoadBalancerClient loadBalancerClient;

    @Autowired
    ProductFeignClient productFeignClient;

    @Autowired
    RestTemplate restTemplate;

    @Override
    public Order createOrder(Long productId, Long userId) {
        Product product = getProductFromRemoteByZJ(productId);
        Order order = new Order();
        order.setId(1L);
        order.setTotalAmount(product.getPrice().multiply(new BigDecimal(product.getNum())));
        order.setUserId(userId);
        order.setNickName("张三");
        order.setAddress("尚硅谷");
        order.setProductList(Arrays.asList(product));

        return order;
    }

    @Override
    public Order createOrderByFeign(Long productId, Long userId) {
        log.info("order request.........");
        Product product = productFeignClient.getProductBtId(productId); // 不需要自定义方法获取实例的请求url
        Order order = new Order();
        order.setId(1L);
        order.setTotalAmount(product.getPrice().multiply(new BigDecimal(product.getNum())));
        order.setUserId(userId);
        order.setNickName("张三");
        order.setAddress("尚硅谷");
        order.setProductList(Arrays.asList(product));

        return order;
    }

    //  进阶1：获取所有实例，可以自定义负载均衡算法
    private Product getProductFromRemote(Long productId){
//        1. 获取到商品服务所有的机器ip+port
        List<ServiceInstance> instances = discoveryClient.getInstances("service-product");
        ServiceInstance serviceInstance = instances.get(0);
        String url = "http://"+ serviceInstance.getHost() + ":" + serviceInstance.getPort()+"/product/"+productId;
        log.info("远程请求路径：{}",url);
        //2. 远程发送请求
        Product product = restTemplate.getForObject(url, Product.class);
        return product;
    }

//  进阶2：自带负载均衡算法
    private Product getProductFromRemoteByLB(Long productId){
        //        1. 获取到商品服务所有的机器ip+port
        ServiceInstance instance = loadBalancerClient.choose("service-product");
        String url = "http://"+ instance.getHost() + ":" + instance.getPort()+"/product/"+productId;
        log.info("远程请求路径：{}",url);
        //2. 远程发送请求
        Product product = restTemplate.getForObject(url, Product.class);
        return product;
    }

//  进阶3：基于注解的负载均衡：在RestTemplate注入时加入注解@loadbalanced。 第一次调用流程：1.从url中找到微服务名，2.请求注册中心服务发现，3.保存到缓存（动态更新）并使用负载均衡算法选择实例，4.远程调用。 后续访问：直接访问缓存中的实例
//  面试题：如果注册中心宕机了，还能远程调用吗？ 注册中心宕机意味着无法进行服务发现和注册，前面两种无法调用，但是如果使用注解方式有可能，因为其底层使用缓存机制，即如果之前调用过，会保存下来，后续之间访问缓存。

    private Product getProductFromRemoteByZJ(Long productId){
        //        1. 获取到商品服务所有的机器ip+port
        String url = "http://service-product/product/"+productId;
        //2. 远程发送请求service-product会被动态替换
        Product product = restTemplate.getForObject(url, Product.class);
        return product;
    }

//    获取配置中心控制台中的配置


}
