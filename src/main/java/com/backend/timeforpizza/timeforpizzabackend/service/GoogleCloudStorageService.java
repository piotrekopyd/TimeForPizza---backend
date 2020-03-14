package com.backend.timeforpizza.timeforpizzabackend.service;

import com.backend.timeforpizza.timeforpizzabackend.repository.ImagesStorageRepository;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;

@Service("googleCloudStorage")
@Qualifier("googleCloudStorage")
public class GoogleCloudStorageService implements ImagesStorageRepository {

    @Value("${google.cloud.storage.project.id}")
    private String projectId;

    @Value("${google.cloud.storage.bucket.name}")
    private String bucketName;

    @Value("${google.cloud.storage.credentials.path}")
    private String credentialsPath;

    @Value("${google.cloud.storage.api.path}")
    private String storageApiPath;

    private Storage storage;

    public GoogleCloudStorageService() {
    }

    @PostConstruct
    void setupStorage() {
        try {
            StorageOptions storageOptions = StorageOptions.newBuilder()
                    .setProjectId(projectId)
                    .setCredentials(GoogleCredentials.fromStream(new
                            FileInputStream(credentialsPath)
                    )).build();

            storage = storageOptions.getService();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public String uploadFile(MultipartFile file, Long recipeId) throws IOException {

        String fileName = file.getOriginalFilename() != null ? recipeId + "/" + file.getOriginalFilename() : "";
        BlobId blobId = BlobId.of(this.bucketName, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(file.getContentType()).build();
        Blob blob = storage.create(blobInfo, file.getBytes());
        return buildImagePath(blob.getName());
    }

    public void deleteAllFilesWithPrefix(String prefix) {

    }

    public void deleteFile(Long recipeId, String objectName) {
        String fileName = recipeId + "/" + objectName;
        storage.delete(bucketName, fileName);
    }

    private String buildImagePath(String fileName) {
        return storageApiPath + bucketName + "/" + fileName;
    }


}
