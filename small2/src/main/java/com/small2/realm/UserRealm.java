package com.small2.realm;

import com.small2.pojo.Administrator;
import com.small2.pojo.User;
import com.small2.service.AdminService;
import com.small2.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import java.util.List;

public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String loginName = (String) principalCollection.fromRealm(getName()).iterator().next();
        if (loginName == null || loginName.length() == 0) {
            return null;
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        User user=userService.findByUsername(loginName);
        if (user != null) {
            info.addRole("user");
        }
        return info;
    }
    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String userName = token.getPrincipal().toString();
        User user = userService.findByUsername(userName);
        if (user==null) {
            throw new UnknownAccountException();
        }
        return new SimpleAuthenticationInfo(userName,user.getPassword(),getName());
    }
}
