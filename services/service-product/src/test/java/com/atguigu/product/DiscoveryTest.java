package com.atguigu.product;

import com.alibaba.cloud.nacos.discovery.NacosServiceDiscovery;
import com.alibaba.nacos.api.exception.NacosException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.discovery.DiscoveryClient;

@SpringBootTest
public class DiscoveryTest {
/*
* 仅做了解，实际开发服务发现会被封装为一个自动化的过程
* */


    @Autowired
    DiscoveryClient discoveryClient;

    @Autowired
    NacosServiceDiscovery nacosServiceDiscovery;

    @Test
    void nacosServiceDiscoveryTest() throws NacosException {
        for (String service : nacosServiceDiscovery.getServices()) {
//            打印微服务名
            System.out.println("service:" + service);
//            获取微服务实例
            nacosServiceDiscovery.getInstances(service).forEach(i -> {
                System.out.println("ip:"+i.getHost() + "port="+i.getPort());
            });
        }
    }

    @Test
    void discoveryTest(){
        for (String service : discoveryClient.getServices()) {
//            打印微服务名
            System.out.println("service:" + service);
//            获取微服务实例
            discoveryClient.getInstances(service).forEach(i -> {
                System.out.println("ip:"+i.getHost() + "port="+i.getPort());
            });
        }
    }
}
