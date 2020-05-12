package com.group2.project.service.impl;

import com.group2.project.entity.*;
import com.group2.project.mapper.*;
import com.group2.project.service.UserService;
import com.group2.project.entity.UserDetail;
import com.group2.project.entity.UserWithRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    UserRoleMapper userRoleMapper;
    @Autowired
    RoleMapper roleMapper;
    @Autowired
    RolePermissionMapper rolePermissionMapper;
    @Autowired
    PermissionMapper permissionMapper;

    @Override
    public User[] getAllUser() {
        System.out.println("serviceimpl");
        return userMapper.getAllUser();
    }

    @Override
    public Integer getIdByName(String username) {
        return userMapper.getIdByName(username);
    }

    @Override
    public User getOneByUsername(String username) {
        System.out.println(username);
        return userMapper.getOneByUsername(username);
    }

    @Override
    public UserWithPwd getOneWithPwdByUsername(String username) {
        return userMapper.getOneWithPwdByUsername(username);
    }

    @Override
    public User getOneByUid(Integer u_id) {
        return userMapper.getOneByUid(u_id);
    }

    @Override
    public List<String> findPermissionByUser(User user) {
        List<String> permissionList = new ArrayList<>();
        User user1 = this.getOneByUsername(user.getUsername());
        UserRole[] userRoles = userRoleMapper.getUserRoleByUid(user1.getU_id());
        for(int i=0; i<userRoles.length; i++) {
            RolePermission[] rolePermissions = rolePermissionMapper.getRolePermissionByRid(userRoles[i].getR_id());
            for(int j=0; j<rolePermissions.length; j++) {
                Permission permission = permissionMapper.getPermissionByPid(rolePermissions[j].getP_id());
                if(!permissionList.contains(permission.getPermissionname())) {
                    permissionList.add(permission.getPermissionname());
                }
            }
        }
        return permissionList;
    }

    @Override
    public UserDetail getUserDetailByUid(Integer u_id) {
        UserDetail userDetail = new UserDetail();
        UserRole[] userRoles = userRoleMapper.getUserRoleByUid(u_id);
        Role[] roles = roleMapper.getAllRole();
        Permission[] permissions = permissionMapper.getAllPermission();
        Map<String,Boolean> roleList = new LinkedHashMap<>();
        Map<String,Boolean> permissionList = new LinkedHashMap<>();
        for(int i=0; i<roles.length; i++) {
            roleList.put(roles[i].getRolename(),false);
        }
        for(int i=0; i<permissions.length; i++) {
            permissionList.put(permissions[i].getPermissionname(),false);
        }
        System.out.println(userRoles);
        for(int i=0; i<userRoles.length; i++) {
            String t = roleMapper.getRoleById(userRoles[i].getR_id()).getRolename();
            roleList.replace(t,true);
            RolePermission[] rolePermissions = rolePermissionMapper.getRolePermissionByRid(userRoles[i].getR_id());
            for(int j=0; j<rolePermissions.length; j++) {
                Permission permission = permissionMapper.getPermissionByPid(rolePermissions[j].getP_id());
                permissionList.replace(permission.getPermissionname(),true);
            }
        }
        userDetail.setU_id(u_id);
        userDetail.setUsername(getOneByUid(u_id).getUsername());
        userDetail.setRoles(roleList);
        userDetail.setPermissions(permissionList);
        return userDetail;
    }

    @Override
    public List getUserWithRoles(Integer pg, Integer count) {
        Map map = new HashMap();
        map.put("start",(pg-1)*count);
        map.put("count",count);
        User[] users = userMapper.getUsersLimit(map);
        List userWithRoless = new LinkedList();
        for(int j=0; j<users.length; j++) {
            Role[] roleList = roleMapper.getAllRole();
            UserRole[] userRoles = userRoleMapper.getUserRoleByUid(users[j].getU_id());
            List roles = new ArrayList();
            for(int i=0; i<userRoles.length; i++) {
                String t = roleMapper.getRoleById(userRoles[i].getR_id()).getRolename();
                roles.add(t);
            }
            UserWithRoles userWithRoles = new UserWithRoles();
            userWithRoles.setU_id(users[j].getU_id());
            userWithRoles.setUsername(users[j].getUsername());
            userWithRoles.setName(users[j].getName());
            userWithRoles.setPhone(users[j].getPhone());
            userWithRoles.setEmail(users[j].getEmail());
            userWithRoles.setRoles(roles);
            userWithRoless.add(userWithRoles);
        }
        return userWithRoless;
    }

    @Override
    public void register(String username, String password) {
        UserWithPwd user = new UserWithPwd();
        user.setUsername(username);
        user.setPassword(password);
        userMapper.insert(user);
    }

    @Override
    public void deleteUserById(Integer u_id) {
        userMapper.deleteById(u_id);
    }

    @Override
    public void saveUserWithRoles(UserWithRoles userWithRoles) throws Exception {
        try {
            User user = userMapper.getOneByUid(userWithRoles.getU_id());
            user.setPhone(userWithRoles.getPhone());
            user.setEmail(userWithRoles.getEmail());
            userMapper.update(user);
            try {
                Integer u_id = userWithRoles.getU_id();
                List<String> roleList = userWithRoles.getRoles();
                userRoleMapper.deleteAllByUid(u_id);
                for(int i=0; i<roleList.size(); i++) {
                    UserRole userRole = new UserRole();
                    userRole.setU_id(u_id);
                    Integer r_id = roleMapper.getIdByName(roleList.get(i));
                    userRole.setR_id(r_id);
                    userRoleMapper.insert(userRole);
                }
            } catch (Exception f) {
                Exception exception = new Exception("修改权限的时候出了问题");
                throw exception;
            }
        } catch (Exception e) {
            Exception exception = new Exception("修改用户信息出了问题");
            throw exception;
        }
    }
    @Override
    public void updateByUser(User user) {
        userMapper.updateByUser(user);
    }
}
