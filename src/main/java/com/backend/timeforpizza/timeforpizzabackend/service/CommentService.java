package com.backend.timeforpizza.timeforpizzabackend.service;

import com.backend.timeforpizza.timeforpizzabackend.exception.ResourceNotFoundException;
import com.backend.timeforpizza.timeforpizzabackend.model.Comment;
import com.backend.timeforpizza.timeforpizzabackend.dto.CommentRequestDTO;
import com.backend.timeforpizza.timeforpizzabackend.repository.CommentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    private static final Logger logger = LoggerFactory.getLogger(CommentService.class);

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment addComment(Comment comment) {
        if (comment.getRecipe() == null) {
            // TODO
        }
        comment = commentRepository.save(comment);
        logger.info("Added comment with id: {}.", comment.getCommentId());
        return comment;
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
        logger.info("Deleted comment with id: {}.", commentId);
    }

    public void deleteAllCommentsByRecipeId(Long recipeId) {
        commentRepository.deleteAllByRecipeRecipeId(recipeId);
        logger.info("Deleted all comments for recipe with id:{}.", recipeId);
    }

    public Comment updateComment(CommentRequestDTO commentRequestDTO, Long commentId) throws ResourceNotFoundException {
        Comment oldComment = commentRepository.findById(commentId)
                .orElseThrow( () -> new ResourceNotFoundException("Comment", "commentId", commentId));

        oldComment.setComment(commentRequestDTO.getComment());
        oldComment.setNickname(commentRequestDTO.getNickname());
        oldComment = commentRepository.save(oldComment);
        logger.info("Updated comment with id: {}.", commentId);
        return oldComment;
    }
}
