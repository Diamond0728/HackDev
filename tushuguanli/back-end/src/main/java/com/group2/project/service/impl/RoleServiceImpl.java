package com.group2.project.service.impl;

import com.group2.project.entity.Role;
import com.group2.project.entity.UserRole;
import com.group2.project.mapper.PermissionMapper;
import com.group2.project.mapper.RoleMapper;
import com.group2.project.mapper.RolePermissionMapper;
import com.group2.project.mapper.UserRoleMapper;
import com.group2.project.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    UserRoleMapper userRoleMapper;
    @Autowired
    RoleMapper roleMapper;

    @Override
    public List<Role> findByUserId(Integer u_id){
        List<Role> roleList = new ArrayList<>();
        UserRole[] userRoles = userRoleMapper.getUserRoleByUid(u_id);
        for(int i=0; i<userRoles.length; i++) {
            roleList.add(roleMapper.getRoleById(userRoles[i].getR_id()));
        }
        return roleList;
    }

    @Override
    public Integer getIdByName(String rolename) {
        return roleMapper.getIdByName(rolename);
    }

    @Override
    public Role[] getAllRoles() {
        return roleMapper.getAllRole();
    }
}
