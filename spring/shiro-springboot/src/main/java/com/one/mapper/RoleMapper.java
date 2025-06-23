package com.one.mapper;

import com.one.entity.Role;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Set;

/**
 * @author zjw
 * @description
 */
public interface RoleMapper {

    @Select("select r.* from users r, user_role ur where r.id = ur.rid and ur.uid = #{uid}")
    Set<Role> findRolesByUid(@Param("uid") Integer uid);

}
