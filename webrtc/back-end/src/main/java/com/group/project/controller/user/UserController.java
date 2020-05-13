package com.group.project.controller.user;

import com.group.project.common.EncodeUtil;
import com.group.project.common.ResponseDomain;
import com.group.project.entity.*;
import com.group.project.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.hibernate.SessionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class UserController {
    final String SALT = "SGKAERHUNLSIONF";
    @Autowired
    UserService userService;
    @RequestMapping(value = "/api/login",method = RequestMethod.POST)
    public ResponseDomain login(@RequestBody UserWithPwd userPara) {
        ResponseDomain responseDomain = new ResponseDomain<>();
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
            User user = userService.getOneByUid(u_id);
            responseDomain.setData(user);
        } catch (UnknownAccountException uae) {
            uae.printStackTrace();
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
            e.printStackTrace();
            responseDomain.fail(e.getMessage());
        }
        return responseDomain;
    }

    @RequestMapping(value = "/api/register",method = RequestMethod.POST)
    public ResponseDomain register(@RequestBody UserWithPwd userPara) {
        ResponseDomain responseDomain = new ResponseDomain<>();
        String username = userPara.getUsername();
        System.out.println("1111111111");
        System.out.println(username);
        System.out.println("1111111111");
        if(userService.getOneByUsername(username) == null) {
            String password = new String("1");
            password = EncodeUtil.MD5(password + SALT);
            try {
                userService.register(username,password);
                Integer u_id = userService.getIdByName(username);
                User user = userService.getOneByUid(u_id);
                user.setName(userPara.getName());
                userService.updateByUser(user);
            } catch (Exception e) {
                e.printStackTrace();
                responseDomain.fail("注册失败");
            }
            if (!responseDomain.isSuccess()) {
                return responseDomain;
            }
            userPara.setUsername(username);
            userPara.setPassword("1");
            return login(userPara);
        } else {
            responseDomain.fail("换个名字吧，这个名字被使用了");
            return responseDomain;
        }
    }


}
