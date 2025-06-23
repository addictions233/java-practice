package com.one.mapper;

import com.one.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author zjw
 * @description
 */
public interface UserMapper {

    @Select("select * from users where username = #{username}")
    User findUserByUsername(@Param("username") String username);

}
