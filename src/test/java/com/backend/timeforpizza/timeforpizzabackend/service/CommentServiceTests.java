package com.backend.timeforpizza.timeforpizzabackend.service;

import com.backend.timeforpizza.timeforpizzabackend.dto.CommentRequestDTO;
import com.backend.timeforpizza.timeforpizzabackend.exception.ResourceNotFoundException;
import com.backend.timeforpizza.timeforpizzabackend.model.Comment;
import com.backend.timeforpizza.timeforpizzabackend.model.Recipe;
import com.backend.timeforpizza.timeforpizzabackend.repository.CommentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class CommentServiceTests {
    @Mock
    CommentRepository commentRepository;

    @InjectMocks
    CommentService commentService;

    private final static Long COMMENT_ID = 1L;
    private final static String POSITIVE_COMMENT = "This pizza is ridiculously good!";
    private final static Long RECIPE_ID = 1L;
    private final static Recipe RECIPE = new Recipe(RECIPE_ID, "Recipe", "preparation", "url", LocalDate.now());
    private final static Comment BEN_COMMENT = new Comment(COMMENT_ID, "Ben Shapiro", POSITIVE_COMMENT, LocalDate.now(), RECIPE);

    @Test
    void shouldAddComment() {
        // given
        when(commentRepository.save(BEN_COMMENT)).thenReturn(BEN_COMMENT);

        // when
        Comment givenComment = commentService.addComment(BEN_COMMENT);

        // then
        assertEquals(BEN_COMMENT, givenComment);
    }

    @Test
    void shouldGetCommentById() {
        // given
        when(commentRepository.findById(COMMENT_ID)).thenReturn(Optional.of(BEN_COMMENT));

        // when
        Comment givenComment = commentService.getCommentById(COMMENT_ID);

        // then
        assertEquals(BEN_COMMENT, givenComment);
    }

    @Test
    void shouldThrowExceptionWhenCommentNotFoundById() {
        // given
        when(commentRepository.findById(COMMENT_ID)).thenReturn(Optional.empty());

        // then
        assertThrows(ResourceNotFoundException.class, () -> commentService.getCommentById(COMMENT_ID));
    }

    @Test
    void shouldReturnAllCommentsByRecipeId() {
        // given
        Comment grandmaToeComment = new Comment(2L, "hater", "Tastes like my grandma's toe", LocalDate.now(), RECIPE);
        when(commentRepository.findAllByRecipeRecipeId(RECIPE_ID)).thenReturn(List.of(BEN_COMMENT, grandmaToeComment));

        // when
        List<Comment> givenComments = commentService.getAllCommentsByRecipeId(RECIPE_ID);

        // then
        assertEquals(List.of(BEN_COMMENT, grandmaToeComment), givenComments);
    }

    @Test
    void shouldReturnEmptyListWhenNoCommentsFoundByRecipeId() {
        // given
        when(commentRepository.findAllByRecipeRecipeId(RECIPE_ID)).thenReturn(List.of());

        // when
        var givenComments = commentService.getAllCommentsByRecipeId(RECIPE_ID);

        // then
        assertEquals(List.of(), givenComments);
    }

    @Test
    void shouldDeleteCommentById() {
        // given
        doNothing().when(commentRepository).deleteById(COMMENT_ID);

        // when
        commentService.deleteCommentById(COMMENT_ID);

        // then
        verify(commentRepository).deleteById(COMMENT_ID);
    }

    @Test
    void shouldDeleteAllCommentsByRecipeId() {
        // given
        doNothing().when(commentRepository).deleteAllByRecipeRecipeId(RECIPE_ID);

        // when
        commentService.deleteAllCommentsByRecipeId(RECIPE_ID);

        // then
        verify(commentRepository).deleteAllByRecipeRecipeId(RECIPE_ID);
    }

    @Test
    void shouldUpdateComment() {
        // given
        Long commentId = 2L;
        String editedComment = "This pizza is definitely really high in my hierarchy. Edit: An oppressive patriarchy is not completely bloody nightmare";
        Comment jordanComment = new Comment(commentId, "Jordan Peterson", "This pizza is definitely really high in my hierarchy", LocalDate.now(), RECIPE);
        Comment newJordanComment = new Comment(commentId, "Jordan Peterson", editedComment, LocalDate.now(), RECIPE);
        CommentRequestDTO commentRequest = new CommentRequestDTO("Jordan Peterson", editedComment);
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(jordanComment));
        when(commentRepository.save(newJordanComment)).thenReturn(newJordanComment);

        // when
        Comment givenComment = commentService.updateComment(commentRequest, commentId);

        // then
        assertEquals(newJordanComment, givenComment);
    }

    @Test
    void shouldThrowExceptionWhenCommentToUpdateNotFoundById() {
        // given
        CommentRequestDTO commentRequest = new CommentRequestDTO("Ben Shapiro", "Even better than I thought it would be");
        when(commentRepository.findById(COMMENT_ID)).thenReturn(Optional.empty());

        // then
        assertThrows(ResourceNotFoundException.class, () -> commentService.updateComment(commentRequest, COMMENT_ID));
    }
}
