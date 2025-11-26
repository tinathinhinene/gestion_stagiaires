package com.gestion.controller;

import com.gestion.dto.StagiaireInput;
import com.gestion.entity.Stagiaire;
import com.gestion.service.StagiaireService;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/stagiaires")
@CrossOrigin(origins = "http://localhost:5173")
public class StagiaireRestController {

    private final StagiaireService service;

    public StagiaireRestController(StagiaireService service) {
        this.service = service;
    }

    @GetMapping
    public List<Stagiaire> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Stagiaire getById(@PathVariable Integer id) {
        return service.getById(id);
    }

    @PostMapping("/create")
    public Stagiaire create(@RequestBody StagiaireInput dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public Stagiaire update(@PathVariable Integer id, @RequestBody StagiaireInput dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}
