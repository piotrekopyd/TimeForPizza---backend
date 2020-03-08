package com.backend.timeforpizza.timeforpizzabackend.controllers;

import com.backend.timeforpizza.timeforpizzabackend.payload.CuriosityRequest;
import com.backend.timeforpizza.timeforpizzabackend.payload.CuriosityResponse;
import com.backend.timeforpizza.timeforpizzabackend.service.CuriosityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
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
    public CuriosityResponse addCuriosity(@Valid @NotNull @RequestBody CuriosityRequest curiosity) {
       return curiosityService.addCuriosity(curiosity);
    }

    @GetMapping
    public List<CuriosityResponse> getAllCuriosities() {
        return curiosityService.getAllCuriosities();
    }

    @GetMapping(path = "{id}")
    public CuriosityResponse getCuriosityById(@PathVariable("id") Long id) {
        return curiosityService.getCuriosityById(id);
    }

    @DeleteMapping(path = "{id}")
    public void deleteCuriosityById(@PathVariable("id") Long id) {
        curiosityService.deleteCuriosityById(id);
    }

    @PutMapping(path = "{id}")
    public void updateCuriosity(@Valid @NotNull @RequestBody CuriosityRequest newCuriosity, @PathVariable Long id) {
        curiosityService.updateCuriosity(id, newCuriosity);
    }

}
