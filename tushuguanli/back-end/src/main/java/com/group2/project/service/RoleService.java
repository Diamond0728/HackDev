package com.group2.project.service;

import com.group2.project.entity.Role;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleService {
    List<Role> findByUserId(Integer u_id);
    Integer getIdByName(String name);
    Role[] getAllRoles();
}
