package com.one.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @description: 使用JDBC进行事务管理
 * @author: wanjunjie
 * @date: 2024/04/15
 */
public class JDBCTx {

    public static void main(String[] args) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            // 获取连接
            connection = JDBCUtils.getConnection();
            // 设置事务自动提交为false, 需要手动提交事务
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            // 张三向李四转账100元
            statement.executeUpdate("UPDATE account SET money = money - 100 where name = '张三';");
            statement.executeUpdate("UPDATE account SET money = money + 100 where name = '李四';");
            // 事务提交
            connection.commit();
        } catch (Exception e) {
            try {
                // 事务回滚
                connection.rollback();
            } catch (SQLException ex) {
               ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            JDBCUtils.close(connection, preparedStatement);
        }
    }
}
