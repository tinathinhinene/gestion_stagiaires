package com.gestion.controller;

import java.util.List;
import org.springframework.web.bind.annotation.*;

import com.gestion.entity.Projet;
import com.gestion.service.ProjetService;

@RestController
@RequestMapping("/api/projets")
@CrossOrigin(origins = "http://localhost:5173")

public class ProjetRestController {

    private final ProjetService service;

    public ProjetRestController(ProjetService service) {
        this.service = service;
    }

    @GetMapping
    public List<Projet> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Projet getById(@PathVariable Integer id) {
        return service.getById(id);
    }

    @PostMapping("/create")
    public Projet create(@RequestBody Projet p) {
        return service.save(p);
    }
    
    @PostMapping("/bulk")
    public List<Projet> createAll(@RequestBody List<Projet> projets) {
        return service.saveAll(projets);
    }
    
    @PutMapping("/{id}")
    public Projet update(@PathVariable Integer id, @RequestBody Projet p) {
        p.setId(id);
        return service.save(p);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}
