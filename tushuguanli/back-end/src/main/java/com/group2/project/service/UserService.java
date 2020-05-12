package com.group2.project.service;

import com.group2.project.entity.User;
import com.group2.project.entity.UserDetail;
import com.group2.project.entity.UserWithPwd;
import com.group2.project.entity.UserWithRoles;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    User[] getAllUser();
    UserWithPwd getOneWithPwdByUsername(String username);
    User getOneByUsername(String username);
    User getOneByUid(Integer u_id);
    Integer getIdByName(String username);
    List<String> findPermissionByUser(User user);
    void register(String username, String password);
    UserDetail getUserDetailByUid(Integer u_id);
    List getUserWithRoles(Integer pg, Integer count) ;
    void deleteUserById(Integer u_id);
    void saveUserWithRoles(UserWithRoles userWithRoles) throws Exception;
    void updateByUser(User user);
}
