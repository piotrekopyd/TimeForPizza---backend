package com.backend.timeforpizza.timeforpizzabackend.util;

import com.backend.timeforpizza.timeforpizzabackend.dto.*;
import com.backend.timeforpizza.timeforpizzabackend.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class ModelMapperTests {

    private static final Long ID = 1L;
    private static final Recipe RECIPE = new Recipe(ID, "recipe", "preparation", "url", LocalDate.now());
    private static final Comment COMMENT = new Comment(ID, "Nelson Mandela", "Delicious!", LocalDate.now(), RECIPE);
    private static final Ingredient INGREDIENT = new Ingredient(ID, "flour", 1D, "kg", RECIPE);
    private static final Curiosity CURIOSITY = new Curiosity(ID, "SATURDAY NIGHT IS PIZZA NIGHT.", "Saturday night is the most popular night of the week to eat pizza.", "Boguslaw Linda");
    private static final RecipeImage RECIPE_IMAGE = new RecipeImage(ID, "https://www.timeforpizza.com/recipe-collections/collection-image/2013/05/chorizo-mozarella.jpg", "pizza-chorizo.jpg", RECIPE);

    @Test
    void shouldMapToComment() {
        // given
        CommentRequestDTO commentRequest = new CommentRequestDTO("nickname", "comment");
        Comment comment = new Comment(null, "nickname", "comment", null, null);

        // then
        assertEquals(comment, ModelMapper.mapToComment(commentRequest));
    }

    @Test
    void shouldMapToCommentResponse() {
        // given
        CommentResponseDTO commentResponse = new CommentResponseDTO(COMMENT.getCommentId(), COMMENT.getNickname(), COMMENT.getComment(), COMMENT.getDate().toString());

        // then
        assertEquals(commentResponse, ModelMapper.mapToCommentResponse(COMMENT));
    }

    @Test
    void shouldMapToIngredient() {
        // given
        IngredientRequestDTO ingredientRequest = new IngredientRequestDTO("name", 2D, "kg");
        Ingredient ingredient = new Ingredient(null, ingredientRequest.getName(), ingredientRequest.getAmount(), ingredientRequest.getUnit(), null);

        // then
        assertEquals(ingredient, ModelMapper.mapToIngredient(ingredientRequest));
    }

    @Test
    void shouldMapToIngredientResponse() {
        // given
        IngredientResponseDTO ingredientResponse = new IngredientResponseDTO(INGREDIENT.getIngredientId(), INGREDIENT.getName(), INGREDIENT.getAmount(), INGREDIENT.getUnit());

        // then
        assertEquals(ingredientResponse, ModelMapper.mapToIngredientResponse(INGREDIENT));
    }

    @Test
    void shouldMapToRecipe() {
        // given
        RecipeRequestDTO recipeRequest = new RecipeRequestDTO("name", "preparation", List.of(new IngredientRequestDTO("ingredient", 1D, "kg")), "url");
        Recipe recipe = new Recipe(null, recipeRequest.getName(), recipeRequest.getPreparation(), recipeRequest.getThumbnailUrl(), null);

        // then
        assertEquals(recipe, ModelMapper.mapToRecipe(recipeRequest));
    }

    @Test
    void shouldMapToRecipeResponse() {
        // given
        CommentResponseDTO commentResponse = new CommentResponseDTO(COMMENT.getCommentId(), COMMENT.getNickname(), COMMENT.getComment(), COMMENT.getDate().toString());
        IngredientResponseDTO ingredientResponse = new IngredientResponseDTO(INGREDIENT.getIngredientId(), INGREDIENT.getName(), INGREDIENT.getAmount(), INGREDIENT.getUnit());
        RecipeImageResponseDTO recipeImageResponse = new RecipeImageResponseDTO(RECIPE_IMAGE.getRecipeImageId(), RECIPE_IMAGE.getUrl(), RECIPE_IMAGE.getImageName());
        RecipeResponseDTO recipeResponse = new RecipeResponseDTO(ID, RECIPE.getName(), RECIPE.getPreparation(), RECIPE.getDate().toString(), RECIPE.getThumbnailUrl(), List.of(ingredientResponse), List.of(commentResponse), List.of(recipeImageResponse));

        // when
        var givenResponse = ModelMapper.mapToRecipeResponse(RECIPE, List.of(INGREDIENT), List.of(COMMENT), List.of(RECIPE_IMAGE));

        // then
        assertEquals(recipeResponse, givenResponse);
    }

    @Test
    void shouldMapToRecipeMiniatureResponse() {
        // given
        RecipeMiniatureResponseDTO recipeMiniatureResponse = new RecipeMiniatureResponseDTO(RECIPE.getRecipeId(), RECIPE.getName(), RECIPE.getThumbnailUrl(), RECIPE.getDate().toString());
        // when
        var response = ModelMapper.mapToRecipeMiniature(RECIPE);
        // then
        assertEquals(recipeMiniatureResponse, response);
    }

    @Test
    void shouldMapToCuriosity() {
        // given
        CuriosityRequestDTO curiosityRequest = new CuriosityRequestDTO("title", "very interesting curiosity", "John Lennon");
        Curiosity curiosity = new Curiosity(null, curiosityRequest.getTitle(), curiosityRequest.getCuriosity(), curiosityRequest.getAuthor());

        // then
        assertEquals(curiosity, ModelMapper.mapToCuriosity(curiosityRequest));
    }

    @Test
    void shouldMapToCuriosityResponse() {
        // given
        CuriosityResponseDTO curiosityResponse = new CuriosityResponseDTO(CURIOSITY.getCuriosityId(), CURIOSITY.getTitle(), CURIOSITY.getCuriosity(), CURIOSITY.getAuthor());

        // then
        assertEquals(curiosityResponse, ModelMapper.mapToCuriosityResponse(CURIOSITY));
    }

    @Test
    void shouldMapToRecipeImageResponse() {
        // given
        RecipeImageResponseDTO recipeImageResponse = new RecipeImageResponseDTO(RECIPE_IMAGE.getRecipeImageId(), RECIPE_IMAGE.getUrl(), RECIPE_IMAGE.getImageName());

        // then
        assertEquals(recipeImageResponse, ModelMapper.mapToRecipeImageResponse(RECIPE_IMAGE));
    }

}
