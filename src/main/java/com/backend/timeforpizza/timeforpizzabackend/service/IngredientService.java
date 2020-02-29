package com.backend.timeforpizza.timeforpizzabackend.service;

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

    public int addIngredient(IngredientRequest ingredientRequest) {
        return ingredientRepository.save(ModelMapper.mapIngredientRequestToIngredient(ingredientRequest)) != null ? 1 : -1;
    }

    public int addAllIngredients(List<IngredientRequest> ingredientsRequests, Recipe recipe) {
        List<Ingredient> ingredients = ingredientsRequests.stream()
                .map(ModelMapper::mapIngredientRequestToIngredient)
                .collect(Collectors.toList());
        ingredients.forEach(ingredient -> ingredient.setRecipe(recipe));
        return ingredientRepository.saveAll(ingredients) != null ? 1 : -1;
    }

    public int addAllIngredients(List<IngredientRequest> ingredientRequests) {
        List<Ingredient> ingredients = ingredientRequests.stream()
                .map(ModelMapper::mapIngredientRequestToIngredient)
                .collect(Collectors.toList());

        return ingredientRepository.saveAll(ingredients) != null ? 1 : -1;
    }

    public List<IngredientResponse> getIngredientsByRecipe(Recipe recipe) {
        return ingredientRepository.findByRecipe(recipe).stream()
            .map(ModelMapper::mapIngredientToIngredientResponse)
            .collect(Collectors.toList());
    }

    public void deleteIngredient(Ingredient ingredient) {
        ingredientRepository.delete(ingredient);
    }

    public void deleteIngredientById(Long ingredientId) {
        ingredientRepository.deleteById(ingredientId);
    }

    public void deleteIngredientsByRecipe(Recipe recipe) {
        ingredientRepository.deleteByRecipe(recipe);
    }

    public int updateIngredient(Ingredient newIngredient) {
        Ingredient oldIngredient = ingredientRepository.findById(newIngredient.getIngredientId())
                .orElse(null);
        if (oldIngredient != null) {
            oldIngredient.setAmount(newIngredient.getAmount());
            oldIngredient.setName(newIngredient.getName());
            oldIngredient.setRecipe(newIngredient.getRecipe());
            oldIngredient.setUnit(newIngredient.getUnit());
            ingredientRepository.save(oldIngredient);
            return 1;
        }
        return -1;
    }

}
