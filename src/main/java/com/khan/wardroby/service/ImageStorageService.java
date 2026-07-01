package com.khan.wardroby.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageStorageService {
    public String uploadImage(MultipartFile img);
    public void deleteImage(String filePath);

}
