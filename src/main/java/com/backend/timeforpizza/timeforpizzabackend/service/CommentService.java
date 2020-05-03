package com.backend.timeforpizza.timeforpizzabackend.service;

import com.backend.timeforpizza.timeforpizzabackend.exception.ResourceNotFoundException;
import com.backend.timeforpizza.timeforpizzabackend.model.Comment;
import com.backend.timeforpizza.timeforpizzabackend.model.Recipe;
import com.backend.timeforpizza.timeforpizzabackend.dto.CommentRequestDTO;
import com.backend.timeforpizza.timeforpizzabackend.dto.CommentResponseDTO;
import com.backend.timeforpizza.timeforpizzabackend.repository.CommentRepository;
import com.backend.timeforpizza.timeforpizzabackend.utils.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    CommentResponseDTO addComment(CommentRequestDTO commentRequestDTO, Recipe recipe) {
        Comment comment = ModelMapper.mapCommentRequestToComment(commentRequestDTO);
        comment.setRecipe(recipe);
        return ModelMapper.mapCommentToCommentResponse(commentRepository.save(comment));
    }

    public CommentResponseDTO getCommentById(Long id) {
         return commentRepository.findById(id)
                .map(ModelMapper::mapCommentToCommentResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "commentId", id));
    }

    public void deleteCommentById(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    public void deleteAllCommentsByRecipeId(Long recipeId) {
        commentRepository.deleteAllByRecipeRecipeId(recipeId);
    }

    public CommentResponseDTO updateComment(CommentRequestDTO commentRequestDTO, Long commentId) throws ResourceNotFoundException {
        Comment oldComment = commentRepository.findById(commentId)
                .orElseThrow( () -> new ResourceNotFoundException("Comment", "commentId", commentId));

        oldComment.setComment(commentRequestDTO.getComment());
        oldComment.setNickname(commentRequestDTO.getNickname());
        return ModelMapper.mapCommentToCommentResponse(commentRepository.save(oldComment));
    }
}
