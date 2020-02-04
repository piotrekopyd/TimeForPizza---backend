package com.backend.timeforpizza.timeforpizzabackend.service;

import com.backend.timeforpizza.timeforpizzabackend.model.Comment;
import com.backend.timeforpizza.timeforpizzabackend.model.Recipe;
import com.backend.timeforpizza.timeforpizzabackend.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(@Qualifier("commentRepository") CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public int addComment(Comment comment) {
        return commentRepository.save(comment) != null ? 1 : -1;
    }

    public int addAllComments(List<Comment> comments) {
        return commentRepository.saveAll(comments) != null ? 1 : -1;
}

    public List<Comment> getCommentsByRecipe(Recipe recipe) {
        List<Comment> comments = new ArrayList<>();
        commentRepository.findByRecipe(recipe).forEach(comments::add);
        return comments;
    }

    public void deleteComment(Comment comment) {
        commentRepository.delete(comment);
    }

    public void deleteCommentsByRecipe(Recipe recipe) {
        commentRepository.deleteByRecipe(recipe);
    }

    public int updateComment(Comment newComment) {
        Comment oldComment = commentRepository.findById(newComment.getCommentId())
                .orElse(null);
        if(oldComment != null) {
            oldComment.setComment(newComment.getComment());
            oldComment.setNickname(newComment.getNickname());
            oldComment.setRecipe(newComment.getRecipe());
            commentRepository.save(oldComment);
            return 1;
        }
        return -1;
    }
}
