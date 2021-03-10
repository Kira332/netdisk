package com.small2.DAO;

import com.small2.pojo.Folder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FolderDao extends JpaRepository<Folder,Integer> {
    Folder findByUsernameAndName(String username,String name);
    List<Folder> findAllByUsername(String username);
}
