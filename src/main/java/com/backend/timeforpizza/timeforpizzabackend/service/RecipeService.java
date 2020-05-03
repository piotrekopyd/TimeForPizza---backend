package com.backend.timeforpizza.timeforpizzabackend.service;

import com.backend.timeforpizza.timeforpizzabackend.exception.ResourceNotFoundException;
import com.backend.timeforpizza.timeforpizzabackend.model.Ingredient;
import com.backend.timeforpizza.timeforpizzabackend.model.Recipe;
import com.backend.timeforpizza.timeforpizzabackend.dto.*;
import com.backend.timeforpizza.timeforpizzabackend.repository.RecipeRepository;
import com.backend.timeforpizza.timeforpizzabackend.utils.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final IngredientService ingredientService;
    private final RecipeImageService recipeImageService;
    private final CommentService commentService;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository, IngredientService ingredientService, CommentService commentService
                         , RecipeImageService recipeImageService
    ) {
        this.recipeRepository = recipeRepository;
        this.ingredientService = ingredientService;
        this.commentService = commentService;
        this.recipeImageService = recipeImageService;
    }

    @Transactional
    public RecipeResponseDTO addRecipe(RecipeRequestDTO recipeRequestDTO) {
        Recipe recipe = ModelMapper.mapRecipeRequestToRecipe(recipeRequestDTO);
        recipeRepository.save(recipe);

        List<IngredientRequestDTO> ingredientRequestDTOS = recipeRequestDTO.getIngredients();
        ingredientService.addAllIngredients(ingredientRequestDTOS, recipe);
        return ModelMapper.mapRecipeToRecipeResponse(recipe);
    }

    public RecipeResponseDTO getRecipeById(Long id) {
        return recipeRepository.findById(id)
                .map(ModelMapper::mapRecipeToRecipeResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Recipe", "recipeId", id));
    }

    public List<RecipeResponseDTO> getAllRecipes() {
        return  recipeRepository.findAll().stream()
                    .map(ModelMapper::mapRecipeToRecipeResponse)
                    .collect(Collectors.toList());
    }

    @Transactional
    public RecipeResponseDTO updateRecipe(RecipeRequestDTO recipeRequestDTO, Long recipeId) {
        Recipe oldRecipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new ResourceNotFoundException("Recipe", "recipeId", recipeId));

        List<Ingredient> newIngredients = recipeRequestDTO.getIngredients().stream()
                .map(ModelMapper::mapIngredientRequestToIngredient)
                .collect(Collectors.toList());
        newIngredients.forEach(ingredient -> ingredient.setRecipe(oldRecipe));
        oldRecipe.setIngredients(newIngredients);
        oldRecipe.setPreparation(recipeRequestDTO.getPreparation());
        oldRecipe.setName(recipeRequestDTO.getName());
        return ModelMapper.mapRecipeToRecipeResponse(recipeRepository.save(oldRecipe));
    }

    @Transactional
    public void deleteRecipe(Long recipeId) {
        commentService.deleteAllCommentsByRecipeId(recipeId);
        ingredientService.deleteAllIngredientsByRecipeId(recipeId);
        recipeImageService.deleteAllImagesByRecipeId(recipeId);
        recipeRepository.deleteById(recipeId);
    }

    /** Ingredients */
    public IngredientResponseDTO addIngredient(Long recipeId, IngredientRequestDTO ingredientRequestDTO) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new ResourceNotFoundException("Recipe", "recipeId", recipeId));
        return ingredientService.addIngredient(ingredientRequestDTO, recipe);
    }
    public List<IngredientResponseDTO> getAllIngredientsByRecipeId(Long recipeId) {
        RecipeResponseDTO recipeResponseDTO = recipeRepository.findById(recipeId)
                .map(ModelMapper::mapRecipeToRecipeResponse)
                .orElse(null);

        if(recipeResponseDTO != null) {
            return recipeResponseDTO.getIngredients();
        }

        return new ArrayList<>();
    }

    /** Comments */
    public CommentResponseDTO addComment(Long recipeId, CommentRequestDTO commentRequestDTO) {
        Recipe recipe =recipeRepository.findById(recipeId)
                .orElseThrow(() -> new ResourceNotFoundException("Recipe", "recipeId", recipeId));
        return commentService.addComment(commentRequestDTO, recipe);
    }

    public void deleteComment(Long commentId) {
        commentService.deleteCommentById(commentId);
    }

    public List<CommentResponseDTO> getAllCommentsByRecipeId(Long recipeId) {
        RecipeResponseDTO recipeResponseDTO = recipeRepository.findById(recipeId)
                .map(ModelMapper::mapRecipeToRecipeResponse)
                .orElse(null);

        if(recipeResponseDTO != null) {
            return recipeResponseDTO.getComments();
        }

        return new ArrayList<>();
    }

    /** Images */
    public void uploadImages(Long recipeId, MultipartFile[] images) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new ResourceNotFoundException("Recipe", "recipeId", recipeId));

        recipeImageService.uploadImages(images, recipe);
    }

    public void deleteImages(List<DeleteImageRequestDTO> deleteImageRequests) {
        recipeImageService.deleteImages(deleteImageRequests);
    }
}
