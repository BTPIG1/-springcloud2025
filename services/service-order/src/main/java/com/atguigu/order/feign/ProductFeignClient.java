package com.atguigu.order.feign;

import com.atguigu.order.feign.fallback.ProductFeignClientFallback;
import com.atguigu.product.bean.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

/*
* 以前实现远程调用需要服务发现找到实例后再负载均衡发送，现在应该都让feign客户端实现
* */
@FeignClient(value = "service-product" , fallback = ProductFeignClientFallback.class) //  指定需要远程调用的微服务,fallback指定兜底实现类
public interface ProductFeignClient {

    /*
    mvc注解的两套使用逻辑
    1. 标注在Controller上是接受请求
    2. 标注在FeignClient上是发送请求
    */
    @GetMapping("/product/{id}")
    Product getProductBtId(@PathVariable("id") Long id);
}
