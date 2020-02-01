package com.backend.timeforpizza.timeforpizzabackend.repository;

import com.backend.timeforpizza.timeforpizzabackend.model.Curiosity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("curiosityRepository")
public interface CuriosityRepository extends CrudRepository<Curiosity, Integer> {
}
