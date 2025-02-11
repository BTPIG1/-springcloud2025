package com.atguigu.order.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component // 自动注入必须是容器里的
@ConfigurationProperties(prefix = "order") // 自动注入配置中心对应data ID中的配置内容：如order.timeout。！！注意：这里 prefix对应的是前缀，并且与使用@Value新加@RefreshScope不同，这里能自动刷新
@Data
public class OrderProperties {
    String timeout;
    String autoConfirm;
    String dbUrl;
}
