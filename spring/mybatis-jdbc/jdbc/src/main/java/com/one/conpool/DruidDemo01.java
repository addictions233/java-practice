package com.one.conpool;


import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * @author one
 * 使用 Druid的连接池:
 *         使用jdbc,一个数据库创建一个连接对象
 *         使用数据库连接池, 一个数据源创建多个连接对象
 */
public class DruidDemo01 {
    public static void main(String[] args) throws Exception {
        // 读取配置文件druid.properties
        InputStream is = DruidDemo01.class.getClassLoader().getResourceAsStream("druid.properties");
        Properties properties = new Properties();
        properties.load(is);
        DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);

        // 通过连接池获取连接者对象
        Connection con = dataSource.getConnection();
        //获取预编译执行者对象
        String sql = "SELECT * FROM tb_employee";
        PreparedStatement pstat = con.prepareStatement(sql);

        //获取结果集对象
        ResultSet result = pstat.executeQuery();

        // 遍历结果集,进行打印输出
        while(result.next()){
            System.out.println(result.getInt("id")+"\t"+result.getString("NAME"));
        }

        // 将连接对象归还连接池
        con.close();
        pstat.close();
        result.close();
    }
}
