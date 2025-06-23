package com.one.one_to_one;

import com.one.bean.Card;

import java.util.List;

/**
 * @author one
 * 主从表的关系: 主表的主键在从表中以外键的形式存在
 */
public interface OneToOneMapper {
    /**
     * 多表内连接查询
     * @return List<Card> 一对一查询结果
     */
    List<Card> selectAll();
}
