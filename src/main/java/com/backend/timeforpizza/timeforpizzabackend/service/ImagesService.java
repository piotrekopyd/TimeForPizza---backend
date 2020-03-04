package com.backend.timeforpizza.timeforpizzabackend.service;

import com.backend.timeforpizza.timeforpizzabackend.model.ImageUrl;
import com.backend.timeforpizza.timeforpizzabackend.model.Recipe;
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

    private RecipeService recipeService;

    ImagesService(@Qualifier("imagesRepository") ImagesRepository imagesRepository, @Qualifier("googleCloudStorage") ImagesStorageRepository imagesStorageRepository, RecipeService recipeService) {
        this.imagesRepository = imagesRepository;
        this.imagesStorageRepository = imagesStorageRepository;
        this.recipeService = recipeService;
    }

    @Transactional
    public void uploadImages(Long recipeId, MultipartFile[] multipartFiles) {
        for (MultipartFile file: multipartFiles) {
            try {
                String path = imagesStorageRepository.uploadFile(file, recipeId);
                ImageUrl imageUrl = saveImagePathToDatabase(recipeId, path);
                System.out.println("");
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private ImageUrl saveImagePathToDatabase(Long recipeId, String path) {
        ImageUrl imageUrl = new ImageUrl(path);
        Recipe recipe = recipeService.getRecipe(recipeId);
        if(recipe != null) {
            imageUrl.setRecipe(recipe);

            if(imagesRepository.existsByUrlAndRecipe(path, recipe)) {
                imageUrl = imagesRepository.findByUrlAndRecipe(path, recipe).get();
            }
            return imagesRepository.save(imageUrl);
        }
        return null;
    }



    /** TODO*/
    public void deleteFile(Long recipeId, String fileName) {
    }

    public void uploadImage(MultipartFile multipartFile) {
        System.out.println(multipartFile.getName());
    }


}