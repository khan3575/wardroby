package com.khan.wardroby.service.impl;

import com.khan.wardroby.service.ImageStorageService;
import org.springframework.web.multipart.MultipartFile;

public class S3StorageServiceImpl implements ImageStorageService {



    @Override
    public String uploadImage(MultipartFile img, Long userId) {
        return "";
    }

    @Override
    public void deleteImage(String filePath) {

    }
}
