package com.backend.timeforpizza.timeforpizzabackend.service;

import com.backend.timeforpizza.timeforpizzabackend.repository.CuriosityRepository;
import com.backend.timeforpizza.timeforpizzabackend.model.Curiosity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CuriosityService {
    private final CuriosityRepository curiosityRepository;

    @Autowired
    public CuriosityService(@Qualifier("curiosityRepository") CuriosityRepository curiosityRepository) {
        this.curiosityRepository = curiosityRepository;
    }

    public int addCuriosity(Curiosity curiosity) {
        Curiosity addedCuriosity = curiosityRepository.save(curiosity);
        return addedCuriosity != null ? 1 : - 1;
    }

    public Iterable<Curiosity> getAllCuriosities() {
        return curiosityRepository.findAll();
    }

    public Optional<Curiosity> getCuriosityById(Integer id) {
        return curiosityRepository.findById(id);
    }

    public void deleteCuriosityById(Integer id) {
        curiosityRepository.deleteById(id);
    }

    public int updateCuriosity(Curiosity newCuriosity) {
        Curiosity oldCuriosity = curiosityRepository.findById(newCuriosity.getCuriosityId())
                .orElse(null);
        if (oldCuriosity != null) {
            oldCuriosity.setTitle(newCuriosity.getTitle());
            oldCuriosity.setCuriosity(newCuriosity.getCuriosity());
            curiosityRepository.save(oldCuriosity);
            return 1;
        }
        return - 1;
    }

}
