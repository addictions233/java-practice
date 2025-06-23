package com.one.conpool;


import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 使用 C3P0连接池
 * @author one
 */
public class C3P0Demo01 {
    public static void main(String[] args) throws SQLException{
        // 获取连接池对象,多态接收
        DataSource dataSource = new ComboPooledDataSource();
        // 获取sql连接对象
        Connection con = dataSource.getConnection();
        String sql = "SELECT * FROM tb_employee";
        // 获取预编译执行对象
        PreparedStatement pstat = con.prepareStatement(sql);
        // 获取结果集对象
        ResultSet result = pstat.executeQuery();
        // 对结果集进行遍历处理
        while(result.next()){
            System.out.println(result.getInt("id")+result.getString("NAME"));
        }
        // 如果是从连接池中获取的连接对象, 调用close方法不是关闭连接,而是归还连接池
        con.close();
        pstat.close();
        result.close();
    }
}
