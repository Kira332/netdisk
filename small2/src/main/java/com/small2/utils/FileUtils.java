package com.small2.utils;

import org.springframework.web.multipart.MultipartFile;

public class FileUtils {
    public static String getType(MultipartFile file){
        return file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
    }
}
