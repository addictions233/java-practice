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
    public abstract List<Student> selectAll();

    //分页查询
    public abstract List<Student> selectByPage(int currentPage, int pageSize);

    //根据id查询
    public abstract Student selectById(Integer id);

    //新增数据
    public abstract Integer insert(Student stu);

    //修改数据
    public abstract Integer update(Student stu);

    //删除数据
    public abstract Integer delete(Integer id);

    //多条件查询
    public abstract List<Student> selectCondition(Student stu);

    //根据多个id查询
    public abstract List<Student> selectByIds(List<Integer> ids);
}
