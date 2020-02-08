package com.backend.timeforpizza.timeforpizzabackend.service;

import com.backend.timeforpizza.timeforpizzabackend.payload.CuriosityRequest;
import com.backend.timeforpizza.timeforpizzabackend.payload.CuriosityResponse;
import com.backend.timeforpizza.timeforpizzabackend.repository.CuriosityRepository;
import com.backend.timeforpizza.timeforpizzabackend.model.Curiosity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CuriosityService {
    private final CuriosityRepository curiosityRepository;

    @Autowired
    public CuriosityService(@Qualifier("curiosityRepository") CuriosityRepository curiosityRepository) {
        this.curiosityRepository = curiosityRepository;
    }

    public int addCuriosity(CuriosityRequest curiosityToAdd) {
        Curiosity curiosity = new Curiosity();
        curiosity.setCuriosity(curiosityToAdd.getCuriosity());
        curiosity.setTitle(curiosityToAdd.getTitle());

        return curiosityRepository.save(curiosity) != null ? 1 : - 1;
    }

    public List<CuriosityResponse> getAllCuriosities() {
        List<Curiosity> curiosities = curiosityRepository.findAll();

        return curiosities.stream()
                .map(curiosity -> new CuriosityResponse(curiosity.getCuriosityId(), curiosity.getTitle(), curiosity.getCuriosity()) )
                .collect(Collectors.toList());
    }

    public CuriosityResponse getCuriosityById(Integer id) {
        return  curiosityRepository.findById(id)
                .map(c -> new CuriosityResponse(c.getCuriosityId(), c.getTitle(), c.getCuriosity()))
                .orElse(null);
    }

    public void deleteCuriosityById(Integer id) {
        curiosityRepository.deleteById(id);
    }

    public int updateCuriosity(Integer id, CuriosityRequest newCuriosity) {
        Curiosity oldCuriosity = curiosityRepository.findById(id)
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
