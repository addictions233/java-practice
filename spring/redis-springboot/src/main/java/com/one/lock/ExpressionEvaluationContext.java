package com.one.lock;


import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

import java.lang.reflect.Method;

/**
 * 缓存解析的context
 *
 * @author one
 */
public class ExpressionEvaluationContext extends MethodBasedEvaluationContext {

    public ExpressionEvaluationContext(Object rootObject, Method method, Object[] arguments, ParameterNameDiscoverer parameterNameDiscoverer) {
        super(rootObject, method, arguments, parameterNameDiscoverer);
    }

    public static ExpressionEvaluationContext createEvaluationContext(ExpressionMetadata metadata) {
        if (metadata.getEvaluationContext() != null) {
            return metadata.getEvaluationContext();
        }
        ExpressionRootObject rootObject = new ExpressionRootObject(metadata.getTargetMethod(), metadata.getArgs(), metadata.getTarget(), metadata.getTargetClass());
        ExpressionEvaluationContext expressionEvaluationContext = new ExpressionEvaluationContext(
                rootObject, metadata.getTargetMethod(), metadata.getArgs(), new DefaultParameterNameDiscoverer());
        expressionEvaluationContext.setVariables(metadata.getVariables());
        return expressionEvaluationContext;
    }


}
