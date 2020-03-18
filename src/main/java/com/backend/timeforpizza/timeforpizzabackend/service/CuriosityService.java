package com.backend.timeforpizza.timeforpizzabackend.service;

import com.backend.timeforpizza.timeforpizzabackend.exception.ResourceNotFoundException;
import com.backend.timeforpizza.timeforpizzabackend.payload.CuriosityRequest;
import com.backend.timeforpizza.timeforpizzabackend.payload.CuriosityResponse;
import com.backend.timeforpizza.timeforpizzabackend.repository.CuriosityRepository;
import com.backend.timeforpizza.timeforpizzabackend.model.Curiosity;
import com.backend.timeforpizza.timeforpizzabackend.utils.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CuriosityService {
    private final CuriosityRepository curiosityRepository;

    @Autowired
    public CuriosityService(CuriosityRepository curiosityRepository) {
        this.curiosityRepository = curiosityRepository;
    }

    public CuriosityResponse addCuriosity(CuriosityRequest curiosityRequest) {
        Curiosity curiosity = new Curiosity();
        curiosity.setCuriosity(curiosityRequest.getCuriosity());
        curiosity.setTitle(curiosityRequest.getTitle());

        return ModelMapper.mapCuriosityToCuriosityResponse(curiosityRepository.save(curiosity));
    }

    Curiosity addCuriosity(Curiosity curiosity) {
        return curiosityRepository.save(curiosity);
    }

    public List<CuriosityResponse> getAllCuriosities() {
        List<Curiosity> curiosities = curiosityRepository.findAll();

        return curiosities.stream()
                .map(curiosity -> new CuriosityResponse(curiosity.getCuriosityId(), curiosity.getTitle(), curiosity.getCuriosity()) )
                .collect(Collectors.toList());
    }

    public CuriosityResponse getCuriosityById(Long id) {
        return  curiosityRepository.findById(id)
                .map(c -> new CuriosityResponse(c.getCuriosityId(), c.getTitle(), c.getCuriosity()))
                .orElseThrow( () -> new ResourceNotFoundException("Curiosity", "curiosityId", id));
    }

    Curiosity getCuriosity(Long curiosityId) {
        return  curiosityRepository.findById(curiosityId)
                .orElseThrow( () -> new ResourceNotFoundException("Curiosity", "curiosityId", curiosityId));
    }

    public void deleteCuriosityById(Long id) {
        curiosityRepository.deleteById(id);
    }

    public CuriosityResponse updateCuriosity(Long id, CuriosityRequest newCuriosity) {
        Curiosity oldCuriosity = getCuriosity(id);
        oldCuriosity.setTitle(newCuriosity.getTitle());
        oldCuriosity.setCuriosity(newCuriosity.getCuriosity());
        return ModelMapper.mapCuriosityToCuriosityResponse(curiosityRepository.save(oldCuriosity));
    }

}
