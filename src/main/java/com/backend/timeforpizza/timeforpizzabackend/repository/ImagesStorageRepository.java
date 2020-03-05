package com.backend.timeforpizza.timeforpizzabackend.repository;

import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Repository
public interface ImagesStorageRepository {
    String uploadFile(MultipartFile file, Long recipeId) throws IOException;
    void deleteFile(Long recipeId, String objectName);
}
