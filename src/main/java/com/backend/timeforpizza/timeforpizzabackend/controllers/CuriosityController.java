package com.backend.timeforpizza.timeforpizzabackend.controllers;

import com.backend.timeforpizza.timeforpizzabackend.model.Curiosity;
import com.backend.timeforpizza.timeforpizzabackend.service.CuriosityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(path = "/curiosities")
public class CuriosityController {
    private final CuriosityService curiosityService;

    @Autowired
    public CuriosityController(CuriosityService curiosityService) {
        this.curiosityService = curiosityService;
    }

    @PostMapping
    public boolean addPerson(@Valid @NotNull @RequestBody Curiosity curiosity) {
        curiosityService.addCuriosity(curiosity);
        return true;
    }

    @GetMapping
    public Iterable<Curiosity> getAllCuriosities() {
        return curiosityService.getAllCuriosities();
    }

    @GetMapping(path = "{id}")
    public Curiosity getCuriosityById(@PathVariable("id") Integer id) {
        return curiosityService.getCuriosityById(id)
                .orElse(null);
    }

    @DeleteMapping(path = "{id}")
    public void deleteCuriosityById(@PathVariable("id") Integer id) {
        curiosityService.deleteCuriosityById(id);
    }

    @PutMapping
    public void updateCuriosity(@Valid @NotNull @RequestBody Curiosity newCuriosity) {
        curiosityService.updateCuriosity(newCuriosity);
    }

}
