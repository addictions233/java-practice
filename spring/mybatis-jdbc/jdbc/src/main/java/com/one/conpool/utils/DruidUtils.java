package com.one.conpool.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * 将Druid连接池集成到工具类DruidUtil中
 */
public class DruidUtils {
    /**
     * 私有构造器,防止其它类创建本类对象
     */
    private DruidUtils() {
    }

    private static DataSource dataSource;

    //类加载时需要执行的静态代码块
    static {
        InputStream is = DruidUtils.class.getClassLoader().getResourceAsStream("druid.properties");
        Properties properties = new Properties();
        try {    // 工具类中不要抛异常,否则其调用者也要抛该异常
            properties.load(is);
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过Druid连接池获取连接者对象
     *
     * @return Connection
     */
    public static Connection getConnection() {
        Connection con = null;
        try {
            con = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    public static void close(Connection con, Statement stat) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (stat != null) {
            try {
                stat.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void close(Connection con, Statement stat, ResultSet result) {
        close(con, stat);
        try {
            result.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
