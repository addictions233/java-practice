package com.one.typehandler;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.*;

/**
 * 自定义TypeHandler, 将javaType中的String类型转换为jdbcType中的int类型
 *
 * @author one
 */
@MappedTypes(String.class)
@MappedJdbcTypes({JdbcType.BIGINT, JdbcType.VARCHAR})
public class MyTypeHandler implements TypeHandler<String> {

    /**
     * 负责将Java类型转换为JDBC类型, 本质上执行的就是JDBC中的PreparedStatement的set方法
     *  例如: String sql = "insert into user(name, age) values(?, ?)";
     *       PreparedStatement ps = conn.prepareStatement(sql);
     *       ps.setString(1, "张三");
     *       ps.setInt(2, 18);
     * @param ps
     * @param i 对应占位符的位置
     * @param parameter 占位符对应的值
     * @param jdbcType 对应的数据库类型
     */
    @Override
    public void setParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
        if (jdbcType == JdbcType.BIGINT) {
            ps.setLong(i, Integer.parseInt(parameter));
        } else if (jdbcType == JdbcType.VARCHAR) {
            ps.setString(i, parameter);
        }
    }

    /**
     * 从ResultSet中获取数据时会调用此方法,会将数据从JdbcType转换为Java类型,本质上执行的就是JDBC中的ResultSet的get方法
     *  例如: ResultSet rs = ps.executeQuery();
     *        String name = rs.getString("name");
     *        int age = rs.getInt("age");
     */
    @Override
    public String getResult(ResultSet rs, String columnName) throws SQLException {
        return rs.getString(columnName);
    }

    @Override
    public String getResult(ResultSet rs, int columnIndex) throws SQLException {
        return rs.getString(columnIndex);
    }

    @Override
    public String getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return cs.getString(columnIndex);
    }
}
