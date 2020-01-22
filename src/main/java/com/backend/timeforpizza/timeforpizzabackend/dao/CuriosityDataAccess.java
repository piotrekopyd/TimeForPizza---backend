package com.backend.timeforpizza.timeforpizzabackend.dao;

import com.backend.timeforpizza.timeforpizzabackend.model.Curiosity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

//@Repository("curiosityDao")
//public class CuriosityDataAccess implements CuriosityDao {

//    @Transactional
//    @Override
//    public int insertCutiosity(UUID id, Curiosity curiosity) {
//        return 0;
//    }
//
//    @Transactional
//    @Override
//    public List<Curiosity> selectAll() {
//        return null;
//    }
//
//    @Transactional
//    @Override
//    public Optional<Curiosity> selectCuriosity(Integer id) {
//        return Optional.empty();
//    }
//
//    @Transactional
//    @Override
//    public int deleteCuriositynById(Integer id) {
//        return 0;
//    }
//
//    @Transactional
//    @Override
//    public int updateCuriosityById(UUID id, Curiosity curiosity) {
//        return 0;
//    }
//}
