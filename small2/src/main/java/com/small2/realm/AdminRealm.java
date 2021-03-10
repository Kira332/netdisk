package com.small2.realm;

import com.small2.pojo.Administrator;
import com.small2.pojo.User;
import com.small2.service.AdminService;
import com.small2.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class AdminRealm extends AuthorizingRealm {
    @Autowired
    private AdminService adminService;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String loginName = (String) principalCollection.fromRealm(getName()).iterator().next();
        if (loginName == null || loginName.length() == 0) {
            return null;
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Administrator administrator=adminService.findByUsername(loginName);
        if (administrator != null) {
            info.addRole("admin");
        }
        return info;
    }
    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String userName = token.getPrincipal().toString();
        Administrator administrator = adminService.findByUsername(userName);
        if (administrator==null) {
            throw new UnknownAccountException();
        }
        return new SimpleAuthenticationInfo(userName,administrator.getPassword(),getName());
    }
}
