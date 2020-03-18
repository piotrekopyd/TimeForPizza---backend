package com.backend.timeforpizza.timeforpizzabackend.controller;

import com.backend.timeforpizza.timeforpizzabackend.payload.CuriosityRequest;
import com.backend.timeforpizza.timeforpizzabackend.payload.CuriosityResponse;
import com.backend.timeforpizza.timeforpizzabackend.service.CuriosityService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PatchMapping(path = "{id}")
    public CuriosityResponse updateCuriosity(@Valid @NotNull @RequestBody CuriosityRequest newCuriosity, @PathVariable Long id) {
        return curiosityService.updateCuriosity(id, newCuriosity);
    }

    @DeleteMapping(path = "{id}")
    public void deleteCuriosityById(@PathVariable("id") Long id) {
        curiosityService.deleteCuriosityById(id);
    }
}
