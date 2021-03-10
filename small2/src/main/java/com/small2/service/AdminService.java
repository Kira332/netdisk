package com.small2.service;

import com.small2.DAO.AdminDao;
import com.small2.pojo.Administrator;
import com.small2.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @Autowired
    AdminDao adminDao;
    public Administrator findByUsername(String username){
        return adminDao.findByUsername(username);
    }
}
