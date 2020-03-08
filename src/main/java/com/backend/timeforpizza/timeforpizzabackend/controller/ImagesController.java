package com.backend.timeforpizza.timeforpizzabackend.controller;

import com.backend.timeforpizza.timeforpizzabackend.payload.DeleteImageRequest;
import com.backend.timeforpizza.timeforpizzabackend.service.ImagesService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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

    @DeleteMapping
    public void deleteImages(@RequestBody List<DeleteImageRequest> deleteImageRequests) {
        imagesService.deleteImages(deleteImageRequests);
    }
}
