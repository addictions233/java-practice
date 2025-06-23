package com.one.mapper;

import com.one.entity.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

/**
 * @author zjw
 * @description
 */
public interface PermissionMapper {

    Set<Permission> findPermsByRoleIdIn(@Param("roleIdSet") Set<Integer> roleIdSet);
}
