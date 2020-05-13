package com.group.project.service;

import com.group.project.entity.User;
import com.group.project.entity.UserWithPwd;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    User[] getAllUser();
    Integer getIdByName(String username);
    User getOneByUsername(String username);
    UserWithPwd getOneWithPwdByUsername(String username);
    User getOneByUid(Integer u_id);
    void register(String username, String password);
    void deleteUserById(Integer u_id);
    void updateByUser(User user);


}
