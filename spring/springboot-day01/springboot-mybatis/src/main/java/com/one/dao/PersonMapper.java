package com.one.dao;

import com.one.pojo.Person;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author one
 * @description TODO
 * @date 2022-8-26
 */
@Mapper
public interface PersonMapper {
    int saveBatch(List<Person> insertList);
}
