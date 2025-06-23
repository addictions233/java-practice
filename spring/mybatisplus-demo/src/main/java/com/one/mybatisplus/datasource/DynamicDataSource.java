package com.one.mybatisplus.datasource;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.one.mybatisplus.datasource.DataSourceManager;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author one
 * @description 动态数据源实现: Spring实现动态数据源必须继承自AbstractRoutingDataSource
 * mybatis plus提供的动态数据源实现: {@link DynamicRoutingDataSource}
 * @date 2022-10-12
 */
public class DynamicDataSource extends AbstractRoutingDataSource implements DataSourceManager {
    /**
     * 所有的数据源map
     */
    private ConcurrentHashMap<String, DataSource> dataSourceMap;

    /**
     * 默认的数据源
     */
    private DataSource defaultDataSource;

    /**
     * 构造器: 初始化需要管理的数据源
     *
     * @param defaultDataSource 默认数据源
     * @param targetDataSourceMap 所有的数据源map
     */
    public DynamicDataSource(DataSource defaultDataSource, Map<String, DataSource> targetDataSourceMap) {
        this.dataSourceMap.putAll(targetDataSourceMap);
        this.defaultDataSource = defaultDataSource;

    }

    /**
     * Retrieve the current target DataSource. Determines the
     * {@link #determineCurrentLookupKey() current lookup key}, performs
     * a lookup in the {@link #setTargetDataSources targetDataSources} map,
     * falls back to the specified
     * {@link #setDefaultTargetDataSource default target DataSource} if necessary.
     *
     * @see #determineCurrentLookupKey()
     */
    @Override
    protected DataSource determineTargetDataSource() {
        return super.determineTargetDataSource();
    }

    /**
     * 重写abstractRoutingDataSource接口中的方法,确认使用哪个数据源
     *
     * @return 数据源
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return null;
    }

    /**
     * 添加数据源
     *
     * @param var1 数据源的key
     * @param var2 DataSource
     */
    @Override
    public void put(String var1, DataSource var2) {
        this.dataSourceMap.put(var1, var2);
    }

    /**
     * 通过key获取数据源
     *
     * @param var1 数据源的key
     * @return DataSource
     */
    @Override
    public DataSource get(String var1) {
        return dataSourceMap.get(var1);
    }

    /**
     * 移除数据源
     *
     * @param var1 数据源的key
     */
    @Override
    public void remove(String var1) {
        dataSourceMap.remove(var1);
    }

    /**
     * 关闭数据源
     *
     * @param var1 数据源的key
     */
    @Override
    public void closeDataSource(String var1) {
        DataSource dataSource = dataSourceMap.get(var1);
        try {
            dataSource.getConnection().close();
            dataSourceMap.remove(var1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取所有的数据源
     *
     * @return 返回数据源集合
     */
    @Override
    public Collection<DataSource> all() {
        return dataSourceMap.values();
    }
}
