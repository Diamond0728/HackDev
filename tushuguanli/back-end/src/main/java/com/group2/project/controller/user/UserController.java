package com.group2.project.controller.user;

import com.group2.project.common.EncodeUtil;
import com.group2.project.common.ResponseDomain;
import com.group2.project.common.SendEmail;
import com.group2.project.entity.*;
import com.group2.project.service.RoleService;
import com.group2.project.service.UserService;
import com.group2.project.service.impl.RoleServiceImpl;
import com.group2.project.service.impl.UserRoleServiceImpl;
import com.group2.project.service.impl.UserServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.hibernate.SessionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    final String SALT = "SGKAERHUNLSIONF";
    @Autowired
    UserServiceImpl userService;
    @Autowired
    RoleServiceImpl roleService;
    @Autowired
    UserRoleServiceImpl userRoleService;

    @RequestMapping(value = "/api/login",method = RequestMethod.POST)
    public ResponseDomain<UserDetail> login(@RequestBody UserPara userPara) {
        ResponseDomain<UserDetail> responseDomain = new ResponseDomain<>();
        String username = userPara.getUsername();
        System.out.println(username);
        String password = userPara.getPassword();
        password = EncodeUtil.MD5(password + SALT);
        System.out.println(password);
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        Subject currentUser = SecurityUtils.getSubject();
        try {
            currentUser.login(token);
            Integer u_id = userService.getOneByUsername(username).getU_id();
            UserDetail userDetail = userService.getUserDetailByUid(u_id);
            responseDomain.setData(userDetail);
        } catch (UnknownAccountException uae) {
            responseDomain.fail("用户名或密码错误");
        }
        if (!responseDomain.isSuccess()) {
            token.clear();
        }
        return responseDomain;
    }

    @RequestMapping(value = "/api/logout",method = RequestMethod.GET)
    public ResponseDomain logout() {
        ResponseDomain responseDomain = new ResponseDomain();
        try {
            Subject currentUser = SecurityUtils.getSubject();
            currentUser.logout();
        } catch (SessionException e) {
            responseDomain.fail(e.getMessage());
        }
        return responseDomain;
    }

    @RequestMapping(value = "/api/register",method = RequestMethod.POST)
    public ResponseDomain<UserDetail> register(@RequestBody UserPara userPara) {
        ResponseDomain<UserDetail> responseDomain = new ResponseDomain<>();
        String username = userPara.getUsername();
        if(userService.getOneByUsername(username) == null) {
            String password = userPara.getPassword();
            password = EncodeUtil.MD5(password + SALT);
            try {
                userService.register(username,password);
                Integer u_id = userService.getIdByName(username);
                try {
                    UserRole userRole = new UserRole();
                    userRole.setU_id(u_id);
                    userRole.setR_id(roleService.getIdByName("ordinary"));
                    userRoleService.addUserRole(userRole);
                } catch (Exception f) {
                    responseDomain.fail("权限分配出现问题");
                    userService.deleteUserById(u_id);
                }
            } catch (Exception e) {
                responseDomain.fail("注册失败");
            }
            if (!responseDomain.isSuccess()) {
                return responseDomain;
            }
            return login(userPara);
        } else {
            responseDomain.fail("该用户名已存在");
            return responseDomain;
        }
    }

    @RequestMapping(value = "/api/current/user",method = RequestMethod.GET)
    public ResponseDomain currentUser (){
        ResponseDomain responseDomain = new ResponseDomain();
        try{
            Subject currentUser = SecurityUtils.getSubject();
            String currentUserName = String.valueOf(currentUser.getPrincipal());
            User user = userService.getOneByUsername(currentUserName);
            responseDomain.setData(user);
        } catch (Exception e) {
            responseDomain.fail("获取用户信息失败");
        }
        return responseDomain;
    }

    @RequestMapping(value = "/api/update/user",method = RequestMethod.POST)
    public ResponseDomain updateUser(@RequestBody User user) {
        ResponseDomain responseDomain = new ResponseDomain();
        try {
            Subject currentUser = SecurityUtils.getSubject();
            String currentUserName = String.valueOf(currentUser.getPrincipal());
            Integer u_id = userService.getIdByName(currentUserName);
            user.setU_id(u_id);
            userService.updateByUser(user);
        }catch (Exception e) {
            e.printStackTrace();
            responseDomain.fail("更新失败");
        }
        return responseDomain;
    }

    @RequestMapping(value = "/index")
    public String index () {
//        SendEmail sendEmail = new SendEmail();
        return "111";
    }

}
