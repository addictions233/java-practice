package com.one.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.one.domain.Student;

import java.util.List;

/**
 * service 业务层接口
 * @author one
 */
public interface StudentService {
    //查询全部
    List<Student> selectAll();

    //分页查询
    List<Student> selectByPage(int currentPage, int pageSize);

    //根据id查询
    Student selectById(Integer id);

    //新增数据
    Integer insert(Student stu);

    //修改数据
    Integer update(Student stu);

    //删除数据
    Integer delete(Integer id);

    //多条件查询
    List<Student> selectCondition(Student stu);

    //根据多个id查询
    List<Student> selectByIds(List<Integer> ids);
}
