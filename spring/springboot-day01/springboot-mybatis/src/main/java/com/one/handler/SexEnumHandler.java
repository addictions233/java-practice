package com.one.handler;

import com.one.enums.SexEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @ClassName: SexEnumHandler
 * @Description: 编写mybatis的结果类型转换器
 * @Author: one
 * @Date: 2022/04/14
 */
public class SexEnumHandler extends BaseTypeHandler<SexEnum> {
    /**
     * 参数处理, 将entity对象映射为mysql中的一行数据时使用
     * @param ps
     * @param i
     * @param parameter
     * @param jdbcType
     * @throws SQLException
     */
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, SexEnum parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getValue());
    }

    @Override
    public SexEnum getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return SexEnum.value2Object(rs.getInt(columnName));
    }

    @Override
    public SexEnum getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return SexEnum.value2Object(rs.getInt(columnIndex));
    }

    @Override
    public SexEnum getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return SexEnum.value2Object(cs.getInt(columnIndex));
    }
}
