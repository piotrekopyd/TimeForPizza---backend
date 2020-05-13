//package com.backend.timeforpizza.timeforpizzabackend.service;
//
//import com.backend.timeforpizza.timeforpizzabackend.dto.*;
//import com.backend.timeforpizza.timeforpizzabackend.exception.ResourceNotFoundException;
//import com.backend.timeforpizza.timeforpizzabackend.model.Comment;
//import com.backend.timeforpizza.timeforpizzabackend.model.Ingredient;
//import com.backend.timeforpizza.timeforpizzabackend.model.Recipe;
//import com.backend.timeforpizza.timeforpizzabackend.repository.RecipeRepository;
//import com.backend.timeforpizza.timeforpizzabackend.util.ModelMapper;
//import org.assertj.core.util.Arrays;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(SpringExtension.class)
//public class RecipeServiceTests {
//
//    @Mock
//    private RecipeRepository recipeRepository;
//    @Mock
//    private IngredientService ingredientService;
//    @Mock
//    private RecipeImageService recipeImageService;
//    @Mock
//    private CommentService commentService;
//
//    @InjectMocks
//    private RecipeService recipeService;
//
//    private final static Long HAWAIIAN_RECIPE_ID = 1L;
//    private final static Recipe HAWAIIAN_RECIPE = new Recipe(HAWAIIAN_RECIPE_ID, "Hawaiian pizza", "preparation", null, null);
//    private final static Ingredient FLOUR = new Ingredient(1L, "flour", 150D, "g", HAWAIIAN_RECIPE);
//    private final static Ingredient MOZZARELLA = new Ingredient(2L, "mozzarella cheese", 75D, "g", HAWAIIAN_RECIPE);
//    private final static RecipeRequestDTO HAWAIIAN_RECIPE_REQUEST = new RecipeRequestDTO("Hawaiian pizza", "preparation", List.of(
//            new IngredientRequestDTO("flour", 150D, "g"),
//            new IngredientRequestDTO("mozzarella cheese", 75D, "g")
//    ), null);
//
//
//    @Test
//    void shouldAddRecipe() {
//        // given
//        Recipe hawaiianPizzaRecipe = new Recipe(null, "Hawaiian pizza", "preparation", null, LocalDate.now());
//        Ingredient flour = new Ingredient(null, "flour", 150D, "g", HAWAIIAN_RECIPE);
//        Ingredient mozzarella = new Ingredient(null, "mozzarella cheese", 75D, "g", HAWAIIAN_RECIPE);
//        when(recipeRepository.save(hawaiianPizzaRecipe)).thenReturn(HAWAIIAN_RECIPE);
//        when(ingredientService.addAllIngredients(List.of(flour, mozzarella))).thenReturn(List.of(FLOUR, MOZZARELLA));
//
//        // when
//        var response = recipeService.addRecipe(HAWAIIAN_RECIPE_REQUEST);
//
//        // then
//        assertEquals(ModelMapper.mapToRecipeResponse(HAWAIIAN_RECIPE, List.of(FLOUR, MOZZARELLA), List.of(), List.of()), response);
//    }
//
//    @Test
//    void shouldGetRecipeById() {
//        // given
//        when(recipeRepository.findById(HAWAIIAN_RECIPE_ID)).thenReturn(Optional.of(HAWAIIAN_RECIPE));
//        when(ingredientService.getAllIngredientsByRecipeId(HAWAIIAN_RECIPE_ID)).thenReturn(List.of(FLOUR, MOZZARELLA));
//        when(commentService.getAllCommentsByRecipeId(HAWAIIAN_RECIPE_ID)).thenReturn(List.of());
//        when(recipeImageService.getAllByRecipeId(HAWAIIAN_RECIPE_ID)).thenReturn(List.of());
//
//        // when
//        var response = recipeService.getRecipeById(HAWAIIAN_RECIPE_ID);
//
//        // then
//        assertEquals(ModelMapper.mapToRecipeResponse(HAWAIIAN_RECIPE, List.of(FLOUR, MOZZARELLA), List.of(), List.of()), response);
//    }
//
//    @Test
//    void shouldThrowExceptionWhenRecipeNotFoundById() {
//        //given
//        when(recipeRepository.findById(HAWAIIAN_RECIPE_ID)).thenReturn(Optional.empty());
//
//        // then
//        assertThrows(ResourceNotFoundException.class, () -> recipeService.getRecipeById(HAWAIIAN_RECIPE_ID));
//    }
//
//    @Test
//    void shouldGetAllRecipeMiniatures() {
//        // given
//        RecipeMiniatureResponseDTO recipeMiniatureResponse = ModelMapper.mapToRecipeMiniature(HAWAIIAN_RECIPE);
//        when(recipeRepository.findAll()).thenReturn(List.of(HAWAIIAN_RECIPE));
//        // when
//        var response = recipeService.getAllRecipesMiniature();
//        // then
//        assertEquals(List.of(recipeMiniatureResponse), response);
//    }
//
//    @Test
//    void shouldUpdateRecipe() {
//        // given
//        Recipe capreseRecipe = new Recipe(2L, "Caprese pizza", "easy prep", "url", LocalDate.now().minusDays(5));
//        RecipeRequestDTO recipeRequest = new RecipeRequestDTO("Caprese pizza", "preparation", List.of(
//                new IngredientRequestDTO("olive oil", 1D, "tablespoon"),
//                new IngredientRequestDTO("large tomatoes slices", 2D, null)
//        ), null);
//        List<Ingredient> ingredients = List.of(
//                new Ingredient(null, "olive oil", 1D, "tablespoon", capreseRecipe),
//                new Ingredient(null, "large tomatoes slices", 2D, null, capreseRecipe)
//        );
//        when(recipeRepository.findById(2L)).thenReturn(Optional.of(capreseRecipe));
//        when(recipeRepository.save(capreseRecipe)).thenReturn(capreseRecipe);
//        when(ingredientService.updateAllIngredientsByRecipeId(2L, ingredients)).thenReturn(ingredients);
//        when(commentService.getAllCommentsByRecipeId(2L)).thenReturn(List.of());
//        when(recipeImageService.getAllByRecipeId(2L)).thenReturn(List.of());
//
//        // when
//        var response  = recipeService.updateRecipe(recipeRequest, 2L);
//
//        // then
//        assertEquals(ModelMapper.mapToRecipeResponse(capreseRecipe, ingredients, List.of(), List.of()), response);
//    }
//
//    @Test
//    void shouldThrowExceptionWhenRecipeNotFoundWhileUpdating() {
//        // given
//        long recipeId = 2L;
//        RecipeRequestDTO recipeRequest = new RecipeRequestDTO("Caprese pizza", "preparation", List.of(
//                new IngredientRequestDTO("olive oil", 1D, "tablespoon")
//        ), null);
//        when(recipeRepository.findById(recipeId)).thenReturn(Optional.empty());
//
//        // then
//        assertThrows(ResourceNotFoundException.class, () -> recipeService.updateRecipe(recipeRequest, recipeId));
//    }
//
//    @Test
//    void shouldUpdateRecipeThumbnail() {
//        // given
//        Recipe napoletana = new Recipe(3L, "napoletana recipe", "preparation", null, LocalDate.now());
//        when(recipeRepository.findById(3L)).thenReturn(Optional.of(napoletana));
//        when(ingredientService.getAllIngredientsByRecipeId(3L)).thenReturn(List.of());
//        when(commentService.getAllCommentsByRecipeId(3L)).thenReturn(List.of());
//        when(recipeImageService.getAllByRecipeId(3L)).thenReturn(List.of());
//
//        // when
//        var response = recipeService.updateRecipeThumbnail(3L, "thumbnailUrl");
//
//        // then
//        assertThat(response.toString()).contains("napoletana recipe");
//        assertThat(response.toString()).contains("3");
//        assertThat(response.toString()).contains("thumbnailUrl");
//    }
//
//    @Test
//    void shouldThrowExceptionWhenRecipeNotFoundWhileUpdatingThumbnail() {
//        // given
//        long recipeId = 3L;
//        when(recipeRepository.findById(recipeId)).thenReturn(Optional.empty());
//
//        // then
//        assertThrows(ResourceNotFoundException.class,  () -> recipeService.updateRecipeThumbnail(recipeId, "thumbnailUrl"));
//    }
//
//    @Test
//    void shouldDeleteRecipeById() {
//        // given
//        long recipeId = 1L;
//        doNothing().when(commentService).deleteAllCommentsByRecipeId(recipeId);
//        doNothing().when(ingredientService).deleteAllIngredientsByRecipeId(recipeId);
//        doNothing().when(recipeImageService).deleteAllImagesByRecipeId(recipeId);
//        doNothing().when(recipeRepository).deleteById(recipeId);
//
//        // when
//        recipeService.deleteRecipeById(recipeId);
//
//        // then
//        verify(commentService).deleteAllCommentsByRecipeId(recipeId);
//        verify(ingredientService).deleteAllIngredientsByRecipeId(recipeId);
//        verify(recipeImageService).deleteAllImagesByRecipeId(recipeId);
//        verify(recipeRepository).deleteById(recipeId);
//    }
//
//    /** Ingredients */
//    @Test
//    void shouldAddIngredient() {
//        // given
//        long recipeId = 1L;
//        long ingredientId = 3L;
//        IngredientRequestDTO ingredientRequest = new IngredientRequestDTO("medium onion", 1D, null);
//        Ingredient onion = new Ingredient(null, "medium onion", 1D, null, HAWAIIAN_RECIPE);
//        when(recipeRepository.findById(recipeId)).thenReturn(Optional.of(HAWAIIAN_RECIPE));
//        when(ingredientService.addIngredient(onion)).then(e -> {
//            Ingredient ingredient = e.getArgument(0);
//            ingredient.setIngredientId(ingredientId);
//            return ingredient;
//        });
//
//        // when
//        var response = recipeService.addIngredient(recipeId, ingredientRequest);
//
//        // then
//        assertThat(response.toString()).contains("3");
//        assertThat(response.toString()).contains("medium onion");
//    }
//
//    @Test
//    void shouldGetAllIngredientsByRecipeId() {
//        // given
//        long recipeId = 1L;
//        List<IngredientResponseDTO> expected = List.of(FLOUR, MOZZARELLA).stream()
//                .map(ModelMapper::mapToIngredientResponse)
//                .collect(Collectors.toList());
//        when(ingredientService.getAllIngredientsByRecipeId(recipeId)).thenReturn(List.of(FLOUR, MOZZARELLA));
//
//        // when
//        var response = recipeService.getAllIngredientsByRecipeId(recipeId);
//
//        // then
//        assertEquals(expected, response);
//    }
//
//    @Test
//    void shouldReturnEmptyListWhenNoIngredientsFoundByRecipeId() {
//        // given
//        long recipeId = 1L;
//        when(ingredientService.getAllIngredientsByRecipeId(recipeId)).thenReturn(List.of());
//
//        // when
//        var response = recipeService.getAllIngredientsByRecipeId(recipeId);
//        // then
//        assertEquals(List.of(), response);
//    }
//
//    /** Comments */
//    @Test
//    void shouldAddComment() {
//        // given
//        long recipeId = 1L;
//        CommentRequestDTO commentRequest = new CommentRequestDTO("John Wick", "Delicious pizza!");
//        Comment johnComment = new Comment(null, "John Wick", "Delicious pizza!", LocalDate.now(), HAWAIIAN_RECIPE);
//        when(recipeRepository.findById(recipeId)).thenReturn(Optional.of(HAWAIIAN_RECIPE));
//        when(commentService.addComment(johnComment)).then(e -> {
//            Comment comment = e.getArgument(0);
//            comment.setCommentId(1L);
//            return comment;
//        });
//
//        // when
//        var response = recipeService.addComment(recipeId, commentRequest);
//        //then
//        assertThat(response.toString()).contains("1");
//        assertThat(response.toString()).contains("John Wick");
//        assertThat(response.toString()).contains("Delicious pizza!");
//    }
//
//    @Test
//    void shouldThrowExceptionWhenRecipeNotFountByIdWhileAddingComment() {
//        // given
//        long recipeId = 1L;
//        CommentRequestDTO commentRequest = new CommentRequestDTO("John Wick", "Delicious pizza!");
//        when(recipeRepository.findById(recipeId)).thenReturn(Optional.empty());
//
//        // then
//        assertThrows(ResourceNotFoundException.class, () -> recipeService.addComment(recipeId, commentRequest));
//    }
//
//    @Test
//    void shouldDeleteCommentById() {
//        // given
//        long commentId = 1L;
//        doNothing().when(commentService).deleteCommentById(commentId);
//
//        // when
//        recipeService.deleteCommentById(commentId);
//        // then
//        verify(commentService).deleteCommentById(commentId);
//    }
//
//    @Test
//    void shouldGetAllCommentsByRecipeId() {
//        // given
//        long recipeId = HAWAIIAN_RECIPE_ID;
//        List<Comment> comments = List.of(
//                new Comment(1L, "Dominic Torreto", "Enjoyed id!", LocalDate.now(), HAWAIIAN_RECIPE),
//                new Comment(2L, "Mia Torreto", "Honestly, not so bad", LocalDate.now(), HAWAIIAN_RECIPE)
//        );
//        List<CommentResponseDTO> expected = comments.stream()
//                .map(ModelMapper::mapToCommentResponse)
//                .collect(Collectors.toList());
//        when(commentService.getAllCommentsByRecipeId(recipeId)).thenReturn(comments);
//
//        // when
//        var response = recipeService.getAllCommentsByRecipeId(recipeId);
//        // then
//        assertEquals(expected, response);
//    }
//
//    /** Images */
//    @Test
//    void shouldUploadImages() {
//        // given
//        long recipeId = HAWAIIAN_RECIPE_ID;
//        byte[] content = {1, 0, 1};
//        MultipartFile[] multipartFiles = Arrays.array(new MockMultipartFile("image.jpg", content));
////        MockMultipartFile mockMultipartFile =
//        when(recipeRepository.findById(recipeId)).thenReturn(Optional.of(HAWAIIAN_RECIPE));
//        doNothing().when(recipeImageService).uploadImages(multipartFiles, HAWAIIAN_RECIPE);
//        // when
//        recipeService.uploadImages(recipeId, multipartFiles);
//        // then
//        verify(recipeImageService).uploadImages(multipartFiles, HAWAIIAN_RECIPE);
//    }
//
//    @Test
//    void shouldThrowExceptionWhenRecipeNotFountWhileUploadingImages() {
//        // given
//        long recipeId = HAWAIIAN_RECIPE_ID;
//        byte[] content = {1, 0, 1};
//        MultipartFile[] multipartFiles = Arrays.array(new MockMultipartFile("image.jpg", content));
//        when(recipeRepository.findById(recipeId)).thenReturn(Optional.empty());
//        // then
//        assertThrows(ResourceNotFoundException.class, () -> recipeService.uploadImages(recipeId, multipartFiles));
//    }
//
//    @Test
//    void shouldDeleteImages() {
//        // given
//        List<DeleteImageRequestDTO> deleteImageRequests = List.of(
//                new DeleteImageRequestDTO(1L, "ingredients_image.jpg"),
//                new DeleteImageRequestDTO(1L, "final_pizza.jpg")
//        );
//        doNothing().when(recipeImageService).deleteImages(deleteImageRequests);
//        // when
//        recipeService.deleteImages(deleteImageRequests);
//        // then
//        verify(recipeImageService).deleteImages(deleteImageRequests);
//    }
//}
