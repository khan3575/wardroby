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
import java.util.List;
import java.util.UUID;

@Service
@ConditionalOnProperty(name="storage.provider", havingValue="local")
public class LocalStorageServiceImpl implements ImageStorageService {
    private static final List<String> ALLOWED_IMAGE_TYPE = List.of("image/jpeg", "image/png", "image/webp");

    //takes upload directory from the properties file
    @Value("${upload.detail.dir}")
    private String uploadDir;


    @Override
    public String uploadImage(MultipartFile file, Long userId) {
        /* file uploading and returning accessable file path */
//        if file empty
        if(file.isEmpty())
        {
            throw new ItemException("Item upload failed");
        }
        try{

            Path userDir = Paths.get(uploadDir, String.valueOf(userId));
//            File dir = new File(userDir);
            // check if dir exists or create
            Files.createDirectories(userDir);

            String contentType = file.getContentType();
            if(contentType == null || !ALLOWED_IMAGE_TYPE.contains(contentType))
            {
                throw new ItemException("Unsupported Content Type (supports only : jpeg, png, webp only) ");
            }
//            if(!dir.exists())
//            {
//                dir.mkdirs();
//            }
            // extension are seted to allowed content type.
            String extension = "."+contentType.substring(contentType.lastIndexOf("/")+1);
            String uniqueFileName = UUID.randomUUID()+extension;

            Path targetLocation = userDir.resolve(uniqueFileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return "/uploads/"+userId+"/"+uniqueFileName;
        }
        catch(ItemException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            throw new ItemException("File not created. Permission issue");
        }
    }

    @Override
    public void deleteImage(String filePath) {

        /* deleting the local file */
        try{
            //trimming "/uploads/"
            String filename = filePath.substring("/uploads/".length());
            Path path = Paths.get(uploadDir).resolve(filename);
            Files.deleteIfExists(path);
        }
        catch(Exception e)
        {
            throw new ItemException("deletation filed");
        }
    }
}
