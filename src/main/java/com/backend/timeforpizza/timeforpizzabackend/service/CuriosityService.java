package com.backend.timeforpizza.timeforpizzabackend.service;

import com.backend.timeforpizza.timeforpizzabackend.exception.ResourceNotFoundException;
import com.backend.timeforpizza.timeforpizzabackend.dto.CuriosityRequestDTO;
import com.backend.timeforpizza.timeforpizzabackend.dto.CuriosityResponseDTO;
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

    public CuriosityResponseDTO addCuriosity(CuriosityRequestDTO curiosityRequestDTO) {
        Curiosity curiosity = new Curiosity();
        curiosity.setCuriosity(curiosityRequestDTO.getCuriosity());
        curiosity.setTitle(curiosityRequestDTO.getTitle());

        return ModelMapper.mapCuriosityToCuriosityResponse(curiosityRepository.save(curiosity));
    }

    public List<CuriosityResponseDTO> getAllCuriosities() {
        List<Curiosity> curiosities = curiosityRepository.findAll();

        return curiosities.stream()
                .map(ModelMapper::mapCuriosityToCuriosityResponse)
                .collect(Collectors.toList());
    }

    public CuriosityResponseDTO getCuriosityById(Long id) {
        return  curiosityRepository.findById(id)
                .map(ModelMapper::mapCuriosityToCuriosityResponse)
                .orElseThrow( () -> new ResourceNotFoundException("Curiosity", "curiosityId", id));
    }

    Curiosity getCuriosity(Long curiosityId) {
        return  curiosityRepository.findById(curiosityId)
                .orElseThrow( () -> new ResourceNotFoundException("Curiosity", "curiosityId", curiosityId));
    }

    public void deleteCuriosityById(Long id) {
        curiosityRepository.deleteById(id);
    }

    public CuriosityResponseDTO updateCuriosity(Long id, CuriosityRequestDTO newCuriosity) {
        Curiosity oldCuriosity = getCuriosity(id);
        oldCuriosity.setTitle(newCuriosity.getTitle());
        oldCuriosity.setCuriosity(newCuriosity.getCuriosity());
        return ModelMapper.mapCuriosityToCuriosityResponse(curiosityRepository.save(oldCuriosity));
    }

}
