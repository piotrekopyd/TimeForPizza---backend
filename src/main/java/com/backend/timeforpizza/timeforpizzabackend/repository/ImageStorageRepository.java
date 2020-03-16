package com.backend.timeforpizza.timeforpizzabackend.repository;

import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Repository
public interface ImageStorageRepository {
    String uploadFile(MultipartFile file, Long recipeId) throws IOException;
    void deleteFile(Long recipeId, String objectName);
    Boolean deleteAllFilesByRecipeId(Long recipeId);
}
