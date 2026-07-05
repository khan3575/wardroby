package com.khan.wardroby.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageStorageService {
    public String uploadImage(MultipartFile img, Long userId);
    public void deleteImage(String filePath);

}
