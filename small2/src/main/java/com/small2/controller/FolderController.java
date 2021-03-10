package com.small2.controller;

import com.small2.pojo.Folder;
import com.small2.result.Result;
import com.small2.result.ResultFactory;
import com.small2.service.FolderService;
import com.small2.service.PictureService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@CrossOrigin
public class FolderController {
    @Autowired
    FolderService folderService;

    //新建文件夹
    @PostMapping("/user/creatfolder")
    public Result createFolder(String foldername){
        String uername= SecurityUtils.getSubject().getPrincipal().toString();
        if (folderService.isExist(foldername,uername)){
            return ResultFactory.buildFailResult("该文件夹已存在");
        }else {
            folderService.sava(foldername,uername);
            return ResultFactory.buildSuccessResult("成功");
        }
    }

    //返回用户所拥有的所有文件夹信息
    @GetMapping("/user/menu")
    public Result showAllFolder(){
        String uername= SecurityUtils.getSubject().getPrincipal().toString();
        List<Folder> folders=folderService.findAllByUsername(uername);
        return ResultFactory.buildSuccessResult(folders);
    }

}
