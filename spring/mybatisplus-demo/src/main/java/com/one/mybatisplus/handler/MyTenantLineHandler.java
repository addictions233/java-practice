package com.one.mybatisplus.handler;

import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;

/**
 * 实现多租户需要重新的Handler
 *
 * @author one
 */
public class MyTenantLineHandler implements TenantLineHandler {

    /**
     * 返回租户ID的字段值
     * @return 租户ID的字段值
     */
    @Override
    public Expression getTenantId() {
        // 这里先写死, 真实场景要从ThreadLocal中获取
        return new StringValue("1111111");
    }

    /**
     * 返回租户ID的字段名称
     */
    @Override
    public String getTenantIdColumn() {
        return "tenant_id";
    }

    /**
     * 哪些表忽略多组
     * @param tableName 表名称
     * @return 忽略的表名称
     */
    @Override
    public boolean ignoreTable(String tableName) {
        return "ignore_table".equals(tableName);
    }
}
