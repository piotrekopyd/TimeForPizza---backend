package com.backend.timeforpizza.timeforpizzabackend.controllers;

import com.backend.timeforpizza.timeforpizzabackend.payload.IngredientRequest;
import com.backend.timeforpizza.timeforpizzabackend.payload.IngredientResponse;
import com.backend.timeforpizza.timeforpizzabackend.service.IngredientService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Controller
@RequestMapping(path = "/ingredients")
public class IngredientController {

    IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

//    @PostMapping
//    public void addIngredient(@Valid @NotNull @RequestBody IngredientRequest ingredientRequest, @RequestParam(value = "recipeId") Long recipeId) {
//
//    }
}
