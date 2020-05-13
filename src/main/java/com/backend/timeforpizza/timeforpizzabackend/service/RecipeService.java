package com.backend.timeforpizza.timeforpizzabackend.service;

import com.backend.timeforpizza.timeforpizzabackend.exception.ResourceNotFoundException;
import com.backend.timeforpizza.timeforpizzabackend.model.Comment;
import com.backend.timeforpizza.timeforpizzabackend.model.Ingredient;
import com.backend.timeforpizza.timeforpizzabackend.model.Recipe;
import com.backend.timeforpizza.timeforpizzabackend.dto.*;
import com.backend.timeforpizza.timeforpizzabackend.model.RecipeImage;
import com.backend.timeforpizza.timeforpizzabackend.repository.RecipeRepository;
import com.backend.timeforpizza.timeforpizzabackend.util.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
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
                         , RecipeImageService recipeImageService) {
        this.recipeRepository = recipeRepository;
        this.ingredientService = ingredientService;
        this.commentService = commentService;
        this.recipeImageService = recipeImageService;
    }

    @Transactional
    public RecipeResponseDTO addRecipe(RecipeRequestDTO recipeRequestDTO) {
        Recipe recipe = ModelMapper.mapToRecipe(recipeRequestDTO);
        recipe.setDate(LocalDate.now());
        Recipe savedRecipe = recipeRepository.save(recipe);

        List<Ingredient> ingredients = ingredientService.addAllIngredients(recipeRequestDTO.getIngredients().stream()
                .map(ModelMapper::mapToIngredient)
                .peek(e -> e.setRecipe(savedRecipe))
                .collect(Collectors.toList()));

        return ModelMapper.mapToRecipeResponse(savedRecipe, ingredients, List.of(), List.of());
    }

    public RecipeResponseDTO getRecipeById(Long recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new ResourceNotFoundException("Recipe", "recipeId", recipeId));

        List<Ingredient> ingredients = ingredientService.getAllIngredientsByRecipeId(recipeId);
        List<Comment> comments = commentService.getAllCommentsByRecipeId(recipeId);
        List<RecipeImage> images = recipeImageService.getAllByRecipeId(recipeId);

        return ModelMapper.mapToRecipeResponse(recipe, ingredients, comments, images);
    }

    public List<RecipeMiniatureResponseDTO> getAllRecipesMiniature() {
        return recipeRepository.findAll().stream()
                .map(ModelMapper::mapToRecipeMiniature)
                .collect(Collectors.toList());
    }

    @Transactional
    public RecipeResponseDTO updateRecipe(RecipeRequestDTO recipeRequestDTO, Long recipeId) {
        Recipe oldRecipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new ResourceNotFoundException("Recipe", "recipeId", recipeId));

        oldRecipe.setName(recipeRequestDTO.getName());
        oldRecipe.setPreparation(recipeRequestDTO.getPreparation());
        oldRecipe.setThumbnailUrl(recipeRequestDTO.getThumbnailUrl());
        oldRecipe.setDate(LocalDate.now());
        recipeRepository.save(oldRecipe);

        List<Ingredient> ingredients = ingredientService.updateAllIngredientsByRecipeId(recipeId, recipeRequestDTO.getIngredients().stream()
                .map(ModelMapper::mapToIngredient)
                .peek(e -> e.setRecipe(oldRecipe))
                .collect(Collectors.toList()));

        List<Comment> comments = commentService.getAllCommentsByRecipeId(recipeId);
        List<RecipeImage> images = recipeImageService.getAllByRecipeId(recipeId);
        return ModelMapper.mapToRecipeResponse(oldRecipe, ingredients, comments, images);
    }

    public RecipeResponseDTO updateRecipeThumbnail(Long recipeId, String thumbnailUrl) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new ResourceNotFoundException("Recipe", "recipeId", recipeId));

        recipe.setThumbnailUrl(thumbnailUrl);
        recipeRepository.save(recipe);
        return ModelMapper.mapToRecipeResponse(recipe, ingredientService.getAllIngredientsByRecipeId(recipeId), commentService.getAllCommentsByRecipeId(recipeId), recipeImageService.getAllByRecipeId(recipeId));
    }

    @Transactional
    public void deleteRecipeById(Long recipeId) {
        commentService.deleteAllCommentsByRecipeId(recipeId);
        ingredientService.deleteAllIngredientsByRecipeId(recipeId);
        recipeImageService.deleteAllImagesByRecipeId(recipeId);
        recipeRepository.deleteById(recipeId);
    }

    /** Ingredients */
    public IngredientResponseDTO addIngredient(Long recipeId, IngredientRequestDTO ingredientRequestDTO) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new ResourceNotFoundException("Recipe", "recipeId", recipeId));

        Ingredient ingredient = ModelMapper.mapToIngredient(ingredientRequestDTO);
        ingredient.setRecipe(recipe);
        return ModelMapper.mapToIngredientResponse(ingredientService.addIngredient(ingredient));
    }

    public List<IngredientResponseDTO> getAllIngredientsByRecipeId(Long recipeId) {
        return ingredientService.getAllIngredientsByRecipeId(recipeId).stream()
                .map(ModelMapper::mapToIngredientResponse)
                .collect(Collectors.toList());
    }

    /** Comments */
    public CommentResponseDTO addComment(Long recipeId, CommentRequestDTO commentRequestDTO) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new ResourceNotFoundException("Recipe", "recipeId", recipeId));

        Comment comment = ModelMapper.mapToComment(commentRequestDTO);
        comment.setRecipe(recipe);
        comment.setDate(LocalDate.now());

        return ModelMapper.mapToCommentResponse(commentService.addComment(comment));
    }

    public void deleteCommentById(Long commentId) {
        commentService.deleteCommentById(commentId);
    }

    public List<CommentResponseDTO> getAllCommentsByRecipeId(Long recipeId) {
        return commentService.getAllCommentsByRecipeId(recipeId).stream()
                .map(ModelMapper::mapToCommentResponse)
                .collect(Collectors.toList());
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
