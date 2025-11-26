package com.gestion.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.gestion.entity.Classe;
import com.gestion.service.ClasseService;

@RestController
@RequestMapping("/api/classes")
@CrossOrigin(origins = "http://localhost:5173")
public class ClasseRestController {

    private final ClasseService service;

    public ClasseRestController(ClasseService service) {
        this.service = service;
    }

    @GetMapping
    public List<Classe> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Classe getById(@PathVariable Integer id) {
        return service.getById(id);
    }

    @PostMapping("/create")
    public Classe create(@RequestBody Classe classe) {
        return service.create(classe);
    }

    @PutMapping("/{id}")
    public Classe update(@PathVariable Integer id, @RequestBody Classe classe) {
        return service.update(id, classe);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}
