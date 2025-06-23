package com.one.dao;

import org.apache.ibatis.annotations.Mapper;

/**
 * @author one
 * @description TODO
 * @date 2022-8-26
 */
@Mapper
public interface SchoolMapper {
    void deleteById(String s);

}
