package com.atguigu.order;


import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Slf4j
@EnableFeignClients // 开启feign远程调用
@EnableDiscoveryClient
@SpringBootApplication
public class OrderMainApplication {
    public static void main(String[] args) {
        try {
            SpringApplication.run(OrderMainApplication.class, args);
            System.out.println("Server startup done.");
        }catch (Exception e){
            log.error("服务xxx-support启动报错", e);
        }
    }

    /*
    * 编码方式实现配置文件的动态刷新
    * 1. 启动类已启动就开启配置监听  加上Bean注解，程序启动时会自动调用方法，并且方法参数会从容器中直接拿取
    * 2. 获取变化值
    * 3. 发送邮件
    * */
    @Bean
    ApplicationRunner applicationRunner(NacosConfigManager nacosConfigManager){
        return args -> {
            ConfigService configService = nacosConfigManager.getConfigService();
            configService.addListener("service-order.properties", "DEFAULT_GROUP",  // data ID,group,监听配置文件变化的Listener类
                    new Listener() {
                        @Override
                        public Executor getExecutor() {
                            return Executors.newFixedThreadPool(4);
                        }

                        @Override
                        public void receiveConfigInfo(String s) {
                            System.out.println("变化的配置信息：" + s);
                            System.out.println("发送邮件....");
                        }
                    });
        };
    }
}
