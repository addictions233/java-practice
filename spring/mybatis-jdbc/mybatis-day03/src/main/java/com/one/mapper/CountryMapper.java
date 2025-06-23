package com.one.mapper;

import com.one.bean.Country;

import java.util.List;

/**
 * @author one
 *  MyBatis框架的核心功能是简化了传统三层架构中的Dao层代码开发量
 *  mapper持久层取代了传统的dao层,简化了和数据库直接交互的dao层代码,甚至将dao层简化为了接口代理的形式
 *  原本的Dao层是要执行sql语句且将sql语句执行的结果封装到与数据表相对应的实体类对象返回给service层
 *  现在一部分Dao层代码向service层转移,而执行sql语句的功能由于重复的代码量太多,由mybatis通过抽取公共代码
 *  并加载映射配置文件CountryMapper.xml实现sql语句的执行和结果映射返回
 */
public interface CountryMapper {
    /**
     * 查询所有
     */
    public List<Country> selectAll();

    /**
     * 通过 id查询
     */
    public Country selectById(Integer id);

    /**
     * 插入一条数据
     */
    public Integer insert (Country country);

    /**
     * 修改一条数据
     */
    public Integer update(Country country);

    /**
     * 删除一条数据
     */
    public Integer delete(Integer id);

}
