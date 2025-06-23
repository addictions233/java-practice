package com.one.expression;

/**
 * @author one
 */
public interface ExpressionParser {


    /**
     * 执行EL表达式解析
     *
     * @param expressionMetadata key操作元数据
     * @param expression         表达式
     * @return SpEl表达式解析后的值
     */
    Object parserExpression(ExpressionMetadata expressionMetadata, String expression);


    /**
     * 解析固定值的表达式，解析后的值会被缓存下来
     *
     * @param expressionMetadata 表达式元数据
     * @param expression         表达式
     * @return 字符串类型
     */
    String parserFixExpressionForString(ExpressionMetadata expressionMetadata, String expression);


}
