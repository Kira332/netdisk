package com.small2.service;

import com.small2.DAO.UserDao;
import com.small2.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserDao userDao;

    public User findByUsername(String username){
        return userDao.findByUsername(username);
    }

    public void save(User user){
        userDao.save(user);
    }

    public List<User> selectall(){
        return userDao.findAll();
    }

    public boolean isExit(String username){
        User user=userDao.findByUsername(username);
        if (user==null){
            return false;
        }else{return true;}
    }

    public int regist(User user){
        String username=user.getUsername();
        String password=user.getPassword();
//        username = HtmlUtils.htmlEscape(username);
//        user.setUsername(username);
//        password = HtmlUtils.htmlEscape(password);
//        user.setUsername(password);
        if (username.equals("")||password.equals("")){
            return 0;
        }
        boolean exit=isExit(username);
        if (exit){
            return 1;
        }
        userDao.save(user);
        return 2;
    }

}
