package com.one.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author one
 */
public class JDBCDemo01 {
    public static void main(String[] args) throws Exception{
        //1,导入jar包
        //2,注册驱动
        Class.forName("com.mysql.jdbc.Driver");
        //3,获取连接
        String url = "jdbc:mysql://localhost:3306/db_test";
        String user = "root";
        String password = "root";
        Connection con = DriverManager.getConnection(url, user, password);

        //4,获取执行者对象 普通执行者对象
        Statement stat = con.createStatement();

        //5,执行sql语句
        String sql = "SELECT * FROM account";
        ResultSet result = stat.executeQuery(sql);

        //6,遍历结果
        while(result.next()){
            int id = result.getInt("id");
            String name = result.getString("name");
            double money = result.getDouble("money");
            System.out.println(id+"\t"+name+"\t\t"+money);
        }
        //7,关流释放资源
        con.close();
        stat.close();
        result.close();
    }
}
