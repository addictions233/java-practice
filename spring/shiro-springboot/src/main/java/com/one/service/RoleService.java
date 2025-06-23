package com.one.service;

import com.one.entity.Role;

import java.util.Set;

/**
 * @author zjw
 * @description
 */
public interface RoleService {

    /**
     * 根据用户id查询角色信息
     * @param uid
     * @return
     */
    Set<Role> findRolesByUid(Integer uid);
}
