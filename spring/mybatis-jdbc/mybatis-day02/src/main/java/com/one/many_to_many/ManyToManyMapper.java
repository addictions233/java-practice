package com.one.many_to_many;

import com.one.bean.Student;

import java.util.List;

public interface ManyToManyMapper {
    /**
     *  多表查询多对多情况
     */
    public List<Student> selectAll();
}
