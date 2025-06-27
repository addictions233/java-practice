package com.one.el;

import org.springframework.expression.ParserContext;

/**
 * @description: SpringEL标准的分界符是 '#{' 和 '}', 我们可以自定义分界符
 * @author: wanjunjie
 * @date: 2024/04/30
 */
public class customParseContext {

    public static void main(String[] args) {
        ParserContext parserContext = new ParserContext() {
            @Override
            public boolean isTemplate() {
                return true;
            }

            @Override
            public String getExpressionPrefix() {
                return "[";
            }

            @Override
            public String getExpressionSuffix() {
                return "]";
            }
        };

    }
}
