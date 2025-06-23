package com.one.dao.impl;

import com.one.dao.BookDao;

/**
 * @ClassName: BookDaoImpl
 * @Description: TODO
 * @Author: one
 * @Date: 2020/12/01
 */
public class BookDaoImpl implements BookDao {
    private String name;
    private String addr;

    public BookDaoImpl() {
        System.out.println("BookDaoImpl被创建了,空参构造");
    }

    public BookDaoImpl(String name, String addr) {
        System.out.println("BookDaoImpl被被创建了,有参构造");
        this.name = name;
        this.addr = addr;
    }


    public void save() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "BookDaoImpl{" +
                "name='" + name + '\'' +
                ", addr='" + addr + '\'' +
                '}';
    }
}
