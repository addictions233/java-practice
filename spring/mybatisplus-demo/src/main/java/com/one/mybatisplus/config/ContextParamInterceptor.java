package com.one.mybatisplus.config;

import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.sql.SQLException;

/**
 * @ClassName: ContextParamInterceptor
 * @Description: 自定义拦截器, 为sql语句添加上下文参数
 * @Author: one
 * @Date: 2025/12/14
 */
public class ContextParamInterceptor implements InnerInterceptor {

    @Override
    public void beforeQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) throws SQLException {
        boundSql.getAdditionalParameters().putIfAbsent("id", 2L);
    }
}
