package com.small2.service;

import com.small2.DAO.FolderDao;
import com.small2.pojo.Folder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FolderService {
    @Autowired
    FolderDao folderDao;

    public boolean isExist(String name,String username){
        Folder folder=folderDao.findByUsernameAndName(username, name);
        if (folder==null){
            return false;
        }else return true;
    }
    public void sava(String name,String username){
        Folder folder=new Folder();
        folder.setName(name);
        folder.setUsername(username);
        folderDao.save(folder);
    }

    public int findId(String name,String username){
        Folder folder=folderDao.findByUsernameAndName(username, name);
        return folder.getId();
    }

    public List<Folder> findAllByUsername(String username){
        return folderDao.findAllByUsername(username);
    }
}
