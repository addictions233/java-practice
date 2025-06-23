package com.one.jdbc;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *  用单元测试的方式执行jdbc
 */
public class JDBCDemo02 {
    private Connection con;
    private Statement stat;
    private ResultSet result;

    @Before
    public void init() throws Exception{
        //1,导入jar包
        //2,注册驱动
        Class.forName("com.mysql.jdbc.Driver");
        //3,获取连接
        String url = "jdbc:mysql://localhost:3306/db_test";
        String user = "root";
        String password = "root";
        con = DriverManager.getConnection(url, user, password);
        //4,获取执行者对象
        stat = con.createStatement();
    }

    @After
    public void close() throws Exception{
        //7,关流
        con.close();
        stat.close();
        result.close();
    }

    @Test
    public void test() throws Exception{
        //5,执行sql语句
        String sql = "SELECT * FROM account";
        result = stat.executeQuery(sql);
        //6,遍历结果
        while(result.next()){
            int id = result.getInt("id");
            String name = result.getString("name");
            double money = result.getDouble("money");
            System.out.println(id+"\t"+name+"\t\t"+money);
        }
    }
}
