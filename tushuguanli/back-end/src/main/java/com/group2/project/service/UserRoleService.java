package com.group2.project.service;

import com.group2.project.entity.UserRole;
import org.springframework.stereotype.Service;

@Service
public interface UserRoleService {
    void addUserRole(UserRole userRole);
}
