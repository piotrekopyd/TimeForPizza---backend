package com.backend.timeforpizza.timeforpizzabackend.service;

import com.backend.timeforpizza.timeforpizzabackend.exception.ResourceNotFoundException;
import com.backend.timeforpizza.timeforpizzabackend.model.Comment;
import com.backend.timeforpizza.timeforpizzabackend.dto.CommentRequestDTO;
import com.backend.timeforpizza.timeforpizzabackend.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment addComment(Comment comment) {
        if (comment.getRecipe() == null) {
            // TODO
        }
        return commentRepository.save(comment);
    }

    public Comment getCommentById(Long id) {
         return commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "commentId", id));
    }

    public List<Comment> getAllCommentsByRecipeId(Long recipeId) {
        return commentRepository.findAllByRecipeRecipeId(recipeId);
    }

    public void deleteCommentById(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    public void deleteAllCommentsByRecipeId(Long recipeId) {
        commentRepository.deleteAllByRecipeRecipeId(recipeId);
    }

    public Comment updateComment(CommentRequestDTO commentRequestDTO, Long commentId) throws ResourceNotFoundException {
        Comment oldComment = commentRepository.findById(commentId)
                .orElseThrow( () -> new ResourceNotFoundException("Comment", "commentId", commentId));

        oldComment.setComment(commentRequestDTO.getComment());
        oldComment.setNickname(commentRequestDTO.getNickname());
        return commentRepository.save(oldComment);
    }
}
