package com.backend.timeforpizza.timeforpizzabackend.repository;

import com.backend.timeforpizza.timeforpizzabackend.model.Comment;
import com.backend.timeforpizza.timeforpizzabackend.model.Recipe;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("commentRepository")
public interface CommentRepository extends CrudRepository<Comment, Integer> {
    Iterable<Comment> findByRecipe(Recipe recipe);
    void deleteByRecipe(Recipe recipe);
}
