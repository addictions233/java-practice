package com.one.mapper;


import com.one.pojo.SystemUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SystemUserMapper {
    @Insert("insert into system_user (name ,age) values ('张三', 23)")
    Integer insertUser(SystemUser systemUser);

    @Select("select * from system_user where id = #{id}")
    SystemUser selectById(Long id);
}
