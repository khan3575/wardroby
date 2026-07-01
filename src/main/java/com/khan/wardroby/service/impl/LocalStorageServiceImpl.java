package com.khan.wardroby.service.impl;

import com.khan.wardroby.exception.ItemException;
import com.khan.wardroby.service.ImageStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@ConditionalOnProperty(name="storage.provider", havingValue="local")
public class LocalStorageServiceImpl implements ImageStorageService {

    //takes upload directory from the properties file
    @Value("${upload.detail.dir}")
    private String uploadDir;


    @Override
    public String uploadImage(MultipartFile file) {
        /* file uploading and returning accessable file path */
//        if file empty
        if(file.isEmpty())
        {
            throw new ItemException("Item upload failed");
        }
        try{
            File dir = new File(uploadDir);
            // check if dir exists or create
            if(!dir.exists())
            {
                dir.mkdirs();
            }
            // extension is set to .jpg as test latter make it dynamic.
            String extension = ".jpg";
            String uniqueFileName = UUID.randomUUID()+extension;

            Path targetLocation = Paths.get(uploadDir).resolve(uniqueFileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return "/uploads/"+uniqueFileName;
        } catch (Exception e)
        {
            throw new ItemException("Failed to save file");
        }
    }

    @Override
    public void deleteImage(String filePath) {

        /* deleteing the local file */
        try{
            String filename = filePath.substring(filePath.lastIndexOf("/")+1);
            Path path = Paths.get(uploadDir).resolve(filename);
            Files.deleteIfExists(path);
        }
        catch(Exception e)
        {
            throw new ItemException("deletation filed");
        }
    }
}
