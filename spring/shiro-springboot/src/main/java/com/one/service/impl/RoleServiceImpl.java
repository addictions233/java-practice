package com.one.service.impl;

import com.one.entity.Role;
import com.one.mapper.RoleMapper;
import com.one.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @author zjw
 * @description
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleMapper roleMapper;


    @Override
    public Set<Role> findRolesByUid(Integer uid) {
        return roleMapper.findRolesByUid(uid);
    }
}
