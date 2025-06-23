package com.one.mybatisplus.datasource;

import javax.sql.DataSource;
import java.util.Collection;

/**
 * 动态数据源的接口规范
 *
 * @author one
 */
public interface DataSourceManager {
    /**
     * 添加数据源
     *
     * @param var1 数据源的key
     * @param var2 DataSource
     */
    void put(String var1, DataSource var2);

    /**
     * 通过key获取数据源
     *
     * @param var1 数据源的key
     * @return DataSource
     */
    DataSource get(String var1);

    /**
     * 移除数据源
     *
     * @param var1 数据源的key
     */
    void remove(String var1);

    /**
     * 关闭数据源
     *
     * @param var1 数据源的key
     */
    void closeDataSource(String var1);

    /**
     * 获取所有的数据源
     *
     * @return 返回数据源集合
     */
    Collection<DataSource> all();
}