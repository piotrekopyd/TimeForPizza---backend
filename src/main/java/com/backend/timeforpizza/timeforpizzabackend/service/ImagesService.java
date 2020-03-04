package com.backend.timeforpizza.timeforpizzabackend.service;

import com.backend.timeforpizza.timeforpizzabackend.repository.ImagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.logging.SocketHandler;

@Service
public class ImagesService {

    private ImagesRepository imagesRepository;

    private GoogleCloudStorageService googleCloudStorageService;

    ImagesService(@Qualifier("imagesRepository") ImagesRepository imagesRepository, GoogleCloudStorageService googleCloudStorageService) {
        this.imagesRepository = imagesRepository;
        this.googleCloudStorageService = googleCloudStorageService;
    }

    public void deleteFile() {

    }

    @Transactional
    public void uploadImages(Long recipeId, MultipartFile[] multipartFiles) {
        for (MultipartFile file: multipartFiles) {
            try {
                googleCloudStorageService.uploadObject(file);
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