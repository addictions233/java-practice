package com.one.mybatisplus.datascope.handler.impl;

import com.baomidou.mybatisplus.extension.plugins.handler.DataPermissionHandler;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;

/**
 * @description: TODO
 * @author: wanjunjie
 * @date: 2024/07/30
 */
public class DataPermissionHandlerImpl implements DataPermissionHandler {
    /**
     * 获取数据权限 SQL 片段
     *
     * @param where             待执行 SQL Where 条件表达式
     * @param mappedStatementId Mybatis MappedStatement Id 根据该参数可以判断具体执行方法
     * @return JSqlParser 条件表达式
     */
    @Override
    public Expression getSqlSegment(Expression where, String mappedStatementId) {
        return new StringValue("");
    }

    /**
     * 逻辑删除处理器（ DelFlag 行级 ）
     *
     * @author mqp
     * @since 3.4.0
     */
    public static interface DelLineHandler {

        /**
         * 根据表名判断是否忽略拼接多租户条件
         * <p>
         * 默认都要进行解析并拼接多租户条件
         *
         * @param tableName 表名
         * @return 是否忽略, true:表示忽略，false:需要解析并拼接多租户条件
         */
        default boolean ignoreTable(String tableName) {
            return false;
        }
    }
}
