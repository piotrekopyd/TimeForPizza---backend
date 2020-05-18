package com.backend.timeforpizza.timeforpizzabackend.controller;

import com.backend.timeforpizza.timeforpizzabackend.dto.CuriosityRequestDTO;
import com.backend.timeforpizza.timeforpizzabackend.dto.CuriosityResponseDTO;
import com.backend.timeforpizza.timeforpizzabackend.service.CuriosityService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(MockitoExtension.class)
public class CuriosityControllerTests {

    @Mock
    private CuriosityService curiosityService;
    @InjectMocks
    private CuriosityController curiosityController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    private final static String PREFIX = "/curiosities";
    private final static String SUPER_BOWL_CURIOSITY_TITLE = "Over 2 million pizzas are sold from Pizza Hut during the Super Bowl";
    private final static String SUPER_BOWL_CURIOSITY = "One of the biggest days for pizzerias around the country is Super Bowl Sunday. Pizza is a major staple to this sports event, with an average of 2,500,000 pizzas being sold just from Pizza Hut alone.";
    private final static String FIRST_PIZZA_CURIOSITY_TITLE = "The first pizza is thought to be invented in Naples during the early 1500s";
    private final static String FIRST_PIZZA_CURIOSITY = "Historians from the William E. Macaulay Honors College discovered that the poorer people of Naples would put slices of tomatoes on dough and top it with cheese to make a cheap and easy meal for their families. According to the researchers, tomatoes were thought to be poisonous during these days, which is why pizza was considered a cheaper food.";
    private final static String GORDON_RAMSAY = "Gordon Ramsay";
    private final static String JAMIE_OLIVER = "Jamie Oliver";

    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(curiosityController)
                .build();
    }

    @Test
    void shouldAddCuriosity() throws Exception {
        // given
        CuriosityRequestDTO curiosityRequest = new CuriosityRequestDTO(SUPER_BOWL_CURIOSITY_TITLE, SUPER_BOWL_CURIOSITY, JAMIE_OLIVER);
        CuriosityResponseDTO curiosityResponse = new CuriosityResponseDTO(1L, SUPER_BOWL_CURIOSITY_TITLE, SUPER_BOWL_CURIOSITY, JAMIE_OLIVER);
        when(curiosityService.addCuriosity(curiosityRequest)).thenReturn(curiosityResponse);

        // when
        MockHttpServletResponse response = mockMvc.perform(
                post(PREFIX)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(curiosityRequest))
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains(SUPER_BOWL_CURIOSITY_TITLE);
        assertThat(response.getContentAsString()).contains("1");
        assertThat(response.getContentAsString()).contains(SUPER_BOWL_CURIOSITY);
        assertThat(response.getContentAsString()).contains(JAMIE_OLIVER);
    }

    @Test
    void shouldGetListOfAllCuriosities() throws Exception {
        // given
        CuriosityResponseDTO superBowlCuriosityResponse = new CuriosityResponseDTO(1L, SUPER_BOWL_CURIOSITY_TITLE, SUPER_BOWL_CURIOSITY, JAMIE_OLIVER);
        CuriosityResponseDTO firstPizzaCuriosityResponse = new CuriosityResponseDTO(2L, FIRST_PIZZA_CURIOSITY_TITLE, FIRST_PIZZA_CURIOSITY, GORDON_RAMSAY);
        when(curiosityService.getAllCuriosities()).thenReturn(List.of(superBowlCuriosityResponse, firstPizzaCuriosityResponse));

        // when
        MockHttpServletResponse response = mockMvc.perform(
                get(PREFIX)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains("1");
        assertThat(response.getContentAsString()).contains(SUPER_BOWL_CURIOSITY_TITLE);
        assertThat(response.getContentAsString()).contains(SUPER_BOWL_CURIOSITY);
        assertThat(response.getContentAsString()).contains(JAMIE_OLIVER);
        assertThat(response.getContentAsString()).contains("2");
        assertThat(response.getContentAsString()).contains(FIRST_PIZZA_CURIOSITY);
        assertThat(response.getContentAsString()).contains(FIRST_PIZZA_CURIOSITY_TITLE);
        assertThat(response.getContentAsString()).contains(GORDON_RAMSAY);
    }

    @Test
    void shouldGetCuriosityById() throws Exception {
        // given
        long id = 1L;
        CuriosityResponseDTO firstPizzaResponse = new CuriosityResponseDTO(id, FIRST_PIZZA_CURIOSITY_TITLE, FIRST_PIZZA_CURIOSITY, GORDON_RAMSAY);
        when(curiosityService.getCuriosityById(id)).thenReturn(firstPizzaResponse);

        // when
        MockHttpServletResponse response = mockMvc.perform(
                get(PREFIX + "/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();
        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains("1");
        assertThat(response.getContentAsString()).contains(FIRST_PIZZA_CURIOSITY);
        assertThat(response.getContentAsString()).contains(FIRST_PIZZA_CURIOSITY_TITLE);
        assertThat(response.getContentAsString()).contains(GORDON_RAMSAY);
    }

    @Test
    void shouldReturnResourceNotFoundWhenNotFoundById() throws Exception {
        // TODO: handler
//        // given
//        long id = 2L;
//        when(curiosityService.getCuriosityById(id)).thenThrow(new ResourceNotFoundException("Curiosity", "curiosityId", id));
//
//        // when
//        MockHttpServletResponse response = mockMvc.perform(
//                get(PREFIX + "/2")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andReturn()
//                .getResponse();
//
//        // then
//        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
//        assertThat(response.getContentAsString()).contains(HttpStatus.NOT_FOUND.getReasonPhrase());
//        assertThat(response.getContentAsString()).contains(FIRST_PIZZA_CURIOSITY_TITLE);
//        assertThat(response.getContentAsString()).contains("Curiosity not found with curiosityId: " + id);
    }

    @Test
    void shouldUpdateCuriosity() throws Exception {
        // given
        long id = 1L;
        String title = "Way over 2 million pizzas are sold from Pizza Hut during the Super Bowl";
        CuriosityRequestDTO curiosityRequest = new CuriosityRequestDTO(SUPER_BOWL_CURIOSITY_TITLE, SUPER_BOWL_CURIOSITY, JAMIE_OLIVER);
        CuriosityResponseDTO curiosityResponse = new CuriosityResponseDTO(id, title, SUPER_BOWL_CURIOSITY, JAMIE_OLIVER);
        when(curiosityService.updateCuriosity(id, curiosityRequest)).thenReturn(curiosityResponse);

        // when
        MockHttpServletResponse response = mockMvc.perform(
                patch(PREFIX + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(curiosityRequest))
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains("1");
        assertThat(response.getContentAsString()).contains(title);
        assertThat(response.getContentAsString()).contains(SUPER_BOWL_CURIOSITY);
        assertThat(response.getContentAsString()).contains(JAMIE_OLIVER);

    }

    // TODO: Exception handler resource not found

    @Test
    void shouldDeleteCuriosityById() throws Exception {
        // given
        long id = 1L;
        doNothing().when(curiosityService).deleteCuriosityById(id);

        // when
        MockHttpServletResponse response = mockMvc.perform(
                delete(PREFIX + "/1"))
                .andReturn()
                .getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        verify(curiosityService).deleteCuriosityById(id);
    }
}
