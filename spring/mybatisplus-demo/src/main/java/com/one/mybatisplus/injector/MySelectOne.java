package com.one.mybatisplus.injector;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * @author one
 * @description 自定义查询方法, 将该查询语句注入到mapper接口中
 */
public class MySelectOne extends AbstractMethod {

    /**
     * @param methodName 方法名
     * @since 3.5.0
     */
    private MySelectOne(String methodName) {
        super(methodName);
    }

    /**
     * 自定义查询方法, 默认方法名: select
     */
    public MySelectOne() {
        // 指定自己定义的SQL对应的方法名时mySelectOne
        this("mySelectOne");
    }

    /**
     * 重要的重写方法: 将sql语句注入到mapper接口中, 生成MappedStatement对象
     * @param mapperClass mapper 接口
     * @param modelClass  mapper 泛型
     * @param tableInfo   数据库表反射信息
     * @return
     */
    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        String sql = "select * from " + tableInfo.getTableName() + " limit 1";
        SqlSource sqlSource = super.createSqlSource(configuration, sql, modelClass);
        // 将自定义的sql添加为MappedStatement
        return this.addSelectMappedStatementForOther(mapperClass, sqlSource, modelClass);
    }
}
