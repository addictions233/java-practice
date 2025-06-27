package com.one.el;

import lombok.Data;
import org.springframework.context.annotation.Primary;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import javax.annotation.sql.DataSourceDefinition;

/**
 * @description: 使用spring el获取某个对象的属性值
 * @author: wanjunjie
 * @date: 2024/04/30
 */
public class PropertyParseUtil {

    private static  ExpressionParser parser = new SpelExpressionParser();

    public static Object getProperty(Object target, String propertyName) {
        EvaluationContext context = new StandardEvaluationContext();
        context.setVariable("model", target);
        // TemplateParserContext用来解析#{}
        Expression expression = parser.parseExpression(String.format("#{#model.%s}", propertyName), new TemplateParserContext());
        return expression.getValue(context, Object.class);
    }

    public static void main(String[] args) {
        Order order = new Order();
        order.setProductName("电脑");
        order.setOrderId(10001);

        System.out.println(getProperty(order,"productName"));
    }


    @Data
    static class Order {
        private String productName;

        private Integer orderId;
    }
}
