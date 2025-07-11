package com.one.one2many;

import com.one.bean.Classes;

import java.util.List;

/**
 * 一对多: 在多的一方表中建立外键, 关联一的一方表中的主键  例如: student表 和 class班级表
 * 一个班级可以有多个学生, 一个学生只能属于一个班级
 */
public interface OneToManyMapper {
    /**
     *  多表查询一对多,查询所有
     */
    List<Classes> selectAll();
}
