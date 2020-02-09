package com.backend.timeforpizza.timeforpizzabackend.service;

import com.backend.timeforpizza.timeforpizzabackend.model.Comment;
import com.backend.timeforpizza.timeforpizzabackend.model.Recipe;
import com.backend.timeforpizza.timeforpizzabackend.payload.CommentRequest;
import com.backend.timeforpizza.timeforpizzabackend.payload.CommentResponse;
import com.backend.timeforpizza.timeforpizzabackend.repository.CommentRepository;
import com.backend.timeforpizza.timeforpizzabackend.utils.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(@Qualifier("commentRepository") CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public int addComment(CommentRequest commentRequest) {
        Comment comment = ModelMapper.mapCommentRequestToComment(commentRequest);
        return commentRepository.save(comment) != null ? 1 : -1;
    }

    public int addComment(Comment comment) {
        return commentRepository.save(comment) != null ? 1 : -1;
    }

    public CommentResponse getCommentById(Integer id) {
         return commentRepository.findById(id)
                .map(ModelMapper::mapCommentToCommentResponse)
                .orElse(null);
    }

    public void deleteCommentById(Integer commentId) {
        commentRepository.deleteById(commentId);
    }

    public int updateComment(CommentRequest newComment, Integer commentId) {
        Comment oldComment = commentRepository.findById(commentId)
                .orElse(null);
        if(oldComment != null) {
            oldComment.setComment(newComment.getComment());
            oldComment.setNickname(newComment.getNickname());
            commentRepository.save(oldComment);
            return 1;
        }
        return -1;
    }
}
