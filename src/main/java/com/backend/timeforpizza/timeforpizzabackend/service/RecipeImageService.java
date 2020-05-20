package com.backend.timeforpizza.timeforpizzabackend.service;

import com.backend.timeforpizza.timeforpizzabackend.exception.ResourceNotFoundException;
import com.backend.timeforpizza.timeforpizzabackend.model.FileType;
import com.backend.timeforpizza.timeforpizzabackend.model.RecipeImage;
import com.backend.timeforpizza.timeforpizzabackend.model.Recipe;
import com.backend.timeforpizza.timeforpizzabackend.dto.DeleteImageRequestDTO;
import com.backend.timeforpizza.timeforpizzabackend.repository.RecipeImageRepository;
import com.backend.timeforpizza.timeforpizzabackend.repository.FileStorageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class RecipeImageService {

    private RecipeImageRepository recipeImageRepository;

    private FileStorageRepository imageStorageRepository;

    private static final Logger logger = LoggerFactory.getLogger(RecipeImageService.class);

    RecipeImageService(RecipeImageRepository recipeImageRepository, @Qualifier("googleCloudStorage") FileStorageRepository imageStorageRepository) {
        this.recipeImageRepository = recipeImageRepository;
        this.imageStorageRepository = imageStorageRepository;
    }

    public List<RecipeImage> getAllByRecipeId(Long recipeId) {
        return recipeImageRepository.findAllByRecipeRecipeId(recipeId);
    }

    @Transactional
    public void uploadImages(MultipartFile[] multipartFiles, Recipe recipe) {
        for (MultipartFile file: multipartFiles) {
//            new Thread(() -> {
                try {
                    String path = imageStorageRepository.uploadFile(file, FileType.RECIPE_IMAGE, recipe.getRecipeId().toString());
                    RecipeImage recipeImage = new RecipeImage(null, path, file.getOriginalFilename(), recipe);
                    addRecipeImage(recipeImage);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
//            }).start();
        }
    }

    public RecipeImage addRecipeImage(RecipeImage recipeImage) {
        if (recipeImageRepository.existsByUrlAndRecipeRecipeId(recipeImage.getUrl(), recipeImage.getRecipe().getRecipeId())) {
            String url = recipeImage.getUrl();
            return recipeImageRepository.findByUrlAndRecipeRecipeId(recipeImage.getUrl(), recipeImage.getRecipe().getRecipeId())
                    .orElseThrow(() -> new ResourceNotFoundException("RecipeImage", "recipeImage", url));
        }
        recipeImage = recipeImageRepository.save(recipeImage);
        logger.info("Added image with id: {}, for recipe with id: {}.", recipeImage.getRecipeImageId(), recipeImage.getRecipe().getRecipeId());
        return recipeImage;
    }

    @Transactional
    public void deleteImages(List<DeleteImageRequestDTO> deleteImageRequestDTOList) {
        for(DeleteImageRequestDTO request: deleteImageRequestDTOList) {
            RecipeImage image = recipeImageRepository.findById(request.getImageId())
                    .orElseThrow(() -> new ResourceNotFoundException("RecipeImage", "recipeImageId", request.getImageId()));
            recipeImageRepository.deleteById(request.getImageId());
            imageStorageRepository.deleteFile(FileType.RECIPE_IMAGE, image.getRecipe().getRecipeId().toString(), request.getImageName());
            logger.info("Deleted image with name: {}.", request.getImageName());
        }
    }

    @Transactional
    public void deleteAllImagesByRecipeId(Long recipeId) {
        if (imageStorageRepository.deleteAllFilesWithPrefix(FileType.RECIPE_IMAGE, recipeId.toString())) {
            recipeImageRepository.deleteAllByRecipeRecipeId(recipeId);
            logger.info("Deleted all images for recipe with id: {}.", recipeId);
        } else {
            logger.error("Failed to delete images from storage for recipe with id: {}.", recipeId);
            throw new RuntimeException("Failed to delete images from storage");
        }
    }

    void deleteImageById(Long imageId) {
        recipeImageRepository.deleteById(imageId);
    }
}