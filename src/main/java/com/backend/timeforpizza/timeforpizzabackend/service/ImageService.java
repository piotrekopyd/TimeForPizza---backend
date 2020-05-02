package com.backend.timeforpizza.timeforpizzabackend.service;

import com.backend.timeforpizza.timeforpizzabackend.model.Image;
import com.backend.timeforpizza.timeforpizzabackend.model.Recipe;
import com.backend.timeforpizza.timeforpizzabackend.dto.DeleteImageRequestDTO;
import com.backend.timeforpizza.timeforpizzabackend.dto.ImageRequestDTO;
import com.backend.timeforpizza.timeforpizzabackend.repository.ImageRepository;
import com.backend.timeforpizza.timeforpizzabackend.repository.ImageStorageRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ImagesService {

    private ImageRepository imageRepository;

    private ImageStorageRepository imagesStorageService;

    private RecipeService recipeService;

    ImagesService(ImageRepository imageRepository, @Qualifier("googleCloudStorage") ImageStorageRepository imagesStorageService) {
        this.imageRepository = imageRepository;
        this.imagesStorageService = imagesStorageService;
    }

    public void setRecipeService(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @Transactional
    public void uploadImages(Long recipeId, MultipartFile[] multipartFiles) {
        for (MultipartFile file: multipartFiles) {
            new Thread(() -> {
                try {
                    String path = imagesStorageService.uploadFile(file, recipeId);
                    saveImagePathToDatabase(new ImageRequestDTO(file.getOriginalFilename(), path, recipeId));
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }).start();
        }
    }

    private Image saveImagePathToDatabase(ImageRequestDTO imageRequestDTO) {
        Image image = new Image(imageRequestDTO.getImageName().replaceAll(" ", "_"), imageRequestDTO.getUrl());
        Recipe recipe = recipeService.getRecipe(imageRequestDTO.getRecipeId());
        if(recipe != null) {
            image.setRecipe(recipe);

            if(imageRepository.existsByUrlAndRecipe(imageRequestDTO.getUrl(), recipe)) {
                image = imageRepository.findByUrlAndRecipe(imageRequestDTO.getUrl(), recipe).get();
            }
            return imageRepository.save(image);
        }
        return null;
    }

    @Transactional
    public void deleteImages(List<DeleteImageRequestDTO> deleteImageRequestDTOList) {
        for(DeleteImageRequestDTO request: deleteImageRequestDTOList) {
            Optional<Image> image = imageRepository.findById(request.getImageId());
            if(image.isPresent()) {
                deleteImageById(request.getImageId());
                imagesStorageService.deleteFile(image.get().getRecipe().getRecipeId(), request.getImageName());
            }
        }
    }

    @Transactional
    public void deleteAllImagesByRecipeId(Long recipeId) {
        if (imagesStorageService.deleteAllFilesByRecipeId(recipeId)) {
            imageRepository.deleteAllByRecipeRecipeId(recipeId);
        } else {
            throw new RuntimeException("Failed to delete images from storage");
        }
    }

    private void deleteImageById(Long imageId) {
        imageRepository.deleteById(imageId);
    }

    public void uploadImage(MultipartFile multipartFile) {
        System.out.println(multipartFile.getName());
    }


}