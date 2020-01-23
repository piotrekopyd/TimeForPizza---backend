package com.backend.timeforpizza.timeforpizzabackend.dao;

import com.backend.timeforpizza.timeforpizzabackend.model.Curiosity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository("curiosityDao")
public interface CuriosityDao extends CrudRepository<Curiosity, Integer> {
}
