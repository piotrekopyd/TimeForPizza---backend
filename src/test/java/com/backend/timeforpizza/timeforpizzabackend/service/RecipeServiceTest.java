package com.backend.timeforpizza.timeforpizzabackend.service;

import com.backend.timeforpizza.timeforpizzabackend.repository.RecipeRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class RecipeServiceTest {

    @InjectMocks
    private RecipeRepository recipeRepository;

    @Mock
    private IngredientService ingredientService;

    @Mock
    private CommentService commentService;


}
