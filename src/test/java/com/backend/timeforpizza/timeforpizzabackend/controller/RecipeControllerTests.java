package com.backend.timeforpizza.timeforpizzabackend.controller;

import com.backend.timeforpizza.timeforpizzabackend.dto.*;
import com.backend.timeforpizza.timeforpizzabackend.service.RecipeService;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(MockitoExtension.class)
public class RecipeControllerTests {

    @Mock
    private RecipeService recipeService;
    @InjectMocks
    private RecipeController recipeController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    private final static String PREFIX = "/recipes";
    private final static IngredientRequestDTO FLOUR_REQUEST = new IngredientRequestDTO("flour", 200D, "g");
    private final static IngredientRequestDTO SALT_REQUEST = new IngredientRequestDTO("a pinch of salt", null, null);
    private final static IngredientRequestDTO PINEAPPLE_REQUEST = new IngredientRequestDTO("pineapple chunks", 0.5, "cup");
    private final static IngredientResponseDTO FLOUR_RESPONSE = new IngredientResponseDTO(1L, "flour", 200D, "g");
    private final static IngredientResponseDTO SALT_RESPONSE = new IngredientResponseDTO(2L, "a pinch of salt", null, null);
    private final static IngredientResponseDTO PINEAPPLE_RESPONSE = new IngredientResponseDTO(3L, "pineapple chunks", 0.5, "cup");
    private final static RecipeImageResponseDTO HAWAIIAN_PIZZA_IMAGE = new RecipeImageResponseDTO(1L, "url/to/image/image.jpg", "image.jpg");
    private final static RecipeImageResponseDTO HAWAIIAN_PIZZA_INGREDIENTS_IMAGE = new RecipeImageResponseDTO(1L, "url/to/image/image-ingredients.jpg", "image-ingredients.jpg");
    private final static String KAROL_OKRASA = "Karol Okrasa";
    private final static String PASCAL_BRODNICKI = "Pascal Brodnicki";
    private final static String POSITIVE_COMMENT = "I strongly feel that this recipe will be back soon in my kitchen";
    private final static String NEGATIVE_COMMENT = "What a piece of garbage this pizza is";
    private final static String HAWAIIAN_PIZZA_TITLE = "the best Hawaiian pizza you will ever try!";
    private final static String HAWAIIAN_PIZZA_PREPARATION = "Prepare pizza dough through step 5, including preheating the oven to 475 F. Cover the shaped dough lightly with plastic wrap and allow it to rest as the oven preheats. " +
            "To prevent the pizza toppings from making your pizza crust soggy, brush the shaped dough lightly with olive oil. Using your fingers, push dents into the surface of the dough to prevent bubbling. Top the dough evenly with pizza sauce, then add the cheese, ham, pineapple, and bacon. " +
            "Bake pizza for 12-15 minutes. Remove from the oven and top with fresh basil, if desired. Slice hot pizza and serve immediately. " +
            "Cover leftover pizza tightly and store in the refrigerator. Reheat as you prefer. Baked pizza slices can be frozen up to 3 months. See pizza crust recipe for instructions on freezing the pizza dough.";

    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(recipeController)
                .build();
    }

    @Test
    void shouldDeleteRecipeById() throws Exception {
        // given
        long id = 1L;
        doNothing().when(recipeService).deleteRecipeById(id);

        // when
        MockHttpServletResponse response = mockMvc.perform(
                delete(PREFIX + "/1"))
                .andReturn()
                .getResponse();
        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        verify(recipeService).deleteRecipeById(id);
    }

    @Test
    void shouldDeleteImages() throws Exception {
        // given
        long id = 1L;
        String imageName = "pizza-image.jpg";
        List<DeleteImageRequestDTO> deleteImageRequests = List.of(new DeleteImageRequestDTO(id, imageName));
        doNothing().when(recipeService).deleteImages(deleteImageRequests);

        // when
        MockHttpServletResponse response = mockMvc.perform(
                delete(PREFIX + "/images")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(deleteImageRequests)))
                .andReturn()
                .getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        verify(recipeService).deleteImages(deleteImageRequests);
    }

    @Test
    void shouldGetRecipeById() throws Exception {
        // given
        long id = 1L;
        CommentResponseDTO positiveComment = new CommentResponseDTO(1L, KAROL_OKRASA, POSITIVE_COMMENT, LocalDate.now().toString());
        RecipeResponseDTO recipeResponse = new RecipeResponseDTO(id, HAWAIIAN_PIZZA_TITLE, HAWAIIAN_PIZZA_PREPARATION, LocalDate.now().toString(), null, List.of(FLOUR_RESPONSE, SALT_RESPONSE, PINEAPPLE_RESPONSE), List.of(positiveComment), List.of(HAWAIIAN_PIZZA_IMAGE));
        when(recipeService.getRecipeById(id)).thenReturn(recipeResponse);

        // when
        MockHttpServletResponse response = mockMvc.perform(
                get(PREFIX + "/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains("1");
        assertThat(response.getContentAsString()).contains(KAROL_OKRASA);
        assertThat(response.getContentAsString()).contains(POSITIVE_COMMENT);
        assertThat(response.getContentAsString()).contains(HAWAIIAN_PIZZA_PREPARATION);
        assertThat(response.getContentAsString()).contains(HAWAIIAN_PIZZA_TITLE);
        assertThat(response.getContentAsString()).contains(PINEAPPLE_RESPONSE.getName());
        assertThat(response.getContentAsString()).contains(PINEAPPLE_RESPONSE.getUnit());
        assertThat(response.getContentAsString()).contains(FLOUR_RESPONSE.getName());
        assertThat(response.getContentAsString()).contains(FLOUR_RESPONSE.getUnit());
        assertThat(response.getContentAsString()).contains(SALT_RESPONSE.getName());
    }

    @Test
    void shouldGetIngredientsByRecipeId() throws Exception {
        // given
        long recipeId = 2L;
        List<IngredientResponseDTO> ingredientResponses = List.of(SALT_RESPONSE, FLOUR_RESPONSE);
        when(recipeService.getAllIngredientsByRecipeId(recipeId)).thenReturn(ingredientResponses);

        // when
        MockHttpServletResponse response = mockMvc.perform(
                get(PREFIX + "/2/ingredients")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains(SALT_RESPONSE.getName());
        assertThat(response.getContentAsString()).contains(FLOUR_RESPONSE.getName());
        assertThat(response.getContentAsString()).contains(FLOUR_RESPONSE.getUnit());
        assertThat(response.getContentAsString()).contains(FLOUR_RESPONSE.getAmount().toString());
    }

    @Test
    void shouldGetCommentsByRecipeId() throws Exception {
        // given
        long recipeId = 1L;
        CommentResponseDTO pascalComment = new CommentResponseDTO(1L, PASCAL_BRODNICKI, NEGATIVE_COMMENT, LocalDate.now().minusDays(2).toString());
        CommentResponseDTO karolComment = new CommentResponseDTO(2L, KAROL_OKRASA, POSITIVE_COMMENT, LocalDate.now().toString());
        when(recipeService.getAllCommentsByRecipeId(recipeId)).thenReturn(List.of(pascalComment, karolComment));

        // when
        MockHttpServletResponse response = mockMvc.perform(
                get(PREFIX + "/1/comments")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains(PASCAL_BRODNICKI);
        assertThat(response.getContentAsString()).contains(NEGATIVE_COMMENT);
        assertThat(response.getContentAsString()).contains("2");
        assertThat(response.getContentAsString()).contains(KAROL_OKRASA);
        assertThat(response.getContentAsString()).contains(POSITIVE_COMMENT);
    }

    @Test
    void shouldGetAllMiniatures() throws Exception {
        // given
        List<RecipeMiniatureResponseDTO> recipeMiniatures = List.of(new RecipeMiniatureResponseDTO(1L, HAWAIIAN_PIZZA_TITLE, "url", LocalDate.now().toString()));
        when(recipeService.getAllRecipesMiniature()).thenReturn(recipeMiniatures);

        // when
        MockHttpServletResponse response = mockMvc.perform(
                get(PREFIX + "/miniatures")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains("1");
        assertThat(response.getContentAsString()).contains(HAWAIIAN_PIZZA_TITLE);
    }

    @Test
    void shouldUpdateRecipe() throws Exception {
        // given
        long recipeId = 1L;
        String newTitle = "The most wonderful Hawaiian pizza you will ever try!";
        RecipeRequestDTO hawaiianRequest = new RecipeRequestDTO(newTitle, HAWAIIAN_PIZZA_PREPARATION, List.of(FLOUR_REQUEST), "url");
        RecipeResponseDTO hawaiianResponse = new RecipeResponseDTO(recipeId, newTitle, HAWAIIAN_PIZZA_PREPARATION, null, null, List.of(FLOUR_RESPONSE), null, null);
        when(recipeService.updateRecipe(hawaiianRequest, recipeId)).thenReturn(hawaiianResponse);

        // when
        MockHttpServletResponse response = mockMvc.perform(
                patch(PREFIX + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(hawaiianRequest))
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains("1");
        assertThat(response.getContentAsString()).contains(newTitle);
        assertThat(response.getContentAsString()).contains(HAWAIIAN_PIZZA_PREPARATION);
        assertThat(response.getContentAsString()).contains(FLOUR_RESPONSE.getName());
    }

    @Test
    void shouldUpdateThumbnail() throws Exception {
        // given
        long id = 1L;
        String fakeUrl = "https://www.timeforpizza.api.com/thumbnail.jpg";
        RecipeResponseDTO recipeResponse = new RecipeResponseDTO(id, HAWAIIAN_PIZZA_TITLE, HAWAIIAN_PIZZA_PREPARATION, null, fakeUrl, List.of(), List.of(), List.of());
        when(recipeService.updateRecipeThumbnail(id, fakeUrl)).thenReturn(recipeResponse);

        // when
        MockHttpServletResponse response = mockMvc.perform(
                patch(PREFIX + "/1" + "/thumbnail")
                        .param("thumbnailUrl", fakeUrl)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains("1");
        assertThat(response.getContentAsString()).contains(HAWAIIAN_PIZZA_TITLE);
        assertThat(response.getContentAsString()).contains(fakeUrl);
    }

    @Test
    void shouldAddRecipe() throws Exception {
        // given
        RecipeRequestDTO recipeRequest = new RecipeRequestDTO(HAWAIIAN_PIZZA_TITLE, HAWAIIAN_PIZZA_PREPARATION, List.of(FLOUR_REQUEST, PINEAPPLE_REQUEST), null);
        RecipeResponseDTO recipeResponse = new RecipeResponseDTO(1L, HAWAIIAN_PIZZA_TITLE, HAWAIIAN_PIZZA_PREPARATION, null, null, List.of(FLOUR_RESPONSE, PINEAPPLE_RESPONSE), List.of(), List.of());
        when(recipeService.addRecipe(recipeRequest)).thenReturn(recipeResponse);

        // when
        MockHttpServletResponse response = mockMvc.perform(
                post(PREFIX)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(recipeRequest))
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains("1");
        assertThat(response.getContentAsString()).contains(HAWAIIAN_PIZZA_TITLE);
        assertThat(response.getContentAsString()).contains(HAWAIIAN_PIZZA_PREPARATION);
        assertThat(response.getContentAsString()).contains(FLOUR_RESPONSE.getName());
        assertThat(response.getContentAsString()).contains(FLOUR_RESPONSE.getUnit());
        assertThat(response.getContentAsString()).contains(PINEAPPLE_REQUEST.getUnit());
    }

    @Test
    void shouldAddComment() throws Exception {
        // given
        long recipeId = 1L;
        CommentRequestDTO commentRequest = new CommentRequestDTO(PASCAL_BRODNICKI, POSITIVE_COMMENT);
        CommentResponseDTO commentResponse = new CommentResponseDTO(2L, PASCAL_BRODNICKI, POSITIVE_COMMENT, LocalDate.now().toString());
        when(recipeService.addComment(recipeId, commentRequest)).thenReturn(commentResponse);

        // when
        MockHttpServletResponse response = mockMvc.perform(
                post(PREFIX + "/1/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(commentRequest))
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains("2");
        assertThat(response.getContentAsString()).contains(PASCAL_BRODNICKI);
        assertThat(response.getContentAsString()).contains(POSITIVE_COMMENT);
        assertThat(response.getContentAsString()).contains(LocalDate.now().toString());
    }

    @Test
    void shouldUploadImages() throws Exception {
        // given
        long recipeId = 1L;
        byte[] content = {1, 0, 1};
        MockMultipartFile mockMultipartFile = new MockMultipartFile("mock-image.jpg", content);
        MockMultipartFile mockMultipartFile1 = new MockMultipartFile("mock-image.jpg", "content".getBytes());
        doNothing().when(recipeService).uploadImages(anyLong(), any(MultipartFile[].class));

        // when
        MockHttpServletResponse response = mockMvc.perform(
                multipart(PREFIX + "/1/images")
                        .file("files", mockMultipartFile.getBytes())
                        .file("files", mockMultipartFile1.getBytes()))
                .andReturn()
                .getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        verify(recipeService).uploadImages(anyLong(), any(MultipartFile[].class));
    }
}
