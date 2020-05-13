package com.group.project.service.impl;

import com.group.project.entity.*;
import com.group.project.mapper.*;
import com.group.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;
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
    public void updateByUser(User user) {
        userMapper.updateByUser(user);
    }

}
