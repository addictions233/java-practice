package com.one.mybatisplus.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.handlers.AbstractJsonTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.lang.reflect.Field;


/**
 * 注意：MyBatisPlus中没有在YML文件中配置全局JSON序列化器的方法，所以需要要使用的话，
 * 需要在指定字段上增加typeHandler进行类型转换，MyBatisPlus默认使用的是JacksonTypeHandler序列化器，步骤如下：
 *
 * 第一步：在数据库中定义JSON类型的字段。
 * 第二步：在指定的字段上增加@TableField(typeHandler = JacksonTypeHandler.class)注解进行类型的转换。
 * 第三步：在实体类上增加autoResultMap = true属性开始自动映射。
 */
@MappedTypes({JSONObject.class})
@MappedJdbcTypes(JdbcType.VARCHAR)
public class JsonbTypeHandler extends AbstractJsonTypeHandler<JSONObject> {


    public JsonbTypeHandler(Class<?> type) {
        super(type);
    }

    public JsonbTypeHandler(Class<?> type, Field field) {
        super(type, field);
    }

    @Override
    public JSONObject parse(String json) {
            return JSON.parseObject(json);
    }

    @Override
    public String toJson(JSONObject obj) {
        return JSON.toJSONString(obj);
    }
}
