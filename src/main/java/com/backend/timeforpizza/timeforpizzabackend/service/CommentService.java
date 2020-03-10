package com.backend.timeforpizza.timeforpizzabackend.service;

import com.backend.timeforpizza.timeforpizzabackend.model.Comment;
import com.backend.timeforpizza.timeforpizzabackend.payload.CommentRequest;
import com.backend.timeforpizza.timeforpizzabackend.payload.CommentResponse;
import com.backend.timeforpizza.timeforpizzabackend.repository.CommentRepository;
import com.backend.timeforpizza.timeforpizzabackend.utils.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Null;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(@Qualifier("commentRepository") CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public CommentResponse addComment(CommentRequest commentRequest) {
        Comment comment = ModelMapper.mapCommentRequestToComment(commentRequest);
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

    @Null
    public CommentResponse updateComment(CommentRequest newComment, Long commentId) {
        Comment oldComment = commentRepository.findById(commentId)
                .orElse(null);
        if(oldComment != null) {
            oldComment.setComment(newComment.getComment());
            oldComment.setNickname(newComment.getNickname());
            return ModelMapper.mapCommentToCommentResponse(commentRepository.save(oldComment));
        }
        return null;
    }
}
