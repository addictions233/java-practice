package com.one.conpool.main;
// 内部类的导包方式
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.one.conpool.utils.DruidUtils;
import com.one.jdbc.JDBCUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *  测试三种数据库连接方式执行效率比较
 */
public class Main {
    public static void main(String[] args) {
        TimeCost.test(new TimeCost.Task(){
            @Override
            public void execute(){
//                druidPool();    // 318ms
//                C3P0Pool();   // 712ms
                common();  // 304ms
            }
        });
    }

    /**
     * 测试不用连接池的情况
     */
    public static void common(){
        for (int i = 0; i < 10; i++) {
            Connection connection = JDBCUtils.getConnection();
            Statement stat = null ;
            ResultSet result = null;
            try {
                stat = connection.createStatement();
                String sql = "SELECT * FROM employee";
                result = stat.executeQuery(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                JDBCUtils.close(connection,stat,result);
            }
        }
    }

    /**
     *  测试 C3P0连接池
     */
    public static void C3P0Pool(){
        //获取连接池对象
        DataSource dataSource = new ComboPooledDataSource();
        for (int i = 0; i < 10; i++) {
            Connection con = null;
            Statement stat = null;
            ResultSet result = null;
            try {
                con = dataSource.getConnection();
                stat = con.createStatement();
                String sql = "SELECT * FROM employee";
                result = stat.executeQuery(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    con.close();
                    stat.close();
                    result.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     *  测试 Druid连接池
     */
    public static void druidPool(){
        for (int i = 0; i < 10; i++) {
            Connection con = DruidUtils.getConnection();
            Statement stat = null ;
            ResultSet result = null;
            try {
             stat = con.createStatement();
             String sql = "SELECT * FROM employee";
             result = stat.executeQuery(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                DruidUtils.close(con,stat,result);
            }
        }
    }
}
