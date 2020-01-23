package com.backend.timeforpizza.timeforpizzabackend.service;

import com.backend.timeforpizza.timeforpizzabackend.dao.CuriosityDao;
import com.backend.timeforpizza.timeforpizzabackend.model.Curiosity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
        Curiosity oldCuriosity = curiosityDao.findById(newCuriosity.getId())
                .orElse(null);
        if (oldCuriosity != null) {
            oldCuriosity.setTitle(newCuriosity.getTitle());
            oldCuriosity.setContent(newCuriosity.getContent());
            curiosityDao.save(oldCuriosity);
        }
        return oldCuriosity != null ? 1 : - 1;
    }

}
