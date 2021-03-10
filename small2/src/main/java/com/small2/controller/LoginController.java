package com.small2.controller;

import com.small2.pojo.User;
import com.small2.result.Result;
import com.small2.result.ResultFactory;
import com.small2.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin
public class LoginController {

    @Autowired
    UserService userService;

    //拿来做测试的
    @RequestMapping("/login")
    public String login(String username, String password, Model model){
        Subject subject= SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            subject.login(token);
            return "index";
        } catch (IncorrectCredentialsException e) {
            model.addAttribute("msg", "密码错误");
            return "login";
        } catch (UnknownAccountException e) {
            model.addAttribute("msg", "用户名不存在");
            return "login";
        }
    }

    //用来与前端链接的版本
    //用户登录
    @PostMapping("/logins")
    public Result login(@RequestBody User user){
        Subject subject= SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        try {
            subject.login(token);
            return ResultFactory.buildSuccessResult(user.getUsername());
        } catch (IncorrectCredentialsException e) {
            return ResultFactory.buildFailResult("密码错误");
        } catch (UnknownAccountException e) {
            return ResultFactory.buildFailResult("用户名不存在");
        }
    }
    //用户注册
    @PostMapping("/regist")
    public Result regist(@RequestBody User user){
        int status = userService.regist(user);
        switch (status) {
            case 0:
                return ResultFactory.buildFailResult("用户名和密码不能为空");
            case 1:
                return ResultFactory.buildFailResult("该用户已存在，用户名不可重复");
            case 2:
                return ResultFactory.buildSuccessResult(user.getUsername());
        }
        return ResultFactory.buildFailResult("未知错误");
    }
    //用户登出
    @GetMapping("/logout")
    public Result logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return ResultFactory.buildSuccessResult("login");
    }
}
