package com.khan.wardroby.service.impl;

import com.khan.wardroby.exception.ItemException;
import com.khan.wardroby.service.ImageStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@ConditionalOnProperty(name="storage.provider", havingValue="s3")
public class S3StorageServiceImpl implements ImageStorageService {

    private static final List<String> ALLOWED_IMAGE_TYPES = List.of("image/jpeg","image/png","image/webp");

    private final S3Client s3Client;

    @Value("${aws.endpoint}")
    private String endpoint;

    @Value("${aws.bucket-name}")
    private String bucketName;

    @Autowired
    public S3StorageServiceImpl(S3Client s3Client)
    {
        this.s3Client=s3Client;
    }

    @Override
    public String uploadImage(MultipartFile file, Long userId) {
        if(file.isEmpty())
        {
            throw new ItemException("Item upload filed: File is empty");
        }

        try{
            String contentType = file.getContentType();
            if(contentType==null || !ALLOWED_IMAGE_TYPES.contains(contentType))
            {
                throw new ItemException("Unsupported content type. Only image/jpeg, image/png, and image/webp are supported.");
            }
            String extension = "." + contentType.substring(contentType.lastIndexOf("/")+1);
            String uniqueName = UUID.randomUUID().toString() + extension;
            String key = userId +"/" + uniqueName;


            // making the put request

            PutObjectRequest putRequest = PutObjectRequest.builder().bucket(bucketName)
                    .key(key).contentType(contentType).build();

            //directly sending the file to s3 instead of saving it. 1. opens a pipeline then read and send to s3 (for self learning)
            s3Client.putObject(putRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

            return endpoint + "/" + bucketName + "/" + key;
        }
        catch(IOException e){
            throw new ItemException("Faile to read file system: "+e.getMessage() );
        }
        catch(Exception e)
        {
            throw new ItemException("S3 upload failed "+ e.getMessage());
        }
    }

    @Override
    public void deleteImage(String filePath) {
        try {

            String prefix = endpoint + "/" + bucketName + "/";

            if (filePath == null || !filePath.startsWith(prefix)) {
                throw new ItemException("Invalid file path");
            }

            String key = filePath.substring(prefix.length());
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(bucketName).key(key).build();
            s3Client.deleteObject(deleteObjectRequest);
        }
        catch(Exception e)
        {
            throw new ItemException("S3 delete failed : "+e.getMessage() );
        }

    }

}
