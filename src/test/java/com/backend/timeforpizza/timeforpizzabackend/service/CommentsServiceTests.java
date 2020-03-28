package com.backend.timeforpizza.timeforpizzabackend.service;

import com.backend.timeforpizza.timeforpizzabackend.model.Comment;
import com.backend.timeforpizza.timeforpizzabackend.model.Comment;
import com.backend.timeforpizza.timeforpizzabackend.model.Comment;
import com.backend.timeforpizza.timeforpizzabackend.payload.CommentRequest;
import com.backend.timeforpizza.timeforpizzabackend.payload.CommentResponse;
import com.backend.timeforpizza.timeforpizzabackend.payload.CommentResponse;
import com.backend.timeforpizza.timeforpizzabackend.repository.CommentRepository;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith({SpringExtension.class})
public class CommentsServiceTests {
    @Mock
    CommentRepository commentRepository;

    @InjectMocks
    CommentService commentService;

    private List<Comment> commentData = new ArrayList<>();

    @AfterEach
    void clean() {
        commentData = new ArrayList<>();
    }

    @Test
    public void shouldGetCommentById() {
        // GIVEN
        Comment comment = new Comment(1L,"test comment", "Nice");
        Comment comment1 = new Comment(2L, "test comment1", "great");
        Comment comment2 = new Comment(10L,"test comment2", "brilliant");
        CommentResponse expectedComment = new CommentResponse(1L,"test comment", "Nice");
        CommentResponse expectedComment2 = new CommentResponse(10L,"test comment2", "brilliant");
        commentData.addAll(List.of(comment,comment1,comment2));

        // WHEN
        when(commentRepository.findById(anyLong())).then(arg -> commentData.stream()
                .filter(e -> e.getCommentId().equals(arg.getArgument(0)))
                .findFirst());
        CommentResponse givenComment = commentService.getCommentById(1L);
        CommentResponse givenComment2 = commentService.getCommentById(10L);

        // THEN
        assertEquals(expectedComment, givenComment);
        assertEquals(expectedComment2, givenComment2);
    }

}
