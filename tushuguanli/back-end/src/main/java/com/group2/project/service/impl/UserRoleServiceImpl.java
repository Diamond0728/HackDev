package com.group2.project.service.impl;

import com.group2.project.entity.UserRole;
import com.group2.project.mapper.UserRoleMapper;
import com.group2.project.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    UserRoleMapper userRoleMapper;

    @Override
    public void addUserRole(UserRole userRole){
        userRoleMapper.insert(userRole);
    }
}
