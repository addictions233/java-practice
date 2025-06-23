package com.one.jdbc;

import java.sql.*;

public class JDBCDemo03 {
    public static void main(String[] args) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        ResultSet result = null;
        //1,导入jar包
        try {
            //2,注册驱动
            Class.forName("com.mysql.jdbc.Driver");
            //3,获取连接对象
            String url = "jdbc:mysql://localhost:3306/db_test";
            String username = "root";
            String password = "root";
            connection= DriverManager.getConnection(url, username, password);
            //4, 获取执行者对象, 也可以是预编译执行者对象
            statement= connection.createStatement();
            //5,执行sql语句
            String sql = "select * from account";
            result = statement.executeQuery(sql);
            //6,遍历查询结果
            while(result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                double money = result.getDouble("money");
                System.out.println("id:" + id + ",name:" + name + ",money:" + money);
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            //7,关流
            result.close();
            statement.close();
            connection.close();
        }
    }
}
