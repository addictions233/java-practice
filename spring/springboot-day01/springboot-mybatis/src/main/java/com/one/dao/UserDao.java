package com.one.dao;


import com.one.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @InterfaceName: UserDao
 * @Description: 操作user表的映射层接口
 *          相同点: 两个注解都是用在Dao层上的。
 *          不同点:
 *             1,@Repository需要在Spring中配置扫描地址，然后生成Dao层的Bean才能被注入到Service层中。
 *                即：@Repository 需要和@MapperScan一起使用。
 *             2,@Mapper不需要配置扫描地址，通过xml里面的namespace里面的接口地址，生成了Bean后注入到Service层中。
 *                即：@Mapper可以单独使用。
 * @Author: one
 * @Date: 2020/12/15
 */
@Mapper
//@Repository  // @Repository需要结合 @MapperScan注解才能达到@Mapper注解的效果
public interface UserDao {

    /**
     * 查询所有user表中所有的用户
     * @return List<User>
     */
    @Select("select * from user")
    List<User> findAll();

    /**
     * 查询 tb_user表中的所有用户
     * @return List<User>
     */
    @Select("select id,name,age from tb_user")
    List<User> getUser();

    /**
     * 查询tb_user1表中的所有用户
     * @return List<User>
     */
    @Select("select id,name,age from tb_user1")
    List<User> getUser1();

    /**
     * 查询tb_user2表中的所有用户
     * @return List<User>
     */
    @Select("select id,name,age from tb_user2")
    List<User> getUser2();

    /**
     * 向tb_user1表中插入一条数据
     * @param user user
     */
    @Insert("insert into tb_user1 (name, age) values (#{user.name}, #{user.age})")
    void insertUser1(@Param("user") User user);

    /**
     * 向tb_user2表中插入一条数据
     * @param user user
     */
    @Insert("insert into tb_user2 (name, age) values (#{user.name}, #{user.age})")
    void insertUser2(@Param("user") User user);

    /**
     * 根据id查询User
     *
     * @param id 主键id
     * @return User
     */
    User selectById(Integer id);

    /**
     * 新增一条数据
     *
     * @param user user
     */
    void insert(User user);

    /**
     * 修改一条数据
     *
     * @param user user
     */
    void update(User user);

    /**
     * 根据id删除一条数据
     *
     * @param id 主键id
     */
    void deleteById(Integer id);
}
