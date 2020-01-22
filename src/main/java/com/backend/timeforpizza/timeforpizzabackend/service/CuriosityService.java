package com.backend.timeforpizza.timeforpizzabackend.service;

import com.backend.timeforpizza.timeforpizzabackend.dao.CuriosityDao;
import com.backend.timeforpizza.timeforpizzabackend.model.Curiosity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CuriosityService {
    private final CuriosityDao curiosityDao;

    @Autowired
    public CuriosityService(@Qualifier("curiosityDao") CuriosityDao curiosityDao) {
        this.curiosityDao = curiosityDao;
    }

    public int addCuriosity(Curiosity curiosity) {
        Curiosity saved = curiosityDao.save(curiosity);
        return saved != null ? 1 : - 1;
    }

    public Iterable<Curiosity> getAllCuriosities() {
        return curiosityDao.findAll();
    }

    public Optional<Curiosity> getCuriosityById(Integer id) {
        return curiosityDao.findById(id);
    }

    public void deleteCuriosityById(Integer id) {
        curiosityDao.deleteById(id);
    }

    public int updateCuriosity(Curiosity newCuriosity) {
        Curiosity saved = curiosityDao.save(newCuriosity);
        return saved != null ? 1 : - 1;
    }

}
