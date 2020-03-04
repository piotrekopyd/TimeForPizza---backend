package com.backend.timeforpizza.timeforpizzabackend.controllers;

import com.backend.timeforpizza.timeforpizzabackend.repository.ImagesStorageRepository;
import com.backend.timeforpizza.timeforpizzabackend.service.GoogleCloudStorageService;
import com.backend.timeforpizza.timeforpizzabackend.service.ImagesService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(path = "/images")
public class ImagesController {

    private ImagesService imagesService;

    public ImagesController(ImagesService imagesService) {
        this.imagesService = imagesService;
    }

    @PostMapping(path = "/{id}")
    public void uploadImages(@PathVariable("id") Long recipeId, @RequestParam("files") MultipartFile[] multipartFiles)  {
        imagesService.uploadImages(recipeId, multipartFiles);
    }

    @PostMapping()
    public void uploadImages(@RequestParam("file") MultipartFile multipartFiles) throws IOException {
        imagesService.uploadImage(multipartFiles);
    }
}
