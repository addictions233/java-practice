package com.one.dao.impl;

import com.one.dao.BookDao;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

@Repository("bookDaoImpl")
// @PropertySource引入资源文件
@PropertySource(value={"classpath:jdbc.properties"},ignoreResourceNotFound = true)
@Ignore
public class BookDaoImpl implements BookDao {
    //用EL表达式对资源文件取值
    @Value("${jdbc.username}")
    private String username;
    @Value("${jdbc.password}")
    private String password;

    public void save() {
        System.out.println("book dao running..."+username+password);
    }
}
