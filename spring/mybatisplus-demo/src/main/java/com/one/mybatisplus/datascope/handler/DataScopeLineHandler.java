package com.one.mybatisplus.datascope.handler;

import com.baomidou.mybatisplus.extension.plugins.handler.DataPermissionHandler;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;

/**
 * @author one
 *
 * @Description 数据权限处理器: 自定义数据权限处理器, 区别于框架提供的 {@link DataPermissionHandler}
 */
public interface DataScopeLineHandler {

    /**
     * 获取数据权限值表达式
     * @return 数据权限值表达式
     */
    ExpressionList getDataSecurityList();

    /**
     * 获取数据权限字段名
     * @return 数据权限字段名
     */
    String getDataSecurityColumn();

    /**
     * 判断该表是否是需要拦截的表
     * @param tableName 表名
     * @return 是否忽略, true:表示忽略，false:需要解析并拼接数据权限条件
     */
    boolean isInterceptTable(String tableName);


}
