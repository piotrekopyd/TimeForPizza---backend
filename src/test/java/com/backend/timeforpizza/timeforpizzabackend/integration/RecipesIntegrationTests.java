package com.backend.timeforpizza.timeforpizzabackend.integration;

import com.backend.timeforpizza.timeforpizzabackend.controller.RecipeController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RecipesIntegrationTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RecipeController recipeController;

    @Test
    public void test() {

    }
}
