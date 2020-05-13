package com.backend.timeforpizza.timeforpizzabackend.repository;

import com.backend.timeforpizza.timeforpizzabackend.model.FileType;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Repository
public interface FileStorageRepository {
    String uploadFile(MultipartFile file, FileType fileType, String prefix) throws IOException;
    void deleteFile(Long recipeId, String objectName);
    Boolean deleteAllFilesWithPrefix(FileType fileType, String prefix);
}
