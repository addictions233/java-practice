package com.one;

import org.junit.Test;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParserContext;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * @description: TODO
 * @author: wanjunjie
 * @date: 2024/11/05
 */
public class SpringELTest {

    @Test
    public void SpELBaseTest() {
        // 创建一个模板表达式,就是一个带字面量和表达式的字符串, '#{'和'}'分别表示表达式的起始和终止, '#name'是一个表达式, 表示引用一个变量
        String expressionStr = "hello, #{#name}";
        // 创建一个表达式解析器, SpEL表达式解析器用Spring提供的SpelExpressionParser, 当然我们也可以自定义表达式解析器
        ExpressionParser expressionParser = new SpelExpressionParser();
        // 解析器规则, 默认的解析器模板TemplateParserContext, 以'#{'开头, '}'结尾, 且作为模板
        ParserContext parserContext = new TemplateParserContext();
        // 解析器解析字符串表达式生成表达式结果
        Expression expression = expressionParser.parseExpression(expressionStr, parserContext);
        // 估值上下文,可以设置环境变量, 环境变量可设置多个值：比如BeanFactoryResolver、PropertyAccessor、TypeLocator等
        EvaluationContext evaluationContext = new StandardEvaluationContext();
        evaluationContext.setVariable("name", "张三");
        // 表达式取值,可以从估值上下文中拿值
        String value = expression.getValue(evaluationContext, String.class);
        System.out.println(value); // 输出hello, 张三
    }
}
