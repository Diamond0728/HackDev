package com.group.project.shiro;

import com.group.project.entity.User;
import com.group.project.service.impl.UserServiceImpl;
import com.group.project.entity.UserWithPwd;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

public class UserRealm extends AuthorizingRealm{
    @Resource
    private UserServiceImpl userService;
//    @Resource
//    private RoleServiceImpl roleService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//        String currentLoginName = (String)principals.getPrimaryPrincipal();
//        List<String> userRoles = new ArrayList<String>();
//        List<String> userPermissions = new ArrayList<String>();
        //从数据库中获取当前登录用户的详细信息
//        User user = userService.getOneByUsername(currentLoginName);
//        if(null != user){
//            //获取当前用户下所有ACL权限列表
//            userPermissions = userService.findPermissionByUser(user);
//
//            //获取当前用户下拥有的所有角色列表
//            List<Role> roles = roleService.findByUserId(user.getU_id());
//            for (int i = 0; i < roles.size(); i++) {
//                userRoles.add(roles.get(i).getRolename());
//            }
//        }else{
//            throw new AuthorizationException();
//        }
//        System.out.println("#######获取角色："+userRoles);
//        System.out.println("#######获取权限："+userPermissions);
//        //为当前用户设置角色和权限
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
//        authorizationInfo.addRoles(userRoles);
//        authorizationInfo.addStringPermissions(userPermissions);
        return authorizationInfo;
    }

    /**
     * 验证当前登录的Subject
     * LoginController.login()方法中执行Subject.login()时 执行此方法
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken authcToken) throws AuthenticationException {
        System.out.println("###【开始认证[SessionId]】"+ SecurityUtils.getSubject().getSession().getId());
        String loginName = (String)authcToken.getPrincipal();
        UserWithPwd user = userService.getOneWithPwdByUsername(loginName);
        if(user == null) {
            throw new UnknownAccountException();//没找到帐号
        }
        System.out.println(user.getPassword());
        //交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user.getUsername(), //用户名
                user.getPassword(), //密码
                //ByteSource.Util.bytes(user.getCredentialsSalt()),//salt=username+salt,采用明文访问时，不需要此句
                getName()  //realm name
        );
        return authenticationInfo;
    }
}
