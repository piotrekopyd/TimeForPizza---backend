package com.backend.timeforpizza.timeforpizzabackend.service;

import com.backend.timeforpizza.timeforpizzabackend.exception.ResourceNotFoundException;
import com.backend.timeforpizza.timeforpizzabackend.model.RecipeImage;
import com.backend.timeforpizza.timeforpizzabackend.model.Recipe;
import com.backend.timeforpizza.timeforpizzabackend.dto.DeleteImageRequestDTO;
import com.backend.timeforpizza.timeforpizzabackend.dto.ImageRequestDTO;
import com.backend.timeforpizza.timeforpizzabackend.repository.RecipeImageRepository;
import com.backend.timeforpizza.timeforpizzabackend.repository.ImageStorageRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class RecipeImageService {

    private RecipeImageRepository recipeImageRepository;

    private ImageStorageRepository imagesStorageService;

    RecipeImageService(RecipeImageRepository recipeImageRepository, @Qualifier("googleCloudStorage") ImageStorageRepository imagesStorageService) {
        this.recipeImageRepository = recipeImageRepository;
        this.imagesStorageService = imagesStorageService;
    }

    @Transactional
    public void uploadImages(MultipartFile[] multipartFiles, Recipe recipe) {
        for (MultipartFile file: multipartFiles) {
            new Thread(() -> {
                try {
                    String path = imagesStorageService.uploadFile(file, recipe.getRecipeId());
                    saveImagePathToDatabase(new ImageRequestDTO(file.getOriginalFilename(), path, recipe.getRecipeId()), recipe);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }).start();
        }
    }

    public RecipeImage saveImagePathToDatabase(ImageRequestDTO imageRequestDTO, Recipe recipe) {
        RecipeImage recipeImage = new RecipeImage(imageRequestDTO.getImageName().replaceAll(" ", "_"), imageRequestDTO.getUrl());
        recipeImage.setRecipe(recipe);

        recipeImage = recipeImageRepository.findByUrlAndRecipe(imageRequestDTO.getUrl(), recipe)
                .orElseThrow(() -> new ResourceNotFoundException("RecipeImage", "recipeImage", imageRequestDTO.getUrl()));
        return recipeImageRepository.save(recipeImage);
    }

    @Transactional
    public void deleteImages(List<DeleteImageRequestDTO> deleteImageRequestDTOList) {
        for(DeleteImageRequestDTO request: deleteImageRequestDTOList) {
            RecipeImage image = recipeImageRepository.findById(request.getImageId())
                    .orElseThrow(() -> new ResourceNotFoundException("RecipeImage", "recipeImageId", request.getImageId()));
            deleteImageById(request.getImageId());
            imagesStorageService.deleteFile(image.getRecipe().getRecipeId(), request.getImageName());
        }
    }

    @Transactional
    public void deleteAllImagesByRecipeId(Long recipeId) {
        if (imagesStorageService.deleteAllFilesByRecipeId(recipeId)) {
            recipeImageRepository.deleteAllByRecipeRecipeId(recipeId);
        } else {
            throw new RuntimeException("Failed to delete images from storage");
        }
    }

    private void deleteImageById(Long imageId) {
        recipeImageRepository.deleteById(imageId);
    }

    public void uploadImage(MultipartFile multipartFile) {
        System.out.println(multipartFile.getName());
    }
}