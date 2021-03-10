package com.small2.DAO;

import com.small2.pojo.Administrator;
import com.small2.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminDao extends JpaRepository<Administrator,Integer> {
    Administrator findByUsername(String username);
    Administrator getByUsernameAndPassword(String username,String password);
}
