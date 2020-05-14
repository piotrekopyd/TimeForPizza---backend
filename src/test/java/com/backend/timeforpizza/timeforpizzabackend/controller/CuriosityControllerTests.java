package com.backend.timeforpizza.timeforpizzabackend.controller;

import com.backend.timeforpizza.timeforpizzabackend.dto.CuriosityRequestDTO;
import com.backend.timeforpizza.timeforpizzabackend.dto.CuriosityResponseDTO;
import com.backend.timeforpizza.timeforpizzabackend.service.CuriosityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(MockitoExtension.class)
public class CuriosityControllerTests {

    private MockMvc mockMvc;
    @Mock
    private CuriosityService curiosityService;
    @InjectMocks
    private CuriosityController curiosityController;

    private final static String PREFIX = "/curiosities";
    private final static String CURIOSITY_TITLE = "350 slices of pizza are eaten each second in the U.S.";
    private final static String CURIOSITY_CONTENT = "Pizza is one of the most popular food options for Americans, with 350 slices eaten by the second. To put that in perspective, about 21,000 slices are eaten nationally in just one minute.";
    private final static String CURIOSITY_AUTHOR = "Jean-Claude Van Damme";
    private final static CuriosityRequestDTO CURIOSITY_REQUEST = new CuriosityRequestDTO(CURIOSITY_TITLE, CURIOSITY_CONTENT, CURIOSITY_AUTHOR);
    private final static Long CURIOSITY_ID = 1L;
    private final static CuriosityResponseDTO CURIOSITY_RESPONSE = new CuriosityResponseDTO(CURIOSITY_ID, CURIOSITY_TITLE, CURIOSITY_CONTENT, CURIOSITY_AUTHOR);


    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(curiosityController)
                .build();
    }

    @Test
    void shouldAddCuriosity() throws Exception {
        // given
        when(curiosityService.addCuriosity(CURIOSITY_REQUEST)).thenReturn(CURIOSITY_RESPONSE);
        // when
        MockHttpServletResponse response = mockMvc.perform(
                post(PREFIX)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // then
//        assertThat(response.getContentAsString())
    }

}
