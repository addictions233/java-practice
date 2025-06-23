package com.one;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.one.domain.Student;
import com.one.service.StudentService;
import com.one.service.impl.StudentServiceImpl;
import org.junit.Test;

import java.util.List;

/**
 * @ClassName: SutentTest
 * @Description: 测试分页插件
 * @Author: one
 * @Date: 2021/05/21
 */

public class StudentTest {

    /**
     * 测试使用分页插件进行分页查询
     */
    @Test
    public void selectByPage() {
        StudentService studentService = new StudentServiceImpl();
        List<Student> students = studentService.selectByPage(1, 2);
        PageInfo<Student> pageInfo = new PageInfo<>(students);
        String pageInfoJSON = JSON.toJSONString(pageInfo);
        System.out.println("pageInfoJSON:" + pageInfoJSON);
        System.out.println("pageInfoToString" + pageInfo.getList());
        String listJSON = JSON.toJSONString(pageInfo.getList());
        System.out.println("listJSON:" + listJSON);
        long total = pageInfo.getTotal();
        System.out.println("total:" + total);
        List<Student> list = pageInfo.getList();
        for (Student student : list) {
            System.out.println(student);
        }
    }
}
