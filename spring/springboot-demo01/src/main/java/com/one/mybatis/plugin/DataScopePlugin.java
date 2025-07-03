package com.one.mybatis.plugin;

import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.one.mybatis.ReflectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;

import java.sql.Connection;
import java.util.*;

@Slf4j
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class DataScopePlugin implements Interceptor, CacheLoader<String, Boolean> {

    private final Map<String, List<String>> companyMap;
    private final LoadingCache<String, Boolean> validationCache;
    private final SqlStatementHelper sqlStatementHelper;

    public DataScopePlugin(Map<String, List<String>> companyMap) {
        this.companyMap = companyMap;
        this.sqlStatementHelper = new SqlStatementHelper("mysql");

        this.validationCache = Caffeine.newBuilder().initialCapacity(300).build(this);
    }

    private Integer getCompanyId() {
        return 1;
    }

    @Override
    public Boolean load(String key) throws Exception {
        // check global level
        Class<?> mapperClass;
        String className = StringUtils.substringBeforeLast(key, ".");
        String methodName = StringUtils.substringAfterLast(key, ".");
        try {
            mapperClass = Class.forName(className);
        } catch (ClassNotFoundException e) {
            log.error("can not find mapper class " + className, e);
            throw e;
        }
        if (mapperClass.isAnnotationPresent(GlobalLevel.class)) {
            return false;
        }

        if (Arrays.stream(mapperClass.getDeclaredMethods()).anyMatch(method ->
                Objects.equals(method.getName(), methodName) &&
                        method.isAnnotationPresent(GlobalLevel.class))) {
            return false;
        }

        // check map
        String tableName = mapperClass.getSimpleName();
        if (StringUtils.endsWith(tableName, "QueryMapper")) {
            tableName = StringUtils.removeEnd(tableName, "QueryMapper");
        }
        if (StringUtils.endsWith(tableName, "CommandMapper")) {
            tableName = StringUtils.removeEnd(tableName, "CommandMapper");
        }

        if (companyMap.containsKey(tableName)) {
            List<String> methods = companyMap.get(tableName);
            return !CollectionUtils.containsAny(methods, methodName, "all");
        }

        return true;
    }

    private boolean ignore(String id, SqlCommandType sqlCommandType) {
        if (ObjectUtils.notEqual(sqlCommandType, SqlCommandType.SELECT)
                && ObjectUtils.notEqual(sqlCommandType, SqlCommandType.UPDATE)
                && ObjectUtils.notEqual(sqlCommandType, SqlCommandType.DELETE)) {
            return true;
        }

        if (id.contains("login")
                || id.contains("logout")
                || id.contains("FakerToken") || id.contains("template_")) {
            return true;
        }

        Boolean result = validationCache.get(id);

        return !Objects.equals(result, true);
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        if (!(invocation.getTarget() instanceof RoutingStatementHandler)) {
            return invocation.proceed();
        }

        RoutingStatementHandler handler = (RoutingStatementHandler) invocation.getTarget();
        ParameterHandler parameterHandler = handler.getParameterHandler();
        MappedStatement mappedStatement = (MappedStatement) ReflectionUtils.getFieldValue(parameterHandler,
                "mappedStatement");

        String id = mappedStatement.getId();
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        BoundSql boundSql = handler.getBoundSql();
        String sql = boundSql.getSql();

        if (ignore(id, sqlCommandType)) {
            return invocation.proceed();
        }

        Integer companyId = getCompanyId();
        if (Objects.isNull(companyId)) {
            throw new DataScopeException("can not find company id for data scope.");
        }

        sql = sqlStatementHelper.addCondition(sql, "company_id", companyId);
        log.debug(sql);

        ReflectionUtils.setFieldValue(boundSql, "sql", sql);

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }
}
