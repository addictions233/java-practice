package com.one.mybatisplus.handler;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
 
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
 
/**
 * JSON类型处理器
 * 用于在MyBatis中处理JSON类型的字段，将数据库中的字符串与Java中的JSONObject相互转换
 * 
 * @author kobe
 */
public class JsonTypeHandler extends BaseTypeHandler<JSONObject> {
 
    /**
     * 设置非空参数
     * 将JSONObject对象转换为JSON字符串并设置到PreparedStatement中
     *
     * @param ps PreparedStatement对象
     * @param i 参数索引位置
     * @param parameter JSONObject参数值
     * @param jdbcType JDBC类型
     * @throws SQLException SQL异常
     */
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, JSONObject parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.toJSONString());
    }
 
    /**
     * 从ResultSet中获取可为空的结果
     * 根据列名获取字符串并转换为JSONObject对象
     *
     * @param rs ResultSet结果集
     * @param columnName 列名
     * @return JSONObject对象或null
     * @throws SQLException SQL异常
     */
    @Override
    public JSONObject getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String json = rs.getString(columnName);
        return json == null ? null : JSONObject.parseObject(json);
    }
 
    /**
     * 从ResultSet中获取可为空的结果
     * 根据列索引获取字符串并转换为JSONObject对象
     *
     * @param rs ResultSet结果集
     * @param columnIndex 列索引
     * @return JSONObject对象或null
     * @throws SQLException SQL异常
     */
    @Override
    public JSONObject getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String json = rs.getString(columnIndex);
        return json == null ? null : JSONObject.parseObject(json);
    }
 
    /**
     * 从CallableStatement中获取可为空的结果
     * 根据列索引获取字符串并转换为JSONObject对象
     *
     * @param cs CallableStatement对象
     * @param columnIndex 列索引
     * @return JSONObject对象或null
     * @throws SQLException SQL异常
     */
    @Override
    public JSONObject getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String json = cs.getString(columnIndex);
        return json == null ? null : JSONObject.parseObject(json);
    }
}