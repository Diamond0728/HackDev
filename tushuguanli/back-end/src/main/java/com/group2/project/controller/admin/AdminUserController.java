package com.group2.project.controller.admin;

import com.group2.project.common.ResponseDomain;
import com.group2.project.entity.Role;
import com.group2.project.entity.UserWithRoles;
import com.group2.project.service.RoleService;
import com.group2.project.service.impl.RoleServiceImpl;
import com.group2.project.service.impl.UserServiceImpl;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/admin")
public class AdminUserController {
    @Autowired
    UserServiceImpl userService;
    @Autowired
    RoleServiceImpl roleService;

    @RequiresRoles("admin")
    @RequestMapping(value = "/user/list",method = RequestMethod.GET)
    public ResponseDomain userList(@RequestParam("pg") Integer pg) {
        ResponseDomain responseDomain = new ResponseDomain();
        try {
            System.out.println(pg);
            List userWithRoles = userService.getUserWithRoles(pg, 10);
            System.out.println(userWithRoles);
            responseDomain.setData(userWithRoles);
        } catch (Exception e) {
            responseDomain.fail("系统繁忙,稍后重试");
        }
        return responseDomain;
    }

    @RequiresRoles("admin")
    @RequestMapping(value = "/all/roles",method = RequestMethod.GET)
    public ResponseDomain getAllRoles (){
        ResponseDomain responseDomain = new ResponseDomain();
        try {
            Role[] roles = roleService.getAllRoles();
            responseDomain.setData(roles);
        } catch (Exception e) {
            responseDomain.fail("系统繁忙,稍后重试");
        }
        return responseDomain;
    }

    @RequiresRoles("admin")
    @RequestMapping(value = "/change/user",method = RequestMethod.POST)
    public ResponseDomain changeUser(@RequestBody UserWithRoles userWithRoles){
        ResponseDomain responseDomain = new ResponseDomain();
        try {
            userService.saveUserWithRoles(userWithRoles);
        } catch (Exception e) {
            responseDomain.fail(e.getMessage());
        }
        return responseDomain;
    }
}
