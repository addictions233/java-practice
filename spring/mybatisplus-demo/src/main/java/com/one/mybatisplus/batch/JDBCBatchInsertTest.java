package com.one.mybatisplus.batch;

import com.one.mybatisplus.entity.TbUser;
import javafx.scene.control.Tab;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 测试JDBC最原始的批量操作, mybatis和mybatis-plus的批量操作都是以此作为基础的
 */
public class JDBCBatchInsertTest {

    public static void main(String[] args) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/db_test";
        String username = "root";
        String password = "root";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)){
            String sql = "INSERT INTO tb_user1 (name, age) VALUES (?, ?)";
            List<TbUser> tbUsers = new ArrayList<>();
            tbUsers.add(new TbUser("张三", 23));
            tbUsers.add(new TbUser("李四", 24));
            tbUsers.add(new TbUser("王五", 25));

            PreparedStatement ps = connection.prepareStatement(sql);
            for (TbUser tbUser : tbUsers) {
                ps.setString(1, tbUser.getName());
                ps.setInt(2, tbUser.getAge());
                ps.addBatch();
            }
            // 重点: 使用JDBC的批量操作, 可以提高插入效率, 只与mysql数据进行一次交互
            int[] results = ps.executeBatch();
            for (int anInt : results) {
                System.out.println(anInt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
