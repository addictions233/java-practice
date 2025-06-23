package com.one.one_to_many;

import com.one.bean.Classes;

import java.util.List;

public interface OneToManyMapper {
    /**
     *  多表查询一对多,查询所有
     */
    List<Classes> selectAll();
}
