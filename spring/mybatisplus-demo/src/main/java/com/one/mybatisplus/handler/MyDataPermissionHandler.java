package com.one.mybatisplus.handler;

import com.baomidou.mybatisplus.extension.plugins.handler.MultiDataPermissionHandler;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Table;

/**
 * 实现数据权限需要重写的Handler
 *
 * @author one
 */
public class MyDataPermissionHandler implements MultiDataPermissionHandler {

    @Override
    public Expression getSqlSegment(Table table, Expression where, String mappedStatementId) {
        try {
            // 数据权限相关的 SQL 片段
            String sqlSegment = "role = 'admin'";
            return CCJSqlParserUtil.parseCondExpression(sqlSegment);
        } catch (JSQLParserException e) {
            throw new RuntimeException(e);
        }
    }
}
