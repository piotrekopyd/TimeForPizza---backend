package com.backend.timeforpizza.timeforpizzabackend.service;

import com.backend.timeforpizza.timeforpizzabackend.dto.DeleteImageRequestDTO;
import com.backend.timeforpizza.timeforpizzabackend.model.FileType;
import com.backend.timeforpizza.timeforpizzabackend.model.Recipe;
import com.backend.timeforpizza.timeforpizzabackend.model.RecipeImage;
import com.backend.timeforpizza.timeforpizzabackend.repository.FileStorageRepository;
import com.backend.timeforpizza.timeforpizzabackend.repository.RecipeImageRepository;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class RecipeImageServiceTests {

    @Mock
    private RecipeImageRepository recipeImageRepository;
    @Mock
    private FileStorageRepository imageStorageRepository;

    @InjectMocks
    private RecipeImageService recipeImageService;

    @Test
    void shouldAddRecipeImage() {
        // given
        Recipe recipe = new Recipe(1L, "Caprese", "preparation", "url", LocalDate.now());
        RecipeImage recipeImage = new RecipeImage(1L, "url", "image.jpg", recipe);
        when(recipeImageRepository.existsByUrlAndRecipeRecipeId(recipeImage.getUrl(), recipeImage.getRecipe().getRecipeId())).thenReturn(false);
        when(recipeImageRepository.save(recipeImage)).thenReturn(recipeImage);

        // when
        var givenRecipeImage = recipeImageService.addRecipeImage(recipeImage);

        // then
        assertEquals(recipeImage, givenRecipeImage);
    }

    @Test
    void shouldReturnRecipeImageWhichAlreadyExists() {
        // given
        Recipe recipe = new Recipe(1L, "Caprese", "preparation", "url", LocalDate.now());
        RecipeImage recipeImage = new RecipeImage(1L, "url", "image.jpg", recipe);
        when(recipeImageRepository.existsByUrlAndRecipeRecipeId(recipeImage.getUrl(), recipeImage.getRecipe().getRecipeId())).thenReturn(true);
        when(recipeImageRepository.findByUrlAndRecipeRecipeId(recipeImage.getUrl(), recipeImage.getRecipe().getRecipeId())).thenReturn(Optional.of(recipeImage));

        // when
        var givenRecipeImage = recipeImageService.addRecipeImage(recipeImage);

        // then
        assertEquals(recipeImage, givenRecipeImage);
    }

    @Test
    void shouldUploadImages() throws IOException {
        // given
        String mockPath = "path/to/mock_file.jpg";
        byte[] content = {1, 0, 1};
        MockMultipartFile mockMultipartFile = new MockMultipartFile("mock_file.jpg", content);
        Recipe recipe = new Recipe(1L, "Caprese", "preparation", "url", LocalDate.now());
        RecipeImage recipeImage = new RecipeImage(null, mockPath, mockMultipartFile.getOriginalFilename(), recipe);
        when(imageStorageRepository.uploadFile(mockMultipartFile, FileType.RECIPE_IMAGE, recipe.getRecipeId().toString())).thenReturn("path/to/mock_file.jpg");
        when(recipeImageRepository.existsByUrlAndRecipeRecipeId(recipeImage.getUrl(), recipeImage.getRecipe().getRecipeId())).thenReturn(false);
        when(recipeImageRepository.save(recipeImage)).thenReturn(recipeImage);

        // when
        recipeImageService.uploadImages(Arrays.array(mockMultipartFile), recipe);

        // then
        verify(imageStorageRepository).uploadFile(mockMultipartFile, FileType.RECIPE_IMAGE, recipe.getRecipeId().toString());
        verify(recipeImageRepository).save(recipeImage);
    }

    @Test
    void shouldDeleteImages() {
        // given
        DeleteImageRequestDTO capreseDeleteImageRequest = new DeleteImageRequestDTO(1L, "caprese_pizza_image.jpg");
        DeleteImageRequestDTO hawaiianDeleteImageRequest = new DeleteImageRequestDTO(2L, "hawaiian_pizza_image.jpg");
        RecipeImage capreseRecipeImage = new RecipeImage(1L, "url", "caprese_pizza_image.jpg", new Recipe(1L, "caprese", null, null, null));
        RecipeImage hawaiianRecipeImage = new RecipeImage(2L, "url", "hawaiian_pizza_image.jpg", new Recipe(2L, "hawaiian", null, null, null));
        when(recipeImageRepository.findById(1L)).thenReturn(Optional.of(capreseRecipeImage));
        when(recipeImageRepository.findById(2L)).thenReturn(Optional.of(hawaiianRecipeImage));
        doNothing().when(recipeImageRepository).deleteById(anyLong());
        doNothing().when(imageStorageRepository).deleteFile(anyLong(), anyString());

        // when
        recipeImageService.deleteImages(List.of(capreseDeleteImageRequest, hawaiianDeleteImageRequest));

        // then
        verify(recipeImageRepository).deleteById(1L);
        verify(recipeImageRepository).deleteById(2L);
        verify(imageStorageRepository).deleteFile(1L, capreseDeleteImageRequest.getImageName());
        verify(imageStorageRepository).deleteFile(2L, hawaiianDeleteImageRequest.getImageName());
    }

    @Test
    void shouldDeleteAllImagesByRecipeId() {
        // given
        Long recipeId = 1L;
        when(imageStorageRepository.deleteAllFilesWithPrefix(FileType.RECIPE_IMAGE, recipeId.toString())).thenReturn(true);
        doNothing().when(recipeImageRepository).deleteAllByRecipeRecipeId(recipeId);

        // when
        recipeImageService.deleteAllImagesByRecipeId(recipeId);

        // then
        verify(imageStorageRepository).deleteAllFilesWithPrefix(FileType.RECIPE_IMAGE, recipeId.toString());
        verify(recipeImageRepository).deleteAllByRecipeRecipeId(recipeId);
    }

    @Test
    void shouldThrowExceptionWhenDeletingFilesByPrefixReturnedFalse() {
        // given
        Long recipeId = 1L;
        when(imageStorageRepository.deleteAllFilesWithPrefix(FileType.RECIPE_IMAGE, recipeId.toString())).thenReturn(false);

        // then
        assertThrows(RuntimeException.class, () -> recipeImageService.deleteAllImagesByRecipeId(recipeId));
    }

    @Test
    void shouldDeleteRecipeImageById() {
        // given
        Long imageId = 1L;
        doNothing().when(recipeImageRepository).deleteById(imageId);

        // when
        recipeImageService.deleteImageById(imageId);

        // then
        verify(recipeImageRepository).deleteById(imageId);
    }
}
