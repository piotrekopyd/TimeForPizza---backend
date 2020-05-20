package com.backend.timeforpizza.timeforpizzabackend.service;

import com.backend.timeforpizza.timeforpizzabackend.model.FileType;
import com.backend.timeforpizza.timeforpizzabackend.repository.FileStorageRepository;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@Service
@Qualifier("googleCloudStorage")
public class GoogleCloudStorageFileService implements FileStorageRepository {

    @Value("${google.cloud.storage.project.id}")
    private String projectId;

    @Value("${google.cloud.storage.bucket.name}")
    private String bucketName;

    @Value("${google.cloud.storage.credentials.path}")
    private String credentialsPath;

    @Value("${google.cloud.storage.api.path}")
    private String storageApiPath;

    private Storage storage;

    private static final Logger logger = LoggerFactory.getLogger(GoogleCloudStorageFileService.class);

    public GoogleCloudStorageFileService() { }

    @PostConstruct
    void setupStorage() {
        // TODO: get rid of hardcoded init
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

    public String uploadFile(MultipartFile file, FileType fileType, String prefix) throws IOException {
        String fileName = file.getOriginalFilename() != null
                ? buildFilePrefix(fileType, prefix) + "/" + file.getOriginalFilename().replaceAll(" ", "_")
                : UUID.randomUUID().toString();

        BlobId blobId = BlobId.of(this.bucketName, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(file.getContentType()).build();
        Blob blob = storage.create(blobInfo, file.getBytes());
        logger.info("Uploaded file with name: {} to {} bucket.", blob.getName(), bucketName);
        return buildFilePath(blob.getName());
    }

    public void deleteFile(FileType fileType, String prefix, String objectName) {
        String fileName = buildFilePrefix(fileType, prefix) + "/" + objectName;
        storage.delete(bucketName, fileName);
        logger.info("Deleted file with name: {}, from {} bucket..", fileName, bucketName);
    }

    public Boolean deleteAllFilesWithPrefix(FileType fileType, String prefix) {
        prefix = buildFilePrefix(fileType, prefix);
        Iterable<Blob> blobs = listOfObjectsWithPrefix(prefix.charAt(prefix.length() - 1) == '/' ? prefix : prefix + "/");
        boolean deleted = false;

        for(Blob blob: blobs) {
            deleted = blob.delete();
        }
        logger.info("Deleted all files with prefix: {}.", prefix);
        return deleted;
    }

    public Iterable<Blob> listOfObjectsWithPrefix(String prefix) {
        Bucket bucket = storage.get(bucketName);
        return bucket.list(
                        Storage.BlobListOption.prefix(prefix),
                        Storage.BlobListOption.currentDirectory()
                ).iterateAll();
    }

    public String buildFilePath(String fileName) {
        return storageApiPath + bucketName + "/" + fileName;
    }

    public String buildFilePrefix(FileType fileType, String prefix) {
        String path = "";
        switch (fileType) {
            case RECIPE_IMAGE:
                path += "recipe_images";
        }

        if (prefix.replaceAll(" ", "").length() > 0) {
            path += prefix.charAt(0) != '/' ? "/" + prefix : prefix;
        }
        return path;
    }
}
