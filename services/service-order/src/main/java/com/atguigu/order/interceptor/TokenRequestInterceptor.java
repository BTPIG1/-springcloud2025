package com.atguigu.order.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TokenRequestInterceptor implements RequestInterceptor {

    /*
    * 请求拦截器
    * @param requestTemplate 这里是！！请求模板，可以做增加请求头、body、查询等等操作
    * 调用方法：1.修改feign配置文件，太冗余每个都需要重新弄。（推荐）2.之前提到过8大openfeign提供的类，这里只需要加入到spring容器中，每次请求都会自动调用。@Component
    * */
    @Override
    public void apply(RequestTemplate requestTemplate) {
        System.out.println("requestTemplate..............");
        requestTemplate.header("Token", UUID.randomUUID().toString());
    }
}
