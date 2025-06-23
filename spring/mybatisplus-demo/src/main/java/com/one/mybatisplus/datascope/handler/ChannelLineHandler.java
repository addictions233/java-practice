package com.one.mybatisplus.datascope.handler;

import net.sf.jsqlparser.expression.Expression;

public interface ChannelLineHandler {

    /**
     * 获取系统渠道
     *
     * @return
     */
    Expression getSysSource();

    /**
     * 获取系统渠道字段名
     * <p>
     * 默认字段名叫: tenant_id
     *
     * @return 系统渠道字段名
     */
    default String getSysSourceColumn() {
        return "sys_source";
    }

    /**
     * 根据表名判断是否忽略拼接多系统渠道条件
     * <p>
     * 默认都要进行解析并拼接多系统渠道条件
     *
     * @param tableName 表名
     * @return 是否忽略, true:表示忽略，false:需要解析并拼接多系统渠道条件
     */
    default boolean ignoreTable(String tableName) {
        return false;
    }
}
