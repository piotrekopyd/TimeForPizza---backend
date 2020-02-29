package com.backend.timeforpizza.timeforpizzabackend.service;

import com.backend.timeforpizza.timeforpizzabackend.model.Comment;
import com.backend.timeforpizza.timeforpizzabackend.model.Ingredient;
import com.backend.timeforpizza.timeforpizzabackend.model.Recipe;
import com.backend.timeforpizza.timeforpizzabackend.payload.*;
import com.backend.timeforpizza.timeforpizzabackend.repository.RecipeRepository;
import com.backend.timeforpizza.timeforpizzabackend.utils.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final IngredientService ingredientService;
    private final CommentService commentService;

    @Autowired
    public RecipeService(@Qualifier("recipeRepository") RecipeRepository recipeRepository, IngredientService ingredientService, CommentService commentService) {
        this.recipeRepository = recipeRepository;
        this.ingredientService = ingredientService;
        this.commentService = commentService;
    }

    public int addRecipe(RecipeRequest recipeRequest) {
        Recipe recipe = ModelMapper.mapRecipeRequestToRecipe(recipeRequest);
        if (recipeRepository.save(recipe) != null) {
            List<IngredientRequest> ingredientRequests = recipeRequest.getIngredients();
            ingredientRequests.forEach(ingredientRequest -> ingredientRequest.setRecipe(recipe));

            if (ingredientService.addAllIngredients(ingredientRequests) > 0) {
                return 1;
            }
        }

        return -1;
    }

    public int addComment(CommentRequest commentRequest, Long recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElse(null);

        if(recipe != null) {
            Comment comment = ModelMapper.mapCommentRequestToComment(commentRequest);
            comment.setRecipe(recipe);
            commentService.addComment(comment);
            return 1;
        }

        return -1;
    }

    public RecipeResponse getRecipeById(Long id) {
        return recipeRepository.findById(id)
                .map(ModelMapper::mapRecipeToRecipeResponse)
                .orElse(null);
    }

    public List<RecipeResponse> getAllRecipes() {
        return  recipeRepository.findAll().stream()
                    .map(ModelMapper::mapRecipeToRecipeResponse)
                    .collect(Collectors.toList());
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

    public List<IngredientResponse> getAllIngredientsByRecipeId(Long recipeId) {
        RecipeResponse recipeResponse = recipeRepository.findById(recipeId)
                .map(ModelMapper::mapRecipeToRecipeResponse)
                .orElse(null);

        if(recipeResponse != null) {
            return recipeResponse.getIngredients();
        }

        return new ArrayList<>();
    }

    public int updateRecipe(RecipeRequest recipeRequest, Long recipeId) {
        Recipe oldRecipe = recipeRepository.findById(recipeId)
                .orElse(null);
        if(oldRecipe != null) {
            List<Ingredient> newIngredients = recipeRequest.getIngredients().stream()
                    .map(ModelMapper::mapIngredientRequestToIngredient)
                    .collect(Collectors.toList());
            newIngredients.forEach(ingredient -> ingredient.setRecipe(oldRecipe));
            oldRecipe.setIngredients(newIngredients);
            oldRecipe.setPreparation(recipeRequest.getPreparation());
            oldRecipe.setName(recipeRequest.getName());
            recipeRepository.save(oldRecipe);
            return 1;
        }
        return -1;
    }
}
