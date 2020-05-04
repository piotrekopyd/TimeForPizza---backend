package com.backend.timeforpizza.timeforpizzabackend.repository;

import com.backend.timeforpizza.timeforpizzabackend.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByRecipeRecipeId(Long recipeId);
    void deleteAllByRecipeRecipeId(Long recipeId);
}
