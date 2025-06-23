package com.one.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

/**
 * 功能：mybatis拦截器拦截所有插入或更新的数据库表的sql,并统一添加对应的创建人、创建时间、更新人、更新时间
 * 注意：1、插入或更新传递的入参只能单实体,否则拦截下来的sql更新可能会报错
 */
@Component
@Slf4j
@Intercepts(@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}))
public class AutoFillInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws IllegalAccessException, InvocationTargetException {
        fillField(invocation);
        return invocation.proceed();
    }

    private void fillField(Invocation invocation) {
        Object[] args = invocation.getArgs();
        SysUser sysUser = getSysUser();
        if (args != null && args.length > 0 && sysUser != null) {
            SqlCommandType sqlCommandType = ((MappedStatement) args[0]).getSqlCommandType();
            Object arg = args[1];
            if (sqlCommandType == SqlCommandType.INSERT) {
                for (Field f : arg.getClass().getDeclaredFields()) {
                    f.setAccessible(true);
                    switch (f.getName()) {
                        case "createId":
                            if (Objects.nonNull(sysUser.getUserId())) {
                                setProperty(arg, "createId", sysUser.getUserId());
                            }
                        case "createTime":
                            setProperty(arg, "createTime", LocalDateTime.now());
                        case "updateId":
                            if (Objects.nonNull(sysUser.getUserId())) {
                                setProperty(arg, "updateId", sysUser.getUserId());
                            }
                        case "updateTime":
                            setProperty(arg, "updateTime", LocalDateTime.now());
                    }
                }
            } else if (sqlCommandType == SqlCommandType.UPDATE) {
                if (arg instanceof MapperMethod.ParamMap) {
                    Set keySet = ((MapperMethod.ParamMap) arg).keySet();
                    if (keySet.size() > 0) {
                        String key = (String) keySet.iterator().next();
                        if (((MapperMethod.ParamMap) arg).get(key) != null) {
                            for (Field f : ((MapperMethod.ParamMap) arg).get(key).getClass().getDeclaredFields()) {
                                f.setAccessible(true);
                                switch (f.getName()) {
                                    case "updateId":
                                        if (Objects.nonNull(sysUser.getUserId())) {
                                            setProperty(((MapperMethod.ParamMap) arg).get(key), "updateId", sysUser.getUserId());
                                        }
                                    case "updateTime":
                                        setProperty(((MapperMethod.ParamMap) arg).get(key), "updateTime", LocalDateTime.now());
                                }
                            }
                        }
                    }
                } else {
                    for (Field f : arg.getClass().getDeclaredFields()) {
                        f.setAccessible(true);
                        switch (f.getName()) {
                            case "updateId":
                                if (Objects.nonNull(sysUser.getUserId())) {
                                    setProperty(arg, "updateId", sysUser.getUserId());
                                }
                            case "updateTime":
                                setProperty(arg, "updateTime", LocalDateTime.now());
                        }
                    }
                }


            }
        }

    }

    /**
     * 为对象的操作属性赋值
     *
     * @param bean
     */
    private void setProperty(Object bean, String name, Object value) {
        try {
            //根据需要，将相关属性赋上默认值
            BeanUtils.setProperty(bean, name, value);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object plugin(Object o) {

        return Plugin.wrap(o, this);
    }

    private SysUser getSysUser() {
        return new SysUser();
    }

    static class SysUser {
        private Long userId;

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }
    }
}
