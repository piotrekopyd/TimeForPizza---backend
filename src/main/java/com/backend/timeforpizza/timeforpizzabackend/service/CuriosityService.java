package com.backend.timeforpizza.timeforpizzabackend.service;

import com.backend.timeforpizza.timeforpizzabackend.exception.ResourceNotFoundException;
import com.backend.timeforpizza.timeforpizzabackend.dto.CuriosityRequestDTO;
import com.backend.timeforpizza.timeforpizzabackend.dto.CuriosityResponseDTO;
import com.backend.timeforpizza.timeforpizzabackend.repository.CuriosityRepository;
import com.backend.timeforpizza.timeforpizzabackend.model.Curiosity;
import com.backend.timeforpizza.timeforpizzabackend.util.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CuriosityService {

    private final CuriosityRepository curiosityRepository;

    private static final Logger logger = LoggerFactory.getLogger(CuriosityService.class);

    @Autowired
    public CuriosityService(CuriosityRepository curiosityRepository) {
        this.curiosityRepository = curiosityRepository;
    }

    public CuriosityResponseDTO addCuriosity(CuriosityRequestDTO curiosityRequestDTO) {
        Curiosity curiosity = ModelMapper.mapToCuriosity(curiosityRequestDTO);
        curiosity = curiosityRepository.save(curiosity);
        logger.info("Added curiosity with id: {}.", curiosity.getCuriosityId());
        return ModelMapper.mapToCuriosityResponse(curiosity);
    }

    public List<CuriosityResponseDTO> getAllCuriosities() {
        List<Curiosity> curiosities = curiosityRepository.findAll();

        return curiosities.stream()
                .map(ModelMapper::mapToCuriosityResponse)
                .collect(Collectors.toList());
    }

    public CuriosityResponseDTO getCuriosityById(Long id) {
        return  curiosityRepository.findById(id)
                .map(ModelMapper::mapToCuriosityResponse)
                .orElseThrow( () -> new ResourceNotFoundException("Curiosity", "curiosityId", id));
    }

    public void deleteCuriosityById(Long id) {
        curiosityRepository.deleteById(id);
        logger.info("Deleted curiosity with id: {}.", id);
    }

    public CuriosityResponseDTO updateCuriosity(Long id, CuriosityRequestDTO newCuriosity) {
        Curiosity oldCuriosity =  curiosityRepository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException("Curiosity", "curiosityId", id));

        oldCuriosity.setTitle(newCuriosity.getTitle());
        oldCuriosity.setCuriosity(newCuriosity.getCuriosity());
        oldCuriosity = curiosityRepository.save(oldCuriosity);
        logger.info("Updated curiosity with id: {}.", id);
        return ModelMapper.mapToCuriosityResponse(oldCuriosity);
    }

}
