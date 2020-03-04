package com.backend.timeforpizza.timeforpizzabackend.repository;

import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Repository
public interface ImagesStorageRepository {
    public String uploadFile(MultipartFile file, Long recipeId) throws IOException;
    public void deleteFile(String objectName);
}
