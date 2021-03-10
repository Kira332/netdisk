package com.small2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@CrossOrigin
//是拿来做测试的
public class MyController {
    @RequestMapping({"/"})
    public String toIndex(Model model){
        model.addAttribute("msg","HelloWorld");
        return "index";
    }

    @RequestMapping("/toLogin")
    public String toLogin(){
        return "login";
    }

    @RequestMapping("/user")
    public String toUser(){
        return "user";
    }

    @RequestMapping("/admin")
    public String toAdmin(){
        return "admin";
    }
}
