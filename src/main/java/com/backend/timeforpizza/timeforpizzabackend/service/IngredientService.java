package com.backend.timeforpizza.timeforpizzabackend.service;

import com.backend.timeforpizza.timeforpizzabackend.exception.ResourceNotFoundException;
import com.backend.timeforpizza.timeforpizzabackend.model.Ingredient;
import com.backend.timeforpizza.timeforpizzabackend.model.Recipe;
import com.backend.timeforpizza.timeforpizzabackend.dto.IngredientRequestDTO;
import com.backend.timeforpizza.timeforpizzabackend.dto.IngredientResponseDTO;
import com.backend.timeforpizza.timeforpizzabackend.repository.IngredientRepository;
import com.backend.timeforpizza.timeforpizzabackend.utils.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IngredientService {
    private final IngredientRepository ingredientRepository;

    @Autowired
    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public IngredientResponseDTO addIngredient(IngredientRequestDTO ingredientRequestDTO, Recipe recipe) {
        Ingredient ingredient = ModelMapper.mapIngredientRequestToIngredient(ingredientRequestDTO);
        ingredient.setRecipe(recipe);
        return ModelMapper.mapIngredientToIngredientResponse(ingredientRepository.save(ingredient));
    }

    public List<IngredientResponseDTO> addAllIngredients(List<IngredientRequestDTO> ingredientRequestDTOS, Recipe recipe) {
        List<Ingredient> ingredients = ingredientRequestDTOS.stream()
                .map(ModelMapper::mapIngredientRequestToIngredient)
                .collect(Collectors.toList());
        ingredients.forEach(ingredient -> ingredient.setRecipe(recipe));

        return ingredientRepository.saveAll(ingredients).stream()
                .map(ModelMapper::mapIngredientToIngredientResponse)
                .collect(Collectors.toList());
    }

    public List<IngredientResponseDTO> getIngredientsByRecipeId(Long recipeId) {
        return ingredientRepository.findAllByRecipeRecipeId(recipeId).stream()
                .map(ModelMapper::mapIngredientToIngredientResponse)
                .collect(Collectors.toList());
    }

    public void deleteIngredientById(Long ingredientId) {
        ingredientRepository.deleteById(ingredientId);
    }

    public void deleteAllIngredientsByRecipeId(Long recipeId) {
        ingredientRepository.deleteAllByRecipeRecipeId(recipeId);
    }

    public IngredientResponseDTO updateIngredient(Long ingredientId, IngredientRequestDTO newIngredient) {
        Ingredient oldIngredient = ingredientRepository.findById(ingredientId)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient", "ingredientId", ingredientId));

        oldIngredient.setAmount(newIngredient.getAmount());
        oldIngredient.setName(newIngredient.getName());
        oldIngredient.setUnit(newIngredient.getUnit());
        return ModelMapper.mapIngredientToIngredientResponse(ingredientRepository.save(oldIngredient));
    }

}
