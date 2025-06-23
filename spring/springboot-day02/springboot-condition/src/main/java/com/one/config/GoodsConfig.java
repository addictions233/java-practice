package com.one.config;

import com.one.condition.ConditionOnProperty;
import com.one.condition.PropertyCondition;
import com.one.domain.Goods;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName: GoodsConfig
 * @Description: 配置类创建bean对象
 * @Author: one
 * @Date: 2022/04/06
 */
@Configuration
public class GoodsConfig {
    @Value("${goods.name:huawei}")
    private String goodsName;

    @Bean("goods")
    @ConditionOnProperty(value = "goods.title") // 直接使用@Conditional注解存在硬编码问题,需要嵌套注解
//    @Conditional(PropertyCondition.class)  // 添加条件判断注解, 只有满足条件才会创建bean对象
    public Goods goods() {
        return new Goods(goodsName);
    }
}
