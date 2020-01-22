package com.backend.timeforpizza.timeforpizzabackend.dao;

import com.backend.timeforpizza.timeforpizzabackend.model.Curiosity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("curiosityDao")
public interface CuriosityDao extends CrudRepository<Curiosity, Integer> {
//    int insertCutiosity(UUID id, Curiosity curiosity);
//
//    default int addCuriosity(Curiosity curiosity) {
//        UUID id = UUID.randomUUID();
//        return insertCutiosity(id, curiosity);
//    }
//
//    public List<Curiosity> selectAll();
//    Optional<Curiosity> selectCuriosity(UUID id);
//
//    int deleteCuriositynById(UUID id);
//    int updateCuriosityById(UUID id, Curiosity curiosity);

}
