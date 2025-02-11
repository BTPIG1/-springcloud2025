package com.atguigu.order;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;

@SpringBootTest
public class LBTest {

//    自带负载均衡算法
    @Autowired
    LoadBalancerClient loadBalancerClient;

    @Test
    void test(){
        ServiceInstance choose = loadBalancerClient.choose("service-product");
        System.out.println("choose = "+choose.getHost()+","+choose.getPort());
    }

}
