package com.backend.timeforpizza.timeforpizzabackend.controller;

import com.backend.timeforpizza.timeforpizzabackend.dto.CuriosityRequestDTO;
import com.backend.timeforpizza.timeforpizzabackend.dto.CuriosityResponseDTO;
import com.backend.timeforpizza.timeforpizzabackend.service.CuriosityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping(path = "/curiosities")
public class CuriosityController {

    private final CuriosityService curiosityService;

    @Autowired
    public CuriosityController(CuriosityService curiosityService) {
        this.curiosityService = curiosityService;
    }

    @PostMapping
    public ResponseEntity<?> addCuriosity(@Valid @NotNull @RequestBody CuriosityRequestDTO curiosity) {
        return new ResponseEntity<>(curiosityService.addCuriosity(curiosity), HttpStatus.CREATED);
    }

    @GetMapping
    public List<CuriosityResponseDTO> getAllCuriosities() {
        return curiosityService.getAllCuriosities();
    }

    @GetMapping(path = "/{id}")
    public CuriosityResponseDTO getCuriosityById(@PathVariable("id") Long id) {
        return curiosityService.getCuriosityById(id);
    }

    @PatchMapping(path = "/{id}")
    public CuriosityResponseDTO updateCuriosity(@Valid @NotNull @RequestBody CuriosityRequestDTO newCuriosity, @PathVariable Long id) {
        return curiosityService.updateCuriosity(id, newCuriosity);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteCuriosityById(@PathVariable("id") Long id) {
        curiosityService.deleteCuriosityById(id);
    }
}
