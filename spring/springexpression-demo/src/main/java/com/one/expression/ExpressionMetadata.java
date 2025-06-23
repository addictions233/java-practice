package com.one.expression;

import lombok.Getter;
import lombok.Setter;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.expression.AnnotatedElementKey;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * @author one
 * EL表达式元数据
 */
@Getter
@Setter
public class ExpressionMetadata {
    private final Class<?> targetClass;
    private final Method targetMethod;
    private final Object[] args;
    private final Object target;
    private final AnnotatedElementKey methodKey;
    private ExpressionEvaluationContext expressionEvaluationContext;
    private final Map<String, Object> variables = new HashMap<>(2);


    public ExpressionMetadata(Object target, Method method, Object[] args) {
        this.target = target;
        // 获取代理对象对应的字节码对象
        this.targetClass = AopProxyUtils.ultimateTargetClass(target);
        // 获取方法对象
        this.targetMethod = (!Proxy.isProxyClass(targetClass) ?
                AopUtils.getMostSpecificMethod(method, targetClass) : method);
        this.args = args;
        this.methodKey = new AnnotatedElementKey(this.targetMethod, targetClass);
    }

    public ExpressionMetadata setEvaluationContext(ExpressionEvaluationContext expressionEvaluationContext) {
        this.expressionEvaluationContext = expressionEvaluationContext;
        return this;
    }

    public ExpressionEvaluationContext getEvaluationContext() {
        return expressionEvaluationContext;
    }

}
