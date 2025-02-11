package com.atguigu.order.feign.fallback;
import java.math.BigDecimal;

import com.atguigu.order.feign.ProductFeignClient;
import com.atguigu.product.bean.Product;
import org.springframework.stereotype.Component;

// 需要为哪个feignClient代理兜底就实现哪个接口
@Component
public class ProductFeignClientFallback implements ProductFeignClient {
    @Override
    public Product getProductBtId(Long id) {
        System.out.println("返回兜底数据。。。。。。。。。。。。。。。");
        Product product = new Product();
        product.setId(id);
        product.setPrice(new BigDecimal("0"));
        product.setProductName("位置商品");
        product.setNum(0);
        return product;
    }
}
