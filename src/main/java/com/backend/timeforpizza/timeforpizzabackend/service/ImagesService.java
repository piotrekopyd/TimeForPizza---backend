package com.backend.timeforpizza.timeforpizzabackend.service;

import com.backend.timeforpizza.timeforpizzabackend.repository.ImagesRepository;
import com.backend.timeforpizza.timeforpizzabackend.repository.ImagesStorageRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImagesService {

    private ImagesRepository imagesRepository;

    private ImagesStorageRepository imagesStorageRepository;

    ImagesService(@Qualifier("imagesRepository") ImagesRepository imagesRepository, @Qualifier("googleCloudStorage") ImagesStorageRepository imagesStorageRepository) {
        this.imagesRepository = imagesRepository;
        this.imagesStorageRepository = imagesStorageRepository;
    }

    public void deleteFile() {

    }

    @Transactional
    public void uploadImages(Long recipeId, MultipartFile[] multipartFiles) {
        for (MultipartFile file: multipartFiles) {
            try {
                imagesStorageRepository.uploadFile(file);
                System.out.println(file.getName());

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }


    public void uploadImage(MultipartFile multipartFile) {
        System.out.println(multipartFile.getName());
    }


}