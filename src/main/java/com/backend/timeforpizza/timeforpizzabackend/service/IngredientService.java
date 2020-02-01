package com.backend.timeforpizza.timeforpizzabackend.service;

import com.backend.timeforpizza.timeforpizzabackend.model.Ingredient;
import com.backend.timeforpizza.timeforpizzabackend.model.Recipe;
import com.backend.timeforpizza.timeforpizzabackend.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IngredientService {
    private final IngredientRepository ingredientRepository;

    @Autowired
    public IngredientService(@Qualifier("ingredientRepository") IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public int addIngredient(Ingredient ingredient) {
        Ingredient addedIngredient = ingredientRepository.save(ingredient);
        return addedIngredient != null ? 1 : -1;
    }

    public int addIngredients(List<Ingredient> ingredients) {
        return ingredientRepository.saveAll(ingredients) != null ? 1 : -1;
    }

    public List<Ingredient> getIngredientsByRecipe(Recipe recipe) {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepository.findByRecipe(recipe).forEach(ingredients::add);
        return ingredients;
    }

    public void deleteIngredient(Ingredient ingredient) {
        ingredientRepository.delete(ingredient);
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
