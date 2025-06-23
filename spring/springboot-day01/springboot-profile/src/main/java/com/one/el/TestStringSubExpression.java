package com.one.el;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * @description: 使用spring el截取字符串
 * @author: wanjunjie
 * @date: 2024/04/30
 */
public class TestStringSubExpression {

//    public static void main(String[] args) {
//        String target = "'hello world'.substring(2,6)";
//        // 执行解析器为SpelExpressionParser
//        ExpressionParser parser = new SpelExpressionParser();
//        // 解析表达式
//        Expression expression = parser.parseExpression(target);
//        // 设置对象模型基础
//        EvaluationContext context = new StandardEvaluationContext(expression);
//        System.out.println(expression.getValue(context));
//    }

    public static void main(String[] args) {
        String target = "'hello word'.substring(#start, #end)";
        ExpressionParser parser = new SpelExpressionParser();
        // 使用context动态传入参数
        EvaluationContext context = new StandardEvaluationContext();
        context.setVariable("start", 2);
        context.setVariable("end", 6);
        Expression expression = parser.parseExpression(target);
        System.out.println(expression.getValue(context));
    }
}
