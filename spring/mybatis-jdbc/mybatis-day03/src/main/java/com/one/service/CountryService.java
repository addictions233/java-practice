package com.one.service;

import com.one.bean.Country;

import java.util.List;


public interface CountryService {
    /**
     * 查询所有
     */
    List<Country> selectAll();

    /**
     * 通过 id查询
     */
    Country selectById(Integer id);

    /**
     * 插入一条数据
     */
    Integer insert (Country country);

    /**
     * 修改一条数据
     */
    Integer update(Country country);

    /**
     * 删除一条数据
     */
    Integer delete(Integer id);
}
