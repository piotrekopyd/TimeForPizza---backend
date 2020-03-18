package com.backend.timeforpizza.timeforpizzabackend.integration;

import com.backend.timeforpizza.timeforpizzabackend.payload.CuriosityRequest;
import com.backend.timeforpizza.timeforpizzabackend.payload.CuriosityResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RecipesIntegrationTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @Order(1)
    public void shouldAddCuriosity() throws Exception {
        // GIVEN
        CuriosityRequest curiosityRequest = new CuriosityRequest("super Curiosity", "It suppose to be such an exciting curiosity");
        CuriosityResponse curiosityResponse = new CuriosityResponse(1L, curiosityRequest.getTitle(), curiosityRequest.getCuriosity());
        List<CuriosityResponse> expectedResponse = List.of(curiosityResponse);

        // WHEN
        mockMvc.perform(post("/curiosities")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(curiosityRequest))).andExpect(status().isOk());

        // THEN
        mockMvc.perform(get("/curiosities"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));
    }

    @Test
    @Order(2)
    public void shouldGetCuriosityById() throws Exception {
        // GIVEN
        CuriosityRequest curiosityRequest = new CuriosityRequest("extra Curiosity", "It suppose to be such a brilliant curiosity");
        CuriosityResponse expectedResponse = new CuriosityResponse(2L, curiosityRequest.getTitle(), curiosityRequest.getCuriosity());

        // WHEN
        mockMvc.perform(post("/curiosities")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(curiosityRequest))).andExpect(status().isOk());

        // THEN
        mockMvc.perform(get("/curiosities/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));
    }

    @Test
    @Order(3)
    public void shouldUpdateCuriosity() throws  Exception {
        // GIVEN
        CuriosityRequest curiosityRequest = new CuriosityRequest("extra Curiosity", "turned out to be really boring");
        CuriosityResponse expectedResponse = new CuriosityResponse(2L, curiosityRequest.getTitle(), curiosityRequest.getCuriosity());

        // THEN
        mockMvc.perform(patch("/curiosities/2")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(curiosityRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));
    }

    @Test
    @Order(4)
    public void shouldDeleteCuriosity() throws Exception {
        // THEN
        mockMvc.perform(delete("/curiosities/2"))
                .andExpect(status().isOk());
    }
}
