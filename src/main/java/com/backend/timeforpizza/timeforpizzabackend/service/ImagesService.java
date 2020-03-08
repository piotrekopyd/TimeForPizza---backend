package com.backend.timeforpizza.timeforpizzabackend.service;

import com.backend.timeforpizza.timeforpizzabackend.model.Image;
import com.backend.timeforpizza.timeforpizzabackend.model.Recipe;
import com.backend.timeforpizza.timeforpizzabackend.payload.DeleteImageRequest;
import com.backend.timeforpizza.timeforpizzabackend.repository.ImagesRepository;
import com.backend.timeforpizza.timeforpizzabackend.repository.ImagesStorageRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ImagesService {

    private ImagesRepository imagesRepository;

    private ImagesStorageRepository imagesStorageService;

    private RecipeService recipeService;

    ImagesService(@Qualifier("imagesRepository") ImagesRepository imagesRepository, @Qualifier("googleCloudStorage") ImagesStorageRepository imagesStorageService, RecipeService recipeService) {
        this.imagesRepository = imagesRepository;
        this.imagesStorageService = imagesStorageService;
        this.recipeService = recipeService;
    }

    @Transactional
    public void uploadImages(Long recipeId, MultipartFile[] multipartFiles) {
        for (MultipartFile file: multipartFiles) {
            new Thread(() -> {
                try {
                    String path = imagesStorageService.uploadFile(file, recipeId);
                    saveImagePathToDatabase(recipeId, path);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }).start();
        }
//        for (MultipartFile file: multipartFiles) {
//            try {
//                String path = imagesStorageService.uploadFile(file, recipeId);
//                saveImagePathToDatabase(recipeId, path);
//            } catch (IOException e) {
//                System.out.println(e.getMessage());
//            }
//        }
    }

    private Image saveImagePathToDatabase(Long recipeId, String path) {
        Image image = new Image(path);
        Recipe recipe = recipeService.getRecipe(recipeId);
        if(recipe != null) {
            image.setRecipe(recipe);

            if(imagesRepository.existsByUrlAndRecipe(path, recipe)) {
                image = imagesRepository.findByUrlAndRecipe(path, recipe).get();
            }
            return imagesRepository.save(image);
        }
        return null;
    }

    @Transactional
    public void deleteImages(List<DeleteImageRequest> deleteImageRequestList) {
        for(DeleteImageRequest request: deleteImageRequestList) {
            Optional<Image> image = imagesRepository.findById(request.getImageId());
            if(image.isPresent()) {
                deleteImageById(request.getImageId());
                imagesStorageService.deleteFile(image.get().getRecipe().getRecipeId(), request.getImageName());
            }
        }
    }

    private void deleteImageById(Long imageId) {
        imagesRepository.deleteById(imageId);
    }

    public void uploadImage(MultipartFile multipartFile) {
        System.out.println(multipartFile.getName());
    }


}