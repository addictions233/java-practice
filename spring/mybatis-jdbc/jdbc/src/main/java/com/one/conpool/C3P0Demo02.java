package com.one.conpool;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.sql.Connection;

public class C3P0Demo02 {
    public static void main(String[] args) throws Exception {
        //获取连接池对象
        DataSource dataSource = new ComboPooledDataSource();

//        for (int i = 0; i < 10; i++) {
//            Connection con = dataSource.getConnection();
//            System.out.println(con);  // 可以看到10个 Connection con对象内存地址都不同
//        }


        //java.sql.SQLException: An attempt by a client to checkout a Connection has timed out
        //当需要连接数量超过连接池最大连接数量时就会抛出上面异常
        for (int i = 0; i < 11; i++) {
            Connection con = dataSource.getConnection();
            System.out.println(con);
            if(i == 5){
                con.close();  //将连接对象归还连接池,可重复使用
            }
        }
    }
}
