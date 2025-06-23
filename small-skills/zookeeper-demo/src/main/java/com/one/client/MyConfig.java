package com.one.client;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 使用zookeeper模拟配置中心, 一个节点最多存储1M数据, 通常用来存储元数据
 */
@Data
@ToString
@NoArgsConstructor
public class MyConfig {
    /**
     * 配置数据的key
     */
    private String key;

    /**
     * 配置数据的value
     */
    private String value;
}
