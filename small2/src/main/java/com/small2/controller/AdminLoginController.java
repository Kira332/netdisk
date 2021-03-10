package com.small2.controller;

import com.small2.pojo.Administrator;
import com.small2.pojo.User;
import com.small2.result.Result;
import com.small2.result.ResultFactory;
import com.small2.service.AdminService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class AdminLoginController {

    @Autowired
    AdminService adminService;

    //管理员登录
    @PostMapping("/adminlogin")
    public Result login(@RequestBody Administrator administrator){
        Subject subject= SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(administrator.getUsername(), administrator.getPassword());
        try {
            subject.login(token);
            return ResultFactory.buildSuccessResult(administrator.getUsername());
        } catch (IncorrectCredentialsException e) {
            return ResultFactory.buildFailResult("密码错误");
        } catch (UnknownAccountException e) {
            return ResultFactory.buildFailResult("用户名不存在");
        }
    }

    //管理员登出
    @GetMapping("/adminlogout")
    public Result logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return ResultFactory.buildSuccessResult("adminlogin");
    }
}
