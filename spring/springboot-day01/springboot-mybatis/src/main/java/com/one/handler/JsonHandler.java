package com.one.handler;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @ClassName: JsonHandler
 * @Description: 将mysql中json格式的字段，进行转换的自定义转换器，转换为实体类的JSONArray属性
 * @Author: one
 * @Date: 2022/01/25
 */
@MappedTypes(JSONArray.class) //MappedTypes注解中的类代表此转换器可以自动转换为的java对象
@MappedJdbcTypes(JdbcType.VARCHAR) //MappedJdbcTypes注解中设置的是对应的jdbctype类型
public class JsonHandler extends BaseTypeHandler<JSONArray> {
    /**
     * 设置非空参数
     * @param ps 预编译对象
     * @param i 参数位置
     * @param parameter 参数
     * @param jdbcType jdbcType
     * @throws SQLException
     */
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, JSONArray parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, String.valueOf(parameter.toJSONString()));
    }

    /**
     * 根据列名, 获取可以为空的结果
     * @param rs 查询结果对象
     * @param columnName 列名
     * @return JSONArray
     * @throws SQLException
     */
    @Override
    public JSONArray getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String jsonString = rs.getString(columnName);
        if (null != jsonString) {
            return JSONObject.parseArray(jsonString);
        }
        return null;
    }

    /**
     * 更加列的索引, 获取可以为空的结果
     * @param rs 查询结果
     * @param columnIndex 列的索引
     * @return JSONArray
     * @throws SQLException
     */
    @Override
    public JSONArray getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String jsonString = rs.getString(columnIndex);
        if (null != jsonString) {
            return JSONObject.parseArray(jsonString);
        }
        return null;
    }

    /**
     *
     * @param cs cs
     * @param columnIndex 列的索引
     * @return JSONArray
     * @throws SQLException
     */
    @Override
    public JSONArray getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String jsonString = cs.getString(columnIndex);
        if (null != jsonString) {
            return JSONObject.parseArray(jsonString);
        }
        return null;
    }
}
