package com.backend.timeforpizza.timeforpizzabackend.service;

import com.backend.timeforpizza.timeforpizzabackend.exception.ResourceNotFoundException;
import com.backend.timeforpizza.timeforpizzabackend.model.Comment;
import com.backend.timeforpizza.timeforpizzabackend.model.Recipe;
import com.backend.timeforpizza.timeforpizzabackend.payload.CommentRequest;
import com.backend.timeforpizza.timeforpizzabackend.payload.CommentResponse;
import com.backend.timeforpizza.timeforpizzabackend.repository.CommentRepository;
import com.backend.timeforpizza.timeforpizzabackend.utils.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    CommentResponse addComment(CommentRequest commentRequest, Recipe recipe) {
        Comment comment = ModelMapper.mapCommentRequestToComment(commentRequest);
        comment.setRecipe(recipe);
        return ModelMapper.mapCommentToCommentResponse(commentRepository.save(comment));
    }

    Comment addComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public CommentResponse getCommentById(Long id) {
         return commentRepository.findById(id)
                .map(ModelMapper::mapCommentToCommentResponse)
                .orElse(null);
    }

    public void deleteCommentById(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    public void deleteAllCommentsByRecipeId(Long recipeId) {
        commentRepository.deleteAllByRecipeRecipeId(recipeId);
    }

    public CommentResponse updateComment(CommentRequest commentRequest, Long commentId) throws ResourceNotFoundException {
        Comment oldComment = commentRepository.findById(commentId)
                .orElseThrow( () -> new ResourceNotFoundException("Comment", "commentId", commentId));

        oldComment.setComment(commentRequest.getComment());
        oldComment.setNickname(commentRequest.getNickname());
        return ModelMapper.mapCommentToCommentResponse(commentRepository.save(oldComment));
    }
}
