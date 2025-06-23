package com.one.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 *  JDBC工具类
 *  抽取config.properties配置文件的优点,之后可以只修改配置文件,不用再修改java代码
 *  抽取JDBC实现代码集成为工具类的优点,简化后续代码书写
 */
public class JDBCUtils {
    /**
     * 1,私有构造方法, 工具类是不需要创建对象,其成员都是静态的
     */
    private JDBCUtils(){

    }

    /**
     * 2,声明所需要的配置变量
     */
    private static String driverClass;
    private static String url;
    private static String username;
    private static String password;

    //3,提供静态代码块,随着类的加载而执行一次,读取配置文件
    static {
        // xxx.class.getClassLoader().getResourceAsStream("文件名")  读取配置文件标准代码
        InputStream is = JDBCUtils.class.getClassLoader().getResourceAsStream("config.properties");
        Properties properties = new Properties();
        try {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

         driverClass = properties.getProperty("driverClass");
         url = properties.getProperty("url");
         username = properties.getProperty("username");
         password = properties.getProperty("password");


        try {
            //注册驱动
            Class.forName(driverClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 4,获取数据库连接的方法
     * @return 数据库连接对象
     */
    public static Connection getConnection(){
        Connection con = null;
        try {
            con = DriverManager.getConnection(url,username,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    /**
     * 5, 关流   查询操作的关流
     * @param con 连接对象
     * @param stat 执行者对象
     * @param result 结果集
     */
    public static void close(Connection con, Statement stat, ResultSet result){
        close(con,stat);
        if(result != null){
            try {
                result.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     *  增,删,改操作的关流
     * @param con 连接对象
     * @param stat 执行者对象
     */
    public static void close(Connection con,Statement stat){
        if(con != null){
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(stat != null){
            try {
                stat.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
