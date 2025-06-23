package com.one.expression;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: 配置ExpressionParser
 * @author: wanjunjie
 * @date: 2024/11/04
 */
@Configuration
public class ExpressionConfig {

    @Bean
    @ConditionalOnMissingBean(ExpressionParser.class)
    public ExpressionParser expressionParser() {
        return new DefaultExpressionParserImpl();
    }
}
