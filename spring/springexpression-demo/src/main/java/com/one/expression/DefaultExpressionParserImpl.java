package com.one.expression;

import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author one
 * EL表达式解析器
 */
public class DefaultExpressionParserImpl implements ExpressionParser {


    private final SpelExpressionParser parser = new SpelExpressionParser();
    /**
     * 缓存expression表达式对象
     */
    private final Map<ExpressionKey, Expression> keyCache = new ConcurrentHashMap<>(64);
    /**
     * 对于固定值的expression表达式解析结果进行缓存
     */
    private final Map<ExpressionKey, Object> valueCache = new ConcurrentHashMap<>(64);

    /**
     * 执行EL表达式解析
     *
     * @param expressionMetadata key操作元数据
     * @param expression         表达式
     * @return SpEl表达式解析后的值
     */
    @Override
    @Nullable
    public Object parserExpression(ExpressionMetadata expressionMetadata, String expression) {
        ExpressionEvaluationContext expressionEvaluationContext = ExpressionEvaluationContext.createEvaluationContext(expressionMetadata);
        Expression ex = getExpression(keyCache, expressionMetadata.getMethodKey(), expression);
        return ex.getValue(expressionEvaluationContext);
    }


    /**
     * 解析固定值的表达式，解析后的值会被缓存下来
     *
     * @param expressionMetadata 表达式元数据
     * @param expression         表达式
     * @return 字符串类型
     */
    @Override
    public String parserFixExpressionForString(ExpressionMetadata expressionMetadata, String expression) {
        Assert.hasText(expression, "expression不能为空");
        ExpressionKey expressionKey = createKey(expressionMetadata.getMethodKey(), expression);
        Object value = valueCache.get(expressionKey);
        if (value == null) {
            ExpressionEvaluationContext expressionEvaluationContext = ExpressionEvaluationContext.createEvaluationContext(expressionMetadata);
            Expression ex = getExpression(keyCache, expressionKey);
            value = Objects.requireNonNull(ex.getValue(expressionEvaluationContext));
            valueCache.putIfAbsent(expressionKey, value);
        }
        return value.toString();
    }


    protected Expression getExpression(Map<ExpressionKey, Expression> cache,
                                       ExpressionKey expressionKey) {
        Expression expr = cache.get(expressionKey);
        if (expr == null) {
            expr = parser.parseExpression(expressionKey.expression);
            cache.putIfAbsent(expressionKey, expr);
        }
        return expr;
    }

    protected Expression getExpression(Map<ExpressionKey, Expression> cache,
                                       AnnotatedElementKey elementKey, String expression) {
        ExpressionKey expressionKey = createKey(elementKey, expression);
        return getExpression(cache, expressionKey);
    }

    private ExpressionKey createKey(AnnotatedElementKey elementKey, String expression) {
        return new ExpressionKey(elementKey, expression);
    }


    static class ExpressionKey implements Comparable<ExpressionKey> {

        private final AnnotatedElementKey element;

        private final String expression;

        protected ExpressionKey(AnnotatedElementKey element, String expression) {
            Assert.notNull(element, "AnnotatedElementKey must not be null");
            Assert.notNull(expression, "Expression must not be null");
            this.element = element;
            this.expression = expression;
        }

        @Override
        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof ExpressionKey)) {
                return false;
            }
            ExpressionKey otherKey = (ExpressionKey) other;
            return (this.element.equals(otherKey.element) &&
                    ObjectUtils.nullSafeEquals(this.expression, otherKey.expression));
        }

        @Override
        public int hashCode() {
            return this.element.hashCode() * 29 + this.expression.hashCode();
        }

        @Override
        public String toString() {
            return this.element + " with expression \"" + this.expression + "\"";
        }

        @Override
        public int compareTo(ExpressionKey other) {
            int result = this.element.toString().compareTo(other.element.toString());
            if (result == 0) {
                result = this.expression.compareTo(other.expression);
            }
            return result;
        }
    }
}
