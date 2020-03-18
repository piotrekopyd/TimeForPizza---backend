package com.backend.timeforpizza.timeforpizzabackend.service;

import com.backend.timeforpizza.timeforpizzabackend.exception.ResourceNotFoundException;
import com.backend.timeforpizza.timeforpizzabackend.model.Ingredient;
import com.backend.timeforpizza.timeforpizzabackend.model.Recipe;
import com.backend.timeforpizza.timeforpizzabackend.payload.*;
import com.backend.timeforpizza.timeforpizzabackend.repository.RecipeRepository;
import com.backend.timeforpizza.timeforpizzabackend.utils.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final IngredientService ingredientService;
    private final ImagesService imagesService;
    private final CommentService commentService;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository, IngredientService ingredientService, CommentService commentService
                         ,ImagesService imagesService
    ) {
        this.recipeRepository = recipeRepository;
        this.ingredientService = ingredientService;
        this.commentService = commentService;
        this.imagesService = imagesService;
    }

    @PostConstruct
    private void setRecipeInImagesService() {
        imagesService.setRecipeService(this);
    }

    /** Recipes */
    @Transactional
    public RecipeResponse addRecipe(RecipeRequest recipeRequest) {
        Recipe recipe = ModelMapper.mapRecipeRequestToRecipe(recipeRequest);
        recipeRepository.save(recipe);

        List<IngredientRequest> ingredientRequests = recipeRequest.getIngredients();
        ingredientService.addAllIngredients(ingredientRequests, recipe);
        return ModelMapper.mapRecipeToRecipeResponse(recipe);
    }

    public RecipeResponse getRecipeById(Long id) {
        return recipeRepository.findById(id)
                .map(ModelMapper::mapRecipeToRecipeResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Recipe", "recipeId", id));
    }

    Recipe getRecipe(Long id) {
        return recipeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recipe", "recipeId", id));
    }

    public List<RecipeResponse> getAllRecipes() {
        return  recipeRepository.findAll().stream()
                    .map(ModelMapper::mapRecipeToRecipeResponse)
                    .collect(Collectors.toList());
    }

    @Transactional
    public RecipeResponse updateRecipe(RecipeRequest recipeRequest, Long recipeId) {
        Recipe oldRecipe = getRecipe(recipeId);

        List<Ingredient> newIngredients = recipeRequest.getIngredients().stream()
                .map(ModelMapper::mapIngredientRequestToIngredient)
                .collect(Collectors.toList());
        newIngredients.forEach(ingredient -> ingredient.setRecipe(oldRecipe));
        oldRecipe.setIngredients(newIngredients);
        oldRecipe.setPreparation(recipeRequest.getPreparation());
        oldRecipe.setName(recipeRequest.getName());
        return ModelMapper.mapRecipeToRecipeResponse(recipeRepository.save(oldRecipe));
    }

    @Transactional
    public void deleteRecipe(Long recipeId) {
        commentService.deleteAllCommentsByRecipeId(recipeId);
        ingredientService.deleteAllIngredientsByRecipeId(recipeId);
        imagesService.deleteAllImagesByRecipeId(recipeId);
        recipeRepository.deleteById(recipeId);
    }

    /** Ingredients */
    public IngredientResponse addIngredient(Long recipeId, IngredientRequest ingredientRequest) {
        Recipe recipe = getRecipe(recipeId);
        return ingredientService.addIngredient(ingredientRequest, recipe);
    }
    public List<IngredientResponse> getAllIngredientsByRecipeId(Long recipeId) {
        RecipeResponse recipeResponse = recipeRepository.findById(recipeId)
                .map(ModelMapper::mapRecipeToRecipeResponse)
                .orElse(null);

        if(recipeResponse != null) {
            return recipeResponse.getIngredients();
        }

        return new ArrayList<>();
    }

    /** Comments */
    public CommentResponse addComment(Long recipeId, CommentRequest commentRequest) {
        Recipe recipe = getRecipe(recipeId);
        return commentService.addComment(commentRequest, recipe);
    }

    public void deleteComment(Long commentId) {
        commentService.deleteCommentById(commentId);
    }

    public List<CommentResponse> getAllCommentsByRecipeId(Long recipeId) {
        RecipeResponse recipeResponse = recipeRepository.findById(recipeId)
                .map(ModelMapper::mapRecipeToRecipeResponse)
                .orElse(null);

        if(recipeResponse != null) {
            return recipeResponse.getComments();
        }

        return new ArrayList<>();
    }
}
