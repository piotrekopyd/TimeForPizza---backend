package com.backend.timeforpizza.timeforpizzabackend.service;

import com.backend.timeforpizza.timeforpizzabackend.model.Ingredient;
import com.backend.timeforpizza.timeforpizzabackend.model.Recipe;
import com.backend.timeforpizza.timeforpizzabackend.repository.IngredientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class IngredientServiceTests {

    @Mock
    IngredientRepository ingredientRepository;

    @InjectMocks
    IngredientService ingredientService;

    private final static Long INGREDIENT_FLOUR_ID = 1L;
    private final static Long INGREDIENT_TOMATO_SAUCE_ID = 2L;
    private final static Recipe RECIPE = new Recipe(1L, "capricciosa", "preparation", "url", LocalDate.now());
    private final static Ingredient INGREDIENT_FLOUR = new Ingredient(INGREDIENT_FLOUR_ID, "flour", 0.5, "kg", RECIPE);
    private final static Ingredient INGREDIENT_TOMATO_SAUCE = new Ingredient(INGREDIENT_TOMATO_SAUCE_ID, "tomato sauce", 150D, "ml", RECIPE);

    @Test
    void shouldAddIngredient() {
        // given
        when(ingredientRepository.save(INGREDIENT_FLOUR)).thenReturn(INGREDIENT_FLOUR);

        // when
        var givenIngredient = ingredientService.addIngredient(INGREDIENT_FLOUR);

        // then
        assertEquals(INGREDIENT_FLOUR, givenIngredient);
    }

    @Test
    void shouldGetAllIngredients() {
        // given
        when(ingredientRepository.saveAll(List.of(INGREDIENT_FLOUR, INGREDIENT_TOMATO_SAUCE))).thenReturn(List.of(INGREDIENT_FLOUR, INGREDIENT_TOMATO_SAUCE));

        // when
        var givenIngredients = ingredientService.addAllIngredients(List.of(INGREDIENT_FLOUR, INGREDIENT_TOMATO_SAUCE));

        // then
        assertEquals(List.of(INGREDIENT_FLOUR, INGREDIENT_TOMATO_SAUCE), givenIngredients);
    }

    @Test
    void shouldGetAllIngredientsByRecipeId() {
        // given
        Long recipeId = RECIPE.getRecipeId();
        when(ingredientRepository.findAllByRecipeRecipeId(recipeId)).thenReturn(List.of(INGREDIENT_FLOUR, INGREDIENT_TOMATO_SAUCE));

        // when
        var givenIngredients = ingredientService.getAllIngredientsByRecipeId(recipeId);

        // then
        assertEquals(List.of(INGREDIENT_FLOUR, INGREDIENT_TOMATO_SAUCE), givenIngredients);
    }

    @Test
    void shouldReturnEmptyListWhenIngredientsNotFoundByRecipeId() {
        // given
        Long recipeId = 2L;
        when(ingredientRepository.findAllByRecipeRecipeId(recipeId)).thenReturn(List.of());

        // when
        var givenIngredients = ingredientService.getAllIngredientsByRecipeId(recipeId);

        // then
        assertEquals(List.of(), givenIngredients);
    }

    @Test
    void shouldDeleteIngredientById() {
        // given
        doNothing().when(ingredientRepository).deleteById(INGREDIENT_FLOUR_ID);

        // when
        ingredientService.deleteIngredientById(INGREDIENT_FLOUR_ID);

        // then
        verify(ingredientRepository).deleteById(INGREDIENT_FLOUR_ID);
    }

    @Test
    void shouldDeleteAllIngredientsByRecipeId() {
        // given
        Long recipeId = RECIPE.getRecipeId();
        doNothing().when(ingredientRepository).deleteAllByRecipeRecipeId(recipeId);

        // when
        ingredientService.deleteAllIngredientsByRecipeId(recipeId);

        // then
        verify(ingredientRepository).deleteAllByRecipeRecipeId(recipeId);
    }

    @Test
    void shouldUpdateIngredientById() {
        // given
        Long ingredientId = 1L;
        Ingredient oldWater = new Ingredient(ingredientId, "water", 100D, "ml", RECIPE);
        Ingredient newWater = new Ingredient(ingredientId, "hot water", 150D, "ml", RECIPE);
        when(ingredientRepository.findById(ingredientId)).thenReturn(Optional.of(oldWater));
        when(ingredientRepository.save(newWater)).thenReturn(newWater);

        // when
        var givenIngredient = ingredientService.updateIngredientById(ingredientId, newWater);

        // then
        assertEquals(newWater, givenIngredient);
    }

    @Test
    void shouldUpdateAllIngredientsByRecipeId() {
        // given
        Long recipeId = RECIPE.getRecipeId();
        doNothing().when(ingredientRepository).deleteAllByRecipeRecipeId(recipeId);
        when(ingredientRepository.saveAll(List.of(INGREDIENT_TOMATO_SAUCE))).thenReturn(List.of(INGREDIENT_TOMATO_SAUCE));

        // when
        var givenIngredients = ingredientService.updateAllIngredientsByRecipeId(recipeId, List.of(INGREDIENT_TOMATO_SAUCE));

        // then
        assertEquals(List.of(INGREDIENT_TOMATO_SAUCE), givenIngredients);
        verify(ingredientRepository).deleteAllByRecipeRecipeId(recipeId);
    }
}
