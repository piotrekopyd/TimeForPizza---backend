package com.backend.timeforpizza.timeforpizzabackend.service;

import com.backend.timeforpizza.timeforpizzabackend.exception.ResourceNotFoundException;
import com.backend.timeforpizza.timeforpizzabackend.model.Ingredient;
import com.backend.timeforpizza.timeforpizzabackend.model.Recipe;
import com.backend.timeforpizza.timeforpizzabackend.payload.IngredientRequest;
import com.backend.timeforpizza.timeforpizzabackend.payload.IngredientResponse;
import com.backend.timeforpizza.timeforpizzabackend.repository.IngredientRepository;
import com.backend.timeforpizza.timeforpizzabackend.utils.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IngredientService {
    private final IngredientRepository ingredientRepository;

    @Autowired
    public IngredientService(@Qualifier("ingredientRepository") IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public IngredientResponse addIngredient(IngredientRequest ingredientRequest) {
        Ingredient ingredient = ModelMapper.mapIngredientRequestToIngredient(ingredientRequest);
        return ModelMapper.mapIngredientToIngredientResponse(ingredientRepository.save(ingredient));
    }

    public List<IngredientResponse> addAllIngredients(List<IngredientRequest> ingredientRequests) {
        List<Ingredient> ingredients = ingredientRequests.stream()
                .map(ModelMapper::mapIngredientRequestToIngredient)
                .collect(Collectors.toList());

        return ingredientRepository.saveAll(ingredients).stream()
                .map(ModelMapper::mapIngredientToIngredientResponse)
                .collect(Collectors.toList());
    }

    public List<IngredientResponse> getIngredientsByRecipeId(Long recipeId) {
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

    public IngredientResponse updateIngredient(Long ingredientId, IngredientRequest newIngredient) {
        Ingredient oldIngredient = ingredientRepository.findById(ingredientId)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient", "ingredientId", ingredientId));

        oldIngredient.setAmount(newIngredient.getAmount());
        oldIngredient.setName(newIngredient.getName());
        oldIngredient.setRecipe(newIngredient.getRecipe());
        oldIngredient.setUnit(newIngredient.getUnit());
        return ModelMapper.mapIngredientToIngredientResponse(ingredientRepository.save(oldIngredient));
    }

}
