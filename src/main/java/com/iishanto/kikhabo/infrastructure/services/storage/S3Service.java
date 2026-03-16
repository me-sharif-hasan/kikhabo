package com.iishanto.kikhabo.infrastructure.services.storage;

import com.iishanto.kikhabo.infrastructure.config.S3Properties;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Service
@AllArgsConstructor
public class S3Service {

    private final S3Client s3Client;
    private final S3Properties s3Properties;

    public String uploadFile(MultipartFile file, String folder) throws IOException {
        String extension = getExtension(file.getOriginalFilename());
        String key = folder + "/" + UUID.randomUUID() + extension;

        s3Client.putObject(
                PutObjectRequest.builder()
                        .bucket(s3Properties.getBucketName())
                        .key(key)
                        .contentType(file.getContentType())
                        .build(),
                RequestBody.fromBytes(file.getBytes())
        );

        return "https://%s.s3.%s.amazonaws.com/%s"
                .formatted(s3Properties.getBucketName(), s3Properties.getRegion(), key);
    }

    public String uploadBytes(byte[] bytes, String contentType, String folder, String extension) {
        String key = folder + "/" + UUID.randomUUID() + extension;
        s3Client.putObject(
                PutObjectRequest.builder()
                        .bucket(s3Properties.getBucketName())
                        .key(key)
                        .contentType(contentType)
                        .build(),
                RequestBody.fromBytes(bytes)
        );
        return "https://%s.s3.%s.amazonaws.com/%s"
                .formatted(s3Properties.getBucketName(), s3Properties.getRegion(), key);
    }

    private String getExtension(String filename) {
        if (filename == null || !filename.contains(".")) return "";
        return filename.substring(filename.lastIndexOf('.'));
    }
}
