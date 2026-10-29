package com.one;

import com.one.expression.DefaultExpressionParserImpl;
import com.one.expression.ExpressionParser;
import org.junit.jupiter.api.Test;

/**
 * @description: TODO
 * @author: wanjunjie
 * @date: 2024/11/05
 */
public class ExpressionParseTest {

    @Test
    public void expressionParserTest() {
        String expressionStr = "hello, #{#name}";
        ExpressionParser expressionParser = new DefaultExpressionParserImpl();
    }
}
