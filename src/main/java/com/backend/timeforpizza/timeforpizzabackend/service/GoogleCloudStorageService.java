package com.backend.timeforpizza.timeforpizzabackend.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Service
public class GoogleCloudStorageService {

    @Value("${google.cloud.storage.project.id}")
    private String projectId;

    @Value("${google.cloud.storage.bucket.name}")
    private String bucketName;

    @Value("${google.cloud.storage.credentials.path}")
    private String credentialsPath;

    private Storage storage;

    private RecipeService recipeService;

    public GoogleCloudStorageService(RecipeService recipeService) {
        this.recipeService = recipeService;
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

    public void uploadObject(MultipartFile file) throws IOException {

        try {
            String fileName = file.getOriginalFilename() != null ? file.getOriginalFilename() : "";
            BlobId blobId = BlobId.of(this.bucketName, fileName);

            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(file.getContentType()).build();
            Blob blob = storage.create(blobInfo, file.getBytes());

            System.out.println("");
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteObject(String bucketName, String objectName) {
        Storage storage1 = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
        storage1.delete(bucketName, objectName);
    }
}
