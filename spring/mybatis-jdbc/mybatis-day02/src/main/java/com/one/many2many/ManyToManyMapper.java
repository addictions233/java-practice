package com.one.many2many;

import com.one.bean.Student;

import java.util.List;

public interface ManyToManyMapper {
    /**
     *  多表查询多对多情况
     */
    List<Student> selectAll();
}
