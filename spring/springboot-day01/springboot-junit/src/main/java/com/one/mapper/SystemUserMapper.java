package com.one.mapper;


import com.one.pojo.SystemUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SystemUserMapper {
    @Insert("insert into system_user (name ,age) values ('张三', 23)")
     Integer insertUser(SystemUser systemUser);
}
