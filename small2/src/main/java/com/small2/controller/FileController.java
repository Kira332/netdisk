package com.small2.controller;

import com.small2.pojo.Folder;
import com.small2.pojo.Picture;
import com.small2.pojo.User;
import com.small2.result.Result;
import com.small2.result.ResultFactory;
import com.small2.service.FolderService;
import com.small2.service.PictureService;
import com.small2.utils.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.List;

@Controller
@CrossOrigin
public class FileController {

    @Autowired
    PictureService pictureService;

    @Autowired
    FolderService folderService;

    //上传文件
    @PostMapping("/user/upload")
    public Result upload(String foldername, MultipartFile file) {
        String uername= SecurityUtils.getSubject().getPrincipal().toString();
        int status=pictureService.upload(file, uername, folderService.findId(foldername, uername));
        switch (status) {
            case 0:
                return ResultFactory.buildFailResult("文件类型错误，请上传图片格式为jpg或png");
            case 1:
                return ResultFactory.buildSuccessResult("");
                //ummm,这里不清楚前端需要什么数据
            case 2:
                return ResultFactory.buildFailResult("文件上传失败");
        }
        return ResultFactory.buildFailResult("未知错误");
    }

    //下载文件
    @GetMapping("/user/download")
    public Result download(String filename, HttpServletResponse response)throws IOException{
        Picture picture=pictureService.findByFilename(filename);
        File file=new File(picture.getPath()+picture.getFilename());
        if (!file.exists()){
            return ResultFactory.buildFailResult("文件不存在");
        }
        pictureService.download(response, file,filename);
        return ResultFactory.buildSuccessResult("成功下载");
    }

    //管理员审核不成功，删除文件及数据库记录
    @PostMapping("/admin/delete")
    public Result delete(String filename) throws IOException {
        pictureService.delete(filename);
        return ResultFactory.buildSuccessResult("删除成功");
    }

    //管理员审核通过
    @PutMapping("/admin/audit")
    public Result audit(String filename){
        pictureService.audit(filename);
        return ResultFactory.buildSuccessResult("审核成功");
    }

    //实现分页，向用户展示文件夹内图片
    @GetMapping("/user/{folderid}/{page}")
    public Result showUserPictures(@PathVariable("folderid")int folderid,@PathVariable("page")int page){
        List<Picture>pictures=pictureService.findAllPictureByFolderid(folderid, page-1);
        return ResultFactory.buildSuccessResult(pictures);
    }

    //实现分页，向管理员展示未审核图片
    @GetMapping("/admin/{page}")
    public Result showAdministrator(@PathVariable("page")int page){
        List<Picture>pictures=pictureService.findAllPictureByIsPass(page);
        return ResultFactory.buildSuccessResult(pictures);
    }
}
