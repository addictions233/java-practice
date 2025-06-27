package com.one.test;

import com.one.dao.BookDao;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @ClassName: TestBookDao
 * @Description: TODO
 * @Author: one
 * @Date: 2020/12/02
 */
public class BookDaoTest {
    @Test
    public void testBookDao(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext-bak.xml");
        BookDao bookDao = (BookDao) ctx.getBean("bookDao");
        bookDao.save();

    }
}
