package com.one.lock.sql;

import com.one.lock.AbstractLock;

import java.sql.*;

/**
 * @author one
 * @description 使用mysql实现一把分布式锁
 * @date 2024-10-23
 */
public class SQLDistributedLock extends AbstractLock {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/my_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "password";

    /**
     * 锁的唯一key
     */
    private String resourceKey;


    private Integer timeOut;

    private String UUID = java.util.UUID.randomUUID().toString();

    public SQLDistributedLock(String resourceKey, Integer timeOut) {
        this.resourceKey = resourceKey;
        this.timeOut = timeOut;
    }


    @Override
    public boolean tryLock() {
        boolean lockAcquired = false;
        Connection connection = null;
        try {
            // 获取连接对象
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // 开启事务
            connection.setAutoCommit(false);

            // 加独占锁
            String selectSql = "SELECT * FROM distributed_lock WHERE resource_key = ? FOR UPDATE";
            PreparedStatement statement = connection.prepareStatement(selectSql);
            statement.setString(1, this.resourceKey);
            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                // 没有值, 表明数据还不存在, 可以加锁
                String insertSql = "INSERT INTO distributed_lock (resource_key, owner, created_time, expiration_time) VALUES (?, ?, NOW(), DATE_ADD(NOW(), INTERVAL ? SECOND))";
                PreparedStatement insertStatement = connection.prepareStatement(insertSql);
                insertStatement.setString(1, this.resourceKey);
                // 设置锁持有的线程名称
                insertStatement.setString(2, UUID.concat(":").concat(String.valueOf(Thread.currentThread().getId())));
                insertStatement.setInt(3, this.timeOut);
                insertStatement.executeUpdate();

                // 提交事务
                connection.commit();

                // 获取锁成功
                lockAcquired = true;

                insertStatement.close();

            }
            statement.close();
            resultSet.close();
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return lockAcquired;
    }

    @Override
    public void waitLock() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void unlock() {
        Connection conn = null;
        try {
            // 创建数据库连接
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // 开始事务
            conn.setAutoCommit(false);

            // 删除锁记录
            String deleteSql = "DELETE FROM distributed_lock WHERE resource_key = ?";
            PreparedStatement deleteStmt = conn.prepareStatement(deleteSql);
            deleteStmt.setString(1, this.resourceKey);
            deleteStmt.executeUpdate();

            // 提交事务
            conn.commit();

            // mysql实现分布式锁一个重要的缺点就是锁释放之后没法通知之前获取锁失败陷入阻塞的线程
            // mysql无法实现分布式阻塞队列, 而redis和zookeeper都可以通过各自的特点实现

            deleteStmt.close();
        } catch (SQLException e) {
            // 处理异常
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException(e);
                }
            }
            throw new RuntimeException(e);
        } finally {
            // 关闭数据库连接
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
