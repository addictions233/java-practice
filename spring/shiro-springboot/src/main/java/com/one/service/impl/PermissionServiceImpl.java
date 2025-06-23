package com.one.service.impl;

import com.one.entity.Permission;
import com.one.mapper.PermissionMapper;
import com.one.service.PermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @author zjw
 * @description
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Resource
    private PermissionMapper permissionMapper;


    @Override
    public Set<Permission> findPermsByRoleSet(Set<Integer> roleIdSet) {
        return permissionMapper.findPermsByRoleIdIn(roleIdSet);
    }
}
