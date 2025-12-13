package com.one.mybatisplus.injector;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author one
 * @description 自定义sql注入器, 用于注入自定义的sql语句
 */
@Component // 注入成为Bean对象才会替代默认的DefaultSqlInjector, 否则不会生效
public class MySqlInjector extends DefaultSqlInjector {

    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass, TableInfo tableInfo) {
        List<AbstractMethod> methodList = super.getMethodList(mapperClass, tableInfo);
        methodList.add(new MySelectOne());
        return methodList;
    }
}
