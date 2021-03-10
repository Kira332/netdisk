package com.small2.service;

import com.small2.DAO.PictureDao;
import com.small2.pojo.Picture;
import com.small2.result.ResultFactory;
import com.small2.utils.FileUtils;
import com.small2.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Timestamp;
import java.util.List;

@Service
public class PictureService {

    @Autowired
    PictureDao pictureDao;

    public void save(Picture picture){
        pictureDao.save(picture);
    }

    public Picture findByFilename(String filename){return pictureDao.findByFilename(filename);}

    public int upload(MultipartFile file,String uername,int foldid){

        String folder = "D:/img/";
        File imageFolder = new File(folder+foldid);

        String type= FileUtils.getType(file);
        String fileName=StringUtils.getRandomString();
        File f = new File(imageFolder, StringUtils.getRandomString()+type);
        if (!f.getParentFile().exists()){
            f.getParentFile().mkdirs();
        }
        if (!(type.equals(".jpg") || type.equals(".png"))){
            return 0;
        }
        try {
            file.transferTo(f);
            Picture picture=new Picture();
            picture.setFilename(fileName+type);
            picture.setUsername(uername);
            picture.setFoldid(foldid);
            picture.setTimestamp(new Timestamp(System.currentTimeMillis()));
            picture.setPass(false);
            picture.setType(type);
            picture.setPath("/img/"+foldid);
            pictureDao.save(picture);
            return 1;
        } catch (IOException e) {
            e.printStackTrace();
            return 2;
        }
    }

    public void download(HttpServletResponse response,File file,String filename) {

        response.setContentType("application/force-download");
        response.setHeader("Content-Disposition", "attachment;fileName=" + filename);

        byte[] buffer = new byte[1024];
        FileInputStream fis = null; //文件输入流
        BufferedInputStream bis = null;
        OutputStream os = null; //输出流
        try {
            os = response.getOutputStream();
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            int i = bis.read(buffer);
            while(i != -1){
                os.write(buffer);
                i = bis.read(buffer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            bis.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void delete(String filename) throws IOException {
        Picture picture= pictureDao.findByFilename(filename);
        Path path=Paths.get(picture.getPath());
        Files.delete(path);
        pictureDao.delete(picture);
    }

    public void audit(String filename){
        Picture picture=pictureDao.findByFilename(filename);
        picture.setPass(true);
        pictureDao.save(picture);
    }

    public List<Picture> findAllPictureByFolderid(int folderid,int page){
        int pageSize=8;
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        Pageable pageable= PageRequest.of(page, pageSize, sort);
        Page<Picture> pictures=pictureDao.findPicturePageByFoldid(folderid, pageable);
        List<Picture> pictureList=pictures.getContent();
        return pictureList;
    }

    public List<Picture> findAllPictureByIsPass(int page){
        int pageSize=8;
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        Pageable pageable= PageRequest.of(page, pageSize, sort);
        Page<Picture> pictures=pictureDao.findPictureByIsPass(pageable);
        List<Picture> pictureList=pictures.getContent();
        return pictureList;
    }
}
